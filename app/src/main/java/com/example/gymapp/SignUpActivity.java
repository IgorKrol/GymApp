package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import android.os.Bundle;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText user_email,user_password,user_name;
    TextView login_btn_on_signup;
    Button signup_btn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user_email = (EditText)findViewById(R.id.user_email);
        user_password = (EditText)findViewById(R.id.user_password);
        user_name = (EditText)findViewById(R.id.user_name);
        signup_btn = (Button) findViewById(R.id.register_btn);
        login_btn_on_signup = (TextView) findViewById(R.id.login_btn_on_signup);
        login_btn_on_signup.setOnClickListener(this);
        signup_btn.setOnClickListener(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_btn_on_signup){
            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
        }
        else if(v.getId() == R.id.register_btn){
            signUpUser(user_email.getText().toString(),user_password.getText().toString());
        }


    }

    /*------------ Below Code is for successful sign up process -----------*/

    private void signUpUser(String email, String password) {
        if(email.equals("")){
            Toast.makeText(SignUpActivity.this, "Enter Email!!",
                    Toast.LENGTH_SHORT).show();
        }
        else if(password.equals("")){
            Toast.makeText(SignUpActivity.this, "Enter Password!!",
                    Toast.LENGTH_SHORT).show();
        }
        else if((user_name.getText().toString()).equals("")){
            Toast.makeText(SignUpActivity.this, "Enter Name!!",
                    Toast.LENGTH_SHORT).show();
        }

        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                userProfile();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Sign up failed: " + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /*----------For saving image and user name in Firebase Database-------*/
    private void userProfile() {
        user = mAuth.getCurrentUser();
        if(user != null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(user_name.getText().toString())
                    .setPhotoUri(null).build();

            user.updateProfile(profileUpdates).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    verifyEmailRequest();
                }
            });
        }
    }
    /*-------For sending verification email on user's registered email------*/
    private void verifyEmailRequest() {
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this,"Email verification sent on\n"+user_email.getText().toString(),Toast.LENGTH_LONG).show();
                            mAuth.signOut();
                            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"Sign up Success But Failed to send verification email.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



}
