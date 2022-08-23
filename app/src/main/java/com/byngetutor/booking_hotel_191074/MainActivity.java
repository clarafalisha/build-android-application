package com.byngetutor.booking_hotel_191074;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button mbtn_booking, mbtn_history, mbtn_maps, mbtn_about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("HOME");
        mbtn_booking = findViewById(R.id.btn_booking);
        mbtn_history = findViewById(R.id.btn_history);
        mbtn_maps = findViewById(R.id.btn_map);
        mbtn_about = findViewById(R.id.btn_about);

        mbtn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, booking.class);
                startActivity(intent);
            }
        });

        mbtn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListItem.class);
                startActivity(intent);
            }
        });

        mbtn_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);

            }
        });

        mbtn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, about .class);
                startActivity(intent);
            }
        });
    }
    }