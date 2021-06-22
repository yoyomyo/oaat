package com.soundsmeow.apps.oaat.ui.buddies;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuddiesViewModel extends ViewModel implements ValueEventListener{

    private DatabaseReference mFirebaseDatabaseReference;


    public BuddiesViewModel() {
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("buddies");
    }

    public void addValueEventListener() {
        mFirebaseDatabaseReference.addValueEventListener(this);
    }

    public void removeValueEventListener() {
        mFirebaseDatabaseReference.removeEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
    // TODO: Implement the ViewModel
}