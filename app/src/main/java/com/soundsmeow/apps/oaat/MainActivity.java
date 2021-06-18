package com.soundsmeow.apps.oaat;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    SharedPreferences mSharedPreferences;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getSupportActionBar().hide();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        mFirebaseAuth = FirebaseAuth.getInstance();
        Uri url = mFirebaseAuth.getCurrentUser().getPhotoUrl();

        android.util.Log.d(TAG, url.toString());
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.start_thread:
//                startThread();
//                break;
//            case R.id.task_A:
//                taskA();
//                break;
//            case R.id.task_B:
//                taskB();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void startThread() {
//        Log.d(TAG, "startThread: looperThread");
//        looperThread.start();
//    }
//
//    private void taskA() {
//        Toast.makeText(this, "TaskA", Toast.LENGTH_SHORT).show();
//        looperThread.handler.post(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 5; i++) {
//                    Log.d(TAG, "run: " + i);
//                    SystemClock.sleep(1000);
//                }
//            }
//        });
//    }
//
//    private void taskB() {
//        Toast.makeText(this, "TaskB", Toast.LENGTH_SHORT).show();
//    }
}
