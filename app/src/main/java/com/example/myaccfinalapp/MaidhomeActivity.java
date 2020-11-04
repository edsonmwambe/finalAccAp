package com.example.myaccfinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MaidhomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maidhome);
        getSupportActionBar().hide();
    }
}