package com.soundsmeow.apps.oaat.ui.task;

import android.app.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TaskViewModel extends ViewModel implements
        NewTaskDialog.AddNewTaskListener, RecyclerViewAdapter.UpdateTaskListener{

    private static final String TAG = TaskViewModel.class.getSimpleName();
    protected static final String TASKS_CHILD = "tasks";

    private TaskDataSource mTaskDataSource;
    protected List<Task>  mTasks;


    public TaskViewModel(Application application, TaskDataSource taskDataSource) {
        mTaskDataSource = taskDataSource;
        mTasks = new ArrayList<>();
    }

    public Flowable<List<Task>> getAllTasks() {
        return mTaskDataSource.getAllTasks();
    }

    public Completable addTask(String taskDetail) {
        Date now = new Date();
        Task task = new Task();
        task.setDetail(taskDetail);
        task.setIsDone(false);
        task.setCreatedTime(now.getTime());
        task.setPriority(mTasks.size());

        mTasks.add(task);
        return mTaskDataSource.insert(task);
    }

    public Completable updateTask(Task t, boolean isDone) {
        t.setIsDone(isDone);
        if (isDone) {
            Date now = new Date();
            t.setFinishedTime(now.getTime());
        } else {
            t.setFinishedTime(0);
        }
        return mTaskDataSource.update(t);
    }
}