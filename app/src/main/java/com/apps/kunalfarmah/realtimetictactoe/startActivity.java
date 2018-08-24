package com.apps.kunalfarmah.realtimetictactoe;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kunalfarmah.realtimetictactoe.R;

import java.io.Serializable;

public class startActivity extends AppCompatActivity implements Serializable {
    EditText e1;
    EditText e2;

    ImageButton i1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        i1 = (ImageButton) findViewById(R.id.gobutton);

        e1 = (EditText) findViewById(R.id.player1);
        e2 = (EditText) findViewById(R.id.player2);

        i1.setOnClickListener(new View.OnClickListener() {

            private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);    //fading effect on clicking go

            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                Intent intent = new Intent(startActivity.this , com.apps.kunalfarmah.realtimetictactoe.MainActivity_optimised.class);
                intent.putExtra("player1", e1.getText().toString());    // sending the edittext data to mainactivity
                intent.putExtra("player2", e2.getText().toString());
                startActivity(intent);

            }
        });
    }

    private static final String SELECTED_ITEM_POSITION = "ItemPosition";
    private int mPosition;

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of item position
        outState.putInt(SELECTED_ITEM_POSITION, mPosition);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Read the state of item position
        mPosition = savedInstanceState.getInt(SELECTED_ITEM_POSITION);
    }
}
