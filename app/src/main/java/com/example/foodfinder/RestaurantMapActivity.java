package com.example.foodfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.Task;

public class RestaurantMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double mLatitude;
    private double mLongitude;
    private Integer count;
    private Spinner spinner;
    private static final int request_code = 101;
    public static final int QUALITY_BALANCED_POWER_ACCURACY = 102;
    private double lat,lng;
    private GoogleMap mMap;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_map);
        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        //url for connecting to the places API
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("myLatLng"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        getCurrentLocation();
    }

    public void getLocation(View view) {

    }

    private void getCurrentLocation() {
        if(ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
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
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                LatLng currentLocation = new LatLng(lat, lng);
                MarkerOptions currentMarkerOption = new MarkerOptions();
                mMap.addMarker(currentMarkerOption.position(currentLocation).title("Current Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

                //Gets nearby restaurants.
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location="+lat+","+lng);
                stringBuilder.append("&radius=1000");
                stringBuilder.append("&type=restaurant");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=AIzaSyAkGQa4TjhJcoDDbgtZTbI02Um4VgJOUCs");

                String url = stringBuilder.toString();
                Object dataFetch[] = new Object[2];
                dataFetch[0] = mMap;
                dataFetch[1] = url;

                FetchMapData fetchMapData = new FetchMapData();
                fetchMapData.execute(dataFetch);
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
