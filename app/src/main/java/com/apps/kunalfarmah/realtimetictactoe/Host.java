package com.apps.kunalfarmah.realtimetictactoe;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunalfarmah.realtimetictactoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Host extends Fragment {

FirebaseDatabase mDatabase;
DatabaseReference mref;

EditText token;

Button Continue;

TextView hidden1;
TextView hidden2;

    public Host() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_host, container, false);
        token = v.findViewById(R.id.token);
        Continue = v.findViewById(R.id.Continue);
        hidden1 = v.findViewById(R.id.hidden1);
        hidden2=v.findViewById(R.id.hidden2);
        //final String val = token.getText().toString();

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance();
                // storing the token in the db
                mref =mDatabase.getReference().child("Code");
                mref.getDatabase().getReference("Code").setValue(token.getText().toString());
                hidden1.setVisibility(View.VISIBLE);
                hidden2.setVisibility(View.VISIBLE);

                //  waiting for game to start

                mref.getDatabase().getReference("Code");

                // settign the referance to the Game

                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.


                          String value = (String) dataSnapshot.getValue();
                          //  as soon as friend has paired, start the game

                        // if code is changed, start the game
                        if(value.equals("Play")){
                            Intent start = new Intent(getContext(),onlineActivity.class);
                                    start.putExtra("isHost","True");
                            startActivity(start);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value;
                    }
                });

            }
        });




     return v;
    }

}
