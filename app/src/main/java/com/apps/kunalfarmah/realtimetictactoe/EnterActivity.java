package com.apps.kunalfarmah.realtimetictactoe;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.apps.kunalfarmah.realtimetictactoe.Fragments.AboutMe;
import com.apps.kunalfarmah.realtimetictactoe.Fragments.Interstitial;
import com.apps.kunalfarmah.realtimetictactoe.Fragments.how_to_play;
import com.example.kunalfarmah.realtimetictactoe.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class EnterActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    Button offline;
    Button online;
    ImageButton info;
    // a variable to check if we are inside teh host or join screen
    public static FrameLayout fragments;

    static String User;

    AboutMe about = new AboutMe();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(),"Signed Out Successfully!!",Toast.LENGTH_SHORT).show();

                // removing all fragments after sign in
                for(int i=0; i<getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getSupportFragmentManager().popBackStack();
                }

                // if a fragemnt was open during sign out, remove it
                if(fragments.getVisibility()==View.VISIBLE)
                    fragments.setVisibility(View.GONE);

                return true;

            case R.id.about_dev:
                if(fragments.getVisibility()==View.GONE || fragments.getVisibility()==View.INVISIBLE)
                    fragments.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,about).addToBackStack("about").commit();
        default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.enter);

        offline = findViewById(R.id.offline);
        online = findViewById(R.id.online);
        info = findViewById(R.id.info);
        fragments = findViewById(R.id.fragment_containter);
        //fragment1 = findViewById(R.id.how_to_play);

        //mFirebaseAuth = FirebaseAuth.getInstance();


        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), startActivity.class);
                startActivity(intent);
            }
        });

        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (hasActiveInternetConnection(getApplicationContext())) {

                    Interstitial interstitial = new Interstitial();
                    fragments.setVisibility(View.VISIBLE);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter, interstitial).addToBackStack("Interstitial").commit();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // if user is logged in continue
                    if (user != null) {
                        Toast.makeText(getApplicationContext(), "Welcome "+user.getDisplayName()+ " :)", Toast.LENGTH_SHORT).show();

                    } else {

                        // Choose authentication providers if user is not logged in
                        List<AuthUI.IdpConfig> providers = Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.PhoneBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders(providers)
                                        .setLogo(R.drawable.logo)
                                        .build(),
                                RC_SIGN_IN);


                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please connect your device to the internet to continue :)", Toast.LENGTH_SHORT).show();
                }
            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                how_to_play play = new how_to_play();
                fragments.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,play).addToBackStack("info").commit();
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // removing the fragment if back is pressed on the host or join screen

       // fragment1.setVisibility(View.GONE);

        if(getSupportFragmentManager().getBackStackEntryCount()==0)
        fragments.setVisibility(View.GONE);
        else{}
            //fragments.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
               try{
                   User = user.getDisplayName();
               }
               catch (Exception e){}

                if (user != null) {
                    Toast.makeText(getApplicationContext(), "Signed In Successfully as "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Please Sign In",Toast.LENGTH_SHORT).show();

                    // Choose authentication providers
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.PhoneBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    //.setIsSmartLockEnabled(true)
                                    .setLogo(R.drawable.logo)
                                    .build(),
                            RC_SIGN_IN);
                }
                // ...
            } else {

                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                onBackPressed();


            }
        }
    }


    // checks if it is connceted to a network
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    // this checks if connection has internet access

    public boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable()) {

            // forcefully using network on main thread
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
               // Log.e(LOG_TAG, "Error checking internet connection", e);
            }
        } else {
            //Log.d(LOG_TAG, "No network available!");
        }
        return false;
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}






