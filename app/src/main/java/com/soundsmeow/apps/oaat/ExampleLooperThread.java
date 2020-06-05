package com.soundsmeow.apps.oaat;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class ExampleLooperThread extends Thread {
    private static final String TAG = "ExampleLooperThread";

    public Handler handler;

    @Override
    public void run() {
        Log.d(TAG, "start of run()");

        Looper.prepare();

        handler = new Handler();

        Looper.loop();
        Log.d(TAG, "end of run()");
    }
}
