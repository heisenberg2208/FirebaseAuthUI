package com.example.devendra.firebaseauthui;

import android.Manifest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;

import android.location.Geocoder;
import android.location.Location;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


import java.io.IOException;

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


    //TextView tvLatitude, tvLongitude, tvTime,tvAddress;
    Button btnBuyer,btnFarmer;
    static String lon;
    static String lat;
    static String add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);

        //tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        //tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        //tvTime = (TextView) findViewById(R.id.tvTime);
        //tvAddress = (TextView) findViewById(R.id.tvAddress);
        btnBuyer = findViewById(R.id.btnBuyer);
        btnFarmer =  findViewById(R.id.btnFarmer);
        //btnChat  = (Button) findViewById(R.id.btnChat);

        getLocationUpdates();

        final SharedPreferences.Editor editor= getSharedPreferences("Mypref",MODE_PRIVATE).edit();

        //lon=tvLongitude.getText().toString();
        if(lon ==null ||lon.equals(""))
            lon="73.093948";
        //tvLongitude.setText(lon);

        //lat =tvLatitude.getText().toString();
        if(lat==null||lat.equals(""))
            lat="19.209401";
        //tvLatitude.setText(lat);



        btnFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Portal.this ,MainActivity.class);
                //String lon1=tvLongitude.getText().toString();
                editor.putString("lon",lon);


                //String lat1 = tvLatitude.getText().toString();
                editor.putString("lat",lat);
                //String add = tvAddress.getText().toString();
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

                //String lon1=tvLongitude.getText().toString();
                editor.putString("lon",lon);


                //String lat1 = tvLatitude.getText().toString();
                editor.putString("lat",lat);

                //String add = tvAddress.getText().toString();
                editor.putString("add",add);
                editor.putString("type","buyer");
                editor.commit();
                startActivity(i);

            }
        });


    }

    private void getLocationUpdates() {

        final LocationRequest request = new LocationRequest();
//        request.setInterval(100);
//        request.setFastestInterval(50);
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
                        //tvLatitude.setText(lat);
                        lon=String.valueOf(location.getLongitude());

                        //tvLongitude.setText(lon);
                        add = getLoc(lat,lon);
                        if(lat !=null && lon!=null && add!=null)
                        {
                            if(! lat.equals("") && ! lon.equals("") && ! add.equals(""))
                            {
                                Toast.makeText(Portal.this, ""+ lon +" "+lat +" "+add, Toast.LENGTH_LONG).show();

          //                      request.setInterval(0);
            //                    request.setFastestInterval(0);
                            }
                        }


                        Log.d("LOcation",""+ lon +" "+lat +" "+add);
                        //tvAddress.setText(add);
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
