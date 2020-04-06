package com.soundsmeow.apps.oaat.ui.task;

import com.soundsmeow.apps.oaat.ui.task.Task;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TaskTest {
    private static final String KEY = "abc";
    private static final String DETAIL = "Make coookies";
    private static final long CREATED_TIME = 1234567;
    private static final long FINISHED_TIME = 7654321;
    private Task task1;
    private Task task2;

    @Before
    public void setUp() {
        task1 = new Task();
        task1.setKey(KEY);
        task1.setDetail(DETAIL);
        task1.setIsDone(false);
        task1.setPriority(0);
        task1.setCreatedTime(CREATED_TIME);
        task1.setFinishedTime(FINISHED_TIME);

        task2 = new Task();
        task2.setKey(KEY);
        task2.setDetail(DETAIL);
        task2.setIsDone(false);
        task2.setPriority(0);
        task2.setCreatedTime(CREATED_TIME);
        task2.setFinishedTime(FINISHED_TIME);
    }

    @Test
    public void testEquals() {
        assertEquals(task1, task2);
    }

    @Test
    public void testEquals_withDifferentKey() {
        task2.setKey("bca");
        assertEquals(task1, task2);
    }

    @Test
    public void testEquals_withDifferentPriority() {
        task2.setPriority(1);
        assertEquals(task1, task2);
    }

    @Test
    public void testEquals_withDifferentFinishedTime() {
        task2.setFinishedTime(0);
        assertEquals(task1, task2);
    }

    @Test
    public void testEquals_withDifferentCreatedTime() {
        task2.setCreatedTime(123456);
        assertNotEquals(task1, task2);
    }

    @Test
    public void testEquals_withNullDetails() {
        task2.setDetail(null);
        assertNotEquals(task1, task2);

        task1.setDetail(null);
        assertNotEquals(task1, task2);
    }
}
