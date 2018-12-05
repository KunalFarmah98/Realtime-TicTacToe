package com.apps.kunalfarmah.realtimetictactoe;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunalfarmah.realtimetictactoe.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class onlineActivity extends AppCompatActivity implements View.OnClickListener {

    int moves[][] = new int[][]{{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};

    ImageView i1;
    ImageView i2;
    ImageView i3;
    ImageView i4;
    ImageView i5;
    ImageView i6;
    ImageView i7;
    ImageView i8;
    ImageView i9;

    TextView player1;
    TextView player2;
    TextView movescount;
    LinearLayout timer;

    int minutes=0,seconds=0;

    TextView min,sec;

    ImageView hosticon;
    ImageView awayicon;


    String pl1 = "Host : X";
    String pl2 ="Away : O";

    String ishost = "";
    // String turn="";

    FirebaseDatabase mdata;
    // reference for the moves
    DatabaseReference ref;

   // DatabaseReference closeref;
    // reference for the turns
    DatabaseReference turn;

    // a reference to a victory
    DatabaseReference iswin;

    //a reference to detect loss in connection
    DatabaseReference connectedRef;

   // DatabaseReference movesref;

    DatabaseReference lostConnection;

    ChildEventListener movelistener;

    boolean host_turn;
    boolean win;


 int steps=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // finding which one is host;
        ishost = getIntent().getSerializableExtra("isHost").toString();

        // setting host to be true for host and false for joiner
        mdata = FirebaseDatabase.getInstance();

        turn = mdata.getReference("Host");
        turn.setValue(true);

        iswin = mdata.getReference("Win");
        iswin.setValue(" ");

        //movesref = mdata.getReference("Movescnt");
        //movesref.setValue(0);

        lostConnection = mdata.getReference("connection_lost");
        lostConnection.setValue(false);
        //lostConnection.setValue(false);

        /** to handle lost connection*/
        /*connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    // System.out.println("connected");
                } else {
                    lostConnection.setValue(true);
                    //Toast.makeText(getApplicationContext(),"Lost",Toast.LENGTH_SHORT).show();

                    //  finish();
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });


        lostConnection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    boolean val = dataSnapshot.getValue(Boolean.class);

                    if (val) {
                        // finish();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent start = new Intent(getApplicationContext(),EnterActivity.class);
                                startActivity(start);
//                                Intent gameover = new Intent(getApplicationContext(), com.apps.kunalfarmah.realtimetictactoe.gameover_online.class);
//                                gameover.putExtra("isHost", ishost);
//                                startActivity(gameover);
                            }
                        }, 1000);
                        Toast.makeText(getApplicationContext(), "Lost Connection to the other Player", Toast.LENGTH_SHORT).show();
                    }

                    // else{}

                } catch(Exception e){}
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/

        i1 = (ImageView) findViewById(R.id.imageView1);
        i2 = (ImageView) findViewById(R.id.imageView2);
        i3 = (ImageView) findViewById(R.id.imageView3);
        i4 = (ImageView) findViewById(R.id.imageView4);
        i5 = (ImageView) findViewById(R.id.imageView5);
        i6 = (ImageView) findViewById(R.id.imageView6);
        i7 = (ImageView) findViewById(R.id.imageView7);
        i8 = (ImageView) findViewById(R.id.imageView8);
        i9 = (ImageView) findViewById(R.id.imageView9);

        hosticon = findViewById(R.id.host);
        awayicon = findViewById(R.id.away);

        player1 = findViewById(R.id.textView);
        player2 = findViewById(R.id.textView2);
        movescount = findViewById(R.id.moves);

        timer = findViewById(R.id.timer);
        timer.setVisibility(View.VISIBLE);

        min = findViewById(R.id.minutes);
        sec = findViewById(R.id.seconds);


        player1.setText(pl1);
        player2.setText(pl2);







        turn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Boolean hostTurn = dataSnapshot.getValue(Boolean.class);

                host_turn = hostTurn;


                // if its host device and hosts turn , disable all the already set values

                if (ishost.equalsIgnoreCase("True") && hostTurn) {

                    awayicon.setVisibility(View.INVISIBLE);
                    hosticon.setVisibility(View.VISIBLE);

//                    i1.setClickable(true);
//                    i2.setClickable(true);
//                    i3.setClickable(true);
//                    i4.setClickable(true);
//                    i5.setClickable(true);
//                    i6.setClickable(true);
//                    i7.setClickable(true);
//                    i8.setClickable(true);
//                    i9.setClickable(true);
                    settingclicklisteners();

                    if (moves[0][0] != -1) {
                        i1.setClickable(false);
                    }
                    if (moves[0][1] != -1) {
                        i2.setClickable(false);
                    }
                    if (moves[0][2] != -1) {
                        i3.setClickable(false);
                    }
                    if (moves[1][0] != -1) {
                        i4.setClickable(false);
                    }
                    if (moves[1][1] != -1) {
                        i5.setClickable(false);
                    }
                    if (moves[1][2] != -1) {
                        i6.setClickable(false);
                    }
                    if (moves[2][0] != -1) {
                        i7.setClickable(false);
                    }
                    if (moves[2][1] != -1) {
                        i8.setClickable(false);
                    }
                    if (moves[2][2] != -1) {
                        i9.setClickable(false);
                    }

                    //   turn.setValue(false);

                }

                // if it is host device and aways turn disable all clicks for host
                if (ishost.equalsIgnoreCase("True") && !hostTurn) {

                    awayicon.setVisibility(View.VISIBLE);
                    hosticon.setVisibility(View.INVISIBLE);

                    settingclicklisteners();
                    i1.setClickable(false);
                    i2.setClickable(false);
                    i3.setClickable(false);
                    i4.setClickable(false);
                    i5.setClickable(false);
                    i6.setClickable(false);
                    i7.setClickable(false);
                    i8.setClickable(false);
                    i9.setClickable(false);

                    //    turn.setValue(true);

                }


                // if it is away device and away turn, disable all the already clicked values

                if (!ishost.equalsIgnoreCase("True") && !hostTurn) {

                    awayicon.setVisibility(View.VISIBLE);
                    hosticon.setVisibility(View.INVISIBLE);

//                    i1.setClickable(true);
//                    i2.setClickable(true);
//                    i3.setClickable(true);
//                    i4.setClickable(true);
//                    i5.setClickable(true);
//                    i6.setClickable(true);
//                    i7.setClickable(true);
//                    i8.setClickable(true);
//                    i9.setClickable(true);

                    settingclicklisteners();

                    if (moves[0][0] != -1) {
                        i1.setClickable(false);
                    }
                    if (moves[0][1] != -1) {
                        i2.setClickable(false);
                    }
                    if (moves[0][2] != -1) {
                        i3.setClickable(false);
                    }
                    if (moves[1][0] != -1) {
                        i4.setClickable(false);
                    }
                    if (moves[1][1] != -1) {
                        i5.setClickable(false);
                    }
                    if (moves[1][2] != -1) {
                        i6.setClickable(false);
                    }
                    if (moves[2][0] != -1) {
                        i7.setClickable(false);
                    }
                    if (moves[2][1] != -1) {
                        i8.setClickable(false);
                    }
                    if (moves[2][2] != -1) {
                        i9.setClickable(false);
                    }

                    //  turn.setValue(true);

                }


                // if it is away device and host turn, disable all clicks for away

                if (!ishost.equalsIgnoreCase("True") && hostTurn) {

                    awayicon.setVisibility(View.INVISIBLE);
                    hosticon.setVisibility(View.VISIBLE);

                    settingclicklisteners();

                    i1.setClickable(false);
                    i2.setClickable(false);
                    i3.setClickable(false);
                    i4.setClickable(false);
                    i5.setClickable(false);
                    i6.setClickable(false);
                    i7.setClickable(false);
                    i8.setClickable(false);
                    i9.setClickable(false);

                    // turn.setValue(false);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        if (ishost.equals("True"))
//            turn.setValue(true);
//
//        else
//            turn.setValue(false);




        /** child event listener for the data in the image views*/

        movelistener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                // Get imgbox object and use the values to update the UI with either o or x


                imagesbox val = dataSnapshot.getValue(imagesbox.class);

                int o_or_x = val.value;

                switch (val.imgvw) {
                    case 1:
                        if (o_or_x == 0) {
                            i1.setImageResource(R.drawable.o);
                            //moves[0][0] = 0;
                        } else {
                            i1.setImageResource(R.drawable.x);
                            // moves[0][0] = 1;
                        }
                        break;
                    case 2:
                        if (o_or_x == 0) {
                            i2.setImageResource(R.drawable.o);
                            // moves[0][1] = 0;
                        } else {
                            i2.setImageResource(R.drawable.x);
                            //   moves[0][1] = 1;
                        }
                        break;
                    case 3:
                        if (o_or_x == 0) {
                            i3.setImageResource(R.drawable.o);
                            //  moves[0][2] = 0;
                        } else {
                            i3.setImageResource(R.drawable.x);
                            // moves[0][2] = 1;
                        }
                        break;
                    case 4:
                        if (o_or_x == 0) {
                            i4.setImageResource(R.drawable.o);
                            //  moves[1][0] = 0;
                        } else {
                            i4.setImageResource(R.drawable.x);
                            //  moves[1][0] = 1;
                        }
                        break;
                    case 5:
                        if (o_or_x == 0) {
                            i5.setImageResource(R.drawable.o);
                            //  moves[1][1] = 0;
                        } else {
                            i5.setImageResource(R.drawable.x);
                            //  moves[1][1] = 1;
                        }
                        break;
                    case 6:
                        if (o_or_x == 0) {
                            i6.setImageResource(R.drawable.o);
                            //  moves[1][2] = 0;
                        } else {
                            i6.setImageResource(R.drawable.x);
                            //  moves[1][2] = 1;
                        }
                        break;
                    case 7:
                        if (o_or_x == 0) {
                            i7.setImageResource(R.drawable.o);
                            //   moves[2][0] = 0;
                        } else {
                            i7.setImageResource(R.drawable.x);
                            //   moves[2][0] = 1;
                        }
                        break;
                    case 8:
                        if (o_or_x == 0) {
                            i8.setImageResource(R.drawable.o);
                            //  moves[2][1] = 0;
                        } else {
                            i8.setImageResource(R.drawable.x);
                            //   moves[2][1] = 1;
                        }
                        break;
                    case 9:
                        if (o_or_x == 0) {
                            i9.setImageResource(R.drawable.o);
                            //  moves[2][2] = 0;
                        } else {
                            i9.setImageResource(R.drawable.x);
                            //  moves[2][2] = 1;
                        }
                        break;

                        default:
                            break;
                }
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                // Get imgbox object and use the values to update the UI with either o or x


                imagesbox val = dataSnapshot.getValue(imagesbox.class);

                int o_or_x = val.value;

                switch (val.imgvw) {
                    case 1:
                        if (o_or_x == 0) {
                            i1.setImageResource(R.drawable.o);
                            //moves[0][0] = 0;
                        } else {
                            i1.setImageResource(R.drawable.x);
                            // moves[0][0] = 1;
                        }

                        i1.setEnabled(false);
                        break;
                    case 2:
                        if (o_or_x == 0) {
                            i2.setImageResource(R.drawable.o);
                            // moves[0][1] = 0;
                        } else {
                            i2.setImageResource(R.drawable.x);
                            //   moves[0][1] = 1;
                        }
                        i2.setEnabled(false);
                        break;
                    case 3:
                        if (o_or_x == 0) {
                            i3.setImageResource(R.drawable.o);
                            //  moves[0][2] = 0;
                        } else {
                            i3.setImageResource(R.drawable.x);
                            // moves[0][2] = 1;
                        }
                        i3.setEnabled(false);
                        break;
                    case 4:
                        if (o_or_x == 0) {
                            i4.setImageResource(R.drawable.o);
                            //  moves[1][0] = 0;
                        } else {
                            i4.setImageResource(R.drawable.x);
                            //  moves[1][0] = 1;
                        }
                        i4.setEnabled(false);
                        break;
                    case 5:
                        if (o_or_x == 0) {
                            i5.setImageResource(R.drawable.o);
                            //  moves[1][1] = 0;
                        } else {
                            i5.setImageResource(R.drawable.x);
                            //  moves[1][1] = 1;
                        }
                        i5.setEnabled(false);
                        break;
                    case 6:
                        if (o_or_x == 0) {
                            i6.setImageResource(R.drawable.o);
                            //  moves[1][2] = 0;
                        } else {
                            i6.setImageResource(R.drawable.x);
                            //  moves[1][2] = 1;
                        }
                        i6.setEnabled(false);
                        break;
                    case 7:
                        if (o_or_x == 0) {
                            i7.setImageResource(R.drawable.o);
                            //   moves[2][0] = 0;
                        } else {
                            i7.setImageResource(R.drawable.x);
                            //   moves[2][0] = 1;
                        }
                        i7.setEnabled(false);
                        break;
                    case 8:
                        if (o_or_x == 0) {
                            i8.setImageResource(R.drawable.o);
                            //  moves[2][1] = 0;
                        } else {
                            i8.setImageResource(R.drawable.x);
                            //   moves[2][1] = 1;
                        }
                        i8.setEnabled(false);
                        break;
                    case 9:
                        if (o_or_x == 0) {
                            i9.setImageResource(R.drawable.o);
                            //  moves[2][2] = 0;
                        } else {
                            i9.setImageResource(R.drawable.x);
                            //  moves[2][2] = 1;
                        }
                        i9.setEnabled(false);
                        break;

                    default:
                        break;
                }


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        iswin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String winner = dataSnapshot.getValue(String.class);
                    if (winner.equalsIgnoreCase("Host")) {
                        Toast.makeText(getApplicationContext(), "Host Wins", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent gameover = new Intent(getApplicationContext(), com.apps.kunalfarmah.realtimetictactoe.gameover_online.class);
                                gameover.putExtra("isHost", ishost);
                                gameover.putExtra("Time",min.getText()+" : "+sec.getText());
                                startActivity(gameover);
                            }
                        }, 1400);
                    } else if (winner.equalsIgnoreCase("Away")) {
                        Toast.makeText(getApplicationContext(), "Away Wins", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent gameover = new Intent(getApplicationContext(), com.apps.kunalfarmah.realtimetictactoe.gameover_online.class);
                                gameover.putExtra("isHost", ishost);
                                gameover.putExtra("Time",min.getText()+" : "+sec.getText());
                                startActivity(gameover);
                            }
                        }, 1400);
                    } else if (winner.equalsIgnoreCase("Draw")) {
                        Toast.makeText(getApplicationContext(), "Drawn!!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent gameover = new Intent(getApplicationContext(), com.apps.kunalfarmah.realtimetictactoe.gameover_online.class);
                                gameover.putExtra("isHost", ishost);
                                gameover.putExtra("Time",min.getText()+" : "+sec.getText());
                                startActivity(gameover);
                            }
                        }, 1400);
                    }



                } catch (Exception e) {
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //Declare the timer
        Timer t = new Timer();


        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        min.setText(String.valueOf(minutes));
                        if(seconds<10)
                        sec.setText("0"+String.valueOf(seconds));
                        else
                            sec.setText(String.valueOf(seconds));
                        seconds += 1;

                        if(seconds == 59 )
                        {
                            seconds=0;
                            minutes+=1;
                            min.setText(String.valueOf(minutes));

                            if(seconds<10)
                                sec.setText("0"+String.valueOf(seconds));
                            else
                                sec.setText(String.valueOf(seconds));

                            seconds += 1;

                        }



                    }

                });
            }

        }, 0, 1000);




        ref = mdata.getReference("Moves");
        ref.addChildEventListener(movelistener);



    }




    @Override
    public void onClick(View v) {



        movescount.setText("Moves : "+(steps+1));


       // ref.addChildEventListener(movelistener);


       // mdata = FirebaseDatabase.getInstance();


        //implementing onClick only once for all buttons by using their IDs

        switch (v.getId()) {


            case R.id.imageView1:

                ++steps;

                // host always takes X (1)
                if (ishost.equalsIgnoreCase("True")) {


                    imagesbox img1 = new imagesbox(1, 1);
                    ref.getDatabase().getReference("Moves").child("img1").setValue(img1);
                    moves[0][0] = 1;


                }
                // away always takes O (0)
                else {
                    imagesbox img1 = new imagesbox(1, 0);
                    ref.getDatabase().getReference("Moves").child("img1").setValue(img1);
                    moves[0][0] = 0;


                }

                if (host_turn)
                    turn.setValue(false);
                else
                    turn.setValue(true);

                break;

            case R.id.imageView2:

                ++steps;


                // host always takes X (1)
                if (ishost.equalsIgnoreCase("True")) {
                    imagesbox img2 = new imagesbox(2, 1);
                    ref.getDatabase().getReference("Moves").child("img2").setValue(img2);
                    moves[0][1] = 1;

                }
                // away always takes O (0)
                else {
                    imagesbox img2 = new imagesbox(2, 0);
                    ref.getDatabase().getReference("Moves").child("img2").setValue(img2);
                    moves[0][1] = 0;


                }

                if (host_turn)
                    turn.setValue(false);
                else
                    turn.setValue(true);

                break;


            case R.id.imageView3:

                ++steps;


                // host always takes X (1)
                if (ishost.equalsIgnoreCase("True")) {
                    imagesbox img3 = new imagesbox(3, 1);
                    ref.getDatabase().getReference("Moves").child("img3").setValue(img3);
                    moves[0][2] = 1;

                }
                // away always takes O (0)
                else {
                    imagesbox img3 = new imagesbox(3, 0);
                    ref.getDatabase().getReference("Moves").child("img3").setValue(img3);
                    moves[0][2] = 0;


                }

                if (host_turn)
                    turn.setValue(false);
                else
                    turn.setValue(true);

                break;
            case R.id.imageView4:

                ++steps;


                // host always takes X (1)
                if (ishost.equalsIgnoreCase("True")) {
                    imagesbox img4 = new imagesbox(4, 1);
                    ref.getDatabase().getReference("Moves").child("img4").setValue(img4);
                    moves[1][0] = 1;
                }
                // away always takes O (0)
                else {
                    imagesbox img4 = new imagesbox(4, 0);
                    ref.getDatabase().getReference("Moves").child("img4").setValue(img4);

                    moves[1][0] = 0;
                }

                if (host_turn)
                    turn.setValue(false);
                else
                    turn.setValue(true);

                break;
            case R.id.imageView5:
                ++steps;

                // host always takes X (1)
                if (ishost.equalsIgnoreCase("True")) {
                    imagesbox img5 = new imagesbox(5, 1);
                    ref.getDatabase().getReference("Moves").child("img5").setValue(img5);
                    moves[1][1] = 1;
                }
                // away always takes O (0)
                else {
                    imagesbox img5 = new imagesbox(5, 0);
                    ref.getDatabase().getReference("Moves").child("img5").setValue(img5);
                    moves[1][1] = 0;

                }

                if (host_turn)
                    turn.setValue(false);
                else
                    turn.setValue(true);

                break;
            case R.id.imageView6:
                ++steps;

                // host always takes X (1)
                if (ishost.equalsIgnoreCase("True")) {
                    imagesbox img6 = new imagesbox(6, 1);
                    ref.getDatabase().getReference("Moves").child("img6").setValue(img6);
                    moves[1][2] = 1;

                }
                // away always takes O (0)
                else {
                    imagesbox img6 = new imagesbox(6, 0);
                    ref.getDatabase().getReference("Moves").child("img6").setValue(img6);
                    moves[1][2] = 0;

                }

                if (host_turn)
                    turn.setValue(false);
                else
                    turn.setValue(true);

                break;
            case R.id.imageView7:
                ++steps;

                // host always takes X (1)
                if (ishost.equalsIgnoreCase("True")) {
                    imagesbox img7 = new imagesbox(7, 1);
                    ref.getDatabase().getReference("Moves").child("img7").setValue(img7);
                    moves[2][0] = 1;
                }
                // away always takes O (0)
                else {
                    imagesbox img7 = new imagesbox(7, 0);
                    ref.getDatabase().getReference("Moves").child("img7").setValue(img7);

                    moves[2][0] = 0;
                }

                if (host_turn)
                    turn.setValue(false);
                else
                    turn.setValue(true);

                break;
            case R.id.imageView8:
                ++steps;

                // host always takes X (1)
                if (ishost.equalsIgnoreCase("True")) {
                    imagesbox img8 = new imagesbox(8, 1);
                    ref.getDatabase().getReference("Moves").child("img8").setValue(img8);
                    moves[2][1] = 1;

                }
                // away always takes O (0)
                else {
                    imagesbox img8 = new imagesbox(8, 0);
                    ref.getDatabase().getReference("Moves").child("img8").setValue(img8);
                    moves[2][1] = 0;

                }

                if (host_turn)
                    turn.setValue(false);
                else
                    turn.setValue(true);

                break;


            case R.id.imageView9:

                ++steps;
                // host always takes X (1)
                if (ishost.equalsIgnoreCase("True")) {
                    imagesbox img9 = new imagesbox(9, 1);
                    ref.getDatabase().getReference("Moves").child("img9").setValue(img9);
                    moves[2][2] = 1;
                }
                // away always takes O (0)
                else {
                    imagesbox img9 = new imagesbox(9, 0);
                    ref.getDatabase().getReference("Moves").child("img9").setValue(img9);
                    moves[2][2] = 0;

                }

                if (host_turn)
                    turn.setValue(false);
                else
                    turn.setValue(true);

                break;

        }



// one player needs min 3 moves to win
        if(steps>=3)
        {
            win = winner(pl1,pl2);
        }

        if (win) {

            i1.setClickable(false);
            i2.setClickable(false);
            i3.setClickable(false);
            i4.setClickable(false);
            i5.setClickable(false);
            i6.setClickable(false);
            i7.setClickable(false);
            i8.setClickable(false);
            i9.setClickable(false);

            if (ishost.equalsIgnoreCase("True"))

                iswin.setValue("Host");

            else

                iswin.setValue("Away");

        }



        // if all turns are done and still no winner, then simply exit saying match drawn and start gameover activity with a delay of 1.4 seconds
        if (steps >=5 && !win) {

            iswin.setValue("Draw");

        }
    }








    // function to check teh winning cases after 5th turn

    private boolean winner(String pl1, String pl2) {

        // check all rows

        if (moves[0][0] != -1 && moves[0][0] == moves[0][1] && moves[0][1] == moves[0][2]) {
            if (moves[0][0] == 0) {
               // Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_SHORT).show();
                return true;
            } else if (moves[0][0] == 1) {
              //  Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (moves[1][0] != -1 && moves[1][0] == moves[1][1] && moves[1][1] == moves[1][2]) {
            if (moves[1][0] == 0) {
              //  Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[1][0] == 1) {
             //   Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (moves[2][0] != -1 && moves[2][0] == moves[2][1] && moves[2][1] == moves[2][2]) {
            if (moves[2][0] == 0) {
             //   Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[2][0] == 1) {
             //   Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        // check all columns

        if (moves[0][0] != -1 && moves[0][0] == moves[1][0] && moves[1][0] == moves[2][0]) {
            if (moves[0][0] == 0) {
               // Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][0] == 1) {
              //  Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }


        if (moves[0][1] != -1 && moves[0][1] == moves[1][1] && moves[1][1] == moves[2][1]) {
            if (moves[0][1] == 0) {
               // Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][1] == 1) {
              //  Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (moves[0][2] != -1 && moves[0][2] == moves[1][2] && moves[1][2] == moves[2][2]) {
            if (moves[0][2] == 0) {
               // Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][2] == 1) {
              //  Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        // checks first diagonal

        if (moves[0][0] != -1 && moves[0][0] == moves[1][1] && moves[1][1] == moves[2][2]) {
            if (moves[0][0] == 0) {
              //  Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][0] == 1) {
              //  Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        // checks secondary diagonal

        if (moves[0][2] != -1 && moves[0][2] == moves[1][1] && moves[1][1] == moves[2][0]) {
            if (moves[0][2] == 0) {
             //   Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][2] == 1) {
             //   Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        return  false;
    }


    @Override
    protected void onStop() {
        super.onStop();

        // resetting the database as -1 when game finishes

        ref.removeEventListener(movelistener);
        iswin.removeValue();
//        connectedRef.removeValue();
  //      lostConnection.removeValue();

        setDefaults();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mdata.goOffline();
    }

    private void settingclicklisteners(){
        i1.setOnClickListener(this);
        i2.setOnClickListener(this);
        i3.setOnClickListener(this);
        i4.setOnClickListener(this);
        i5.setOnClickListener(this);
        i6.setOnClickListener(this);
        i7.setOnClickListener(this);
        i8.setOnClickListener(this);
        i9.setOnClickListener(this);
    }

    void setDefaults(){
        imagesbox defaultvals = new imagesbox(-1,-1);
        ref.getDatabase().getReference("Moves");
        ref.child("img1").setValue(defaultvals);
        ref.child("img2").setValue(defaultvals);
        ref.child("img3").setValue(defaultvals);
        ref.child("img4").setValue(defaultvals);
        ref.child("img5").setValue(defaultvals);
        ref.child("img6").setValue(defaultvals);
        ref.child("img7").setValue(defaultvals);
        ref.child("img8").setValue(defaultvals);
        ref.child("img9").setValue(defaultvals);
    }
}
