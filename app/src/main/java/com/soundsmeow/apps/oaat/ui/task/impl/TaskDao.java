package com.soundsmeow.apps.oaat.ui.task.impl;

import com.soundsmeow.apps.oaat.ui.task.Task;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TaskDao {
    // Select all from Task table and order by "complete by" date
    @Query("SELECT * FROM task ORDER By task.finished_time")
    Flowable<List<Task>> getAllTasks();

    // Select one task from Task table by id
    @Query("SELECT * FROM task WHERE task.id=:id")
    LiveData<Task> getTaskById(String id);

    @Insert
    Completable insertAll(Task... tasks);

    @Delete
    Completable delete(Task task);

    @Update
    Completable update(Task task);
}
