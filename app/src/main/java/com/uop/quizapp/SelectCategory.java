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
        //DBContract category = new DBContract();
        switch (view.getId())
        {
            case R.id.national_bt:
                selectedCategory = DBContract.NationalTable.TABLE_NAME;
                break;
            case R.id.general_bt:
                selectedCategory = DBContract.GeneralTable.TABLE_NAME;
                break;
            case R.id.clubs_bt:
                selectedCategory = DBContract.ClubsTable.TABLE_NAME;
                break;
            case R.id.geography_bt:
                selectedCategory= DBContract.GeographyTable.TABLE_NAME;
                break;
        }
        if (!isMute) {
            click_sound.start();
        }
        //pass selected category to MainGame.class
        Intent intent = new Intent(SelectCategory.this, MainGame.class);
        intent.putExtra("selectedCategory", selectedCategory);
        intent.putExtra("playing_team", playing_team);
        intent.putExtra("team1Name",t1n);
        intent.putExtra("team2Name",t2n);
        intent.putExtra("team1Score",t1s);
        intent.putExtra("team2Score",t2s);
        intent.putExtra("score", score);
        intent.putExtra("lastChance",lastChance);
        // passing values for correct answered counters for each category for each team
        intent.putExtra("team1NationalCorrectAnswers", team1NationalCorrectAnswers);
        intent.putExtra("team2NationalCorrectAnswers", team2NationalCorrectAnswers);
        intent.putExtra("team1ClubsCorrectAnswers", team1ClubsCorrectAnswers);
        intent.putExtra("team2ClubsCorrectAnswers", team2ClubsCorrectAnswers);
        intent.putExtra("team1GeographyCorrectAnswers", team1GeographyCorrectAnswers);
        intent.putExtra("team2GeographyCorrectAnswers", team2GeographyCorrectAnswers);
        intent.putExtra("team1GeneralCorrectAnswers", team1GeneralCorrectAnswers);
        intent.putExtra("team2GeneralCorrectAnswers", team2GeneralCorrectAnswers);
        //passing the language
        intent.putExtra("selected_language",language);
        intent.putExtra("isMute",isMute);
        //passing the time in seconds
        intent.putExtra("timeInSeconds",timeInSeconds);
        //passing the images to MainGame.class
        if (team1bitmap != null){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team1bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
            byte[] team1byte = bytes.toByteArray();
            intent.putExtra("team1byte",team1byte);
        }else {
            intent.putExtra("team1byte", (byte[]) null);
        }

        if (team2bitmap != null){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team2bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
            byte[] team2byte = bytes.toByteArray();
            intent.putExtra("team2byte",team2byte);
        }else {
            intent.putExtra("team2byte", (byte[]) null);
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


        //take the team names and scores and playing team
        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");
        score = getIntent().getExtras().getInt("score");
        playing_team = getIntent().getExtras().getString("playing_team");
        lastChance = getIntent().getExtras().getBoolean("lastChance");
        //retrieving the values for correct answered counters for each category for each team
        team1NationalCorrectAnswers =  getIntent().getExtras().getInt("team1NationalCorrectAnswers");
        team2NationalCorrectAnswers =  getIntent().getExtras().getInt("team2NationalCorrectAnswers");
        team1ClubsCorrectAnswers =  getIntent().getExtras().getInt("team1ClubsCorrectAnswers");
        team2ClubsCorrectAnswers =  getIntent().getExtras().getInt("team2ClubsCorrectAnswers");
        team1GeographyCorrectAnswers =  getIntent().getExtras().getInt("team1GeographyCorrectAnswers");
        team2GeographyCorrectAnswers =  getIntent().getExtras().getInt("team2GeographyCorrectAnswers");
        team1GeneralCorrectAnswers =  getIntent().getExtras().getInt("team1GeneralCorrectAnswers");
        team2GeneralCorrectAnswers =  getIntent().getExtras().getInt("team2GeneralCorrectAnswers");

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
        language = getIntent().getExtras().getString("selected_language");
        isMute = getIntent().getExtras().getBoolean("isMute");
        //getting the time in seconds
        timeInSeconds = getIntent().getExtras().getInt("timeInSeconds");
        //getting the images for two teams
        Bundle ex = getIntent().getExtras();
        team1byte = ex.getByteArray("team1byte");
        if (team1byte != null) {
            team1bitmap = BitmapFactory.decodeByteArray(team1byte, 0, team1byte.length);
            team1_im.setImageBitmap(team1bitmap);
        }else {
            team1_im.setImageDrawable(getResources().getDrawable(R.drawable.user_im));
        }
        team2byte = ex.getByteArray("team2byte");
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
        intent.putExtra("team1Name",t1n);
        intent.putExtra("team2Name",t2n);
        intent.putExtra("team1Score",t1s);
        intent.putExtra("team2Score",t2s);
        //passing selected_language
        intent.putExtra("selected_language",language);
        intent.putExtra("isMute",isMute);
        intent.putExtra("score",score);
        intent.putExtra("timeInSeconds",timeInSeconds);
        intent.putExtra("team1byte",team1byte);
        intent.putExtra("team2byte", team2byte);
        //passing the winning team image
        if (t1s > t2s){
            if (team1bitmap != null){
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                team1bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
                team1byte = bytes.toByteArray();
                intent.putExtra("winning_byte",team1byte);
            }else {
                intent.putExtra("winning_byte", (byte[]) null);
            }
        } else if (t1s == t2s) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.draw);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
            team1byte = bytes.toByteArray();
            intent.putExtra("winning_byte",team1byte);

        } else {
            if (team2bitmap != null){
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                team2bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
                team2byte = bytes.toByteArray();
                intent.putExtra("winning_byte",team2byte);
            }else {
                intent.putExtra("winning_byte", (byte[]) null);
            }
        }
        startActivity(intent);
    }

}