package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.models.User;

public class SplashActivity extends AppCompatActivity implements Runnable {

    private static final int DELAY_MILLIS = 2000;
    private static final String USER_TYPE = "USERTYPE";
    private FirebaseUser currentUser;
    private int type;

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
        if (currentUser != null) {
            startMain();
        } else {
            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
            finish();
        }
    }

    private void startMain() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child("users").child(currentUser.getUid()).getValue(User.class);
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                if(user.getType().equals(getString(R.string.student))) type = 1;
                else type = 2;
                intent.putExtra(USER_TYPE,type);
                intent.putExtra("USERID",currentUser.getUid());
                startActivity(intent);
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FIREBASE_ERROR","onCancelled Error = "+databaseError.getMessage());
            }
        });
    }
}
