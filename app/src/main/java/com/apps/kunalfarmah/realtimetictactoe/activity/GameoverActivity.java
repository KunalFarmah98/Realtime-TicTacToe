package com.apps.kunalfarmah.realtimetictactoe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.kunalfarmah.realtimetictactoe.R;

public class GameoverActivity extends AppCompatActivity {

    TextView time;
    String p1,p2;
    ImageButton close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        close = findViewById(R.id.close);


        time = findViewById(R.id.time);

        final String timeval = getIntent().getStringExtra("Time");
        p1 = getIntent().getStringExtra("player1");
        p2 = getIntent().getStringExtra("player2");

        // displaying the time taken in a match
        time.setText("Time : "+ timeval);

        ImageButton replay = findViewById(R.id.repeat);
        replay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // this function will only close the game over activity and will restart from the main activity
                Intent restart = new Intent(GameoverActivity.this,MainActivity.class);
                restart.putExtra("player1", p1);    // sending the edittext data to mainactivity
                restart.putExtra("player2", p2);
                startActivity(restart);
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this function will close the app
                ActivityCompat.finishAffinity(GameoverActivity.this);
            }
        });


    }

    @Override
    public void onBackPressed() {
        close.callOnClick();
    }

    public void restartActivity(){
        //finishing current activity and then restarting it
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }
}