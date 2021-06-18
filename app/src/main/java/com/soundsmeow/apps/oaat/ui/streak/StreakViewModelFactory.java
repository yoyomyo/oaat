package com.soundsmeow.apps.oaat.ui.streak;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.soundsmeow.apps.oaat.ui.streak.impl.StreakDataSourceImpl;
import com.soundsmeow.apps.oaat.ui.streak.impl.StreakDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;


public class StreakViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final Application application;

    public StreakViewModelFactory(@NonNull Application application, DatabaseReference dbRef) {
        super(application);
        this.application = application;
    }

    @Override
    public StreakViewModel create(Class modelClass) {
        StreakDatabase taskDatabase = StreakDatabase.getDatabaseInstance(application);
        StreakDataSourceImpl taskDataSourceImpl = new StreakDataSourceImpl(taskDatabase.streakDao());

        return new StreakViewModel(application, taskDataSourceImpl);
    }
}
