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


public class ProjectActivity extends AppCompatActivity {

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

    @BindView(R.id.recycler_project_professors)
    RecyclerView professorRecyclerView;

    @BindView(R.id.recycler_project_students)
    RecyclerView studentsRecyclerView;

    private StudentsAdapter studentsAdapter;
    private ProfessorsAdapter professorsAdapter;
    private List<Professor> professorList;
    private List<Student> studentList;
    private String projectID;
    private Project project;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        projectID = intent.getStringExtra("PROJECT_ID");
        setMembers();
        setRecyclers();

    }

    private void setMembers() {
        studentList = new ArrayList<>();
        professorList = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                project = dataSnapshot.child("projects").child(projectID).getValue(Project.class);
                studentList.clear();
                for(String id : project.getStudents()){
                    Student student = dataSnapshot.child("students").child(id).getValue(Student.class);
                    studentList.add(student);
                }
                professorList.clear();
                for(String id : project.getProfessors()){
                    Professor professor = dataSnapshot.child("professors").child(id).getValue(Professor.class);
                    professorList.add(professor);
                }

                if(project.getTitle().isEmpty()) titleTextView.setText(getString(R.string.null_info));
                else titleTextView.setText(project.getTitle());

                if(project.getDescription().isEmpty()) descTextView.setText(getString(R.string.null_info));
                else descTextView.setText(project.getDescription());

                if(project.getStartDate().isEmpty()) startTextView.setText(getString(R.string.null_info));
                else startTextView.setText(project.getStartDate());

                if(project.getEndDate().isEmpty()) endTextView.setText(getString(R.string.null_info));
                else endTextView.setText(project.getEndDate());

                statusTextView.setText(project.isFinished() ? getString(R.string.finished) : getString(R.string.in_progress));
                studentsAdapter.notifyDataSetChanged();
                professorsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("STUDENTS_FIREBASE",databaseError.getMessage());
            }
        });
    }

    private void setRecyclers() {
        RecyclerView.LayoutManager layoutStudent = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutProfessor = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        professorRecyclerView.setLayoutManager(layoutStudent);
        studentsRecyclerView.setLayoutManager(layoutProfessor);

        studentsAdapter = new StudentsAdapter(studentList,this);
        studentsRecyclerView.setAdapter(studentsAdapter);

        professorsAdapter = new ProfessorsAdapter(professorList,this);
        professorRecyclerView.setAdapter(professorsAdapter);
    }
}
