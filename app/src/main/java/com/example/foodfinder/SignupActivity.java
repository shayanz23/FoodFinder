package com.example.foodfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // get the instance of the Firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();
        // get the reference to the JSON tree
        databaseReference = firebaseDatabase.getReference();

        Button toMain = findViewById(R.id.CreateAccountRegBtn);
        toMain.setOnClickListener(view -> {

            // First, check if "Enter password" and "Confirm password" matches
            EditText edtEnterPassword, edtConfirmPassword;
            edtEnterPassword = findViewById(R.id.passwordReg);
            edtConfirmPassword = findViewById(R.id.passwordRepeatReg);

            String enterPassword = edtEnterPassword.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            if (!enterPassword.trim().isEmpty() &&
                    !confirmPassword.trim().isEmpty() &&
                    enterPassword.equals(confirmPassword)) {
                Intent intent = new Intent(this, MainActivity.class);
                // Putting signup values into intent
                EditText edtFirstName, edtLastName, edtPhoneNumber, edtEmailAddress;
                edtFirstName = findViewById(R.id.firstnameReg);
                edtLastName = findViewById(R.id.lastnameReg);
                edtPhoneNumber = findViewById(R.id.phoneNumReg);
                edtEmailAddress = findViewById(R.id.emailReg);

                String firstName, lastName, phoneNumber, emailAddress;
                firstName = edtFirstName.getText().toString().trim();
                lastName = edtLastName.getText().toString().trim();
                phoneNumber = edtPhoneNumber.getText().toString().trim();
                emailAddress = edtEmailAddress.getText().toString().trim();

                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("emailAddress", emailAddress);
                intent.putExtra("password", enterPassword);
                // End


                intent.putExtra("loggedIn", true);

                String id = databaseReference.push().getKey();
                User user = new User(firstName, lastName, phoneNumber, emailAddress, enterPassword);
                Task setValueTask = databaseReference.child("Users").child(id).setValue(user);

                setValueTask.addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(SignupActivity.this, "Account created", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                });

                // add a failure listener to the task
                setValueTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(e.toString(), e.toString());
                        Toast.makeText(SignupActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });


            } else {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
            }


        });
    }
}