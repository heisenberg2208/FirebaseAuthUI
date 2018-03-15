package com.example.devendra.firebaseauthui;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Buyer extends AppCompatActivity {

    private TextView tvLon , tvLat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        tvLon = (TextView) findViewById(R.id.tvLon);
        tvLat = (TextView)findViewById(R.id.tvLat);

        SharedPreferences sharedPreferences = getSharedPreferences("Mypref",MODE_PRIVATE);
        String lon=sharedPreferences.getString("lon","");
        String lat  = sharedPreferences.getString("lat","");

        tvLon.setText(lon);
        tvLat.setText(lat);


        String f_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User u = new User(f_id,"name",lat,lon);
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
    }
}
