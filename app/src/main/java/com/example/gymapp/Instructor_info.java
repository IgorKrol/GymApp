package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Instructor_info extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener{
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_info);

        TextView textView=(TextView)findViewById(R.id.instructor_info_view);
        Intent intent=getIntent();
        String info="";
        info+="Name:  "+intent.getStringExtra("name")+"\n";
        info+="Address:  "+intent.getStringExtra("address")+"\n";
        info+="Birthday:  "+intent.getStringExtra("birthday")+"\n";
        //info+="image:"+intent.getStringExtra("image_id")+"\n";
        info+="Info:  "+intent.getStringExtra("info")+"\n";
        textView.setText(info);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        findViewById(R.id.appointment_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                showDatePickerDialog();
            }
//
        });
    }
    private void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public void onDateSet(DatePicker view,int year,int month,int dayofMonth)
    {
        String date = dayofMonth + "/" + month + "/" + year;
        Appointment appointment=new Appointment(date,getIntent().getStringExtra("id"),mAuth.getCurrentUser().getUid());
        myRef.child("Appointment").child(myRef.push().getKey()).setValue(appointment);
    }

}

