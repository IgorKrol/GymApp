package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddInstructorActivity extends AppCompatActivity {

    Button btnAddInst;
    FirebaseDatabase instDB;
    DatabaseReference dbRootRef;
    EditText name_input, adress_input, info_input;
    EditText birthday_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);

        instDB = FirebaseDatabase.getInstance();
        dbRootRef = instDB.getReference();
        btnAddInst = (Button)findViewById(R.id.add_inst_button);

        name_input = (EditText)findViewById(R.id.name_input);
        birthday_input = (EditText) findViewById(R.id.birthday_input);
        adress_input = (EditText)findViewById(R.id.adress_input);
        info_input = (EditText)findViewById(R.id.info_input);




        btnAddInst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstractorData insData = new InstractorData(name_input.getText().toString(),birthday_input.getText().toString(),adress_input.toString(),info_input.toString(),0);
                insData.setId(dbRootRef.push().getKey());
                dbRootRef.child("Instructors").child(insData.getId()).setValue(insData);
            }
        });
    }
}
