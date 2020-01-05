package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class BuyoutActivity extends AppCompatActivity {

    CheckBox sixMonthBox,oneYearBox,twoYearBox;
    Button buyoutBtn;
    EditText addressTxt,pidTxt,phoneTxt;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyout);
        sixMonthBox = findViewById(R.id.six_month_box);
        oneYearBox = findViewById(R.id.one_year_box);
        twoYearBox = findViewById(R.id.two_year_box);
        buyoutBtn = (Button)findViewById(R.id.payment_button);
        addressTxt = findViewById(R.id.address_text);
        pidTxt = findViewById(R.id.pid_text);
        phoneTxt = findViewById(R.id.phone_text);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        user = mAuth.getCurrentUser();

        sixMonthBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sixMonthBox.setChecked(true);
                oneYearBox.setChecked(false);
                twoYearBox.setChecked(false);

            }
        });
        oneYearBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                oneYearBox.setChecked(true);
                sixMonthBox.setChecked(false);
                twoYearBox.setChecked(false);
            }
        });
        twoYearBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                twoYearBox.setChecked(true);
                sixMonthBox.setChecked(false);
                oneYearBox.setChecked(false);
            }
        });

        buyoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkFields();
                startActivity(new Intent(BuyoutActivity.this,MainActivity.class));
            }
        });
    }

    private void checkFields()
    {
        if(pidTxt.getText().toString().equals("")){
            Toast.makeText(BuyoutActivity.this, "Enter ID!!",
                    Toast.LENGTH_SHORT).show();
        }
        else if(addressTxt.getText().toString().equals("")){
            Toast.makeText(BuyoutActivity.this, "Enter Address!!",
                    Toast.LENGTH_SHORT).show();
        }
        else if(phoneTxt.getText().toString().equals("")){
            Toast.makeText(BuyoutActivity.this, "Enter Phone Number!!",
                    Toast.LENGTH_SHORT).show();
        }
        else
            createMembership();

    }

    private void createMembership()
    {
        String userID = user.getUid();
        String pid = pidTxt.getText().toString();
        String address = addressTxt.getText().toString();
        String phone = phoneTxt.getText().toString();
        String type="";

       Calendar cal = Calendar.getInstance();
       Date date = cal.getTime();
       String startDate = date.toString();
       if(sixMonthBox.isChecked()) {
           type = "6 months";
           cal.add(Calendar.MONTH, 6);
       }
       if(oneYearBox.isChecked()) {
           type = "1 year";
           cal.add(Calendar.MONTH, 12);
       }
       if(twoYearBox.isChecked()) {
           type = "2 year";
           cal.add(Calendar.MONTH, 24);
       }
       date = cal.getTime();
       String endDate = date.toString();

       Membership membership = new Membership(userID,type,startDate,endDate,pid,phone,address);
        membership.setMembershipID(myRef.push().getKey());
        myRef.child("Memberships").child(membership.getMembershipID()).setValue(membership);
        myRef.child("Users").child(userID).child("membership").setValue(membership);
        myRef.child("Users").child(userID).child("isMember").setValue("true");




    }




}
