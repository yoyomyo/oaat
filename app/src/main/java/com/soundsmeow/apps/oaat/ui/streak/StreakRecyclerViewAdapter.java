package com.soundsmeow.apps.oaat.ui.streak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.soundsmeow.apps.oaat.R;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StreakRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LEVEL_MAX = 10000;

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

        if (daysElapsed(new Date(streak.getFinishedTime())).equals("today") && streak.getCount() > 0) {
            streakViewHolder.rootView.setOnClickListener(v ->
                    Toast.makeText(context, R.string.already_seen, Toast.LENGTH_SHORT).show());
            streakViewHolder.rootView.setOnLongClickListener(null);
        } else {
            streakViewHolder.rootView.setOnLongClickListener(
                    v -> {
                        streakViewHolder.animate(streak, position);
                        return true;
                    });
        }
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
        LayerDrawable background;
        ClipDrawable longPressProgress;

        public StreakViewHolder(@NonNull View itemView, int pos) {
            super(itemView);
            rootView = itemView.findViewById(R.id.streak);
            detail = itemView.findViewById(R.id.streak_detail);
            count = itemView.findViewById(R.id.streak_count);
            timestamp = itemView.findViewById(R.id.last_update_time);
            background = (LayerDrawable) rootView.getBackground();
            longPressProgress = (ClipDrawable) background.getDrawable(0);
            longPressProgress.setAlpha(30);
        }

        private void animate(Streak streak, int position) {
            ValueAnimator animator = ValueAnimator.ofInt(0, LEVEL_MAX);
            animator.setDuration(1000);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addUpdateListener(animation -> {
                longPressProgress.setLevel((int) animator.getAnimatedValue());
            });
            animator.addListener(
                    new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            longPressProgress.setLevel(0);

                            streakList.set(position, streak);
                            listener.updateStreak(streakList.get(position))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> Log.d("DEBUG", "successfully updated task"));
                        }
                    }
            );
            animator.start();
        }
    }
}
