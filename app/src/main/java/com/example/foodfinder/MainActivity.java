package com.example.foodfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            loggedIn = getIntent().getExtras().getBoolean("loggedIn");
        } catch (Exception ignored) {
        }

        if (!loggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        Button toLogin = findViewById(R.id.SignOutBtn);
        toLogin.setOnClickListener(view -> {
            loggedIn = false;
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loggedIn", loggedIn);
        startActivity(intent);
    }

    public void toFindFood(View view){
        Intent intent = new Intent(this, RestaurantListActivity.class);
        intent.putExtra("loggedIn", loggedIn);
        startActivity(intent);
    }
    public void toManageAccount(View view){
        Intent intent = new Intent(this, ManageAccountActivity.class);
        intent.putExtra("loggedIn", loggedIn);
        startActivity(intent);
    }
}