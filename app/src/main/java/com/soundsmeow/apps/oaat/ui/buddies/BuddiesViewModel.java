package com.soundsmeow.apps.oaat.ui.buddies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuddiesViewModel extends ViewModel {

    private FirebaseUser mUser;
    private DatabaseReference buddiesDBReference;
    private DatabaseReference userDetailsDBReference;
    public List<Buddy> buddies;


    public BuddiesViewModel() {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        buddiesDBReference = FirebaseDatabase.getInstance().getReference().child("buddies");
        userDetailsDBReference = FirebaseDatabase.getInstance().getReference().child("users");
        buddies = new ArrayList<>();
    }

    public void findBuddies() {
        buddiesDBReference.child(mUser.getUid()).get().addOnCompleteListener(buddyTask -> {
            for (DataSnapshot userId : buddyTask.getResult().getChildren()) {
                if (userId != null) {
                    String id = userId.getValue(String.class);
                    userDetailsDBReference.child(id).get().addOnCompleteListener(userDetail -> {
                        if (userDetail != null) {
                            Buddy b = Buddy.fromSnapshot(userDetail.getResult());
                            buddies.add(b);
                        }
                    });
                }
            }
        });
    }

    public Flowable<List<Buddy>> findBuddiesFlowable() {
        return Flowable.create(emitter -> buddiesDBReference.child(mUser.getUid()).get().addOnCompleteListener(buddyTask -> {
            for (DataSnapshot userId : buddyTask.getResult().getChildren()) {
                if (userId != null) {
                    String id = userId.getValue(String.class);
                    userDetailsDBReference.child(id).get().addOnCompleteListener(userDetail -> {
                        if (userDetail != null) {
                            Buddy b = Buddy.fromSnapshot(userDetail.getResult());
                            if (!buddies.contains(b)) {
                                buddies.add(b);
                            }
                        }
                        emitter.onNext(buddies);
                    });
                } else {
                    emitter.onError(new Exception("userId is null"));
                }
            }
        }), BackpressureStrategy.DROP);
    }


    public Flowable<Void> addBuddiesFlowable(String id) {
        Map<String, Object> newBuddy = new HashMap<>();
        DatabaseReference key = buddiesDBReference.child(mUser.getUid()).push();
        newBuddy.put(key.getKey(), id);

        return Flowable.create(emitter -> buddiesDBReference.child(mUser.getUid()).updateChildren(newBuddy, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    emitter.onError(new Exception("Data could not be saved " + error.getMessage()));
                } else {
                    ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            System.out.println("Data saved successfully.");
                        }
                    });

                }
            }
        }), BackpressureStrategy.DROP);
    }

}