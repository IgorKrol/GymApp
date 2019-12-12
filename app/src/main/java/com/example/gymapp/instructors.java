package com.example.gymapp;

//import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;

import android.view.View;

import android.widget.AdapterView;

import android.widget.ListView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class instructors extends AppCompatActivity {


    private ListView instListView;
    FirebaseDatabase instDB;
    DatabaseReference dbRootRef;
    List<InstractorData> listViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors);
        instDB = FirebaseDatabase.getInstance();
        dbRootRef = instDB.getReference();

        instListView = (ListView)findViewById(R.id.listView);
        listViewItems  = new ArrayList<InstractorData>();

        ValueEventListener dbListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                InstractorData instractorData = dataSnapshot.child("Instructors").getValue(InstractorData.class);
                listViewItems.add(instractorData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("load Failed",databaseError.toException());
            }
        };
        dbRootRef.addValueEventListener(dbListener);




        instListView.setAdapter(new CustomAdapter(this, listViewItems));

        // On click action
        instListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();

            }

        });

    }
}
