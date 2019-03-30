package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.studio.pci.R;
import com.studio.pci.adapters.ViewPageAdapter;
import com.studio.pci.fragments.ProjetosEmAndamentoFragment;
import com.studio.pci.fragments.ProjetosFinalizadosFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tab_layout_main)
    TabLayout tabLayout;

    @BindView(R.id.projects_view_pager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ProjetosEmAndamentoFragment(), "Em andamento");
        viewPagerAdapter.addFragment(new ProjetosFinalizadosFragment(), "Finalizados");

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case R.id.menu_project:
                startActivity(new Intent(this, FragmentActivity.class));
                break;
        }
        return false;
    }
}


