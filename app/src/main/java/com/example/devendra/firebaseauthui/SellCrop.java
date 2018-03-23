package com.example.devendra.firebaseauthui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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


        SharedPreferences sharedPreferences = getSharedPreferences("Mypref",MODE_PRIVATE);
        String lon=sharedPreferences.getString("lon","");
        String lat  = sharedPreferences.getString("lat","");
        String name = sharedPreferences.getString("user","");
        String request = sharedPreferences.getString("request","");
        String add = sharedPreferences.getString("add","");
        Log.d("Debug","Got Lan Lot");


        if(! request.equals(""))
        {
            Intent in = new Intent(SellCrop.this,Waiting.class);
            startActivity(in);
        }

        tvLoc.setText(add);
        Log.d("Debug","Got addr");

        String f_id =FirebaseAuth.getInstance().getCurrentUser().getUid();
        User u = new User(f_id,name,lat,lon,add);
        FirebaseDatabase.getInstance().getReference().child("Farmer").child(f_id).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SellCrop.this, "farmer added", Toast.LENGTH_SHORT).show();
                Log.d("Debug","Added farmer");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SellCrop.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(SellCrop.this ,  BuyerList.class);
//                startActivity(i);


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                Log.d("Debug","Inside button sell");





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
                        Log.d("Debug","Inside on Activity Result");

                        tvLoc.setText("request is being uploaded");
                        String crop_name = etCropName.getText().toString();
                        String f_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Requests").child(f_id);
                        r = new Request(crop_name,f_id ,"pending",img_url);
                        databaseReference.setValue(r).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Glide.with(imgUpload.getContext())
//                                        .load(r.getImage())
//                                        .into(imgUpload);

                                Log.d("Debug","Request added");
                                Picasso.get().load(img_url).into(imgUpload);
                                Toast.makeText(SellCrop.this, "Request has been added", Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = getSharedPreferences("Mypref",MODE_PRIVATE).edit();
                                editor.putString("request","true");
                                editor.commit();

                                Intent in = new Intent(SellCrop.this,Waiting.class);
                                startActivity(in);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SellCrop.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                Log.d("Debug","request not added");
                            }
                        });

                    }
                });
            }
        }
    }
}
