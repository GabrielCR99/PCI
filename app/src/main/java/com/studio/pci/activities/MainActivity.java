package com.studio.pci.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.studio.pci.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
}
