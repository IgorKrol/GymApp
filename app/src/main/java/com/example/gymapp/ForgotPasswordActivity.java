package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


import android.os.Bundle;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText forgot_email;
    private Button reset_btn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgot_email = (EditText)findViewById(R.id.user_forgot_email);
        reset_btn = (Button)findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.reset_btn){
            PasswordResetEmail(forgot_email.getText().toString());
        }

    }

    /*------------ Below Code is for reset password process user will get email on registered email-----------*/

    private void PasswordResetEmail(final String email) {
        if(email.equals("")){
            Toast.makeText(ForgotPasswordActivity.this, "Enter Email!! ", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "We have sent a reset password link to email: " + email, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Email not found in database!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}