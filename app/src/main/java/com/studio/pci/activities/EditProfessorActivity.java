package com.studio.pci.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.studio.pci.R;
import com.studio.pci.models.Professor;
import com.studio.pci.models.University;
import com.studio.pci.models.Upload;
import com.studio.pci.providers.ProfessorDAO;
import com.studio.pci.utils.DatePickerDialogHelper;
import com.studio.pci.utils.FormHelper;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
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

    private Professor professor;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Uri file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_professor);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        professor = (Professor) intent.getSerializableExtra(getString(R.string.professor_info));

        bindInfo();

        DatePickerDialogHelper.setDatePickerDialog(birthDateEditText,this,new SimpleDateFormat(getString(R.string.date_formatter), new Locale("pt", "BR")));
        // TODO ON UNIVERSITY EDITLAYOUT CLICK LISTENER GO TO SELECTION FRAGMENT
        setProfileImage();
    }

    private void bindInfo() {
        // TODO PASS PROFESSOR OBJECT, NOT ARRAY
        // TODO GET UNIVERSITIES
        nameEditText.setText(info.get(1));
        if(info.get(2).equals(getString(R.string.male))) genderSpinner.setSelection(0);
        else if(info.get(2).equals(getString(R.string.female))) genderSpinner.setSelection(1);
        else genderSpinner.setSelection(2);
        birthDateEditText.setText(info.get(3));
        degreeEditText.setText(info.get(5));
        faceEditText.setText(info.get(7));
        skypeEditText.setText(info.get(8));
        bioEditText.setText(info.get(9));
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
        professor.setName(nameEditText.getText().toString());
        professor.setGender(genderSpinner.getSelectedItem().toString());
        professor.setBirthDate(birthDateEditText.getText().toString());
        professor.setDegree(degreeEditText.getText().toString());
        professor.setFacebookUrl(faceEditText.getText().toString());
        professor.setSkypeUrl(skypeEditText.getText().toString());
        professor.setBio(bioEditText.getText().toString());
        professorDAO.update(professor.getId(),professor);
        if(file != null){
            String path = file.getPath();
            if(path != null) {
                String ext = path.substring(path.lastIndexOf("."));
                final StorageReference storage = storageReference.child(professor.getId()+"."+ext);
                storage.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Upload upload = new Upload(uri.toString());
                                        databaseReference.child(professor.getId()).setValue(upload);
                                        finish();
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfessorActivity.this);
                                builder.setTitle(getString(R.string.upload_photo_fail));
                                builder.setMessage(e.getMessage());
                                builder.create().show();
                            }
                        });
            }

        }
        finish();
    }

    private void setProfileImage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile_photo").child(professor.getId());
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

    @OnClick(R.id.professor_photo)
    public void OnClick(){
        checkAndroidVersion();
    }

    public void checkAndroidVersion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
            } catch(Exception e){
                Log.e("Android Version Error",e.getMessage());
            }
        } else {
            pickImage();
        }
    }

    public void pickImage() {
        CropImage.startPickImageActivity(this);
    }

    private void cropRequest(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        } else {
            checkAndroidVersion();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE :
                if(resultCode == Activity.RESULT_OK&& data!=null && data.getData()!=null){
                    Uri uri = CropImage.getPickImageResultUri(this, data);
                    cropRequest(uri);
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE :
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                        ((ImageView)findViewById(R.id.student_photo)).setImageBitmap(bitmap);
                        file = result.getUri();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}