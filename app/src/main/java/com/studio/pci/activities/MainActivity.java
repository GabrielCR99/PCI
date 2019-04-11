package com.studio.pci.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.studio.pci.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.models.Professor;
import com.studio.pci.models.Project;
import com.studio.pci.providers.ProjectDAO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private int type;
    private String uid;

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

        Intent intent = getIntent();
        type = intent.getIntExtra("USERTYPE",0);
        String name = intent.getStringExtra("USERNAME");
        uid = intent.getStringExtra("USERID");

        setNavInfo(name,type);
    }

    private void setNavInfo(String name, int type) {
        View header = navigationView.getHeaderView(0);
        TextView nameTextView = header.findViewById(R.id.nav_name);
        TextView typeTextView = header.findViewById(R.id.nav_type);
        nameTextView.setText(name);
        if(type==1) {
            typeTextView.setText(getString(R.string.student));
        }
        else if(type==2) {
            typeTextView.setText(getString(R.string.professor));
        }
        else {
            typeTextView.setText(getString(R.string.null_user));
        }
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

            case R.id.menu_professors:
                break;

            case R.id.menu_project:
                Intent intent = new Intent(this, FragmentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                break;
            case R.id.menu_students:
                startActivity(new Intent(this, ViewStudentsActivity.class));
                break;
            case R.id.menu_profile:
                Intent iProfile;
                switch (type){
                    case 1:
                        iProfile = new Intent(MainActivity.this,StudentActivity.class);
                        iProfile.putExtra("UID",uid);
                        startActivity(iProfile);
                        break;
                    case 2:
                        iProfile = new Intent(MainActivity.this,Professor.class);
                        iProfile.putExtra("UID",uid);
                        startActivity(iProfile);
                        break;
                    default:
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Tipo de usuário nulo.");
                        builder.setTitle("Erro de usuário");
                        builder.create().show();
                        break;
                }
                break;
        }
        return false;
    }
}