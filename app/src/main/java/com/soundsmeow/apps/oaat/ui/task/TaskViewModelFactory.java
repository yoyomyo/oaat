package com.soundsmeow.apps.oaat.ui.task;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;


public class TaskViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final DatabaseReference firebaseDatabaseReference;

    public TaskViewModelFactory(@NonNull Application application, DatabaseReference dbRef) {
        super(application);
        this.firebaseDatabaseReference = dbRef;
    }

    @Override
    public TaskViewModel create(Class modelClass) {
        return new TaskViewModel(firebaseDatabaseReference);
    }
}
