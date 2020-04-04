package com.soundsmeow.apps.oaat.ui.task;


import com.google.firebase.database.DataSnapshot;

public class Task {

    private String key;
    private int priority;
    private String detail;
    private boolean isDone;

    // For computing stats
    private long createdTime;
    private long finishedTime;

    public void setKey(String id) {
        this.key = id;
    }

    public String getKey() {
        return key;
    }

    public void setPriority(int p) {
        priority = p;
    }

    public void setDetail(String d) {
        detail = d;
    }

    public void setIsDone(boolean b) {
        isDone = b;
    }

    public void setCreatedTime(long d) {
        createdTime = d;
    }

    public void setFinishedTime(long d) {
        finishedTime = d;
    }

    public int getPriority() {
        return priority;
    }

    public String getDetail() {
        return detail;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public long getFinishedTime() {
        return finishedTime;
    }

    public static Task fromSnapshot(DataSnapshot snapshot) {
        Task task = new Task();

        String detail = snapshot.child("detail").getValue(String.class);
        boolean isDone = snapshot.child("isDone").getValue(Boolean.class);
        int priority = snapshot.child("priority").getValue(Integer.class);
        float createdTime = snapshot.child("createdTime").getValue(Float.class);
        float finishedTime = snapshot.child("finishedTime").getValue(Float.class);
        task.setKey(snapshot.getKey());
        task.setDetail(detail);
        task.setIsDone(isDone);
        task.setPriority(priority);
        return task;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Task) {
            Task t = (Task) obj;
            return t.getDetail() != null
                    && detail.equals(t.getDetail())
                    && isDone == t.isDone
                    && createdTime == t.createdTime
                    && finishedTime == t.finishedTime;
        }
        return false;
    }

}
