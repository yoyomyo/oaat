package com.soundsmeow.apps.oaat.ui.streak;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class StreakViewModel extends ViewModel implements
        NewStreakDialog.AddNewStreakListener, StreakRecyclerViewAdapter.UpdateStreakListener {

    private static final String TAG = StreakViewModel.class.getSimpleName();
    protected static final String TASKS_CHILD = "tasks";

    private FirebaseUser mUser;
    private StreakDataSource mStreakDataSource;
    protected List<Streak> mStreaks;

    public StreakViewModel(Application application, StreakDataSource streakDataSource) {
        mStreakDataSource = streakDataSource;
        mStreaks = new ArrayList<>();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public Flowable<List<Streak>> getAllTasks() {
        return mStreakDataSource.getAllTasks(mUser.getUid());
    }

    public Completable addStreak(String taskDetail) {
        Date now = new Date();
        Streak streak = new Streak();
        streak.setUid(mUser.getUid());
        streak.setDetail(taskDetail);
        streak.setCount(0);
        streak.setCreatedTime(now.getTime());
        streak.setFinishedTime(now.getTime());
        mStreaks.add(streak);
        return mStreakDataSource.insert(streak);
    }

    public Completable updateStreak(Streak t) {
        t.setCount(t.getCount() + 1);
        Date now = new Date();
        t.setFinishedTime(now.getTime());
        return mStreakDataSource.update(t);
    }
}