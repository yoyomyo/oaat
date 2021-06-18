package com.soundsmeow.apps.oaat.ui.streak.impl;

import com.soundsmeow.apps.oaat.ui.streak.Streak;
import com.soundsmeow.apps.oaat.ui.streak.StreakDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class StreakDataSourceImpl implements StreakDataSource {

    private StreakDao mTaskDao;

    public StreakDataSourceImpl(StreakDao taskDao) {
        mTaskDao = taskDao;
    }

    @Override
    public Flowable<List<Streak>> getAllTasks() {
        return mTaskDao.getAllTasks();
    }

    @Override
    public Completable insert(Streak streak) {
        return mTaskDao.insertAll(streak);
    }

    @Override
    public Completable update(Streak streak) {
        return mTaskDao.update(streak);
    }

    @Override
    public Completable deleteTask(Streak streak) {
        return mTaskDao.delete(streak);
    }
}
