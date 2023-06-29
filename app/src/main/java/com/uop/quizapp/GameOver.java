package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class GameOver extends AppCompatActivity {
    private TextView winning_tv,team1Name_tv,team2Name_tv,team1Score_tv,team2Score_tv;
    private int t1s,t2s,score,timeInSeconds,questionsPerCategory;
    private String t1n,t2n,winning_team,language;
    private boolean isMute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // call this method to initialize the start values
        initializing();

    }
    private void initializing(){
        isMute = getIntent().getExtras().getBoolean("isMute");
        final MediaPlayer win_sound = MediaPlayer.create(this,R.raw.win_sound);
        if (!isMute) {
            win_sound.start();
        }
        winning_tv = findViewById(R.id.winning_tv);
        team1Name_tv = findViewById(R.id.team1Name_tv);
        team2Name_tv = findViewById(R.id.team2Name_tv);
        team1Score_tv = findViewById(R.id.team1Score_tv);
        team2Score_tv = findViewById(R.id.team2Score_tv);
        ImageView winning_im = findViewById(R.id.winning_im);

        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");
        //getting selected_language from SelectCategory.java
        language = getIntent().getExtras().getString("selected_language");
        score = getIntent().getExtras().getInt("score");
        timeInSeconds = getIntent().getExtras().getInt("timeInSeconds");
        //getting the winning team's image
        Bundle ex = getIntent().getExtras();
        byte[] winning_byte = ex.getByteArray("winning_byte");
        if (winning_byte != null) {
            Bitmap winning_image = BitmapFactory.decodeByteArray(winning_byte, 0, winning_byte.length);
            winning_im.setImageBitmap(winning_image);
        }else {
            winning_im.setImageDrawable(getResources().getDrawable(R.drawable.user_im));
        }

        if(t1s>t2s) {
            winning_team = t1n;
        }else{
            winning_team = t2n;
        }
        if (!language.equals("English")) {
            if (t1s == t2s){
                winning_tv.setText("Ισοπαλία!");

            }else {
                winning_tv.setText("Η ομάδα " + winning_team + " νικάει!");
            }
        }else {
            if (t1s == t2s) {
                winning_tv.setText("DRAW!");
            } else {
                winning_tv.setText(winning_team + " WINS!");
            }
        }

        team1Name_tv.setText(t1n);
        team2Name_tv.setText(t2n);
        team1Score_tv.setText(String.valueOf(t1s));
        team2Score_tv.setText(String.valueOf(t2s));

    }

    public void exit(View view) {
        //initialize click sound
        final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
        if (!isMute) {
            click_sound.start();
        }
        finishAffinity();
        finish();
    }
    public void restart(View view) {
        //initialize click sound
        final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
        if (!isMute) {
            click_sound.start();
        }
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        intent.putExtra("restart_boolean",true);
        intent.putExtra("selected_language", language);
        intent.putExtra("timeInSeconds", timeInSeconds);
        intent.putExtra("score", score);
        intent.putExtra("isMute",isMute);
        intent.putExtra("t1n",t1n);
        intent.putExtra("t2n",t2n);
        startActivity(intent);
        finish();
    }
    public void shareGameOver(View view) {
        final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
        if (!isMute) {
            click_sound.start();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String Body = "Download this app";
        String Sub = "https://google.play.com";
        intent.putExtra(Intent.EXTRA_TEXT, Body);
        intent.putExtra(Intent.EXTRA_TEXT, Sub);
        startActivity(Intent.createChooser(intent, "Share using"));
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAndRemoveTask();
            this.finishAffinity();

        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK twice to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }
}