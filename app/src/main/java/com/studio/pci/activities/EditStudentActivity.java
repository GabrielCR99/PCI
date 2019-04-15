package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.studio.pci.R;
import com.studio.pci.models.Student;
import com.studio.pci.providers.StudentDAO;
import com.studio.pci.utils.DatePickerDialogHelper;
import com.studio.pci.utils.FormHelper;

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

    private ArrayList<String> info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        info = intent.getStringArrayListExtra("STUDENT_INFO");

        nameEditText.setText(info.get(1));
        if(info.get(2).equals(getString(R.string.male))) genderSpinner.setSelection(0);
        else if(info.get(2).equals(getString(R.string.female))) genderSpinner.setSelection(1);
        else genderSpinner.setSelection(2);
        birthDateEditText.setText(info.get(3));
        faceEditText.setText(info.get(6));
        skypeEditText.setText(info.get(7));

        DatePickerDialogHelper.setDatePickerDialog(birthDateEditText,this,new SimpleDateFormat(getString(R.string.date_formatter), new Locale("pt", "BR")));
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
        Student student = new Student(info.get(0),name,gender,birthDate,info.get(4),info.get(5),facebook,skype,true);
        studentDAO.update(student.getId(),student);
        onBackPressed();
        finish();
    }
}