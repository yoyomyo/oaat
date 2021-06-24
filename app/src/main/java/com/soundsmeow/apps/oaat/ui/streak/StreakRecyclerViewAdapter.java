package com.soundsmeow.apps.oaat.ui.streak;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soundsmeow.apps.oaat.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StreakRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface UpdateStreakListener {
        Completable updateStreak(Streak streak);
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

    public StreakRecyclerViewAdapter(Activity context, List<Streak> data) {
        this.context = context;
        this.streakList = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskView = LayoutInflater.from(context).inflate(R.layout.item_streak, parent, false);
        return new StreakViewHolder(taskView, -1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Streak streak = streakList.get(position);
        final StreakViewHolder streakViewHolder = (StreakViewHolder) holder;

        streakViewHolder.detail.setText(streak.getDetail());
        streakViewHolder.count.setText(streak.getCount()+"");
        streakViewHolder.timestamp.setText(context.getString(
                R.string.last_updated_time,
                daysElapsed(new Date(streak.getFinishedTime()))));
        streakViewHolder.rootView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        streakList.set(position, streak);
                        listener.updateStreak(streakList.get(position))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(()-> Log.d("DEBUG", "successfully updated task"));
                        return true;
                    }
                });

    }

    private String daysElapsed(Date lastTime) {
        Date now = new Date();
        int daysElapsed = now.getDate() - lastTime.getDate();
        if (daysElapsed <= 0) {
            return "today";
        } else if (daysElapsed == 1) {
            return "yesterday";
        } else {
            return daysElapsed + " days ago";
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

        View rootView;
        TextView detail;
        TextView timestamp;
        TextView count;

        public StreakViewHolder(@NonNull View itemView, int pos) {
            super(itemView);
            rootView = itemView.findViewById(R.id.streak);
            detail = itemView.findViewById(R.id.streak_detail);
            count = itemView.findViewById(R.id.streak_count);
            timestamp = itemView.findViewById(R.id.last_update_time);
        }
    }
}
