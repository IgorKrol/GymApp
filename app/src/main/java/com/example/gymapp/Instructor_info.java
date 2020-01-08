package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_info);

        TextView textView=(TextView)findViewById(R.id.instructor_info_view);
        intent=getIntent();
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
        String date = dayofMonth + "/" + (month+1) + "/" + year;
        Appointment appointment=new Appointment(date,getIntent().getStringExtra("id").toString(),mAuth.getCurrentUser().getUid());
        myRef.child("Appointment").child(myRef.push().getKey()).setValue(appointment);
        initChannels(this);
        sendNotificationAppointment(date);
        Toast.makeText(this,"Appointment Made",Toast.LENGTH_LONG).show();

    }

    /**
     * init channel for notification
     * @param context
     */
    public void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * send notification function with new appointment
     */
    public void sendNotificationAppointment(String date){
        String msg = date + " with " + intent.getStringExtra("name");
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "default")
                        .setSmallIcon(R.drawable.gymapp_icon) //set icon for notification
                        .setContentTitle("New Appointment") //set title of notification
                        .setContentText(msg)//this is notification message
//                        .setAutoCancel(true) // makes auto cancel of notification
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification


        Intent notificationIntent = new Intent(this, My_appointments.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
//        notificationIntent.putExtra("message", "This is a notification message");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}

