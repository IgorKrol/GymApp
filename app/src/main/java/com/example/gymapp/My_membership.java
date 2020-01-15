package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class My_membership extends AppCompatActivity {
    FirebaseDatabase myDB;
    DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    //String out="";
    TextView textView;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_membership);
        myDB = FirebaseDatabase.getInstance();
        dbRef=myDB.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        textView=(TextView)findViewById(R.id.my_membership_txt);

        String userID = user.getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
String out="Started on: ";
                String startDate =  dataSnapshot.child("membership").child("startDate").getValue().toString();
                out+=startDate;
                String endDate =  dataSnapshot.child("membership").child("endDate").getValue().toString();
                out+="\nEnding on: "+endDate;
                textView.setText(out);




    }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
    });
}
}
