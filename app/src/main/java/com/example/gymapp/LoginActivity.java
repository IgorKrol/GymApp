package com.example.gymapp;

import android.os.Bundle;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.appcompat.app.AppCompatActivity ;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText input_email, input_password;
    TextView signUp_btn_on_login, forgot_password_on_login;
    Button login_btn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        login_btn = (Button) findViewById(R.id.login_btn);
        signUp_btn_on_login = (TextView) findViewById(R.id.signup_btn_on_login);
        forgot_password_on_login = (TextView) findViewById(R.id.forgot_password_on_login);
        login_btn.setOnClickListener(this);
        signUp_btn_on_login.setOnClickListener(this);
        forgot_password_on_login.setOnClickListener(this);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        updateUI(user);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup_btn_on_login) {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        } else if (v.getId() == R.id.forgot_password_on_login) {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        } else if (v.getId() == R.id.login_btn) {
            loginUser(input_email.getText().toString(), input_password.getText().toString());
        }
    }

    /*------------ Below Code is for successful login process -----------*/

    private void loginUser(String email, String password) {
        if(email.equals("")){
            Toast.makeText(LoginActivity.this, "Enter Email!!",
                    Toast.LENGTH_SHORT).show();
        }
        else if(password.equals("")){
            Toast.makeText(LoginActivity.this, "Enter Password!!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void updateUI(FirebaseUser user) {
        user = mAuth.getCurrentUser();
        /*-------- Check if user is already logged in or not--------*/
        if (user != null) {
            /*------------ If user's email is verified then access login -----------*/
            if(user.isEmailVerified()){
                Toast.makeText(LoginActivity.this, "Login Success.",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
            else {
                Toast.makeText(LoginActivity.this, "Your Email is not verified.",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        }

    }
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}

