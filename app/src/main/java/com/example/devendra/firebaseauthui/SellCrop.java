package com.example.devendra.firebaseauthui;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellCrop extends AppCompatActivity {

    private EditText etCropName;
    private Button btnSell;
    private Button btnModerator;


    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_crop);


        etCropName = (EditText) findViewById(R.id.etCropName);
        btnSell = (Button) findViewById(R.id.btnSell);
        btnModerator = (Button) findViewById(R.id.btnModerator);


        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String crop_name = etCropName.getText().toString();
                String f_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference().child(f_id);
                Request r = new Request(crop_name,f_id ,"pending","iamge_url");
                databaseReference.setValue(r).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SellCrop.this, "Request has been added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SellCrop.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        btnModerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SellCrop.this, ModeratorHome.class);
                startActivity(i);

            }
        });


    }
}
