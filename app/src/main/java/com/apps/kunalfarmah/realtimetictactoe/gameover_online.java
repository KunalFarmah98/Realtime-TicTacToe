package com.apps.kunalfarmah.realtimetictactoe;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunalfarmah.realtimetictactoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class gameover_online extends AppCompatActivity {

    FirebaseDatabase mdata;
    DatabaseReference closeref;
    DatabaseReference restartref;

    TextView time;
    int difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        onlineActivity.isover = true;

        time = findViewById(R.id.time);
        ImageButton replay = findViewById(R.id.repeat);
        final ImageButton close = findViewById(R.id.close);

        difficulty = getIntent().getIntExtra("difficulty",2);

        replay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                restartref.setValue(true);
               /*  //this function will only close the game over activity and will restart from the main activity
                Intent start = new Intent(getApplicationContext(),onlineActivity.class);
                start.putExtra("isHost",ishost);
                startActivity(start);
                System.exit(0);*/
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                restartref.setValue(false);
                closeref.setValue(true);
/*
                // this function will close the app
                ActivityCompat.finishAffinity(gameover_online.this);
                System.exit(0)*/;
            }
        });



        final String ishost = getIntent().getSerializableExtra("isHost").toString();

        final String timeval = getIntent().getStringExtra("Time");

        time.setText("Time : "+ timeval);


        mdata =FirebaseDatabase.getInstance();
        closeref = mdata.getReference("isClosed");
        closeref.setValue(false);
        restartref= mdata.getReference("isRestarted");
        restartref.setValue(false);

        restartref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    Boolean restarted = dataSnapshot.getValue(Boolean.class);


                    if (restarted) {

                        if (hasActiveInternetConnection(getApplicationContext())) {
                            Intent start = new Intent(getApplicationContext(), onlineActivity.class);
                            start.putExtra("isHost", ishost);
                            start.putExtra("difficulty", difficulty);
                            startActivity(start);
                            //finish();
                           // restartref.setValue(false);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (Exception e) {
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        closeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {


                    Boolean closed = dataSnapshot.getValue(Boolean.class);

                    if (closed) {
                        ActivityCompat.finishAffinity(gameover_online.this);
                        finish();
                        System.exit(0);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        int i  =timeval.indexOf(':');
        String min = timeval.substring(0,i-1);
        if(min.equals("1")){
            Toast.makeText(getApplicationContext(),"One or more players have lost connection to the server :(", Toast.LENGTH_SHORT).show();
        }


    }

//    public void restartActivity(){
//        //finishing current activity and then restarting it
//        Intent mIntent = getIntent();
//        finish();
//        startActivity(mIntent);


    @Override
    protected void onStop() {
        super.onStop();
        restartref.removeValue();
        closeref.removeValue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mdata.goOffline();

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

            // forcefully using network on main threaad
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
}

