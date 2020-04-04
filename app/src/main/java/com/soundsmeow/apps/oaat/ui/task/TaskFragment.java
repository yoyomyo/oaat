package com.soundsmeow.apps.oaat.ui.task;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soundsmeow.apps.oaat.R;

import java.util.List;

public class TaskFragment extends Fragment {

    private static final String TAG = TaskFragment.class.getSimpleName();
    private TaskViewModel taskViewModel;
    private RecyclerView taskList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);
        progressBar = root.findViewById(R.id.progress_bar);

        taskList = root.findViewById(R.id.task_list);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), null);
        taskList.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskList.setAdapter(recyclerViewAdapter);
        Observer<List<Task>> taskListObserver = new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                progressBar.setVisibility(View.GONE);
                if (recyclerViewAdapter.getTaskList() == null) {
                    recyclerViewAdapter.setTaskList(tasks);
                    recyclerViewAdapter.notifyDataSetChanged();
                } else if (tasks != null) {
                    // A list already exists
                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                            new TasksDiffCallback(recyclerViewAdapter.getTaskList(), tasks));
                    recyclerViewAdapter.setTaskList(tasks);
                    result.dispatchUpdatesTo(recyclerViewAdapter);
                }
            }
        };
        taskViewModel =
                ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getTasksLiveData().observe(this, taskListObserver);
        recyclerViewAdapter.setListener(taskViewModel);

        View fab = root.findViewById(R.id.add_task_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(taskViewModel);
            }
        });

        taskViewModel.addValueEventListener();
        return root;
    }

    @Override
    public void onDestroy() {
        taskViewModel.removeValueEventListener();
        super.onDestroy();
    }

    void showDialog(NewTaskDialog.AddNewTaskListener listener) {
        DialogFragment newFragment = NewTaskDialog.newInstance(listener);
        newFragment.show(getFragmentManager(), NewTaskDialog.DIALOG_TAG);
    }
}