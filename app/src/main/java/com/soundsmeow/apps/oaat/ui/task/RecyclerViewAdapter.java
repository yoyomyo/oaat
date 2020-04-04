package com.soundsmeow.apps.oaat.ui.task;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.soundsmeow.apps.oaat.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface UpdateTaskListener {
        void updateTask(int taskPosition, boolean isDone);
    }

    private UpdateTaskListener listener;
    private Activity context;
    private List<Task> taskList;

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> tasks) {
        taskList = tasks;
    }

    public void setListener(UpdateTaskListener listener) {
        this.listener = listener;
    }


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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Task task = taskList.get(position);
        final TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
        taskViewHolder.detail.setText(task.getDetail());
        taskViewHolder.isDone.setChecked(task.getIsDone());
        updateTaskViewHolder(taskViewHolder.detail, task.getIsDone());
        taskViewHolder.isDone.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTaskViewHolder(taskViewHolder.detail, isChecked);
                taskList.set(position, task);
                listener.updateTask(position, isChecked);
            }
        });
    }

    private void updateTaskViewHolder(TextView detail, boolean isDone) {
        if (isDone) {
            detail.setPaintFlags(detail.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            detail.setPaintFlags(0);
        }
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
        CheckBox isDone;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            detail = itemView.findViewById(R.id.task_detail);
            isDone = itemView.findViewById(R.id.is_task_done);
        }
    }
}
