package com.studio.pci.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.studio.pci.activities.EditStudentActivity;
import com.studio.pci.models.Student;
import com.studio.pci.models.University;
import com.studio.pci.models.Upload;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentFragment extends Fragment {

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

    @BindView(R.id.student_photo)
    ImageView imageView;

    @BindView(R.id.student_edit_button)
    Button button;

    private View view;
    private ArrayList<String> info;
    private DatabaseReference databaseReference;
    private String userID;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_dashboard,container,false);
        ButterKnife.bind(this,view);

        databaseReference = FirebaseDatabase.getInstance().getReference("students");

        Bundle arguments = getArguments();
        userID = arguments.getString("USERID");

        getInfo();


        return view;
    }

    private void setProfileImage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile_photo").child(userID);
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
               Log.e("ERROR_IMAGE",databaseError.getMessage());
            }
        });
    }

    private void getInfo() {
        setProfileImage();
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    final Student student = dataSnapshot.getValue(Student.class);
                    if(!student.getName().isEmpty()) name.setText(student.getName());
                    else name.setText(getString(R.string.null_info));

                    if(!student.getGender().isEmpty()) gender.setText(student.getGender());
                    else gender.setText(getString(R.string.null_info));

                    if(!student.getBirthDate().isEmpty()) birthDate.setText(student.getBirthDate());
                    else birthDate.setText(getString(R.string.null_info));

                    if(!student.getEmail().isEmpty()) email.setText(student.getEmail());
                    else email.setText(getString(R.string.null_info));

                    if(!student.getUniversity().isEmpty()) getUniversityName();
                    else university.setText(getString(R.string.null_info));

                    if(!student.getFacebookUrl().isEmpty()) {
                        facebook.setEnabled(true);
                        facebook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = getFBIntent(getContext(),student.getFacebookUrl());
                                if(intent!=null) startActivity(intent);
                            }
                        });
                    }
                    else { facebook.setEnabled(false); }

                    if(!student.getSkypeUrl().isEmpty()) {
                        skype.setEnabled(true);
                        skype.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = getSkypeIntent(student.getSkypeUrl());
                                startActivity(intent);
                            }
                        });
                    }
                    else skype.setEnabled(false);

                    info = student.toArray();
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context,EditStudentActivity.class);
                            intent.putExtra(getString(R.string.student_info),info);
                            startActivity(intent);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("USER_FIREBASE", databaseError.getMessage());
            }
        });
    }

    private void getUniversityName() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("universities").child(info.get(8));
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
        }
        catch(Exception e) {
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
