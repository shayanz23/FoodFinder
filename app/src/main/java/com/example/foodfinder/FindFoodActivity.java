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

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FindFoodActivity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    private FusedLocationProviderClient fusedLocationClient;
    private double mLatitude;
    private double mLongitude;
    private Integer count;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findfood);

        Bundle bundle = getIntent().getExtras();
        String apiKey = getString(R.string.googlemapsAPI);
        String codeToSearch = bundle.getString("codeToSearch");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(apiKey);

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("myLatLng"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loggedIn", true);
        startActivity(intent);
    }


    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            RequestQueue queue = Volley.newRequestQueue(FindFoodActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray resultArray = response.getJSONArray("response");
                        for(int i = 0; i < 5; i++) {
                            //FOR THE FIRST 5 ENTRIES IN THE RESULT ARRAY
                            JSONObject obj = resultArray.getJSONObject(i);
                            System.out.println(obj);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, error -> Toast.makeText(FindFoodActivity.this, error.toString(), Toast.LENGTH_LONG).show());
            queue.add(request);
            return null;
        }
    }

}
