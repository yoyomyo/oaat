package com.soundsmeow.apps.oaat.ui.streak.impl;

import com.soundsmeow.apps.oaat.ui.streak.Streak;

import java.util.List;

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
    @Query("SELECT * FROM Streak WHERE Streak.uid = :uid ORDER By Streak.created_time")
    Flowable<List<Streak>> getAllTasks(String uid);

    @Insert
    Completable insertAll(Streak... streaks);

    @Delete
    Completable delete(Streak streak);

    @Update
    Completable update(Streak streak);
}
