package com.example.foodfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsactivity);
    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loggedIn", true);
        startActivity(intent);
    }
}