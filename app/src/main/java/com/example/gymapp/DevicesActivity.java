package com.example.gymapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DevicesActivity extends AppCompatActivity {

    private ListView devListView;
    FirebaseDatabase DB;
    DatabaseReference dbRootRef;
    List<DeviceData> listViewItems;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);
        context = this;
        DB = FirebaseDatabase.getInstance();
        dbRootRef = DB.getReference();

        devListView = (ListView)findViewById(R.id.devicesListView);

        listViewItems  = new ArrayList<DeviceData>();
        ValueEventListener vel = dbRootRef.child("Devices").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){

                    DeviceData dd = data.getValue(DeviceData.class);
                    if(dd.getName() != null)
                        listViewItems.add(dd);
                }
                devListView.setAdapter(new CustomAdapterDevices(context, listViewItems));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        dbRootRef.addValueEventListener(vel);

        devListView.setAdapter(new CustomAdapterDevices(this, listViewItems));

        // On click action
        devListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
            @Override
//
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceData dd = listViewItems.get(position);
//                String url = dd.getUrl();
                Log.i("URL", "111");
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                if (browserIntent.resolveActivity(getPackageManager()) != null) {
//                    // Here we use an intent without a Chooser unlike the next example
//                    startActivity(browserIntent);
//                }
            }
        });
    }
}
