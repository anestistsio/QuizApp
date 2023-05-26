package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {
    private TextView winning_tv,team1Name_tv,team2Name_tv,team1Score_tv,team2Score_tv;
    private int t1s,t2s;
    private String t1n,t2n,winning_team,language;
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
        ImageView winning_im = findViewById(R.id.winning_im);

        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");
        //getting selected_language from SelectCategory.java
        language = getIntent().getExtras().getString("selected_language");
        //getting the winning team's image
        Bundle ex = getIntent().getExtras();
        byte[] winning_byte = ex.getByteArray("winning_byte");
        if (winning_byte != null) {
            Bitmap winning_image = BitmapFactory.decodeByteArray(winning_byte, 0, winning_byte.length);
            winning_im.setImageBitmap(winning_image);
        }else {
            winning_im.setVisibility(View.GONE);
        }

        if(t1s>t2s) {
            winning_team = t1n;
        }else {
            winning_team = t2n;
        }
        if (!language.equals("English")) {
            winning_tv.setText("Η ομάδα " + winning_team + " νικάει!");
        }else {
            winning_tv.setText(winning_team + " WINS!");
        }

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