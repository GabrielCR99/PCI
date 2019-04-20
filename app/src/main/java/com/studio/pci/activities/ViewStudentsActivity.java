package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.adapters.StudentsAdapter;
import com.studio.pci.models.Project;
import com.studio.pci.models.Student;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewStudentsActivity extends AppCompatActivity {

    private String projectID;
    private List<Student> students;
    private StudentsAdapter adapter;
    private Project project;

    @BindView(R.id.recycler_students)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_view);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        projectID = intent.getStringExtra("PROJECT_ID");
        setStudents();
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new StudentsAdapter(students,this);
        recyclerView.setAdapter(adapter);
    }

    private void setStudents() {
        students = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                project = dataSnapshot.child("projects").child(projectID).getValue(Project.class);
                students.clear();
                for(String id : project.getStudents()){
                    Student student = dataSnapshot.child("students").child(id).getValue(Student.class);
                    Log.v("STUDENT_INFO",student.toString());
                    if(student.isEnable()) {
                        students.add(student);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DATABASE_PROJECT_ERROR",databaseError.getMessage());
            }
        });
    }
}