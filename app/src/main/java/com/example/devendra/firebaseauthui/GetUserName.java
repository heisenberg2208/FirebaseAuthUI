package com.example.devendra.firebaseauthui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class GetUserName extends AppCompatActivity {

    private EditText etGetName;
    private Button btnSubmitName;
    static String newname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_name);
        etGetName = (EditText)findViewById(R.id.etGetName);
        btnSubmitName = (Button) findViewById(R.id.btnSubmitName);


        SharedPreferences preferences  = getSharedPreferences("Mypref",MODE_PRIVATE);
        String name = preferences.getString("user","");
        if(!name.equals(""))
        {
            goFurther();

            Log.d("MyName","Retrieving Data");
        }

        btnSubmitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etGetName.getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences("Mypref",MODE_PRIVATE).edit();
                editor.putString("user",name);
                editor.commit();
                Log.d("MyName","Taking Data First Time");
                goFurther();
            }
        });







    }

    private void goFurther() {

        SharedPreferences preferences  = getSharedPreferences("Mypref",MODE_PRIVATE);
        String type = preferences.getString("type","");
        if(type.equals("farmer"))
        {
            Intent i = new Intent(GetUserName.this , FarmerPortal.class);
            startActivity(i);
            finish();
        }
        else if(type.equals("buyer"))
        {
            Intent i = new Intent(GetUserName.this,Buyer.class);
            startActivity(i);
            finish();
        }
    }
}
