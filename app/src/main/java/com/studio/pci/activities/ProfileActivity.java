package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.studio.pci.R;

public class ProfileActivity extends AppCompatActivity {

    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        type = intent.getIntExtra("USER_TYPE",0);
        switch (type){
            case 1:
                setContentView(R.layout.activity_dashboard_student);
                break;
            case 2:
                setContentView(R.layout.activity_dashboard_professor);
            default:
                Log.v("GetUserTypeError","Couldn't get the type of user to set content view on profile activity");
                break;
        }
    }
}
