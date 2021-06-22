package com.soundsmeow.apps.oaat.ui.buddies;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soundsmeow.apps.oaat.R;

public class BuddiesFragment extends Fragment {

    private BuddiesViewModel mViewModel;

    public static BuddiesFragment newInstance() {
        return new BuddiesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.buddies_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BuddiesViewModel.class);
        // TODO: Use the ViewModel
    }

}