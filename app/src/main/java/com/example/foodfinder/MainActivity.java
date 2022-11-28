package com.example.foodfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    String firstName, lastName, phoneNumber, emailAddress, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the instance of the Firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();
        // get the reference to the JSON tree
        databaseReference = firebaseDatabase.getReference();


        Button toLogin = findViewById(R.id.SignOutBtn);
        toLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toFindFood(View view){
        Intent intent = new Intent(this, RestaurantListActivity.class);
        startActivity(intent);
    }
    public void toManageAccount(View view){
        Intent intent = new Intent(this, ManageAccountActivity.class);
        startActivity(intent);
    }
}