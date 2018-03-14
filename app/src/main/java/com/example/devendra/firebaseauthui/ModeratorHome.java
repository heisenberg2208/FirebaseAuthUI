package com.example.devendra.firebaseauthui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ModeratorHome extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView mRequestListView;
    private RequestAdapter mRequestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderator_home);

        mRequestListView = (ListView) findViewById(R.id.requestListView);


        final List<Request> Requests = new ArrayList<>();
        mRequestAdapter = new RequestAdapter(this, R.layout.item_request, Requests);
        mRequestListView.setAdapter(mRequestAdapter);
        

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query fil = databaseReference.orderByChild("status").equalTo("pending");

        fil.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Request r = dataSnapshot.getValue(Request.class);
                Log.d("Request",r.getF_id());
                Request r1 = dataSnapshot.getValue(Request.class);
                // Getting Message From DataBase and Display in List View
                mRequestAdapter.add(r1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Request r = dataSnapshot.getValue(Request.class);
                Log.d("Request",r.getF_id());
                finish();
                startActivity(getIntent());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRequestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Request r = (Request) mRequestListView.getItemAtPosition(i);
                Intent in = new Intent(ModeratorHome.this , VerifyCrop.class);
                in.putExtra("crop_name",r.getCrop_name());
                in.putExtra("status",r.getStatus());
                in.putExtra("f_id",r.getF_id());
                in.putExtra("image",r.getImage());
                startActivity(in);



            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ModeratorHome.this,SellCrop.class);
        startActivity(i);
        finish();
    }
}
