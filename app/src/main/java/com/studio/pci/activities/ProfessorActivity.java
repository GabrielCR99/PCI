package com.studio.pci.activities;

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
import com.studio.pci.models.Professor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfessorActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String userID;

    @BindView(R.id.professor_name)
    TextView name;

    @BindView(R.id.professor_gender)
    TextView gender;

    @BindView(R.id.professor_birthDate)
    TextView birthDate;

    @BindView(R.id.professor_email)
    TextView email;

    @BindView(R.id.professor_degree)
    TextView degree;

    @BindView(R.id.professor_facebook)
    TextView facebook;

    @BindView(R.id.professor_skype)
    TextView skype;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_professor);
        ButterKnife.bind(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("professors");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        getInfo();
    }

    private void getInfo() {
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Professor professor = dataSnapshot.getValue(Professor.class);
                    Log.v("USER_FIREBASE_UID", professor.getId());
                    if(!professor.getName().isEmpty()) name.setText(professor.getName());
                    else name.setText(getString(R.string.null_info));

                    if(!professor.getGender().isEmpty()) gender.setText(professor.getGender());
                    else gender.setText(getString(R.string.null_info));

                    if(!professor.getBirthDate().isEmpty()) birthDate.setText(professor.getBirthDate());
                    else birthDate.setText(getString(R.string.null_info));

                    if(!professor.getEmail().isEmpty()) email.setText(professor.getEmail());
                    else email.setText(getString(R.string.null_info));

                    if(!professor.getDegree().isEmpty()) degree.setText(professor.getDegree());
                    else degree.setText(getString(R.string.null_info));

                    if(!professor.getFacebookUrl().isEmpty()) facebook.setText(professor.getFacebookUrl());
                    else facebook.setText(getString(R.string.null_info));

                    if(!professor.getSkypeUrl().isEmpty()) skype.setText(professor.getSkypeUrl());
                    else skype.setText(getString(R.string.null_info));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("USER_FIREBASE", databaseError.getMessage());
            }
        });
    }
}