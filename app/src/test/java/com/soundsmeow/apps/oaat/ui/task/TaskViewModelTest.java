package com.soundsmeow.apps.oaat.ui.task;

import com.google.firebase.database.DatabaseReference;
import com.soundsmeow.apps.oaat.ui.task.TaskViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TaskViewModelTest {

    private static final String TASK_DETAIl = "Make coookies";
    private TaskViewModel taskViewModel;
    @Mock DatabaseReference mockDatabaseReference;

    @Before
    public void setUp() {
        mockDatabaseReference = Mockito.mock(DatabaseReference.class);
        Mockito.when(mockDatabaseReference.push()).thenReturn(mockDatabaseReference);
        taskViewModel = new TaskViewModel(mockDatabaseReference);
    }

    @Test
    public void testAddTask() {
        taskViewModel.addTask(TASK_DETAIl);
        assertNotNull(taskViewModel.mTasks);
        assertEquals(1, taskViewModel.mTasks.size());
        Task t = taskViewModel.mTasks.get(0);
        Mockito.verify(mockDatabaseReference).push();
        Mockito.verify(mockDatabaseReference).setValue(t);
    }



}
