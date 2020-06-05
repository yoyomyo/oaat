package com.soundsmeow.apps.oaat.ui.task.impl;

import android.content.Context;

import com.soundsmeow.apps.oaat.ui.task.Task;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
//@TypeConverters(DateConverter.class)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    public static TaskDatabase sInstance;

    // Get a database instance
    public static synchronized TaskDatabase getDatabaseInstance(Context context) {
        if (sInstance == null) {
            sInstance = create(context);
        }
        return sInstance;
    }

    // Create the database
    static TaskDatabase create(Context context) {
        RoomDatabase.Builder<TaskDatabase> builder = Room.databaseBuilder(context.getApplicationContext(),
                TaskDatabase.class,
                "task");
        return builder.build();
    }
}