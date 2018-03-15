package com.example.devendra.firebaseauthui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;

public class Portal extends AppCompatActivity
{




    TextView tvLatitude, tvLongitude, tvTime;
    Button btnBuyer,btnFarmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);

        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvTime = (TextView) findViewById(R.id.tvTime);
        btnBuyer = (Button) findViewById(R.id.btnBuyer);
        btnFarmer = (Button) findViewById(R.id.btnFarmer);

        getLocationUpdates();
        btnFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Portal.this ,MainActivity.class);

                SharedPreferences.Editor editor= getSharedPreferences("Mypref",MODE_PRIVATE).edit();
                editor.putString("type","farmer");
                editor.putString("lon",tvLongitude.getText().toString());
                editor.putString("lat",tvLatitude.getText().toString());
                editor.commit();

                startActivity(i);

            }
        });

        btnBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Portal.this , MainActivity.class);

                SharedPreferences.Editor editor= getSharedPreferences("Mypref",MODE_PRIVATE).edit();
                editor.putString("type","buyer");

                editor.putString("lon",tvLongitude.getText().toString());
                editor.putString("lat",tvLatitude.getText().toString());
                editor.commit();
                startActivity(i);

            }
        });
    }

    private void getLocationUpdates() {

        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        Log.d("Devendra", "location update " + location.getLatitude() + " " + location.getLatitude());
                        tvLatitude.setText(Double.toString(location.getLatitude()));
                        tvLongitude.setText(Double.toString(location.getLongitude()));
                    }
                }
            }, null);

        }
        if(permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},105);


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==105 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
            getLocationUpdates();

        }
        else
        {
            finish();
        }
    }



}
