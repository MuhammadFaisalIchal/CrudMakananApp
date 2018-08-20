package com.imastudio.crudmakananapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.imastudio.crudmakananapp.R;
import com.imastudio.crudmakananapp.helper.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // * Handler untuk delay activity
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                manager = new SessionManager(SplashScreenActivity.this);
                manager.checkLogin();
            }
        },3000);
    }
}
