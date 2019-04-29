package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.models.Professor;
import com.studio.pci.models.Student;
import com.studio.pci.models.User;
import com.studio.pci.utils.FormHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {

    private static final String TAG = "SignInActivity";

    @BindView(R.id.email_field)
    EditText emailField;

    @BindView(R.id.password_field)
    EditText passwordField;

    @BindView(R.id.emailInputLayout)
    TextInputLayout emailLayout;

    @BindView(R.id.passwordInputLayout)
    TextInputLayout passwordLayout;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
    }

    private boolean validateForm(String email, String password) {

        boolean resultValidate = true;

        emailLayout.setError(null);
        passwordLayout.setError(null);

        if (FormHelper.isEmpty(email)) {
            emailLayout.setError(getString(R.string.error_email_empty));
            resultValidate = false;
        } else if (FormHelper.isEmailValid(email)) {
            emailLayout.setError(getString(R.string.error_email_invalid));
            resultValidate = false;
        }

        if (FormHelper.isEmpty(email)) {
            emailLayout.setError(getString(R.string.error_email_empty));
            resultValidate = false;
        } else if (FormHelper.isEmailValid(email)) {
            emailLayout.setError(getString(R.string.error_email_invalid));
            resultValidate = false;
        }

        if (FormHelper.isEmpty(password)) {
            passwordLayout.setError(getString(R.string.error_password_empty));
            resultValidate = false;
        } else if (FormHelper.isPasswordValid(password)) {
            passwordLayout.setError(getString(R.string.error_password_invalid));
            resultValidate = false;
        }

        return resultValidate;
    }


    private void signIn(String email, String password) {
        if (!validateForm(email, password)) {
            Log.v(TAG, "validateForm error");
            return;
        }
        showProgressDialog();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startMain();
                        } else {
                            hideProgressDialog();
                            showToast(getString(R.string.auth_failed));
                        }
                    }
                });
    }

    private void startMain() {
        firebaseUser = auth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child("users").child(firebaseUser.getUid()).getValue(User.class);
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                if (user.getType().equals(getString(R.string.student))) {
                    type = 1;
                    Student student = dataSnapshot.child("students").child(firebaseUser.getUid()).getValue(Student.class);
                    intent.putExtra("USERNAME", student.getName());
                } else {
                    type = 2;
                    Professor professor = dataSnapshot.child("professors").child(firebaseUser.getUid()).getValue(Professor.class);
                    intent.putExtra("USERNAME", professor.getName());
                }
                intent.putExtra("USERTYPE", type);
                intent.putExtra("USERID", firebaseUser.getUid());
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FIREBASE_ERROR", "onCancelled Error = " + databaseError.getMessage());
            }
        });
    }


    @OnClick(R.id.sign_in_button)
    public void signInOnClick(View view) {
        Log.v(TAG, "signInOnClick execute");
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        signIn(email, password);
    }

    @OnClick(R.id.go_to_sign_up_text)
    public void goToSignUpOnClick(View view) {
        Log.v(TAG, "goToSignUpOnClick execute");
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    @OnClick(R.id.password_forgot)
    public void sendPasswordResetEmail(View view) {
        startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
    }
}
