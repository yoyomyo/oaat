package com.soundsmeow.apps.oaat.ui.streak;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.soundsmeow.apps.oaat.R;

import androidx.fragment.app.DialogFragment;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewStreakDialog extends DialogFragment {

    public interface AddNewBuddyListener {
        Completable addStreak(String taskDetail);
    }

    public static final String DIALOG_TAG = "Dialog";
    private AddNewBuddyListener listener;

    static NewStreakDialog newInstance(AddNewBuddyListener listener) {
        NewStreakDialog dialog = new NewStreakDialog();
        dialog.listener = listener;
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_new_task, container, false);
        final EditText taskInput = v.findViewById(R.id.task_detail_edittext);

        Button button = (Button)v.findViewById(R.id.create_task);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTask = taskInput.getText().toString();
                if (!TextUtils.isEmpty(newTask)) {
                    listener.addStreak(newTask)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> NewStreakDialog.this.dismiss(),
                                    throwable -> Log.e("DEBUG",
                                            "something wrong happened "
                                                    + throwable.getMessage()));
                }
            }
        });
        return v;
    }
}
