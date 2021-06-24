package com.soundsmeow.apps.oaat.ui.buddies;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soundsmeow.apps.oaat.R;

import java.util.List;

public class BuddiesFragment extends Fragment {

    private BuddiesViewModel mViewModel;
    private RecyclerView recyclerView;
    private BuddyRecyclerViewAdapter buddyAdapter;
    private ProgressBar progressBar;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public static BuddiesFragment newInstance() {
        return new BuddiesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.buddies_fragment, container, false);
        recyclerView = root.findViewById(R.id.buddy_list);
        progressBar = root.findViewById(R.id.progress_bar);

        mViewModel = new ViewModelProvider(this).get(BuddiesViewModel.class);
        buddyAdapter = new BuddyRecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(buddyAdapter);

        final Observer<List<Buddy>>  buddyListObserver = buddies -> {
            progressBar.setVisibility(View.GONE);
            buddyAdapter.setData(buddies);
            buddyAdapter.notifyDataSetChanged();
        };
        mDisposable.add(mViewModel.findBuddiesFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(buddyList -> buddyListObserver.onChanged(buddyList)));


        return root;
    }

    public static class BuddyViewHolder extends RecyclerView.ViewHolder {
        TextView buddyTextView;
        TextView buddyStatusTextView;
        CircleImageView buddyProfileImageView;

        public BuddyViewHolder(View v) {
            super(v);
            buddyTextView = (TextView) itemView.findViewById(R.id.buddy_name);
            buddyStatusTextView = (TextView) itemView.findViewById(R.id.buddy_status);
            buddyProfileImageView = (CircleImageView) itemView.findViewById(R.id.buddy_profile);
        }
    }

}