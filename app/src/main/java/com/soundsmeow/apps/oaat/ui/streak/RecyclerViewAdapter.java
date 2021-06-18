package com.soundsmeow.apps.oaat.ui.streak;

import android.app.Activity;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.soundsmeow.apps.oaat.R;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface UpdateStreakListener {
        Completable updateTask(Streak streak, boolean isDone);
    }

    private UpdateStreakListener listener;
    private Activity context;
    private List<Streak> streakList;

    public List<Streak> getStreakList() {
        return streakList;
    }

    public void setStreakList(List<Streak> streaks) {
        streakList = streaks;
    }

    public void setListener(UpdateStreakListener listener) {
        this.listener = listener;
    }

    public RecyclerViewAdapter(Activity context, List<Streak> data) {
        this.context = context;
        this.streakList = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new StreakViewHolder(taskView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Streak streak = streakList.get(position);
        final StreakViewHolder taskViewHolder = (StreakViewHolder) holder;

        taskViewHolder.detail.setText(streak.getDetail());
        taskViewHolder.isDone.setChecked(streak.getIsDone());
        taskViewHolder.timestamp.setText(new Date(streak.getFinishedTime()).toString());
        updateStreakViewHolder(taskViewHolder.detail, streak.getIsDone());
        taskViewHolder.isDone.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateStreakViewHolder(taskViewHolder.detail, isChecked);
                streakList.set(position, streak);
                listener.updateTask(streakList.get(position), isChecked)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(()-> Log.d("DEBUG", "successfully updated task"));
            }
        });
    }

    private void updateStreakViewHolder(TextView detail, boolean isDone) {
        if (isDone) {
            detail.setPaintFlags(detail.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            detail.setPaintFlags(0);
        }
    }

    @Override
    public int getItemCount() {
        if (streakList != null) {
            return streakList.size();
        }
        return 0;
    }

    public class StreakViewHolder extends RecyclerView.ViewHolder {

        TextView detail;
        TextView timestamp;
        CheckBox isDone;

        public StreakViewHolder(@NonNull View itemView) {
            super(itemView);
            detail = itemView.findViewById(R.id.task_detail);
            isDone = itemView.findViewById(R.id.is_task_done);
            timestamp = itemView.findViewById(R.id.task_finished_time);
        }
    }
}
