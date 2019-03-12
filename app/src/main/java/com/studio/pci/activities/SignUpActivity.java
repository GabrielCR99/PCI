package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studio.pci.R;
import com.studio.pci.utils.FormHelper;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity{

    private FirebaseAuth firebaseAuth;

    @BindView(R.id.name)
    EditText nameField;

    @BindView(R.id.email)
    EditText emailField;

    @BindView(R.id.password)
    EditText passwordField;

    @BindView(R.id.confirm_password)
    EditText confirmPasswordField;

    @BindView(R.id.layout_name)
    TextInputLayout nameLayout;

    @BindView(R.id.layout_email)
    TextInputLayout emailLayout;

    @BindView(R.id.layout_password)
    TextInputLayout passwordLayout;

    @BindView(R.id.layout_confirm_password)
    TextInputLayout confirmPasswordLayout;

    @BindView(R.id.spinner_sign_up)
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();



    }

    private boolean validateForm(String name, String email, String password, String confirmPassword) {

        boolean resultValidate = true;

        nameLayout.setError(null);
        emailLayout.setError(null);
        passwordLayout.setError(null);
        confirmPasswordLayout.setError(null);

        if (FormHelper.isEmpty(name)) {
            nameLayout.setError(getString(R.string.error_name_empty));
            resultValidate = false;
        }

        if (FormHelper.isEmpty(email)) {
            emailLayout.setError(getString(R.string.error_email_empty));
            resultValidate = false;
        } else if (!FormHelper.isEmailValid(email)) {
            emailLayout.setError(getString(R.string.error_email_invalid));
            resultValidate = false;
        }

        if (FormHelper.isEmpty(password)) {
            passwordLayout.setError(getString(R.string.error_password_empty));
            resultValidate =  false;
        } else if (!FormHelper.isPasswordValid(password)) {
            passwordLayout.setError(getString(R.string.error_password_invalid));
            resultValidate =  false;
        }

        if (FormHelper.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError(getString(R.string.error_password_empty));
            resultValidate =  false;
        } else if (!password.equals(confirmPassword)){
            confirmPasswordLayout.setError(getString(R.string.error_password_different));
            resultValidate =  false;
        }

        return resultValidate;
    }

    private void createAccount(final String email, String password) {
        if (!validateForm(nameField.getText().toString(), email, password, confirmPasswordField.getText().toString())) {
            return;
        }

        showProgressDialog();
        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            hideProgressDialog();
                        } else {
                            showToast(getString(R.string.auth_failed));
                            hideProgressDialog();
                        }
                    }
                });
    }

    @OnClick(R.id.sign_up_button)
    public void signInOnClick(View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        System.out.println(email);
        System.out.println(password);

        createAccount(email, password);
    }

    @OnClick(R.id.sign_in_text)
    public void goToSignIn(View view){
        finish();

    }


}
