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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uop.quizapp.Category;

import java.io.ByteArrayOutputStream;

public class SelectCategory extends AppCompatActivity {
    private TextView  teamplay_tv,team1_name_tv,team2_name_tv,team1_score_tv,team2_score_tv, team1_geography, team1_general, team1_national, team1_clubs, team2_geography, team2_general, team2_national, team2_clubs;
    private String selectedCategory,playing_team,t1n,t2n,language;//t1n = team1name and t1s = team2score
    private int t1s,t2s,timeInSeconds;
    private int team1NationalCorrectAnswers;
    private int team2NationalCorrectAnswers;
    private int team1ClubsCorrectAnswers;
    private int team2ClubsCorrectAnswers;
    private int team1GeographyCorrectAnswers;
    private int team2GeographyCorrectAnswers;
    private int team1GeneralCorrectAnswers;
    private int team2GeneralCorrectAnswers;
    private Bitmap team1bitmap,team2bitmap;
    private byte[] team1byte;
    private byte[] team2byte;
    private boolean lastChance;
    private int score;
    private View score_layout,shadow_v;
    private ImageView playing_team_im;
    ImageButton national_bt;
    ImageButton general_bt;
    ImageButton geography_bt;
    ImageButton clubs_bt;
    private boolean isMute;
    private TextView general_tv,geography_tv,national_tv,clubs_tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Set fullscreen
        setContentView(R.layout.activity_select_category);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // call this method to initialize the start values
        // and check if any team wins or if any team answers 3 correct questions of any category
        initializing();

    }
    //by clicking any of image buttons this starts this method
    public void Startgame(View view) {
        //initialize click sound
        final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
        //check which button user clicked
        switch (view.getId())
        {
            case R.id.national_bt:
                selectedCategory = Category.NATIONAL;
                break;
            case R.id.general_bt:
                selectedCategory = Category.GENERAL;
                break;
            case R.id.clubs_bt:
                selectedCategory = Category.CLUBS;
                break;
            case R.id.geography_bt:
                selectedCategory = Category.GEOGRAPHY;
                break;
        }
        if (!isMute) {
            click_sound.start();
        }
        //pass selected category to MainGame.class
        Intent intent = new Intent(SelectCategory.this, MainGame.class);
        RedisManager db = RedisManager.getInstance();
        db.put("selectedCategory", selectedCategory);
        db.put("playing_team", playing_team);
        db.put("team1Name", t1n);
        db.put("team2Name", t2n);
        db.put("team1Score", t1s);
        db.put("team2Score", t2s);
        db.put("score", score);
        db.put("lastChance", lastChance);
        db.put("team1NationalCorrectAnswers", team1NationalCorrectAnswers);
        db.put("team2NationalCorrectAnswers", team2NationalCorrectAnswers);
        db.put("team1ClubsCorrectAnswers", team1ClubsCorrectAnswers);
        db.put("team2ClubsCorrectAnswers", team2ClubsCorrectAnswers);
        db.put("team1GeographyCorrectAnswers", team1GeographyCorrectAnswers);
        db.put("team2GeographyCorrectAnswers", team2GeographyCorrectAnswers);
        db.put("team1GeneralCorrectAnswers", team1GeneralCorrectAnswers);
        db.put("team2GeneralCorrectAnswers", team2GeneralCorrectAnswers);
        db.put("selected_language", language);
        db.put("isMute", isMute);
        db.put("timeInSeconds", timeInSeconds);
        //passing the images to MainGame.class
        if (team1bitmap != null){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team1bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
            byte[] team1byte = bytes.toByteArray();
            db.put("team1byte", team1byte);
        }else {
            db.put("team1byte", null);
        }

        if (team2bitmap != null){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team2bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
            byte[] team2byte = bytes.toByteArray();
            db.put("team2byte", team2byte);
        }else {
            db.put("team2byte", null);
        }


        startActivity(intent);
    }

    private void initializing(){
        teamplay_tv = findViewById(R.id.teamplay_tv);
        team1_name_tv = findViewById(R.id.team1_name_tv);
        team2_name_tv = findViewById(R.id.team2_name_tv);
        team1_score_tv =findViewById(R.id.team1_score_tv);
        team2_score_tv = findViewById(R.id.team2_score_tv);
        national_bt = findViewById(R.id.national_bt);
        general_bt = findViewById(R.id.general_bt);
        geography_bt = findViewById(R.id.geography_bt);
        clubs_bt = findViewById(R.id.clubs_bt);
        ImageView team1_im = findViewById(R.id.team1_im);
        ImageView team2_im = findViewById(R.id.team2_im);
        Button score_bt = findViewById(R.id.score_bt);
        score_layout = findViewById(R.id.score_layout);
        playing_team_im = findViewById(R.id.playing_team_im);
        team1_geography = findViewById(R.id.team1_geographyquestions);
        team1_general = findViewById(R.id.team1_generalquestions);
        team1_national = findViewById(R.id.team1_nationalquestions);
        team1_clubs = findViewById(R.id.team1_clubsquestions);
        team2_geography = findViewById(R.id.team2_geographyquestions);
        team2_general = findViewById(R.id.team2_generalquestions);
        team2_national = findViewById(R.id.team2_nationalquestions);
        team2_clubs = findViewById(R.id.team2_clubsquestions);
        shadow_v = findViewById(R.id.shadow_v);
        general_tv = findViewById(R.id.general_tv);
        geography_tv = findViewById(R.id.geography_tv);
        clubs_tv = findViewById(R.id.clubs_tv);
        national_tv = findViewById(R.id.national_tv);


        //take the team names and scores and playing team from RedisManager
        RedisManager db = RedisManager.getInstance();
        t1n = db.get("team1Name");
        t2n = db.get("team2Name");
        t1s = db.get("team1Score");
        t2s = db.get("team2Score");
        score = db.get("score");
        playing_team = db.get("playing_team");
        lastChance = db.get("lastChance");
        //retrieving the values for correct answered counters for each category for each team
        team1NationalCorrectAnswers =  db.get("team1NationalCorrectAnswers");
        team2NationalCorrectAnswers =  db.get("team2NationalCorrectAnswers");
        team1ClubsCorrectAnswers =  db.get("team1ClubsCorrectAnswers");
        team2ClubsCorrectAnswers =  db.get("team2ClubsCorrectAnswers");
        team1GeographyCorrectAnswers =  db.get("team1GeographyCorrectAnswers");
        team2GeographyCorrectAnswers =  db.get("team2GeographyCorrectAnswers");
        team1GeneralCorrectAnswers =  db.get("team1GeneralCorrectAnswers");
        team2GeneralCorrectAnswers =  db.get("team2GeneralCorrectAnswers");

        //set the score to the score board

        team1_geography.setText(team1GeographyCorrectAnswers + "/" + score/4);
        team1_general.setText(team1GeneralCorrectAnswers + "/" + score/4);
        team1_national.setText(team1NationalCorrectAnswers + "/" + score/4);
        team1_clubs.setText(team1ClubsCorrectAnswers + "/" + score/4);
        team2_geography.setText(team2GeographyCorrectAnswers + "/" + score/4);
        team2_general.setText(team2GeneralCorrectAnswers + "/" + score/4);
        team2_national.setText(team2NationalCorrectAnswers + "/" + score/4);
        team2_clubs.setText(team2ClubsCorrectAnswers + "/" + score/4);

        //getting the selected_language from MainActivity.java or from MainGame.java
        language = db.get("selected_language");
        isMute = db.get("isMute");
        //getting the time in seconds
        timeInSeconds = db.get("timeInSeconds");
        //getting the images for two teams
        team1byte = db.get("team1byte");
        if (team1byte != null) {
            team1bitmap = BitmapFactory.decodeByteArray(team1byte, 0, team1byte.length);
            team1_im.setImageBitmap(team1bitmap);
        }else {
            team1_im.setImageDrawable(getResources().getDrawable(R.drawable.user_im));
        }
        team2byte = db.get("team2byte");
        if (team2byte != null) {
            team2bitmap = BitmapFactory.decodeByteArray(team2byte, 0, team2byte.length);
            team2_im.setImageBitmap(team2bitmap);
        }else {
            team2_im.setImageDrawable(getResources().getDrawable(R.drawable.user_im));
        }

        //checks which team is playing to put the right image in the playing_team_im
        if (playing_team.equals(t1n)){
            if (team1byte != null) {
                playing_team_im.setImageBitmap(team1bitmap);
            }else {
                playing_team_im.setImageDrawable(getResources().getDrawable(R.drawable.user_im));
            }
        }else {
            if (team2byte != null) {
                playing_team_im.setImageBitmap(team2bitmap);
            }else {
                playing_team_im.setImageDrawable(getResources().getDrawable(R.drawable.user_im));
            }
        }
        //checks if any team answered all  questions from any category and if yes it disables the button
        if (playing_team.equals(t1n)){
            if (team1NationalCorrectAnswers >= score/4){
                national_bt.setClickable(false);
                national_bt.setImageDrawable(getResources().getDrawable(R.drawable.national_icon_disabled));
            }
            if (team1ClubsCorrectAnswers >= score/4) {
                clubs_bt.setClickable(false);
                clubs_bt.setImageDrawable(getResources().getDrawable(R.drawable.clubs_icon_disabled));
            }
            if (team1GeographyCorrectAnswers >= score/4) {
                geography_bt.setClickable(false);
                geography_bt.setImageDrawable(getResources().getDrawable(R.drawable.geography_icon_disabled));
            }
            if (team1GeneralCorrectAnswers >= score/4) {
                general_bt.setClickable(false);
                general_bt.setImageDrawable(getResources().getDrawable(R.drawable.general_icon_disabled));
            }
        }else {
            if (team2NationalCorrectAnswers >= score/4){
                national_bt.setClickable(false);
                national_bt.setImageDrawable(getResources().getDrawable(R.drawable.national_icon_disabled));
            }
            if (team2ClubsCorrectAnswers >= score/4) {
                clubs_bt.setClickable(false);
                clubs_bt.setImageDrawable(getResources().getDrawable(R.drawable.clubs_icon_disabled));
            }
            if (team2GeographyCorrectAnswers >= score/4) {
                geography_bt.setClickable(false);
                geography_bt.setImageDrawable(getResources().getDrawable(R.drawable.geography_icon_disabled));
            }
            if (team2GeneralCorrectAnswers >= score/4) {
                general_bt.setClickable(false);
                general_bt.setImageDrawable(getResources().getDrawable(R.drawable.general_icon_disabled));
            }
        }


        //set team names and scores and playing team to TextViews
        if (!language.equals("English")) {
            score_bt.setText("ΣΚΟΡ");
            general_tv.setText("Γενικές");
            geography_tv.setText("Γεωγραφία");
            clubs_tv.setText("Συλλόγοι");
            national_tv.setText("Εθνικές");

        }
        teamplay_tv.setText(playing_team);
        team1_name_tv.setText(t1n);
        team2_name_tv.setText(t2n);
        team1_score_tv.setText(String.valueOf(t1s));
        team2_score_tv.setText(String.valueOf(t2s));

        // if team 1 reach first the score the team 2 has one last chance
        if (t1s >= score && lastChance){
            lastChance = false;
        } else if (t2s >= score) {
            GameEnd();
        } else if (t1s >= score) {
            GameEnd();
        }

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
    public void Score(View view){
        score_layout.setVisibility(View.VISIBLE);
        geography_bt.setClickable(false);
        general_bt.setClickable(false);
        clubs_bt.setClickable(false);
        national_bt.setClickable(false);
        shadow_v.setVisibility(View.GONE);


    }
    public void ReturnFromScore(View view){
        score_layout.setVisibility(View.GONE);
        geography_bt.setClickable(true);
        general_bt.setClickable(true);
        clubs_bt.setClickable(true);
        national_bt.setClickable(true);
        shadow_v.setVisibility(View.VISIBLE);
    }
    private void GameEnd(){
        Intent intent = new Intent(SelectCategory.this, GameOver.class);
        RedisManager db = RedisManager.getInstance();
        db.put("team1Name", t1n);
        db.put("team2Name", t2n);
        db.put("team1Score", t1s);
        db.put("team2Score", t2s);
        db.put("selected_language", language);
        db.put("isMute", isMute);
        db.put("score", score);
        db.put("timeInSeconds", timeInSeconds);
        db.put("team1byte", team1byte);
        db.put("team2byte", team2byte);
        //passing the winning team image
        if (t1s > t2s){
            if (team1bitmap != null){
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                team1bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
                team1byte = bytes.toByteArray();
                db.put("winning_byte", team1byte);
            }else {
                db.put("winning_byte", null);
            }
        } else if (t1s == t2s) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.draw);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
            team1byte = bytes.toByteArray();
            db.put("winning_byte", team1byte);

        } else {
            if (team2bitmap != null){
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                team2bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
                team2byte = bytes.toByteArray();
                db.put("winning_byte", team2byte);
            }else {
                db.put("winning_byte", null);
            }
        }
        startActivity(intent);
    }

}