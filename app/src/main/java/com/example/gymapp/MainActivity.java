package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btnInstructors,btnBuy,btnDevices;
    FirebaseAuth mAuth;
    private FirebaseUser user;
    Boolean isManager=false;
    Menu menu;
    public static boolean isActiveMembership = false;


    TextView welcomeText,membershipText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeText = findViewById(R.id.welcome_text_2);
        membershipText = findViewById(R.id.membership_text);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btnDevices = (Button)findViewById(R.id.devices_button) ;
        btnDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DevicesActivity.class));
            }
        });
        btnInstructors = (Button)findViewById(R.id.instractors_button);
        btnInstructors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, instructors.class));
            }
        });
        btnBuy = (Button)findViewById(R.id.buy_button);





        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null)
                {
                    if(membershipText.getText().toString().equals("Membership: inactive"))
                        startActivity(new Intent(MainActivity.this, DeclarationActivity.class));
                    else
                        Toast.makeText(MainActivity.this, "Still got active membership",
                                Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(MainActivity.this, "You need to register first",
                            Toast.LENGTH_SHORT).show();
            }
        });
        //~~~display welcome text~~~//

        if(user!=null) {

            final String userID = user.getUid();
            final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

            myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    isManager=Boolean.parseBoolean(dataSnapshot.child("isManager").getValue().toString());
                    if(isManager){
                        menu.findItem(R.id.manage).setVisible(true);
                    }
                    else menu.findItem(R.id.manage).setVisible(false);
                    String userName =  dataSnapshot.child("user_name").getValue().toString();
                    welcomeText.setText("Welcome "+userName);
                    String isMember = dataSnapshot.child("isMember").getValue().toString();
                    if(isMember.equals("true")) {
                        String dateString = dataSnapshot.child("membership").child("endDate").getValue().toString();
                        try {
                            Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(dateString);
                            boolean activeMemb = isActiveMemb(date);
                            isActiveMembership = activeMemb;
                            if (activeMemb) {
                                membershipText.setText("Membership: active");
                                btnBuy.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                membershipText.setText("Membership: inactive");
                                btnBuy.setVisibility(View.VISIBLE);
                                myRef.child("Users").child(userID).child("isMember").setValue(false);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }


    }

    public boolean isActiveMemb (Date date)
    {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
        int ans = currentDate.compareTo(date);
        if(ans>0)
            return false;
        else
            return true;
    }

    /* menu creation */
    public boolean onCreateOptionsMenu(Menu menu2) {
        menu=menu2;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        if(isManager){
            menu.findItem(R.id.manage).setVisible(true);
        }
        else menu.findItem(R.id.manage).setVisible(false);


        if(mAuth.getCurrentUser()!=null) {
            menu.findItem(R.id.login).setVisible(false);
            menu.findItem(R.id.logout).setVisible(true);

        }
        else{
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.logout).setVisible(false);

        }
        return true;
    }

    /* menu options */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                return true;
            case R.id.manage:
                startActivity(new Intent(MainActivity.this,ManagerActivity.class));
                return true;
            case R.id.appointment_menu:
                startActivity(new Intent(MainActivity.this,My_appointments.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(MainActivity.this,SignoutActivity.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}



