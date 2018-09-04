package com.apps.kunalfarmah.realtimetictactoe;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.kunalfarmah.realtimetictactoe.R;

public class EnterActivity extends AppCompatActivity {

    Button offline;
    Button online;
    FrameLayout fragments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.enter);

        offline = findViewById(R.id.offline);
        online = findViewById(R.id.online);
        fragments =findViewById(R.id.fragment_containter);


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
                Interstitial interstitial = new Interstitial();
                fragments.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,interstitial).commit();


            }
        });

    }
}
