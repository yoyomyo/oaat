package com.soundsmeow.apps.oaat.ui.task;

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


public class TaskViewModelTest {

    private static final String TASK_DETAIl = "Make coookies";
    private TaskViewModel taskViewModel;
    @Mock DatabaseReference mockDatabaseReference;
    @Mock Application application;

    @Mock TaskDataSource mockDataSource;

    @Before
    public void setUp() {
        mockDataSource = Mockito.mock(TaskDataSource.class);
        Mockito.when(mockDataSource.insert(any(Task.class))).thenReturn(Completable.complete());
        taskViewModel = new TaskViewModel(application, mockDataSource);
    }

    @Test
    public void testAddTask() {
        taskViewModel.addTask(TASK_DETAIl);
        assertNotNull(taskViewModel.mTasks);
        assertEquals(1, taskViewModel.mTasks.size());
        Task t = taskViewModel.mTasks.get(0);
        Mockito.verify(mockDataSource).insert(t);
    }

    @Test
    public void testUpdateTask() {

    }

    @Test
    public void testDeleteTask() {

    }



}
