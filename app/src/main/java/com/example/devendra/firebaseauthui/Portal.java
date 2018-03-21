package com.example.devendra.firebaseauthui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
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

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Portal extends AppCompatActivity
{

    public String getLoc(String lat, String lon)
    {
        Geocoder g = new Geocoder(Portal.this, Locale.ENGLISH);

        List<Address> addressList = null;
        try {
            addressList = g.getFromLocation(Double.parseDouble(lat) , Double.parseDouble(lon) ,1);
            Address address = addressList.get(0);
            String add =
                    address.getCountryName()+" "+
                            address.getAdminArea()+" "+
                            address.getSubAdminArea()+" "+
                            address.getLocality()+" "+
                            address.getSubLocality();
            return add;

        } catch (IOException e) {
            return "Some error while getting address";
        }

    }


    TextView tvLatitude, tvLongitude, tvTime,tvAddress;
    Button btnBuyer,btnFarmer,btnChat;
    static String lon;
    static String lat;
    static String add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);

        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        btnBuyer = (Button) findViewById(R.id.btnBuyer);
        btnFarmer = (Button) findViewById(R.id.btnFarmer);
        btnChat  = (Button) findViewById(R.id.btnChat);

        getLocationUpdates();

        final SharedPreferences.Editor editor= getSharedPreferences("Mypref",MODE_PRIVATE).edit();

        lon=tvLongitude.getText().toString();
        if(lon.equals(""))
            lon="73.093948";
        tvLongitude.setText(lon);

        lat =tvLatitude.getText().toString();
        if(lat.equals(""))
            lat="19.209401";
        tvLatitude.setText(lat);



        btnFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Portal.this ,MainActivity.class);
                String lon1=tvLongitude.getText().toString();
                editor.putString("lon",lon1);


                String lat1 = tvLatitude.getText().toString();
                editor.putString("lat",lat1);
                String add = tvAddress.getText().toString();
                editor.putString("add",add);

                editor.putString("type","farmer");
                editor.commit();

                startActivity(i);

            }
        });

        btnBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Portal.this , MainActivity.class);

                String lon1=tvLongitude.getText().toString();
                editor.putString("lon",lon1);


                String lat1 = tvLatitude.getText().toString();
                editor.putString("lat",lat1);

                String add = tvAddress.getText().toString();
                editor.putString("add",add);
                editor.putString("type","buyer");
                editor.commit();
                startActivity(i);

            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Portal.this , ChatActivity.class);


                String lon1=tvLongitude.getText().toString();
                editor.putString("lon",lon1);

                String lat1 = tvLatitude.getText().toString();
                editor.putString("lat",lat1);

                String add = tvAddress.getText().toString();
                editor.putString("add",add);


                startActivity(i);
                finish();
            }
        });
    }

    private void getLocationUpdates() {

        LocationRequest request = new LocationRequest();
        request.setInterval(100);
        request.setFastestInterval(50);
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
                        lat=String.valueOf(location.getLatitude());
                        tvLatitude.setText(lat);
                        lon=String.valueOf(location.getLongitude());

                        tvLongitude.setText(lon);
                        add = getLoc(lat,lon);
                        tvAddress.setText(add);
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
