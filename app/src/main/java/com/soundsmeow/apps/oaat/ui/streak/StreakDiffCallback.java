package com.soundsmeow.apps.oaat.ui.streak;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class StreakDiffCallback extends DiffUtil.Callback {

    List<Streak> oldList;
    List<Streak> newList;


    public StreakDiffCallback(List<Streak> oldL, List<Streak> newL) {
        this.oldList = oldL;
        this.newList = newL;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getDetail().equals(newList.get(newItemPosition).getDetail());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
