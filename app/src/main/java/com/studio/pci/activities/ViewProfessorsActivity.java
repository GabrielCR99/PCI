package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.adapters.ProfessorsAdapter;
import com.studio.pci.models.Professor;
import com.studio.pci.models.Project;
import com.studio.pci.models.Upload;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewProfessorsActivity extends AppCompatActivity {

    private String projectID;
    private List<Professor> professors;
    private List<Upload> uploads;
    private ProfessorsAdapter adapter;
    private Project project;

    @BindView(R.id.recycler_users_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_view);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        projectID = intent.getStringExtra("PROJECT_ID");
        setProfessors();
        setRecyclerView();
    }

    private void setProfessors() {
        professors = new ArrayList<>();
        uploads = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                project = dataSnapshot.child("projects").child(projectID).getValue(Project.class);
                professors.clear();
                uploads.clear();
                for(String id : project.getProfessors()){
                    Professor professor = dataSnapshot.child("professors").child(id).getValue(Professor.class);
                    Upload upload;
                    if(dataSnapshot.child("profile_photo").child(id).exists()) upload = dataSnapshot.child("profile_photo").child(id).getValue(Upload.class);
                    else upload = new Upload("null");
                    professors.add(professor);
                    uploads.add(upload);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DATABASE_PROJECT_ERROR",databaseError.getMessage());
            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new ProfessorsAdapter(professors,uploads,this);
        recyclerView.setAdapter(adapter);
    }
}
