package com.apps.kunalfarmah.realtimetictactoe;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunalfarmah.realtimetictactoe.R;


public class MainActivity_optimised extends AppCompatActivity implements View.OnClickListener {

    int turns = -1;
    boolean win = false;


    ImageView i1;
    ImageView i2;
    ImageView i3;
    ImageView i4;
    ImageView i5;
    ImageView i6;
    ImageView i7;
    ImageView i8;
    ImageView i9;
    ImageView o;
    ImageView x;
    TextView t1;
    TextView t2;

    String pl1;
    String pl2;

    int moves[][] = {{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();   //receiving the extras supplied to MainActivity

        pl1 = (String) intent.getSerializableExtra("player1");
        pl2 = (String) intent.getSerializableExtra("player2");


        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);

        if (!pl1.equals("") && !pl2.equals("")) {
            t1.setText(pl1 + ": X");
            t2.setText(pl2 + ": O");
        } else if (pl1.equals("") && pl2.equals("")) {
            pl1 = "Player 1";
            pl2 = "Player 2";
        }

        i1 = (ImageView) findViewById(R.id.imageView1);
        i2 = (ImageView) findViewById(R.id.imageView2);
        i3 = (ImageView) findViewById(R.id.imageView3);
        i4 = (ImageView) findViewById(R.id.imageView4);
        i5 = (ImageView) findViewById(R.id.imageView5);
        i6 = (ImageView) findViewById(R.id.imageView6);
        i7 = (ImageView) findViewById(R.id.imageView7);
        i8 = (ImageView) findViewById(R.id.imageView8);
        i9 = (ImageView) findViewById(R.id.imageView9);

        Toast.makeText(getApplicationContext(), pl1 + " Goes first", Toast.LENGTH_SHORT).show();


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

    @Override
    public void onClick(View view) {              //implementing onClick only once for all buttons by using their IDs

        switch (view.getId()) {

            case R.id.imageView1:

                ++turns;
                if (turns % 2 == 0) {
                    i1.setImageResource(R.drawable.x);

                    moves[0][0] = 0;
                } else {
                    i1.setImageResource(R.drawable.o);
                    moves[0][0] = 1;
                }

                i1.setEnabled(false);   // disabling the onClickListener after first click

                break;

            case R.id.imageView2:

                ++turns;
                if (turns % 2 == 0) {
                    i2.setImageResource(R.drawable.x);

                    moves[0][1] = 0;
                } else {
                    i2.setImageResource(R.drawable.o);
                    moves[0][1] = 1;
                }

                i2.setEnabled(false);

                break;

            case R.id.imageView3:
                ++turns;
                if (turns % 2 == 0) {
                    i3.setImageResource(R.drawable.x);

                    moves[0][2] = 0;
                } else {
                    i3.setImageResource(R.drawable.o);
                    moves[0][2] = 1;
                }
                i3.setEnabled(false);

                break;

            case R.id.imageView4:
                ++turns;
                if (turns % 2 == 0) {
                    i4.setImageResource(R.drawable.x);

                    moves[1][0] = 0;
                } else {
                    i4.setImageResource(R.drawable.o);
                    moves[1][0] = 1;
                }
                i4.setEnabled(false);
                break;

            case R.id.imageView5:
                ++turns;
                if (turns % 2 == 0) {
                    i5.setImageResource(R.drawable.x);

                    moves[1][1] = 0;
                } else {
                    i5.setImageResource(R.drawable.o);
                    moves[1][1] = 1;
                }
                i5.setEnabled(false);
                break;

            case R.id.imageView6:
                ++turns;
                if (turns % 2 == 0) {
                    i6.setImageResource(R.drawable.x);

                    moves[1][2] = 0;
                } else {
                    i6.setImageResource(R.drawable.o);
                    moves[1][2] = 1;
                }

                i6.setEnabled(false);
                break;

            case R.id.imageView7:
                ++turns;
                if (turns % 2 == 0) {
                    i7.setImageResource(R.drawable.x);

                    moves[2][0] = 0;
                } else {
                    i7.setImageResource(R.drawable.o);
                    moves[2][0] = 1;
                }

                i7.setEnabled(false);
                break;

            case R.id.imageView8:
                ++turns;
                if (turns % 2 == 0) {
                    i8.setImageResource(R.drawable.x);

                    moves[2][1] = 0;
                } else {
                    i8.setImageResource(R.drawable.o);
                    moves[2][1] = 1;
                }
                i8.setEnabled(false);
                break;

            case R.id.imageView9:
                ++turns;
                if (turns % 2 == 0) {
                    i9.setImageResource(R.drawable.x);

                    moves[2][2] = 0;
                } else {
                    i9.setImageResource(R.drawable.o);
                    moves[2][2] = 1;
                }

                i9.setEnabled(false);
                break;

        }


        if (turns >= 4 && turns <= 8)
            win = winner(pl1, pl2);

        // if a player wins, start gameover activity with a delay of 1.4 seconds

        if (win) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent gameover = new Intent(MainActivity_optimised.this, com.apps.kunalfarmah.realtimetictactoe.gameover.class);
                    startActivity(gameover);
                }
            }, 1400);
        }

        if (turns == 8 && !win) {
            Toast.makeText(getApplicationContext(), "Drawn!!", Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent gameover = new Intent(MainActivity_optimised.this, com.apps.kunalfarmah.realtimetictactoe.gameover.class);
                    startActivity(gameover);
                }
            }, 1400);
        }
    }



    private boolean winner(String pl1, String pl2) {

        if (moves[0][0] != -1 && moves[0][0] == moves[0][1] && moves[0][1] == moves[0][2]) {
            if (moves[0][0] == 0) {
                Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][0] == 1) {
                Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (moves[1][0] != -1 && moves[1][0] == moves[1][1] && moves[1][1] == moves[1][2]) {
            if (moves[1][0] == 0) {
                Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[1][0] == 1) {
                Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (moves[2][0] != -1 && moves[2][0] == moves[2][1] && moves[2][1] == moves[2][2]) {
            if (moves[2][0] == 0) {
                Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[2][0] == 1) {
                Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (moves[0][0] != -1 && moves[0][0] == moves[1][0] && moves[1][0] == moves[2][0]) {
            if (moves[0][0] == 0) {
                Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][0] == 1) {
                Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }


        if (moves[0][1] != -1 && moves[0][1] == moves[1][1] && moves[1][1] == moves[2][1]) {
            if (moves[0][1] == 0) {
                Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][1] == 1) {
                Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (moves[0][2] != -1 && moves[0][2] == moves[1][2] && moves[1][2] == moves[2][2]) {
            if (moves[0][2] == 0) {
                Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][2] == 1) {
                Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (moves[0][0] != -1 && moves[0][0] == moves[1][1] && moves[1][1] == moves[2][2]) {
            if (moves[0][0] == 0) {
                Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][0] == 1) {
                Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (moves[0][2] != -1 && moves[0][2] == moves[1][1] && moves[1][1] == moves[2][0]) {
            if (moves[0][2] == 0) {
                Toast.makeText(getApplicationContext(), pl1 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            } else if (moves[0][2] == 1) {
                Toast.makeText(getApplicationContext(), pl2 + " Wins", Toast.LENGTH_LONG).show();
                return true;
            }
        }

            return  false;
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


