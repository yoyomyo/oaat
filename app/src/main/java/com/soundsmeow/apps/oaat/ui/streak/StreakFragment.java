package com.soundsmeow.apps.oaat.ui.streak;


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

import static com.soundsmeow.apps.oaat.ui.streak.StreakViewModel.TASKS_CHILD;

public class StreakFragment extends Fragment {

    private static final String TAG = StreakFragment.class.getSimpleName();
    private StreakViewModel streakViewModel;
    private RecyclerView streakList;
    private StreakRecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;

    private final CompositeDisposable mDisposable = new CompositeDisposable();


    public static StreakFragment newInstance() {
        StreakFragment fragment = new StreakFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_streaks, container, false);
        progressBar = root.findViewById(R.id.progress_bar);

        streakList = root.findViewById(R.id.streak_list);

        recyclerViewAdapter = new StreakRecyclerViewAdapter(getActivity(), null);

        streakList.setLayoutManager(new LinearLayoutManager(getActivity()));
        streakList.setAdapter(recyclerViewAdapter);

        final Observer<List<Streak>> streakListObserver = streaks -> {
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
                recyclerViewAdapter.notifyDataSetChanged();
            }
        };
        StreakViewModelFactory factory = new StreakViewModelFactory(this.getActivity().getApplication(),
                FirebaseDatabase.getInstance().getReference().child(TASKS_CHILD));
        streakViewModel =
                ViewModelProviders.of(this, factory).get(StreakViewModel.class);

        mDisposable.add(streakViewModel.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(streakList -> streakListObserver.onChanged(streakList)));

        recyclerViewAdapter.setListener(streakViewModel);

        View fab = root.findViewById(R.id.add_streak_button);
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

    private void showDialog(NewStreakDialog.AddNewStreakListener listener) {
        DialogFragment newFragment = NewStreakDialog.newInstance(listener);
        if (getFragmentManager() != null) {
            newFragment.show(getFragmentManager(), NewStreakDialog.DIALOG_TAG);
        }
    }
}