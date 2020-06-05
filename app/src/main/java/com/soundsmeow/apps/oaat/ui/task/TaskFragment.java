package com.soundsmeow.apps.oaat.ui.task;


import android.os.Bundle;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import com.google.firebase.database.FirebaseDatabase;
import com.soundsmeow.apps.oaat.R;

import java.util.List;

import static com.soundsmeow.apps.oaat.ui.task.TaskViewModel.TASKS_CHILD;

public class TaskFragment extends Fragment {

    private static final String TAG = TaskFragment.class.getSimpleName();
    private TaskViewModel taskViewModel;
    private RecyclerView taskList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);
        progressBar = root.findViewById(R.id.progress_bar);

        taskList = root.findViewById(R.id.task_list);

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), null);

        taskList.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskList.setAdapter(recyclerViewAdapter);

        final Observer<List<Task>> taskListObserver = tasks -> {
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
        };
        TaskViewModelFactory factory = new TaskViewModelFactory(this.getActivity().getApplication(),
                FirebaseDatabase.getInstance().getReference().child(TASKS_CHILD));
        taskViewModel =
                ViewModelProviders.of(this, factory).get(TaskViewModel.class);

        mDisposable.add(taskViewModel.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskList -> taskListObserver.onChanged(taskList)));

        recyclerViewAdapter.setListener(taskViewModel);

        View fab = root.findViewById(R.id.add_task_button);
        fab.setOnClickListener(v -> showDialog(taskViewModel));

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

    private void showDialog(NewTaskDialog.AddNewTaskListener listener) {
        DialogFragment newFragment = NewTaskDialog.newInstance(listener);
        if (getFragmentManager() != null) {
            newFragment.show(getFragmentManager(), NewTaskDialog.DIALOG_TAG);
        }
    }
}