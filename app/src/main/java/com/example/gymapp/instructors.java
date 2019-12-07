package com.example.gymapp;

//import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;

import android.view.Menu;

import android.view.MenuItem;

import android.view.View;

import android.widget.AdapterView;

import android.widget.ListView;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import java.util.List;

public class instructors extends AppCompatActivity {


    private ListView instListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors);
        instListView = (ListView)findViewById(R.id.listView);

        List<InstractorDataForList> listViewItems = new ArrayList<InstractorDataForList>();

        listViewItems.add(new InstractorDataForList("Abby", R.drawable.ic_launcher_foreground));

        listViewItems.add(new InstractorDataForList("Bob", R.drawable.ic_launcher_foreground));

        listViewItems.add(new InstractorDataForList("Chaya", R.drawable.ic_launcher_foreground));

        listViewItems.add(new InstractorDataForList("David", R.drawable.ic_launcher_foreground));

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
