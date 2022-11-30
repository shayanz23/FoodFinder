package com.example.foodfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText edtUsername, edtPassword;
    private String username, password;
    private boolean match;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get the instance of the Firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();
        // get the reference to the JSON tree
        databaseReference = firebaseDatabase.getReference();

        edtUsername = findViewById(R.id.usernameLogin);
        edtPassword = findViewById(R.id.passwordLogin);
        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        Button loginBtn = findViewById(R.id.LoginBtn);
        loginBtn.setOnClickListener(view -> {
            if (authenticateUser()) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);
            }
        });

        Button toSignup = findViewById(R.id.SignupBtn);
        toSignup.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
    }

    protected boolean authenticateUser() {
        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG).show();
            return false;
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Comparing " + username + " with ");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (username.equals(user.getUsername())
                    && password.equals(user.getPassword())) {
                        match = true;
                        Toast.makeText(LoginActivity.this, "Access granted", Toast.LENGTH_LONG).show();
                        currentUser = user;
                    }
                }
                if(!match) {
                    Toast.makeText(LoginActivity.this, "Access denied", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return match;
    }
}