package com.example.foodfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FindFoodActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findfood);

    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loggedIn", true);
        startActivity(intent);
    }

}
