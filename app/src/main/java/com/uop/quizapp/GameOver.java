package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {
    private TextView winning_tv,team1Name_tv,team2Name_tv,team1Score_tv,team2Score_tv;
    private int t1s,t2s;
    private String t1n,t2n,winning_team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        // call this method to initialize the start values
        initializing();

    }
    private void initializing(){

        final MediaPlayer win_sound = MediaPlayer.create(this,R.raw.win_sound);
        win_sound.start();
        winning_tv = findViewById(R.id.winning_tv);
        team1Name_tv = findViewById(R.id.team1Name_tv);
        team2Name_tv = findViewById(R.id.team2Name_tv);
        team1Score_tv = findViewById(R.id.team1Score_tv);
        team2Score_tv = findViewById(R.id.team2Score_tv);

        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");

        if(t1s>t2s) {
            winning_team = t1n;
        }else {
            winning_team = t2n;
        }
        winning_tv.setText(winning_team + " WINS!");
        team1Name_tv.setText(t1n);
        team2Name_tv.setText(t2n);
        team1Score_tv.setText(String.valueOf(t1s));
        team2Score_tv.setText(String.valueOf(t2s));

    }

    public void exit(View view) {
        //initialize click sound
        final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
        click_sound.start();
        finishAffinity();
        finish();
    }
    public void restart(View view) {
        //initialize click sound
        final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
        click_sound.start();
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {

    }
}