package com.example.foodfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantListActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int request_code = 101;
    private double lat,lng;
    private ArrayList<String> restaurants;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        getCurrentLocation();
        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        //Gets nearby restaurants.



    }

    public ArrayList<String> getRestaurants() {
        return restaurants;
    }

    class FetchListData extends AsyncTask<Object, String, String> {
        String googleNearbyPlacesData;
        String url;

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject getInfo = jsonArray.getJSONObject(i);
                    String name = getInfo.getString("name");
                    String vicinity = getInfo.getString("vicinity");
                    double rating = 0;
                    try {
                        rating = getInfo.getDouble("rating");
                    } catch (Exception e) {
                    }
                    String string = name + "\n" +
                            "Vicinity: " +
                            vicinity + "\n" +
                            "No Rating";
                    if (rating != 0) {
                         string = name + "\n" +
                                "Vicinity: " +
                                vicinity + "\n" +
                                "Rating: " +
                                rating;
                    }
                    restaurants.add(string);
                }
                Fragment restaurantListFragment = new RestaurantListFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("restaurants" , restaurants);
                restaurantListFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.restaurantListFragment, restaurantListFragment);
                fragmentTransaction.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Object... objects) {

            try {
                url = (String) objects[1];
                DownloadUrl downloadUrl = new DownloadUrl();
                googleNearbyPlacesData = downloadUrl.retrieveURL(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return googleNearbyPlacesData;
        }
    }

    public void backBtnOnClick(View view) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void toMap(View view) {
        Intent intent = new Intent(this, RestaurantMapActivity.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    private void getCurrentLocation() {
        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions
                    (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},request_code);
        }
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 60000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(1000)
                .setMaxUpdateDelayMillis(2000)
                .build();
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for(Location location: locationResult.getLocations()) {
                    if (location!=null) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                    }

                }
                if (restaurants == null) {
                    restaurants = new ArrayList<>();
                }

            }
        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location="+lat+","+lng);
                stringBuilder.append("&radius=1000");
                stringBuilder.append("&type=restaurant");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=AIzaSyAkGQa4TjhJcoDDbgtZTbI02Um4VgJOUCs");
                String url = stringBuilder.toString();
                Object dataFetch[] = new Object[2];
                dataFetch[1] = url;
                FetchListData fetchListData = new FetchListData();
                fetchListData.execute(dataFetch);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (request_code){
            case request_code:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
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
}