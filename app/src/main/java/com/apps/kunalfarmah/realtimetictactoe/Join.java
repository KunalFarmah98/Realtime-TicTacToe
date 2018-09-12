package com.apps.kunalfarmah.realtimetictactoe;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class Join extends Fragment {
    

    FirebaseDatabase mDatabase;
    DatabaseReference mref;

    EditText token;
    Button play;
    public Join() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_join, container, false);
        token = v.findViewById(R.id.token1);
        play = v.findViewById(R.id.play);

        //final String val = token.getText().toString();

        // storing the token in the db



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance();
                mref = mDatabase.getReference("Code");
                // Read from the database
                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        try {
                            String value = (String) dataSnapshot.getValue();


                            if (token.getText().toString().equals(value)) {
                                Toast.makeText(getContext(), "Paired", Toast.LENGTH_SHORT).show();

                                // updating code as play which will in turn start the game for friend and starting game for urself

                                mref.getDatabase().getReference("Code").setValue("Play");

                                Intent start = new Intent(getContext(), onlineActivity.class);
                                start.putExtra("isHost","False");

                                startActivity(start);
                            } else if(!token.getText().toString().equals(value) && !value.equalsIgnoreCase("Play")){
                                Toast.makeText(getContext(), "Please Enter Correct Code", Toast.LENGTH_SHORT).show();
                            }
                            //Log.d(TAG, "Value is: " + value);
                        } catch (Exception e) {
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        // Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

            }
        });

       return v;
    }

}
