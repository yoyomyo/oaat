package com.soundsmeow.apps.oaat.ui.streak;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.reactivex.Completable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;


public class StreakViewModelTest {

    private static final String TASK_DETAIl = "Make coookies";
    private StreakViewModel taskViewModel;
    @Mock DatabaseReference mockDatabaseReference;
    @Mock Application application;

    @Mock
    StreakDataSource mockDataSource;

    @Before
    public void setUp() {
        mockDataSource = Mockito.mock(StreakDataSource.class);
        Mockito.when(mockDataSource.insert(any(Streak.class))).thenReturn(Completable.complete());
        taskViewModel = new StreakViewModel(application, mockDataSource);
    }

    @Test
    public void testAddTask() {
        taskViewModel.addStreak(TASK_DETAIl);
        assertNotNull(taskViewModel.mStreaks);
        assertEquals(1, taskViewModel.mStreaks.size());
        Streak t = taskViewModel.mStreaks.get(0);
        Mockito.verify(mockDataSource).insert(t);
    }

    @Test
    public void testUpdateTask() {

    }

    @Test
    public void testDeleteTask() {

    }



}
