package com.example.devendra.firebaseauthui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class BuyerList extends AppCompatActivity {

    private ListView lvBuyers;
//    RecyclerView.Adapter adapter;
//    RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private  static TreeMap<Double,User> tm = new TreeMap<>();
    private ValueEventListener valueEventListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_list);
        lvBuyers=(ListView)findViewById(R.id.lvBuyers);

        final ArrayList<User> users = new ArrayList<>();
        userAdapter = new UserAdapter(this, R.layout.user_adapter,users);
        lvBuyers.setAdapter(userAdapter);


        SharedPreferences sharedPreferences = getSharedPreferences("Mypref",MODE_PRIVATE);
        String lon=sharedPreferences.getString("lon","19.209401");
        String lat  = sharedPreferences.getString("lat","73.093948");
        lat="19.209401";
        lon="73.093948";

        Log.d("Distance",lon);
        Log.d("Distance",lat);
        final double my_lat = Double.parseDouble(lat);
        final double my_lon = Double.parseDouble(lon);


        DatabaseReference b = FirebaseDatabase.getInstance().getReference().child("Buyer");

        b.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("DataSnapShot", String.valueOf(dataSnapshot));

                for(DataSnapshot child:dataSnapshot.getChildren() )
                {
                    User u = child.getValue(User.class);
                    double b_lat = Double.parseDouble(u.getUlat());
                    double b_lon = Double.parseDouble(u.getUlon());

                    double dis = distance(my_lat,my_lon,b_lat,b_lon);
                    tm.put(dis,u);
//                    userAdapter.add(u);

                }

                Log.d("TreeMapSize", String.valueOf(tm.size()));

                for(Map.Entry<Double,User> entry : tm.entrySet()) {
                    double key = entry.getKey();
                    User value = entry.getValue();

                    userAdapter.add(value);
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
//                //userAdapter.add(u);
//
//                double b_lat = Double.parseDouble(u.getUlat());
//                double b_lon = Double.parseDouble(u.getUlon());
//
//                Log.d("Distance",my_lat + " " +my_lon +" " + b_lat +" " + b_lon);
//
//                double dis = distance(my_lat,my_lon,b_lat,b_lon);
//                tm.put(dis,u);
//                Log.d("Size", String.valueOf(tm.size()));
//                Log.d("Distance",u.getUname() + dis);
//
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
//        }
//        );

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Size","Inside Data changed"+tm.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        lvBuyers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User u = (User) lvBuyers.getItemAtPosition(i);
                Intent in = new Intent(BuyerList.this , ChatActivity.class);
                in.putExtra("farmer", FirebaseAuth.getInstance().getCurrentUser().getUid());
                in.putExtra("buyer",u.getU_id());
                startActivity(in);

            }
        });



    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {


        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
