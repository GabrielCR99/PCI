package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.studio.pci.R;
import com.studio.pci.models.Professor;
import com.studio.pci.models.Upload;
import com.studio.pci.providers.ProfessorDAO;
import com.studio.pci.utils.DatePickerDialogHelper;
import com.studio.pci.utils.FormHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfessorActivity extends AppCompatActivity {

    @BindView(R.id.professor_name_edit)
    EditText nameEditText;

    @BindView(R.id.nameInputLayout)
    TextInputLayout nameTextInputLayout;

    @BindView(R.id.professor_birthDate_edit)
    EditText birthDateEditText;

    @BindView(R.id.spinner_professor_edit)
    Spinner genderSpinner;

    @BindView(R.id.professor_degree_edit)
    EditText degreeEditText;

    @BindView(R.id.professor_face_edit)
    EditText faceEditText;

    @BindView(R.id.professor_skype_edit)
    EditText skypeEditText;

    @BindView(R.id.professor_bio_edit)
    EditText bioEditText;

    @BindView(R.id.professor_edit_button)
    Button confirmButton;

    @BindView(R.id.professor_photo)
    ImageView imageView;

    private ArrayList<String> info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_professor);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        info = intent.getStringArrayListExtra(getString(R.string.professor_info));

        nameEditText.setText(info.get(1));
        if(info.get(2).equals(getString(R.string.male))) genderSpinner.setSelection(0);
        else if(info.get(2).equals(getString(R.string.female))) genderSpinner.setSelection(1);
        else genderSpinner.setSelection(2);
        birthDateEditText.setText(info.get(3));
        degreeEditText.setText(info.get(5));
        faceEditText.setText(info.get(7));
        skypeEditText.setText(info.get(8));
        bioEditText.setText(info.get(9));

        DatePickerDialogHelper.setDatePickerDialog(birthDateEditText,this,new SimpleDateFormat(getString(R.string.date_formatter), new Locale("pt", "BR")));
        // TODO ON UNIVERSITY EDITLAYOUT CLICK LISTENER GO TO SELECTION FRAGMENT
        setProfileImage();
    }

    private boolean validateForm(String name){
        boolean validate = true;
        nameTextInputLayout.setError(null);
        if(FormHelper.isEmpty(name)){
            nameTextInputLayout.setError(getString(R.string.error_name_empty));
            validate = false;
        }
        return validate;
    }

    @OnClick(R.id.professor_edit_button)
    public void confirmButtonClick(){
        String name = nameEditText.getText().toString();
        if(validateForm(name)){
            confirmEdit();
        }
    }

    private void confirmEdit() {
        ProfessorDAO professorDAO = new ProfessorDAO();
        String name = nameEditText.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        String birthDate = birthDateEditText.getText().toString();
        String degree = degreeEditText.getText().toString();
        String facebook = faceEditText.getText().toString();
        String skype = skypeEditText.getText().toString();
        String bio = bioEditText.getText().toString();
        Professor professor = new Professor(info.get(0),name,gender,birthDate,info.get(4),degree,info.get(6),facebook,skype,bio,true);
        professorDAO.update(professor.getId(),professor);
        onBackPressed();
        finish();
    }

    private void setProfileImage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile_photo").child(info.get(0));
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
                Toast.makeText(EditProfessorActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}