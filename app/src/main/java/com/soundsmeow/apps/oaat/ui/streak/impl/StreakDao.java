package com.soundsmeow.apps.oaat.ui.streak.impl;

import com.soundsmeow.apps.oaat.ui.streak.Streak;

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
public interface StreakDao {
    // Select all from Task table and order by "complete by" date
    @Query("SELECT * FROM Streak ORDER By Streak.created_time")
    Flowable<List<Streak>> getAllTasks();

    // Select one task from Task table by id
    @Query("SELECT * FROM Streak WHERE Streak.id=:id")
    LiveData<Streak> getTaskById(String id);

    @Insert
    Completable insertAll(Streak... streaks);

    @Delete
    Completable delete(Streak streak);

    @Update
    Completable update(Streak streak);
}
