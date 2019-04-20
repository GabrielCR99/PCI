package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
import com.studio.pci.fragments.FeedsFragment;
import com.studio.pci.fragments.ProfessorFragment;
import com.studio.pci.fragments.ProjectListFragment;
import com.studio.pci.fragments.StudentFragment;
import com.studio.pci.models.Upload;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private int type;
    private String uid;
    private ImageView imageView;
    private View header;
    private FeedsFragment feedsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        header = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        type = intent.getIntExtra("USERTYPE",0);
        uid = intent.getStringExtra("USERID");

        navigationView.setNavigationItemSelectedListener(this);
        setNavInfo(type);

        feedsFragment = new FeedsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,feedsFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else if (feedsFragment.isVisible()) super.onBackPressed();
        else getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,feedsFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_profile:
                Bundle arguments = new Bundle();
                if(type==1){
                    StudentFragment fragment = new StudentFragment();
                    arguments.putString("USERID",uid);
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                }else{
                    ProfessorFragment fragment = new ProfessorFragment();
                    arguments.putString("USERID",uid);
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                }
                break;

            case R.id.menu_project:
                ProjectListFragment fragment = new ProjectListFragment();
                Bundle args = new Bundle();
                args.putInt("TYPE",type);
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                break;

            case R.id.menu_privacy_policy:
                startActivity(new Intent(this, PrivacyPolicyActivity.class));
                break;

            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                break;

            default:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Tipo de usuário nulo.");
                builder.setTitle("Erro de usuário");
                builder.create().show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
