package com.example.devendra.firebaseauthui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Waiting extends AppCompatActivity {

    private TextView tvWaitingMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        tvWaitingMessage = (TextView) findViewById(R.id.tvWaitingMessage);

        DatabaseReference req = FirebaseDatabase.getInstance().getReference().child("Requests");
        String f_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query q = req.orderByChild("f_id").equalTo(f_id);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren())
                {
                    Request r = child.getValue(Request.class);
                    String status = r.getStatus();
                    if(! status.equals("approved"))
                    {
                        tvWaitingMessage.setText("Your request is being "+status);
                    }
                    else
                    {
                        Intent in = new Intent(Waiting.this , BuyerList.class);
                        startActivity(in);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
