package com.soundsmeow.apps.oaat.ui.streak.impl;

import com.soundsmeow.apps.oaat.ui.streak.Streak;
import com.soundsmeow.apps.oaat.ui.streak.StreakDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class StreakDataSourceImpl implements StreakDataSource {

    private StreakDao mStreakDao;

    public StreakDataSourceImpl(StreakDao taskDao) {
        mStreakDao = taskDao;
    }

    @Override
    public Flowable<List<Streak>> getAllTasks(String uid) {
        return mStreakDao.getAllTasks(uid);
    }

    @Override
    public Completable insert(Streak streak) {
        return mStreakDao.insertAll(streak);
    }

    @Override
    public Completable update(Streak streak) {
        return mStreakDao.update(streak);
    }

    @Override
    public Completable deleteTask(Streak streak) {
        return mStreakDao.delete(streak);
    }
}
