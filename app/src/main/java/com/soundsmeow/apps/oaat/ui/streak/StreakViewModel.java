package com.soundsmeow.apps.oaat.ui.streak;

import android.app.Application;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class StreakViewModel extends ViewModel implements
        NewStreakDialog.AddNewBuddyListener, RecyclerViewAdapter.UpdateStreakListener {

    private static final String TAG = StreakViewModel.class.getSimpleName();
    protected static final String TASKS_CHILD = "tasks";

    private FirebaseUser mUser;
    private StreakDataSource mStreakDataSource;
    protected List<Streak> mStreaks;


    public StreakViewModel(Application application, StreakDataSource streakDataSource) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth != null) {
            mUser = firebaseAuth.getCurrentUser();
        }
        mStreakDataSource = streakDataSource;
        mStreaks = new ArrayList<>();
    }

    public Flowable<List<Streak>> getAllTasks() {
        return mStreakDataSource.getAllTasks();
    }

    public Completable addStreak(String taskDetail) {
        Date now = new Date();
        Streak streak = new Streak();
        streak.setDetail(taskDetail);
        streak.setIsDone(false);
        streak.setCreatedTime(now.getTime());
        streak.setPriority(mStreaks.size());

        mStreaks.add(streak);
        return mStreakDataSource.insert(streak);
    }

    public Completable updateTask(Streak t, boolean isDone) {
        t.setIsDone(isDone);
        if (isDone) {
            Date now = new Date();
            t.setFinishedTime(now.getTime());
        } else {
            t.setFinishedTime(0);
        }
        return mStreakDataSource.update(t);
    }

    public Uri getUserAvatarUrl() {
        return mUser.getPhotoUrl();
    }
}