package com.studio.pci.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.adapters.StudentsAdapter;
import com.studio.pci.models.Student;
import com.studio.pci.providers.StudentDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewStudentsActivity extends AppCompatActivity {

    private List<Student> students;
    private StudentsAdapter adapter;
    private DatabaseReference db;

    @BindView(R.id.recycler_students)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_view);
        ButterKnife.bind(this);
        getStudents();
        getRecyclerView();
    }

    private void getRecyclerView() {
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        adapter = new StudentsAdapter(students,this);
        recyclerView.setAdapter(adapter);
    }

    private void getStudents() {
        students = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("students");
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(!userID.equals(ds.getValue(Student.class).getId()) && ds.getValue(Student.class).isEnable()){
                        Student student = ds.getValue(Student.class);
                        students.add(student);
                        adapter.notifyDataSetChanged();
                    }
                }
                // TODO Remove EventListener so the activity cannot close when DataChange
                db.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("STUDENTS_FIREBASE",databaseError.getMessage());
            }
        });
    }
}