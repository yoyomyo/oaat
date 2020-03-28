package com.soundsmeow.apps.oaat.ui.task;


import java.util.Date;

public class Task {
    private int priority;
    private String details;
    private boolean isDone;

    // For computing stats
    private Date created;
    private Date finished;

    public void setPriority(int p) {
        priority = p;
    }

    public void setDetails(String d) {
        details = d;
    }

    public void setIsDone(boolean b) {
        isDone = b;
    }

    public void setCreated(Date d) {
        created = d;
    }

    public void setFinished(Date d) {
        finished = d;
    }

    public int getPriority() {
        return priority;
    }

    public String getDetails() {
        return details;
    }

    public boolean getisDone() {
        return isDone;
    }

    public Date getCreatedTimestamp() {
        return created;
    }

    public Date getFinishedTimestamp() {
        return finished;
    }

}
