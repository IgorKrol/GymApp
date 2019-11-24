package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity implements View.OnClickListener {
    Button News;
    Button Instructors;
    Button Fitness_Divices;
    Button Fitness_Advice;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            News.setOnClickListener(this);
            Instructors.setOnClickListener(this);
            Fitness_Divices.setOnClickListener(this);
            Fitness_Advice.setOnClickListener(this);
        }

    public void onClick(View view) {
        
    }
    }
