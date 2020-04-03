package com.soundsmeow.apps.oaat.ui.task;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.soundsmeow.apps.oaat.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Activity context;
    List<Task> taskList;

    public RecyclerViewAdapter(Activity context, List<Task> data) {
        this.context = context;
        this.taskList = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(taskView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Task task = taskList.get(position);
        TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
        taskViewHolder.detail.setText(task.getDetail());
        taskViewHolder.isDone.setChecked(task.getisDone());
    }

    @Override
    public int getItemCount() {
        if (taskList != null) {
            return taskList.size();
        }
        return 0;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView detail;
        RadioButton isDone;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            detail = itemView.findViewById(R.id.task_detail);
            isDone = itemView.findViewById(R.id.is_task_done);
        }
    }
}
