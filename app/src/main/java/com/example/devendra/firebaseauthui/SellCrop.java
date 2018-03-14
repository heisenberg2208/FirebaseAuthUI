package com.example.devendra.firebaseauthui;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class SellCrop extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER =123 ;
    private static String img_url;
    private EditText etCropName;
    private Button btnSell;
//    private Button btnModerator;
    private Button btnUpload;
    private ImageView imgUpload;
    private TextView tvLoc;


    Request r;

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_crop);


        etCropName = (EditText) findViewById(R.id.etCropName);
        btnSell = (Button) findViewById(R.id.btnSell);
      //  btnModerator = (Button) findViewById(R.id.btnModerator);
        //btnUpload = (Button) findViewById(R.id.btnUpload);
        imgUpload = (ImageView) findViewById(R.id.imgUpload);
        tvLoc = (TextView) findViewById(R.id.tvLoc);


//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });





        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);






            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Mypref",MODE_PRIVATE);
        String lon=sharedPreferences.getString("lon","");
        String lat  = sharedPreferences.getString("lat","");

        tvLoc.setText(""+lat+" "+lon);

        String f_id =FirebaseAuth.getInstance().getCurrentUser().getUid();
        Farmer f = new Farmer(f_id,"name",lat,lon);
        FirebaseDatabase.getInstance().getReference().child("Farmer").child(f_id).setValue(f).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SellCrop.this, "farmer added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SellCrop.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


//        btnModerator.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(SellCrop.this, ModeratorHome.class);
//                startActivity(i);
//
//            }
//        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_PHOTO_PICKER )
        {
            if(resultCode == RESULT_OK)
            {
                Uri imageUri = data.getData();
                StorageReference photoReference = FirebaseStorage.getInstance().getReference().child("crop_photo"+"/"+imageUri.getLastPathSegment());

                //upload file to firebase
                photoReference.putFile(imageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        img_url = downloadUrl.toString();


                        String crop_name = etCropName.getText().toString();
                        String f_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child(f_id);
                        r = new Request(crop_name,f_id ,"pending",img_url);
                        databaseReference.setValue(r).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Glide.with(imgUpload.getContext())
//                                        .load(r.getImage())
//                                        .into(imgUpload);


                                Picasso.get().load(img_url).into(imgUpload);
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
            }
        }
    }
}
