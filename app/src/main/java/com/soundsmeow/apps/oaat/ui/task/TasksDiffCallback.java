package com.soundsmeow.apps.oaat.ui.task;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class TasksDiffCallback extends DiffUtil.Callback {

    List<Task> oldList;
    List<Task> newList;


    public TasksDiffCallback(List<Task> oldL, List<Task> newL) {
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
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
