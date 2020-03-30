package com.soundsmeow.apps.oaat.ui.task;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskViewModel extends ViewModel {

    private MutableLiveData<List<Task>> mTasksLiveData;
    private List<Task>  mTasks;

    public TaskViewModel() {
        mTasksLiveData = new MutableLiveData<>();
        mTasks = new LinkedList<>();
        init();
    }

    public LiveData<List<Task>> getTasksLiveData() {
        return mTasksLiveData;
    }

    public void addTask(String taskDetail) {
        Task task = new Task();
        task.setDetails(taskDetail);
        task.setIsDone(false);
        task.setCreated(new Date());
        task.setPriority(mTasks.size());

        mTasks.add(task);
    }

    private void init() {
        populateList();
        mTasksLiveData.setValue(mTasks);
    }

    private void populateList() {
        Task task1 = new Task();
        task1.setDetails("Read an article");
        task1.setIsDone(false);
        mTasks.add(task1);

        Task task2 = new Task();
        task2.setDetails("Go for a walk in Rose Garden");
        task2.setIsDone(true);
        mTasks.add(task2);
    }

}