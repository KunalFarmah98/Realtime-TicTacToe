package com.apps.kunalfarmah.realtimetictactoe;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kunalfarmah.realtimetictactoe.R;

public class gameover extends AppCompatActivity {

    TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        time = findViewById(R.id.time);

        final String timeval = getIntent().getStringExtra("Time");

        // displaying the time taken in a match
        time.setText("Time : "+ timeval);

        ImageButton replay = findViewById(R.id.repeat);
        replay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // this function will only close the game over activity and will restart from the main activity
                finish();
                System.exit(0);
            }
        });

        ImageButton close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this function will close the app
                ActivityCompat.finishAffinity(gameover.this);
                System.exit(0);
            }
        });


    }

    public void restartActivity(){
        //finishing current activity and then restarting it
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }
}