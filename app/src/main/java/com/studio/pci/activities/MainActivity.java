package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.studio.pci.R;
import com.studio.pci.models.Professor;
import com.studio.pci.models.Project;
import com.studio.pci.models.Upload;
import com.studio.pci.providers.ProjectDAO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private int type;
    private String uid;
    private View header;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        header = navigationView.getHeaderView(0);
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Intent intent = getIntent();
        type = intent.getIntExtra("USERTYPE",0);
        uid = intent.getStringExtra("USERID");

        setNavInfo(type);

    }

    private void setProfileImage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile_photo").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()) {
                   Upload upload = dataSnapshot.getValue(Upload.class);
                   Picasso.get().load(upload.getPhoto()).placeholder(R.drawable.ic_launcher_background).into(imageView);
               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setNavInfo(int type) {
        final TextView nameTextView = header.findViewById(R.id.nav_name);
        TextView typeTextView = header.findViewById(R.id.nav_type);
        imageView = header.findViewById(R.id.user_photo);
        if(type==1) {
            typeTextView.setText(getString(R.string.student));
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("students");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    nameTextView.setText(dataSnapshot.child(uid).child("name").getValue(String.class));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.v("NAME DATABASE ERROR",databaseError.getMessage());
                }
            });
        }
        else if(type==2) typeTextView.setText(getString(R.string.professor));
        else typeTextView.setText(getString(R.string.null_user));
        setProfileImage();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_professors:
                break;

            case R.id.menu_project:
                onBackPressed();
                Intent intent = new Intent(this, FragmentActivity.class);
                intent.putExtra("USERTYPE",type);
                startActivity(intent);
                break;

            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                break;
            case R.id.menu_students:
                onBackPressed();
                startActivity(new Intent(this, ViewStudentsActivity.class));
                break;
            case R.id.menu_profile:
                Intent iProfile;
                onBackPressed();
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

            case R.id.menu_privacy_policy:
                startActivity(new Intent(this, PrivacyPolicyActivity.class));
                break;
        }
        return false;
    }

}