package com.soundsmeow.apps.oaat.ui.buddies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soundsmeow.apps.oaat.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class BuddyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Buddy> buddyList;

    public void setData(List<Buddy> data) {
        this.buddyList = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buddy, parent, false);
        return new BuddyRecyclerViewAdapter.BuddyViewHolder(taskView, -1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Buddy buddy = buddyList.get(position);
        final BuddyViewHolder buddyViewHolder = (BuddyViewHolder) holder;
        buddyViewHolder.userName.setText(buddy.getUserName());
        Glide.with(buddyViewHolder.profile.getContext())
                .load(buddy.getPhotoUrl())
                .into(buddyViewHolder.profile);
        buddyViewHolder.userStatus.setText("placeholder");
    }

    @Override
    public int getItemCount() {
        return buddyList == null? 0 : buddyList.size();
    }

    public class BuddyViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        TextView userName;
        CircleImageView profile;
        TextView userStatus;

        public BuddyViewHolder(@NonNull View itemView, int pos) {
            super(itemView);
            rootView = itemView;
            userName = itemView.findViewById(R.id.buddy_name);
            profile = itemView.findViewById(R.id.buddy_profile);
            userStatus = itemView.findViewById(R.id.buddy_status);
        }
    }
}
