package com.apps.kunalfarmah.realtimetictactoe.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.kunalfarmah.realtimetictactoe.onlineActivity;
import com.example.kunalfarmah.realtimetictactoe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;


/**
 * A simple {@link Fragment} subclass.
 */
public class Host extends Fragment {

    FirebaseDatabase mDatabase;
    DatabaseReference mref;
    DatabaseReference hostRef;
    DatabaseReference diffRef;

    FirebaseUser user;

    EditText token;

    Button Continue;

    int difficulty;

    TextView hidden1;
    TextView hidden2;
    TextView hidden3;

    RadioButton d1, d2, d3;

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
        hidden2 = v.findViewById(R.id.hidden2);
        hidden3 = v.findViewById(R.id.hidden3);

        d1 = v.findViewById(R.id.d1);
        d2 = v.findViewById(R.id.d2);
        d3 = v.findViewById(R.id.d3);


        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 1;
            }
        });


        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 2;
            }
        });


        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 3;
            }
        });


        mDatabase = FirebaseDatabase.getInstance();

        mref = mDatabase.getReference("Code");

        diffRef = mDatabase.getReference("Difficulty");


        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (difficulty == 0) {
                        Toast.makeText(getContext(), "Please Select a Difficulty Level", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    diffRef.setValue(difficulty);

                    // storing the token in the db

                    if (token.getText().toString().equals("") || token.getText().toString().equals(" ")) {
                        hidden3.setVisibility(View.VISIBLE);
                    } else {
                        Log.d("token", token.getText().toString());
                        mref.setValue(token.getText().toString());
                        hidden3.setVisibility(View.GONE);
                        hidden1.setVisibility(View.VISIBLE);
                        hidden2.setVisibility(View.VISIBLE);

                        //  waiting for game to start

                        mref = mDatabase.getReference("Code");


                        // setting the referrence to the Game

                        mref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.

                                try {
                                    String value = (String) dataSnapshot.getValue();
                                    Log.d("value", value);
                                    //  as soon as friend has paired, start the game

                                    // if code is changed, start the game
                                    if (value.equals("Play")) {

                                        Intent start = new Intent(getContext(), onlineActivity.class);
                                        start.putExtra("isHost", "True");
                                        start.putExtra("difficulty", difficulty);
                                        startActivity(start);
                                        getActivity().finish();


                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value;
                            }
                        });

                    }

                    user = FirebaseAuth.getInstance().getCurrentUser();
                    hostRef = mDatabase.getReference("HostName");
                    hostRef.setValue(user.getDisplayName());

                }

                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // hostRef.removeValue();
        //  mref.removeValue();
        //     mDatabase.goOffline();
    }

    @Override
    public void onStop() {
        super.onStop();
        //  hostRef.removeValue();
        // mref.removeValue();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // hostRef.removeValue();
        //    mref.removeValue();

    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabase.goOnline();
    }
}
