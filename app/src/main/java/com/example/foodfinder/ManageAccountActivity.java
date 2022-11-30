package com.example.foodfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ManageAccountActivity extends AppCompatActivity {
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageaccount);
        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        if (currentUser != null) {
            String name = "name: " + currentUser.getFirstName() + " " + currentUser.getLastName();
            String phone = "phone #: " +currentUser.getPhoneNumber();
            String email = "email: " +currentUser.getEmailAddress();
            TextView usernameManage = findViewById(R.id.usernameManage);
            TextView nameManage = findViewById(R.id.nameManage);
            TextView phoneManage = findViewById(R.id.phoneNumberManage);
            TextView emailManage = findViewById(R.id.emailAddressManage);
            usernameManage.setText(currentUser.getUsername());
            nameManage.setText(name);
            phoneManage.setText(phone);
            emailManage.setText(email);
        }
    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    public void toFindFood(View view){
        Intent intent = new Intent(this, RestaurantListActivity.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    public void toManageAccount(View view){
        Intent intent = new Intent(this, ManageAccountActivity.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    public void toRecentReviews(View view) {
        Intent intent = new Intent(this, RecentReviewsActivity.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }


}
