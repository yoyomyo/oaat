package com.soundsmeow.apps.oaat.ui.task;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;

public class TaskViewModel extends ViewModel implements ValueEventListener,
        NewTaskDialog.AddNewTaskListener{

    private static final String TAG = TaskViewModel.class.getSimpleName();
    private static final String TASKS_CHILD = "tasks";

    // Persist data using a data source, either Room or in the cloud or both
    private DatabaseReference mFirebaseDatabaseReference;

    private MutableLiveData<List<Task>> mTasksLiveData;
    private List<Task>  mTasks;

    public TaskViewModel() {
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(TASKS_CHILD);
        mTasksLiveData = new MutableLiveData<>();
        mTasks = new LinkedList<>();
    }

    public LiveData<List<Task>> getTasksLiveData() {
        return mTasksLiveData;
    }

    public void addTask(String taskDetail) {
        Date now = new Date();
        Task task = new Task();
        task.setDetail(taskDetail);
        task.setIsDone(false);
        task.setCreatedTime(now.getTime());
        task.setPriority(mTasks.size());

        mTasks.add(task);
        mFirebaseDatabaseReference.push().setValue(task);
    }

    public void addValueEventListener() {
        mFirebaseDatabaseReference.addValueEventListener(this);
    }

    public void removeValueEventListener() {
        mFirebaseDatabaseReference.removeEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mTasks = new LinkedList<>();
        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
            Task t = Task.fromSnapshot(snapshot);
            mTasks.add(t);
        }
        mTasksLiveData.setValue(mTasks);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.w(TAG, "database error", databaseError.toException());
    }

}