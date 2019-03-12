package com.studio.pci.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.studio.pci.R;
import com.studio.pci.models.University;
import com.studio.pci.providers.UniversityDAO;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}