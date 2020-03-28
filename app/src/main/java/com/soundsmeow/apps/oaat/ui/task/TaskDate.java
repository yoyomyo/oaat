package com.soundsmeow.apps.oaat.ui.task;

import java.util.Date;


public class TaskDate {
    private Date date;
    private String dayOfWeek;
    private String month;
    private String year;
    private String dayOfMonth;

    public TaskDate() {
        init();
    }

    private void init() {
        date = new Date();
    }

}
