package com.soundsmeow.apps.oaat.ui.streak;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StreakTest {
    private static final String KEY = "abc";
    private static final String DETAIL = "Make coookies";
    private static final long CREATED_TIME = 1234567;
    private static final long FINISHED_TIME = 7654321;
    private Streak streak1;
    private Streak streak2;

    @Before
    public void setUp() {
        streak1 = new Streak();
        streak1.setKey(KEY);
        streak1.setDetail(DETAIL);
        streak1.setIsDone(false);
        streak1.setPriority(0);
        streak1.setCreatedTime(CREATED_TIME);
        streak1.setFinishedTime(FINISHED_TIME);

        streak2 = new Streak();
        streak2.setKey(KEY);
        streak2.setDetail(DETAIL);
        streak2.setIsDone(false);
        streak2.setPriority(0);
        streak2.setCreatedTime(CREATED_TIME);
        streak2.setFinishedTime(FINISHED_TIME);
    }

    @Test
    public void testEquals() {
        assertEquals(streak1, streak2);
    }

    @Test
    public void testEquals_withDifferentKey() {
        streak2.setKey("bca");
        assertEquals(streak1, streak2);
    }

    @Test
    public void testEquals_withDifferentPriority() {
        streak2.setPriority(1);
        assertEquals(streak1, streak2);
    }

    @Test
    public void testEquals_withDifferentFinishedTime() {
        streak2.setFinishedTime(0);
        assertEquals(streak1, streak2);
    }

    @Test
    public void testEquals_withDifferentCreatedTime() {
        streak2.setCreatedTime(123456);
        assertNotEquals(streak1, streak2);
    }

    @Test
    public void testEquals_withNullDetails() {
        streak2.setDetail(null);
        assertNotEquals(streak1, streak2);

        streak1.setDetail(null);
        assertNotEquals(streak1, streak2);
    }
}
