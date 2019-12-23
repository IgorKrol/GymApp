package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class My_appointments extends AppCompatActivity {
    FirebaseDatabase myDB;
    DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    String out="";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("check","12344321");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        myDB = FirebaseDatabase.getInstance();
        dbRef=myDB.getReference();
        mAuth = FirebaseAuth.getInstance();
        textView=(TextView)findViewById(R.id.my_appointments_txt);
        ValueEventListener vel = dbRef.child("Appointment").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    Appointment appointment = data.getValue(Appointment.class);
                    if (appointment.getDate() != null) {
                        if(appointment.getUser_id().equals(mAuth.getCurrentUser().getUid())) {
                            out+="Date:   " + appointment.getDate().toString() + "\n" + "with:   " +dbRef.child("Instructors").child(appointment.getInstructor_id()).child("name").toString()+"\n";

                        }
                    }
                }
                Log.i("test",out);
                textView.setText(out);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        dbRef.addValueEventListener(vel);

    }
}
