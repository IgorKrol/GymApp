package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Add device activity
 */
public class AddDeviceActivity extends AppCompatActivity {

    Button btnAddDev;
    FirebaseDatabase instDB;
    DatabaseReference dbRootRef;
    EditText name_input, url_input;
    DeviceData dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        instDB = FirebaseDatabase.getInstance();
        dbRootRef = instDB.getReference();
        btnAddDev = (Button)findViewById(R.id.dev_add_device);

        name_input = (EditText)findViewById(R.id.dev_name_input);
        url_input = (EditText)findViewById(R.id.dev_url_input);

        btnAddDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd = new DeviceData(name_input.getText().toString(),url_input.getText().toString() ,R.drawable.ic_launcher_foreground);
                dd.setId(dbRootRef.push().getKey());
                dbRootRef.child("Devices").child(dd.getId()).setValue(dd);
            }
        });



    }
}
