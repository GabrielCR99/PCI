package com.studio.pci.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.studio.pci.R;
import com.studio.pci.models.Student;
import com.studio.pci.models.University;
import com.studio.pci.models.Upload;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentActivity extends AppCompatActivity {

    @BindView(R.id.student_name)
    TextView name;

    @BindView(R.id.student_gender)
    TextView gender;

    @BindView(R.id.student_birthDate)
    TextView birthDate;

    @BindView(R.id.student_email)
    TextView email;

    @BindView(R.id.student_university)
    TextView university;

    @BindView(R.id.student_face)
    ImageButton facebook;

    @BindView(R.id.student_skype)
    ImageButton skype;

    @BindView(R.id.student_layout_button)
    LinearLayout linearLayout;

    @BindView(R.id.student_photo)
    ImageView imageView;

    private Student student;
    private DatabaseReference databaseReference;
    private String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_student);
        ButterKnife.bind(this);

        getInfo();
    }

    private void addButton() {
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, EditStudentActivity.class);
                intent.putExtra(getString(R.string.student_info), student);
                startActivity(intent);
            }
        });
        button.setText(getString(R.string.edit));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 25;
        button.setLayoutParams(params);
        button.setBackground(getResources().getDrawable(R.color.buttonColor));
        button.setTextColor(getResources().getColor(R.color.colorWhite));
        linearLayout.addView(button);
    }

    private void setProfileImage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile_photo").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    Picasso.get().load(upload != null ? upload.getPhoto() : null).placeholder(R.drawable.ic_launcher_background).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StudentActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getInfo() {
        Intent intent = getIntent();
        userID = intent.getStringExtra("UID");

        databaseReference = FirebaseDatabase.getInstance().getReference("students");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser.getUid().equals(userID)) addButton();
        setProfileImage();
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    student = dataSnapshot.getValue(Student.class);
                    assert student != null;
                    if (!student.getName().isEmpty()) name.setText(student.getName());
                    else name.setText(getString(R.string.null_info));

                    if (!student.getGender().isEmpty()) gender.setText(student.getGender());
                    else gender.setText(getString(R.string.null_info));

                    if (!student.getBirthDate().isEmpty())
                        birthDate.setText(student.getBirthDate());
                    else birthDate.setText(getString(R.string.null_info));

                    if (!student.getEmail().isEmpty()) email.setText(student.getEmail());
                    else email.setText(getString(R.string.null_info));

                    if(!student.getUniversity().isEmpty()) getUniversityName(student.getUniversity());
                    else university.setText(getString(R.string.null_info));

                    if(!student.getFacebookUrl().isEmpty()) {
                        facebook.setEnabled(true);
                        facebook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = getFBIntent(StudentActivity.this, student.getFacebookUrl());
                                if (intent != null) startActivity(intent);
                            }
                        });
                    } else {
                        facebook.setEnabled(false);
                    }
                    if (!student.getSkypeUrl().isEmpty()) {
                        skype.setEnabled(true);
                        skype.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = getSkypeIntent(student.getSkypeUrl());
                                startActivity(intent);
                            }
                        });
                    } else skype.setEnabled(false);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("USER_FIREBASE", databaseError.getMessage());
            }
        });
    }

    private void getUniversityName(String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("universities").child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                University un = dataSnapshot.getValue(University.class);
                university.setText(un.getName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("ERROR UNIVERSITY EDIT",databaseError.getMessage());
            }
        });
    }

    public Intent getFBIntent(Context context, String facebookId) {
        try {
            // Check if FB app is even installed
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);

            String facebookScheme = "fb://profile/" + facebookId;
            return new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
        } catch (Exception e) {
            // Cache and Open a url in browser
            String facebookProfileUri = "https://www.facebook.com/" + facebookId;
            return new Intent(Intent.ACTION_VIEW, Uri.parse(facebookProfileUri));
        }
    }

    public Intent getSkypeIntent(String skypeId) {
        try {
            String skypeScheme = "skype:" + skypeId;
            return new Intent(Intent.ACTION_VIEW, Uri.parse(skypeScheme));
        } catch (ActivityNotFoundException e) {
            Log.e("SKYPE CALL", "Skype failed", e);
            return null;
        }
    }
}