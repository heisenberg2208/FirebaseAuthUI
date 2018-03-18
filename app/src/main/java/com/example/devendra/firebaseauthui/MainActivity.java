package com.example.devendra.firebaseauthui;



import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final int RC_SIGN_IN =1;


    private TextView tvUserName;
    private EditText etGetName;
    private Button btnSubmitName;



    //Auth
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    //Auth Ends here




    Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignOut = (Button) findViewById(R.id.btnSignOut);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        etGetName = (EditText) findViewById(R.id.etGetName);
        btnSubmitName = (Button) findViewById(R.id.btnSubmitName);


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(MainActivity.this);



            }
        });


        //Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        //Auth ends


        //Auth
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                 FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //signed in

//                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                .setDisplayName("Heisenberg").build();
//                        user.updateProfile(profileUpdates);
                    //Toast.makeText(MainActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();



                    Intent in = new Intent(MainActivity.this , GetUserName.class);
                    startActivity(in);
                    finish();

                }
                else {

                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders(Arrays.asList(
                                                new AuthUI.IdpConfig.PhoneBuilder().build(),
                                                new AuthUI.IdpConfig.GoogleBuilder().build()
                                        ))
                                        .build(),
                                RC_SIGN_IN);
                    }
                }
            };


            //Auth ended


        }




//Auth helping method for AuthStateListener

//Auth ended





    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN)
        {
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(this, "You are signed in", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }




}
