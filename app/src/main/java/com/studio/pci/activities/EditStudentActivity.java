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
import android.view.View;
import android.webkit.MimeTypeMap;
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
import com.studio.pci.models.Student;
import com.studio.pci.models.University;
import com.studio.pci.models.Upload;
import com.studio.pci.providers.StudentDAO;
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

public class EditStudentActivity extends AppCompatActivity {

    @BindView(R.id.student_name_edit)
    EditText nameEditText;

    @BindView(R.id.nameInputLayout)
    TextInputLayout nameTextInputLayout;

    @BindView(R.id.student_birthDate_edit)
    EditText birthDateEditText;

    @BindView(R.id.spinner_student_edit)
    Spinner genderSpinner;

    @BindView(R.id.student_face_edit)
    EditText faceEditText;

    @BindView(R.id.student_skype_edit)
    EditText skypeEditText;

    @BindView(R.id.student_edit_button)
    Button confirmButton;

    @BindView(R.id.student_university_edit)
    EditText universityEditText;

    @BindView(R.id.student_photo)
    ImageView imageView;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ArrayList<String> info;
    private Uri file;
    public static String universityID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        ButterKnife.bind(this);

        storageReference = FirebaseStorage.getInstance().getReference("profile_photo");
        databaseReference = FirebaseDatabase.getInstance().getReference("profile_photo");

        Intent intent = getIntent();
        info = intent.getStringArrayListExtra(getString(R.string.student_info));

        nameEditText.setText(info.get(1));
        if(info.get(2).equals(getString(R.string.male))) genderSpinner.setSelection(0);
        else if(info.get(2).equals(getString(R.string.female))) genderSpinner.setSelection(1);
        else genderSpinner.setSelection(2);
        birthDateEditText.setText(info.get(3));
        faceEditText.setText(info.get(6));
        skypeEditText.setText(info.get(7));

        if(!info.get(8).isEmpty()) getUniversityName();
        else universityEditText.setText(getString(R.string.null_info));

        universityEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newUniversity = new Intent(EditStudentActivity.this,SelectUniversityActivity.class);
                newUniversity.putExtra("UID",info.get(0));
                startActivity(newUniversity);
            }
        });
        DatePickerDialogHelper.setDatePickerDialog(birthDateEditText,this,new SimpleDateFormat(getString(R.string.date_formatter), new Locale("pt", "BR")));

        setProfileImage();
    }

    private void getUniversityName() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("universities").child(info.get(8));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                University university = dataSnapshot.getValue(University.class);
                universityEditText.setText(university.getName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("ERROR UNIVERSITY EDIT",databaseError.getMessage());
            }
        });
    }

    private void setProfileImage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile_photo").child(info.get(0));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    assert upload != null;
                    Picasso.get().load(upload.getPhoto()).placeholder(R.drawable.ic_launcher_background).into(imageView);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditStudentActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
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

    @OnClick(R.id.student_edit_button)
    public void confirmButtonClick(){
        String name = nameEditText.getText().toString();
        if(validateForm(name)){
            confirmEdit();
        }
    }

    private void confirmEdit() {
        StudentDAO studentDAO = new StudentDAO();
        String name = nameEditText.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        String birthDate = birthDateEditText.getText().toString();
        String facebook = faceEditText.getText().toString();
        String skype = skypeEditText.getText().toString();
        // TODO UNIVERSITY LIST FOR SELECTION
        final Student student = new Student(info.get(0),name,gender,birthDate,info.get(4),info.get(5),facebook,skype,"",true);
        studentDAO.update(student.getId(),student);
        if(file != null){
            String path = file.getPath();
            assert path != null;
            String ext = path.substring(path.lastIndexOf("."));
            final StorageReference storage = storageReference.child(student.getId()+"."+ext);
            storage.putFile(file)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Upload upload = new Upload(uri.toString());
                            databaseReference.child(student.getId()).setValue(upload);
                            finish();
                        }
                    });
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditStudentActivity.this);
                    builder.setTitle(getString(R.string.upload_photo_fail));
                    builder.setMessage(e.getMessage());
                    builder.create().show();
                }
            });
        }
        finish();
    }

    @OnClick(R.id.student_photo)
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        } else {
            checkAndroidVersion();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //RESULT FROM SELECTED IMAGE
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK
            && data!=null && data.getData()!=null) {
            Uri uri = CropImage.getPickImageResultUri(this, data);
            cropRequest(uri);
        }

        //RESULT FROM CROPING ACTIVITY
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
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
        }
    }
}