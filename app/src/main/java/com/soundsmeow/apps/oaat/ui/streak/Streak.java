package com.soundsmeow.apps.oaat.ui.streak;

import com.google.firebase.database.DataSnapshot;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Streak {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "uid")
    private String uid;

    @ColumnInfo(name = "detail")
    private String detail;
    @ColumnInfo(name = "count")
    private int count;

    @ColumnInfo(name = "created_time")
    private long createdTime;
    @ColumnInfo(name = "finished_time")
    private long finishedTime;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUid(String id) {
        this.uid = id;
    }

    public String getUid() {
        return uid;
    }

    public void setDetail(String d) {
        detail = d;
    }

    public void setCount(int c) {
        count = c;
    }

    public void setCreatedTime(long d) {
        createdTime = d;
    }

    public void setFinishedTime(long d) {
        finishedTime = d;
    }

    public String getDetail() {
        return detail;
    }

    public int getCount() {
        return count;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public long getFinishedTime() {
        return finishedTime;
    }

    public static Streak fromSnapshot(DataSnapshot snapshot) {
        Streak streak = new Streak();

        String detail = snapshot.child("detail").getValue(String.class);
        int count = snapshot.child("count").getValue(Integer.class);
        long createdTime = snapshot.child("createdTime").getValue(Long.class);
        long finishedTime = snapshot.child("finishedTime").getValue(Long.class);
        streak.setUid(snapshot.child("uid").getValue(String.class));
        streak.setDetail(detail);
        streak.setCount(count);
        streak.setCreatedTime((long)createdTime);
        streak.setFinishedTime((long)finishedTime);
        return streak;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Streak) {
            Streak t = (Streak) obj;
            return t.getDetail() != null
                    && detail.equals(t.getDetail())
                    && count == t.getCount()
                    && finishedTime == t.finishedTime;
        }
        return false;
    }
}
