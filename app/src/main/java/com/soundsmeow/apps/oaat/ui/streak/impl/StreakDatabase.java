package com.soundsmeow.apps.oaat.ui.streak.impl;

import android.content.Context;

import com.soundsmeow.apps.oaat.ui.streak.Streak;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Streak.class}, version = 4)
public abstract class StreakDatabase extends RoomDatabase {

    private static final String DB_NAME = "streak";

    public abstract StreakDao streakDao();

    public static StreakDatabase sInstance;

    // Get a database instance
    public static synchronized StreakDatabase getDatabaseInstance(Context context) {
        if (sInstance == null) {
            sInstance = create(context);
        }
        return sInstance;
    }

    // Create the database
    static StreakDatabase create(Context context) {
        RoomDatabase.Builder<StreakDatabase> builder = Room
                .databaseBuilder(context.getApplicationContext(), StreakDatabase.class, DB_NAME);
        return builder.build();
    }
}