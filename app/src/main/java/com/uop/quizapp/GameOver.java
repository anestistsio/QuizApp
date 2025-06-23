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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameOver extends AppCompatActivity {
    private TextView winning_tv,team1Name_tv,team2Name_tv,team1Score_tv,team2Score_tv;
    private int t1s,t2s,score,timeInSeconds,questionsPerCategory;
    private String t1n,t2n,winning_team,language;
    private boolean isMute;
    byte[] team1byte;
    byte[] team2byte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Set fullscreen
        setContentView(R.layout.activity_game_over);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // call this method to initialize the start values
        initializing();

    }
    private void initializing(){
        DataBetweenActivitiesManager db = DataBetweenActivitiesManager.getInstance();
        GameState gs = db.getGameState();
        isMute = gs != null && gs.isMute;
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

        t1n = gs.team1Name;
        t2n = gs.team2Name;
        t1s = gs.team1Score;
        t2s = gs.team2Score;
        team1byte = gs.team1byte;
        team2byte = gs.team2byte;
        language = gs.selectedLanguage;
        score = gs.score;
        timeInSeconds = gs.timeInSeconds;
        //getting the winning team's image
        byte[] winning_byte = db.get("winning_byte");
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
                if (winning_team.equals("Ομάδα 1") || winning_team.equals("Ομάδα 2")) {
                    winning_tv.setText( winning_team);
                }else {
                    winning_tv.setText(winning_team);
                }
            }
        }else {
            if (t1s == t2s) {
                winning_tv.setText("DRAW!");
            } else {
                winning_tv.setText(winning_team);
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
        DataBetweenActivitiesManager db = DataBetweenActivitiesManager.getInstance();
        db.put("restart_boolean", true);
        GameState gs = db.getGameState();
        if (gs != null) {
            db.put("selected_language", gs.selectedLanguage);
            db.put("timeInSeconds", gs.timeInSeconds);
            db.put("score", gs.score);
            db.put("isMute", gs.isMute);
            if(!gs.team1Name.equals("Ομάδα 1") && !gs.team1Name.equals("Team 1")){
                db.put("t1n", gs.team1Name);
            }
            if(!gs.team2Name.equals("Ομάδα 2") && !gs.team2Name.equals("Team 2")){
                db.put("t2n", gs.team2Name);
            }
            db.put("team1byte", gs.team1byte);
            db.put("team2byte", gs.team2byte);
        }
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
        String subject = "Download this app";
        String body = "https://google.play.com";
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
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