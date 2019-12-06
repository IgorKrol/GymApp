package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.Intent;


public class SignoutActivity extends AppCompatActivity implements View.OnClickListener {


    Button signout;
    TextView welcome_text;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);
        welcome_text = (TextView)findViewById(R.id.welcome_text);
        signout = (Button)findViewById(R.id.signout);

        signout.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user!=null){
            String name = user.getDisplayName();
            String email_id = user.getEmail();
            welcome_text.setText("Welcome "+name+"\n"+email_id);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signout){
            mAuth.signOut();
            Toast.makeText(SignoutActivity.this,"Logged out!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(SignoutActivity.this,LoginActivity.class));
        }
}
}
