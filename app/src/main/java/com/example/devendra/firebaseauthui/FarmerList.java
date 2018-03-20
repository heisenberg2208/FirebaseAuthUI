package com.example.devendra.firebaseauthui;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FarmerList extends AppCompatActivity {

    private ListView lvFarmers;
    //    RecyclerView.Adapter adapter;
//    RecyclerView recyclerView;
    private UserAdapter userAdapter;

    private TextView tvFarmerList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_list);


        tvFarmerList = (TextView) findViewById(R.id.tvFarmerList);

        Intent in = getIntent();
        String crop = in.getStringExtra("crop");
        tvFarmerList.setText("Showing Farmer's who are selling "+crop);


        lvFarmers=(ListView)findViewById(R.id.lvFarmers);

        final ArrayList<User> users = new ArrayList<>();
        userAdapter = new UserAdapter(this, R.layout.user_adapter,users);
        lvFarmers.setAdapter(userAdapter);

        DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("Requests");

        Query q = r.orderByChild("crop_name").equalTo(crop);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> alF_id = new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren())
                {
                    Request r = child.getValue(Request.class);
                    if(r.getStatus().equals("approved"))
                        alF_id.add(r.getF_id());
                    Log.d("Cropfarmer",r.getF_id());
                }

                for(int i=0; i < alF_id.size() ; i++)
                {
                    String f_id = alF_id.get(i);
                    DatabaseReference farmers = FirebaseDatabase.getInstance().getReference().child("Farmer");
                    Query q = farmers.orderByChild("u_id").equalTo(f_id);

                    q.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot child:dataSnapshot.getChildren())
                            {
                                User u = child.getValue(User.class);
                                userAdapter.add(u);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        b.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                User u = dataSnapshot.getValue(User.class);
////               Log.d("Devendra",u.getU_id());
//                userAdapter.add(u);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        lvFarmers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User u = (User) lvFarmers.getItemAtPosition(i);
                Intent in = new Intent(FarmerList.this , ChatActivity.class);
                in.putExtra("buyer", FirebaseAuth.getInstance().getCurrentUser().getUid());
                in.putExtra("farmer",u.getU_id());
                startActivity(in);

            }
        });

    }
}

