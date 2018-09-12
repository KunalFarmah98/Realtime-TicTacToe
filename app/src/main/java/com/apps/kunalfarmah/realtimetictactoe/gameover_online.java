package com.apps.kunalfarmah.realtimetictactoe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.kunalfarmah.realtimetictactoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class gameover_online extends AppCompatActivity {

    FirebaseDatabase mdata;
    DatabaseReference closeref;
    DatabaseReference restartref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        final String ishost = getIntent().getSerializableExtra("isHost").toString();

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
                        Intent start = new Intent(getApplicationContext(), onlineActivity.class);
                        start.putExtra("isHost", ishost);
                        startActivity(start);
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
                        finish();
                        ActivityCompat.finishAffinity(gameover_online.this);
                        System.exit(0);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        ImageButton replay = findViewById(R.id.repeat);
        replay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                restartref.setValue(true);
                // this function will only close the game over activity and will restart from the main activity
//                Intent start = new Intent(getApplicationContext(),onlineActivity.class);
//                start.putExtra("isHost",ishost);
//                startActivity(start);
               // System.exit(0);
            }
        });

        final ImageButton close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                restartref.setValue(false);
                closeref.setValue(true);

                // this function will close the app
//                ActivityCompat.finishAffinity(gameover_online.this);
//                System.exit(0);
            }
        });


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
}

