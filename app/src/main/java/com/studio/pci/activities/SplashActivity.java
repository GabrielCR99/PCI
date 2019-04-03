package com.studio.pci.activities;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studio.pci.R;

public class SplashActivity extends BaseActivity implements Runnable {

    private static final int DELAY_MILLIS = 2000;

    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Handler handle = new Handler();
        handle.postDelayed(this, DELAY_MILLIS);
    }

    @Override
    public void run() {
        Intent intent;
        /*if (currentUser != null) {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, SignInActivity.class);
        }*/
        intent = new Intent(SplashActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
