package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;

import com.studio.pci.R;

public class MainActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        type = intent.getIntExtra("USERTYPE",0);
        uid = intent.getStringExtra("USERID");

        setNavInfo(type);

    }
}