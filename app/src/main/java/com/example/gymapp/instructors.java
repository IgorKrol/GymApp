package com.example.gymapp;

//import android.support.v7.app.ActionBarActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.util.IslamicCalendar;
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

import java.time.Clock;
import java.util.ArrayList;

import java.util.Iterator;
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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors);
        context = this;
        instDB = FirebaseDatabase.getInstance();
        dbRootRef = instDB.getReference();

        instListView = (ListView)findViewById(R.id.listView);
        listViewItems  = new ArrayList<InstractorData>();
        ValueEventListener vel = dbRootRef.child("Instructors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){

                    InstractorData idata = data.getValue(InstractorData.class);
                    if(idata.getName() != null)
                        listViewItems.add(idata);
                }
                instListView.setAdapter(new CustomAdapter(context, listViewItems));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        dbRootRef.addValueEventListener(vel);

        instListView.setAdapter(new CustomAdapter(this, listViewItems));

        // On click action
        instListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(instructors.this, Instructor_info.class);
                InstractorData ins=listViewItems.get(position);
                intent.putExtra("name", ins.getName());
                intent.putExtra("address",ins.getAddress());
                intent.putExtra("birthday",ins.getBirthday());
                intent.putExtra("image_id",ins.getImageId());
                intent.putExtra("info",ins.getInfo());
                intent.putExtra("id",ins.getId());
                startActivity(intent);

            }

        });
    }
}
