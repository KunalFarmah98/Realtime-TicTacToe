package com.apps.kunalfarmah.realtimetictactoe;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.kunalfarmah.realtimetictactoe.R;

public class gameover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        ImageButton replay = findViewById(R.id.repeat);
        replay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        ImageButton close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAffinity(gameover.this);
                System.exit(0);
            }
        });


    }

    public void restartActivity(){
        //finishing current activity and hten restarting it
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }
}