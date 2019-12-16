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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;

public class  SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText user_email,user_password,user_name,user_height,user_weight,confirm_password;
    TextView login_btn_on_signup;
    Button signup_btn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user_email = (EditText)findViewById(R.id.user_email);
        user_password = (EditText)findViewById(R.id.user_password);
        confirm_password = (EditText)findViewById(R.id.confirm_password);
        user_name = (EditText)findViewById(R.id.user_name);
        user_height = (EditText)findViewById(R.id.height);
        user_weight = (EditText)findViewById(R.id.weight);
        signup_btn = (Button) findViewById(R.id.register_btn);
        login_btn_on_signup = (TextView) findViewById(R.id.login_btn_on_signup);
        login_btn_on_signup.setOnClickListener(this);
        signup_btn.setOnClickListener(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
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
        else if((user_height.getText().toString()).equals("")){
            Toast.makeText(SignUpActivity.this, "Enter Height!!",
                    Toast.LENGTH_SHORT).show();
        }
        else if((user_weight.getText().toString()).equals("")){
            Toast.makeText(SignUpActivity.this, "Enter Weight!!",
                    Toast.LENGTH_SHORT).show();
        }
        //////////////
        else if(!((user_password.getText().toString()).equals(confirm_password.getText().toString())))
        {
            Toast.makeText(SignUpActivity.this, "Passwords don't match",
                    Toast.LENGTH_SHORT).show();
        }
        /////////////
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

    /*----------For saving user in Firebase Database-------*/
    private void userProfile() {
        user = mAuth.getCurrentUser();
        String userID = user.getUid();
        String email = user_email.getText().toString();
        String name = user_name.getText().toString();
        String weight = user_weight.getText().toString();
        String height = user_height.getText().toString();
        if(user != null){
            User newUser = new User(email,name,weight,height);
            myRef.child("Users").child(userID).setValue(newUser);

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
