package com.soundsmeow.apps.oaat.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        taskViewModel =
                ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getTasksLiveData().observe(this, taskListObserver);

        return root;
    }

    Observer<List<Task>> taskListObserver = new Observer<List<Task>>() {
        @Override
        public void onChanged(List<Task> tasks) {
            recyclerViewAdapter.taskList = tasks;
            recyclerViewAdapter.notifyDataSetChanged();
        }
    };
}