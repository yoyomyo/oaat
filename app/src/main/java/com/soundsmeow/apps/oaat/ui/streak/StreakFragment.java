package com.soundsmeow.apps.oaat.ui.streak;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import static com.soundsmeow.apps.oaat.ui.streak.StreakViewModel.TASKS_CHILD;

public class StreakFragment extends Fragment {

    private static final String TAG = StreakFragment.class.getSimpleName();
    private StreakViewModel streakViewModel;
    private RecyclerView buddyList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;
    private ImageView userAvatar;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buddies, container, false);
        progressBar = root.findViewById(R.id.progress_bar);

        buddyList = root.findViewById(R.id.buddy_list);
        userAvatar = root.findViewById(R.id.user_avatar);

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), null);

        buddyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        buddyList.setAdapter(recyclerViewAdapter);

        final Observer<List<Streak>> taskListObserver = streaks -> {
            progressBar.setVisibility(View.GONE);
            if (recyclerViewAdapter.getStreakList() == null) {
                recyclerViewAdapter.setStreakList(streaks);
                recyclerViewAdapter.notifyDataSetChanged();
            } else if (streaks != null) {
                // A list already exists
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                        new StreakDiffCallback(recyclerViewAdapter.getStreakList(), streaks));
                recyclerViewAdapter.setStreakList(streaks);
                result.dispatchUpdatesTo(recyclerViewAdapter);
            }
        };
        StreakViewModelFactory factory = new StreakViewModelFactory(this.getActivity().getApplication(),
                FirebaseDatabase.getInstance().getReference().child(TASKS_CHILD));
        streakViewModel =
                ViewModelProviders.of(this, factory).get(StreakViewModel.class);

        if (streakViewModel.getUserAvatarUrl() != null) {
            userAvatar.setImageURI(streakViewModel.getUserAvatarUrl());
        }

        mDisposable.add(streakViewModel.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskList -> taskListObserver.onChanged(taskList)));

        recyclerViewAdapter.setListener(streakViewModel);

        View fab = root.findViewById(R.id.add_buddy_button);
        fab.setOnClickListener(v -> showDialog(streakViewModel));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

    private void showDialog(NewStreakDialog.AddNewBuddyListener listener) {
        DialogFragment newFragment = NewStreakDialog.newInstance(listener);
        if (getFragmentManager() != null) {
            newFragment.show(getFragmentManager(), NewStreakDialog.DIALOG_TAG);
        }
    }
}