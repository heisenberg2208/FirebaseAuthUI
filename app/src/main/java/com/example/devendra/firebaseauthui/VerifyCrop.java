package com.example.devendra.firebaseauthui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class VerifyCrop extends AppCompatActivity {

    private ImageView imgCrop;
    private TextView tvImageTitle;
    private Button btnAccept , btnReject;
    private ProgressBar pbLoad;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_crop);


        imgCrop=  (ImageView) findViewById(R.id.imgCrop);
        tvImageTitle = (TextView) findViewById(R.id.tvImageTitle);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnReject = (Button) findViewById(R.id.btnReject);
        pbLoad = (ProgressBar) findViewById(R.id.pbLoad);
        pbLoad.setVisibility(ProgressBar.INVISIBLE);
        Intent i = getIntent();
        tvImageTitle.setText("Crop Name is" + i.getStringExtra("crop_name"));
        String f_id = i.getStringExtra("f_id");
        databaseReference = FirebaseDatabase.getInstance().getReference().child(f_id+"/status");

        pbLoad.setVisibility(ProgressBar.VISIBLE);
//        Glide.with(imgCrop.getContext())
//                .load(i.getStringExtra("image"))
//                .into(imgCrop);

        String img =i.getStringExtra("image");

        Log.d("image",img);
        Picasso.get().load(img).into(imgCrop);
        pbLoad.setVisibility(ProgressBar.INVISIBLE);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.setValue("Approved").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VerifyCrop.this, "Crop pic Accepted", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.setValue("Rejected").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VerifyCrop.this, "Crop Pic rejected", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}
