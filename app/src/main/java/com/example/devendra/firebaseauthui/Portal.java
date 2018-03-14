package com.example.devendra.firebaseauthui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Portal extends AppCompatActivity {

    Button btnBuyer , btnFramer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);

        btnBuyer = (Button) findViewById(R.id.btnBuyer);
        btnFramer = (Button) findViewById(R.id.btnFarmer);


        btnFramer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Portal.this , MainActivity.class);

                SharedPreferences.Editor editor= getSharedPreferences("Mypref",MODE_PRIVATE).edit();
                editor.putString("type","farmer");
                editor.commit();
                startActivity(i);

            }
        });

        btnBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Portal.this , MainActivity.class);

                SharedPreferences.Editor editor= getSharedPreferences("Mypref",MODE_PRIVATE).edit();
                editor.putString("type","buyer");
                editor.commit();
                startActivity(i);

            }
        });
    }
}
