package com.soundsmeow.apps.oaat.ui.buddies;

import com.google.firebase.database.DataSnapshot;
import com.soundsmeow.apps.oaat.ui.streak.Streak;

public class Buddy {

    private String userName;
    private String photoUrl;

    private Buddy() {
    }

    public Buddy(String userName, String photoUrl) {
        this.userName = userName;
        this.photoUrl = photoUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public static Buddy fromSnapshot(DataSnapshot snapshot) {
        Buddy buddy = new Buddy();

        String name = snapshot.child("user_name").getValue(String.class);
        String photo = snapshot.child("photo_url").getValue(String.class);
        buddy.setUserName(name);
        buddy.setPhotoUrl(photo);
        return buddy;
    }

}
