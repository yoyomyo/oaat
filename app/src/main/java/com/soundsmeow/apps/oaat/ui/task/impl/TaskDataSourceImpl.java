package com.soundsmeow.apps.oaat.ui.task.impl;

import android.app.VoiceInteractor;

import com.soundsmeow.apps.oaat.ui.task.Task;
import com.soundsmeow.apps.oaat.ui.task.TaskDataSource;

import java.util.List;
import java.util.Observable;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class TaskDataSourceImpl implements TaskDataSource {

    private TaskDao mTaskDao;

    public TaskDataSourceImpl(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    @Override
    public Flowable<List<Task>> getAllTasks() {
        return mTaskDao.getAllTasks();
    }

    @Override
    public Completable insert(Task task) {
        return mTaskDao.insertAll(task);
    }

    @Override
    public Completable update(Task task) {
        return mTaskDao.update(task);
    }

    @Override
    public Completable deleteTask(Task task) {
        return mTaskDao.delete(task);
    }
}
