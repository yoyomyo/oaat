package com.soundsmeow.apps.oaat.ui.task;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.soundsmeow.apps.oaat.ui.task.impl.TaskDao;
import com.soundsmeow.apps.oaat.ui.task.impl.TaskDataSourceImpl;
import com.soundsmeow.apps.oaat.ui.task.impl.TaskDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;


public class TaskViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final Application application;

    public TaskViewModelFactory(@NonNull Application application, DatabaseReference dbRef) {
        super(application);
        this.application = application;
    }

    @Override
    public TaskViewModel create(Class modelClass) {
        TaskDatabase taskDatabase = TaskDatabase.getDatabaseInstance(application);
        TaskDataSourceImpl taskDataSourceImpl = new TaskDataSourceImpl(taskDatabase.taskDao());
        return new TaskViewModel(application, taskDataSourceImpl);
    }
}
