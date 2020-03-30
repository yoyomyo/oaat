package com.soundsmeow.apps.oaat.ui.task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soundsmeow.apps.oaat.R;

import java.util.LinkedList;
import java.util.List;

public class TaskFragment extends Fragment {

    private TaskViewModel taskViewModel;
    private RecyclerView taskList;
    private RecyclerViewAdapter recyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);

        taskList = root.findViewById(R.id.task_list);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), null);
        taskList.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskList.setAdapter(recyclerViewAdapter);
        Observer<List<Task>> taskListObserver = new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                recyclerViewAdapter.taskList = tasks;
                recyclerViewAdapter.notifyDataSetChanged();
            }
        };

        taskViewModel =
                ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getTasksLiveData().observe(this, taskListObserver);

        View fab = root.findViewById(R.id.add_task_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNewTask(getContext());
            }
        });

        return root;
    }

    private void enterNewTask(Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final EditText edittext = new EditText(context);
        dialogBuilder.setView(edittext);
        dialogBuilder.setPositiveButton(R.string.create_task, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String newTask = edittext.getText().toString();
                taskViewModel.addTask(newTask);
            }
        });
        dialogBuilder.show();
    }
    
}