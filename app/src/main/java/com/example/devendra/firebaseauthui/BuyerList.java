package com.example.devendra.firebaseauthui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class BuyerList extends AppCompatActivity {

    private ListView lvBuyers;
//    RecyclerView.Adapter adapter;
//    RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_list);
        lvBuyers=(ListView)findViewById(R.id.lvBuyers);

        final ArrayList<User> users = new ArrayList<>();
        userAdapter = new UserAdapter(this, R.layout.user_adapter,users);
        lvBuyers.setAdapter(userAdapter);

        DatabaseReference b = FirebaseDatabase.getInstance().getReference().child("Buyer");
        b.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User u = dataSnapshot.getValue(User.class);
//               Log.d("Devendra",u.getU_id());
                userAdapter.add(u);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

    }
}
