package com.apps.kunalfarmah.realtimetictactoe;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.kunalfarmah.realtimetictactoe.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Interstitial extends Fragment {

    Button host;
    Button join;
    EnterActivity act;


    public Interstitial() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_interstitial, container, false);


        host = v.findViewById(R.id.host);
        join = v.findViewById(R.id.join);



        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Join join = new Join();
                Host host = new Host();
               act.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,host).addToBackStack("host").commit();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Join join = new Join();
                act.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,join).addToBackStack("join").commit();
            }
        });




        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        act = (EnterActivity)activity;
    }

}
