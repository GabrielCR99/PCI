package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.adapters.ProfessorsAdapter;
import com.studio.pci.adapters.StudentsAdapter;
import com.studio.pci.models.Professor;
import com.studio.pci.models.Project;
import com.studio.pci.models.Student;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProjectActivity extends NavigationActivity {

    @BindView(R.id.project_title)
    TextView titleTextView;

    @BindView(R.id.project_description)
    TextView descTextView;

    @BindView(R.id.project_startDate)
    TextView startTextView;

    @BindView(R.id.project_endDate)
    TextView endTextView;

    @BindView(R.id.project_status)
    TextView statusTextView;

    private String projectID;
    private Project project;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_project);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        projectID = intent.getStringExtra("PROJECT_ID");
        setInfo();
    }

    private void setInfo() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                project = dataSnapshot.child("projects").child(projectID).getValue(Project.class);

                if(project.getTitle().isEmpty()) titleTextView.setText(getString(R.string.null_info));
                else titleTextView.setText(project.getTitle());

                if(project.getDescription().isEmpty()) descTextView.setText(getString(R.string.null_info));
                else descTextView.setText(project.getDescription());

                if(project.getStartDate().isEmpty()) startTextView.setText(getString(R.string.null_info));
                else startTextView.setText(project.getStartDate());

                if(project.getEndDate().isEmpty()) endTextView.setText(getString(R.string.null_info));
                else endTextView.setText(project.getEndDate());

                statusTextView.setText(project.isFinished() ? getString(R.string.finished) : getString(R.string.in_progress));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("STUDENTS_FIREBASE",databaseError.getMessage());
            }
        });
    }

    @OnClick(R.id.students_project)
    public void goToStudents(){
        Intent intent = new Intent(ProjectActivity.this,ViewStudentsActivity.class);
        intent.putExtra("PROJECT_ID",projectID);
        startActivity(intent);
    }

    @OnClick(R.id.professors_project)
    public void goToProfessors(){
        Intent intent = new Intent(ProjectActivity.this,ViewProfessorsActivity.class);
        intent.putExtra("PROJECT_ID",projectID);
        startActivity(intent);
    }
}