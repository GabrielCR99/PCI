package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.models.Student;

import butterknife.BindView;

public class StudentActivity extends AppCompatActivity {

    @BindView(R.id.student_name)
    TextView nameTextView;

    @BindView(R.id.student_gender)
    TextView genderTextView;

    @BindView(R.id.student_birthDate)
    TextView birthTextView;

    @BindView(R.id.student_email)
    TextView emailTextView;

    @BindView(R.id.student_facebook)
    TextView faceTextView;

    @BindView(R.id.student_skype)
    TextView skypeTextView;

    private DatabaseReference databaseStudents;
    private Student student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_student);
        getInfo();
    }

    private void getInfo() {
        databaseStudents = FirebaseDatabase.getInstance().getReference("students");
        student = new Student();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseStudents.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   student = dataSnapshot.getValue(Student.class);
                   nameTextView.setText(student.getName());
                   genderTextView.setText(student.getGender());
                   birthTextView.setText(student.getBirthDate());
                   emailTextView.setText(student.getEmail());
                   faceTextView.setText(student.getFacebookUrl());
                   skypeTextView.setText(student.getSkypeUrl());
               }else{
                   Log.v("FetchStudentError","Couldn't fetch student data");
               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("FetchStudentError",databaseError.getMessage());
            }
        });
    }
}
