package com.ghy.katherinejy.first;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Basketball extends Activity {

    private Button mTeam1ThreePointsBtn;
    private Button mTeam1TwoPointsBtn;
    private Button mTeam1FreeThrowBtn;
    private Button mTeam2ThreePointsBtn;
    private Button mTeam2TwoPointsBtn;
    private Button mTeam2FreeThrowBtn;
    private Button mResetBtn;
    private TextView mTeam1Score;
    private TextView mTeam2Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basketball);
        Log.i("Life","onCreat:");

        mTeam1ThreePointsBtn = (Button) findViewById(R.id.team1_3points);
        mTeam1TwoPointsBtn = (Button) findViewById(R.id.team1_2points);
        mTeam1FreeThrowBtn = (Button) findViewById(R.id.team1_freeThrow);
        mTeam1Score = (TextView) findViewById(R.id.score1);
        mTeam2ThreePointsBtn = (Button) findViewById(R.id.team2_3points);
        mTeam2TwoPointsBtn = (Button) findViewById(R.id.team2_2points);
        mTeam2FreeThrowBtn = (Button) findViewById(R.id.team2_freeThrow);
        mTeam2Score = (TextView) findViewById(R.id.score2);
        mResetBtn = (Button) findViewById(R.id.reset);

        mTeam1ThreePointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int past = Integer.parseInt(mTeam1Score.getText().toString());
                mTeam1Score.setText( (past+3)+"" );
            }
        });

        mTeam1TwoPointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int past = Integer.parseInt(mTeam1Score.getText().toString());
                mTeam1Score.setText( (past+2)+"" );
            }
        });

        mTeam1FreeThrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int past = Integer.parseInt(mTeam1Score.getText().toString());
                mTeam1Score.setText( (past+1)+"" );
            }
        });

        mTeam2ThreePointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int past = Integer.parseInt(mTeam2Score.getText().toString());
                mTeam2Score.setText( (past+3)+"" );
            }
        });

        mTeam2TwoPointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int past = Integer.parseInt(mTeam2Score.getText().toString());
                mTeam2Score.setText( (past+2)+"" );
            }
        });

        mTeam2FreeThrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int past = Integer.parseInt(mTeam2Score.getText().toString());
                mTeam2Score.setText( (past+1)+"" );
            }
        });

        mResetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mTeam1Score.setText(0+"");
                mTeam2Score.setText(0+"");
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String score1 = mTeam1Score.getText().toString();
        String score2 = mTeam2Score.getText().toString();
        Log.i("life","onSaveInstanceState");
        outState.putString("team1",score1);
        outState.putString("team2",score2);
    }

    @Override
    protected  void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String score1 = savedInstanceState.getString("team1");
        String score2 = savedInstanceState.getString("team2");
        Log.i("life","onRestoreInstanceState");
        mTeam1Score.setText(score1);
        mTeam2Score.setText(score2);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("life","onStart:");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("life","onResume:");
    }

    @Override
    protected  void onRestart(){
        super.onRestart();
        Log.i("life","onRestart:");
    }

    @Override
    protected  void onPause(){
        super.onPause();
        Log.i("life","onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("life","onStop:");
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        Log.i("life","onDestroy:");
    }

}
