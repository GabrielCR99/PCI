package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.studio.pci.R;
import com.studio.pci.adapters.ViewPagerAdapter;
import com.studio.pci.fragments.InProgressProjectFragment;
import com.studio.pci.fragments.FinishedProjectFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentActivity extends NavigationActivity{

    @BindView(R.id.tab_layout_main)
    TabLayout tabLayout;

    @BindView(R.id.projects_view_pager)
    ViewPager viewPager;

    @BindView(R.id.add_project_button)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fragments);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        type = intent.getIntExtra("USERTYPE",0);
        uid = intent.getStringExtra("UID");

        setNavInfo(type);
        if(type==1) fab.hide();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new InProgressProjectFragment(), getString(R.string.projects_in_progress));
        viewPagerAdapter.addFragment(new FinishedProjectFragment(), getString(R.string.finished));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}


