package com.soundsmeow.apps.oaat.ui.streak;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface StreakDataSource {

    Flowable<List<Streak>> getAllTasks(String uid);

    /**
     * Inserts the task into the data source, or, if this is an existing task, updates it.
     *
     * @param streak the task to be inserted or updated.
     */
    Completable insert(Streak streak);

    Completable update(Streak streak);

    /**
     * Deletes all tasks from the data source.
     */
    Completable deleteTask(Streak streak);
}
