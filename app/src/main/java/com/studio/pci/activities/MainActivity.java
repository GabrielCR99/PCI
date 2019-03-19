package com.studio.pci.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studio.pci.R;
import com.studio.pci.adapters.ViewPageAdapter;
import com.studio.pci.fragments.ProjetosEmAndamentoFragment;
import com.studio.pci.fragments.ProjetosFinalizadosFragment;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tab_layout_main)
    TabLayout tabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.projects_view_pager)
    ViewPager viewPager;

    private int type;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getUserType();
    }

    private void getUserType() {
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        user = new User();
        databaseUsers.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    user = dataSnapshot.getValue(User.class);
                    if(user.getType().equals(getString(R.string.student))){
                        type = 1;
                    }else{
                        type = 2;
                    }
                }else{
                    Log.v("GetUserError","Null User");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("RetrieveDataError",databaseError.getMessage());
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(getSupportFragmentManager());
                viewPagerAdapter.addFragment(new ProjetosEmAndamentoFragment(), "Projetos em andamento");
                viewPagerAdapter.addFragment(new ProjetosFinalizadosFragment(), "Projetos finalizados");
                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_profile:
                Intent intent;
                switch (type){
                    case 1:
                        intent = new Intent(MainActivity.this,StudentActivity.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this,ProfessorActivity.class);
                        break;
                    default:
                        intent = new Intent(MainActivity.this,MainActivity.class);
                        break;
                }
                intent.putExtra("UID",user.getUid());
                finish();
                startActivity(intent);
                break;
        }
        return false;
    }
}