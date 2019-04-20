package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
import com.studio.pci.models.Upload;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected int type;
    protected String uid;
    protected ImageView imageView;
    protected View header;
    protected NavigationView navigationView;
    protected DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.menu_project:
                Intent intent = new Intent(this, FragmentActivity.class);
                intent.putExtra("UID",uid);
                intent.putExtra("USERTYPE",type);
                startActivity(intent);
                break;

            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                break;

            case R.id.menu_profile:
                Intent iProfile;
                switch (type){
                    case 1:
                        iProfile = new Intent(this,StudentActivity.class);
                        iProfile.putExtra("UID",uid);
                        iProfile.putExtra("USERTYPE",type);
                        startActivity(iProfile);
                        break;
                    case 2:
                        iProfile = new Intent(this, ProfessorActivity.class);
                        iProfile.putExtra("UID",uid);
                        iProfile.putExtra("USERTYPE",type);
                        startActivity(iProfile);
                        break;
                    default:
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(getString(R.string.null_info));
                        builder.setTitle(getString(R.string.null_info));
                        builder.create().show();
                        break;
                }
                break;

            case R.id.menu_privacy_policy:
                startActivity(new Intent(this, PrivacyPolicyActivity.class));
                break;
        }
        return true;
    }

    public void setProfileImage() {
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
                Log.e("DATABASE_IMAGE_ERROR",databaseError.getMessage());
            }
        });
    }

    public void setNavInfo(int type) {
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
}
