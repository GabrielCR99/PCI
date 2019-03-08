package com.studio.pci.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.studio.pci.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @BindView(R.id.reset_edit_text)
    EditText resetPasswordEditText;

    @BindView(R.id.resetInputLayout)
    TextInputLayout resetPasswordInputLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);



        firebaseAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.send_reset_email)
    public void sendEmailVerification(View view){

        String userEmail = resetPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(userEmail)){
            resetPasswordInputLayout.setError("Erro");

        }
        else {
            firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this, SignInActivity.class));
                    }
                    else {
                        String message = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), "Error:" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
