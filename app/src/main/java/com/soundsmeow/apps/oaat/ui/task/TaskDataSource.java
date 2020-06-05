package com.soundsmeow.apps.oaat.ui.task;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface TaskDataSource {

    Flowable<List<Task>> getAllTasks();

    /**
     * Inserts the task into the data source, or, if this is an existing task, updates it.
     *
     * @param task the task to be inserted or updated.
     */
    Completable insert(Task task);

    Completable update(Task task);

    /**
     * Deletes all tasks from the data source.
     */
    Completable deleteTask(Task task);
}
