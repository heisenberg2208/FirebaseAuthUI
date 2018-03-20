package com.example.devendra.firebaseauthui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Buyer extends AppCompatActivity {

    private TextView tvLon , tvLat;
    private Button btnSearchFarmer;
    private Spinner spnCrops;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
//
//        tvLon = (TextView) findViewById(R.id.tvLon);
//        tvLat = (TextView)findViewById(R.id.tvLat);
        btnSearchFarmer = (Button) findViewById(R.id.btnSearchFarmer);

        spnCrops = (Spinner) findViewById(R.id.spnCrops);

        SharedPreferences sharedPreferences = getSharedPreferences("Mypref",MODE_PRIVATE);
        String lon=sharedPreferences.getString("lon","");
        String lat  = sharedPreferences.getString("lat","");

        String add = sharedPreferences.getString("add","");

//        tvLon.setText(add);

        String name = sharedPreferences.getString("user","");

        String f_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User u = new User(f_id,name,lat,lon,add);
        FirebaseDatabase.getInstance().getReference().child("Buyer").child(f_id).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Buyer.this, "Buyer added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Buyer.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

//        btnSearchFarmer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Buyer.this , FarmerList.class);
//                startActivity(i);
//            }
//        });

        spnCrops.setEnabled(false);
        spnCrops.setClickable(false);

        DatabaseReference requests = FirebaseDatabase.getInstance().getReference().child("Requests");
        requests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> alCrops = new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren())
                {
                    Request r = child.getValue(Request.class);
                    String crop=r.getCrop_name();
                    if(! alCrops.contains(crop))
                        alCrops.add(crop);
                }

                alCrops.add(0,"Click here to select crops");
                ArrayAdapter<String> aaCrops = new ArrayAdapter<String>(Buyer.this,android.R.layout.simple_spinner_item,alCrops);
                spnCrops.setAdapter(aaCrops);

                spnCrops.setEnabled(true);
                spnCrops.setClickable(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spnCrops.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                    return;
                String crop = adapterView.getItemAtPosition(i).toString();
                Intent in = new Intent(Buyer.this , FarmerList.class);
                in.putExtra("crop",crop);
                startActivity(in);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
