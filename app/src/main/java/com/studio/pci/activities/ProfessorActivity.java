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
import com.studio.pci.models.Professor;
import com.studio.pci.models.Upload;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfessorActivity extends AppCompatActivity {

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

    @BindView(R.id.professor_face)
    ImageButton facebook;

    @BindView(R.id.professor_skype)
    ImageButton skype;

    @BindView(R.id.professor_bio)
    TextView bio;

    @BindView(R.id.professor_layout_button)
    LinearLayout linearLayout;

    @BindView(R.id.professor_photo)
    ImageView imageView;

    private ArrayList<String> info;
    private DatabaseReference databaseReference;
    private String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_professor);
        ButterKnife.bind(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("professors");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        userID = intent.getStringExtra("UID");

        getInfo();

        if(user != null && user.getUid().equals(userID)) addButton();
    }

    private void addButton() {
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfessorActivity.this,EditProfessorActivity.class);
                intent.putExtra(getString(R.string.professor_info),info);
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
                if(dataSnapshot.exists()) {
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    Picasso.get().load(upload != null ? upload.getPhoto() : null).placeholder(R.drawable.ic_launcher_background).into(imageView);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfessorActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getInfo() {
        setProfileImage();
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    final Professor professor = dataSnapshot.getValue(Professor.class);
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

                    if(!professor.getBio().isEmpty()) bio.setText(professor.getBio());
                    else bio.setText(getString(R.string.null_info));

                    if(!professor.getFacebookUrl().isEmpty()) {
                        facebook.setEnabled(true);
                        facebook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = getFBIntent(ProfessorActivity.this,professor.getFacebookUrl());
                                if(intent!=null) startActivity(intent);
                            }
                        });
                    }
                    else { facebook.setEnabled(false); }

                    if(!professor.getSkypeUrl().isEmpty()) {
                        skype.setEnabled(true);
                        skype.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = getSkypeIntent(professor.getSkypeUrl());
                                if(intent!=null) startActivity(intent);
                            }
                        });
                    }
                    else skype.setEnabled(false);

                    info = professor.toArray();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("USER_FIREBASE", databaseError.getMessage());
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
