package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btnInstructors;
    FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnInstructors = (Button)findViewById(R.id.instractors_button);
        btnInstructors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, instructors.class));
            }
        });
        //~~~display welcome text~~~//

        welcomeText = findViewById(R.id.welcome_text_2);
        user = mAuth.getCurrentUser();
        if(user!=null) {

            String userID = user.getUid();
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

            myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String userName =  dataSnapshot.child("user_name").getValue().toString();
                    welcomeText.setText("Welcome "+userName);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }


    }

    /* menu creation */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        if(mAuth.getCurrentUser()!=null) {
            menu.findItem(R.id.login).setVisible(false);
            menu.findItem(R.id.logout).setVisible(true);
        }
        else{
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.logout).setVisible(false);
        }
        return true;
    }

    /* menu options */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                return true;
            case R.id.manage:
                startActivity(new Intent(MainActivity.this,ManagerActivity.class));
                return true;
            case R.id.appointment_menu:
                startActivity(new Intent(MainActivity.this,My_appointments.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(MainActivity.this,SignoutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



