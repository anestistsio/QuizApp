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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class SelectCategory extends AppCompatActivity {
    private TextView  teamplay_tv,team1_name_tv,team2_name_tv,team1_score_tv,team2_score_tv,team1_greekfootball,team1_generalquestions,team1_sciencequestions,team1_sportsquestions,team2_greekfootball,team2_generalquestions,team2_sciencequestions,team2_sportsquestions;
    private String selectedCategory,playing_team,t1n,t2n,language;//t1n = team1name and t1s = team2score
    private int t1s,t2s,timeInSeconds;
    private int team1ScienceCorrectAnswers;
    private int team2ScienceCorrectAnswers;
    private int team1SportsCorrectAnswers;
    private int team2SportsCorrectAnswers;
    private int team1GeographyCorrectAnswers;
    private int team2GeographyCorrectAnswers;
    private int team1GeneralCorrectAnswers;
    private int team2GeneralCorrectAnswers;
    private Bitmap team1bitmap,team2bitmap;
    private byte[] team1byte;
    private byte[] team2byte;
    private boolean lastChance;
    private int score;
    private View score_layout;
    private ImageView playing_team_im;
    ImageButton science_bt;
    ImageButton general_bt;
    ImageButton geography_bt;
    ImageButton sports_bt;
    private boolean isMute;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            case R.id.science_bt:
                selectedCategory = DBContract.ScienceTable.TABLE_NAME;
                break;
            case R.id.general_bt:
                selectedCategory = DBContract.GeneralTable.TABLE_NAME;
                break;
            case R.id.sport_bt:
                selectedCategory = DBContract.SportsTable.TABLE_NAME;
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
        intent.putExtra("team1ScienceCorrectAnswers", team1ScienceCorrectAnswers);
        intent.putExtra("team2ScienceCorrectAnswers", team2ScienceCorrectAnswers);
        intent.putExtra("team1SportsCorrectAnswers", team1SportsCorrectAnswers);
        intent.putExtra("team2SportsCorrectAnswers", team2SportsCorrectAnswers);
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
        science_bt = findViewById(R.id.science_bt);
        general_bt = findViewById(R.id.general_bt);
        geography_bt = findViewById(R.id.geography_bt);
        sports_bt = findViewById(R.id.sport_bt);
        ImageView science_iv = findViewById(R.id.science_iv);
        ImageView general_iv = findViewById(R.id.general_iv);
        ImageView geography_iv = findViewById(R.id.geography_iv);
        ImageView sports_iv = findViewById(R.id.sport_iv);
        ImageView team1_im = findViewById(R.id.team1_im);
        ImageView team2_im = findViewById(R.id.team2_im);
        Button score_bt = findViewById(R.id.score_bt);
        score_layout = findViewById(R.id.score_layout);
        playing_team_im = findViewById(R.id.playing_team_im);
        team1_greekfootball = findViewById(R.id.team1_geographyquestions);
        team1_generalquestions = findViewById(R.id.team1_generalquestions);
        team1_sciencequestions = findViewById(R.id.team1_sciencequestions);
        team1_sportsquestions = findViewById(R.id.team1_sportsquestions);
        team2_greekfootball = findViewById(R.id.team2_geographyquestions);
        team2_generalquestions = findViewById(R.id.team2_generalquestions);
        team2_sciencequestions = findViewById(R.id.team2_sciencequestions);
        team2_sportsquestions = findViewById(R.id.team2_sportsquestions);



        //take the team names and scores and playing team
        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");
        score = getIntent().getExtras().getInt("score");
        playing_team = getIntent().getExtras().getString("playing_team");
        lastChance = getIntent().getExtras().getBoolean("lastChance");
        //retrieving the values for correct answered counters for each category for each team
        team1ScienceCorrectAnswers =  getIntent().getExtras().getInt("team1ScienceCorrectAnswers");
        team2ScienceCorrectAnswers =  getIntent().getExtras().getInt("team2ScienceCorrectAnswers");
        team1SportsCorrectAnswers =  getIntent().getExtras().getInt("team1SportsCorrectAnswers");
        team2SportsCorrectAnswers =  getIntent().getExtras().getInt("team2SportsCorrectAnswers");
        team1GeographyCorrectAnswers =  getIntent().getExtras().getInt("team1GeographyCorrectAnswers");
        team2GeographyCorrectAnswers =  getIntent().getExtras().getInt("team2GeographyCorrectAnswers");
        team1GeneralCorrectAnswers =  getIntent().getExtras().getInt("team1GeneralCorrectAnswers");
        team2GeneralCorrectAnswers =  getIntent().getExtras().getInt("team2GeneralCorrectAnswers");

        //set the score to the score board

        team1_greekfootball.setText(team1GeographyCorrectAnswers + "/" + score/4);
        team1_generalquestions.setText(team1GeneralCorrectAnswers + "/" + score/4);
        team1_sciencequestions.setText(team1ScienceCorrectAnswers + "/" + score/4);
        team1_sportsquestions.setText(team1SportsCorrectAnswers + "/" + score/4);
        team2_greekfootball.setText(team2GeographyCorrectAnswers + "/" + score/4);
        team2_generalquestions.setText(team2GeneralCorrectAnswers + "/" + score/4);
        team2_sciencequestions.setText(team2ScienceCorrectAnswers + "/" + score/4);
        team2_sportsquestions.setText(team2SportsCorrectAnswers + "/" + score/4);

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
            if (team1ScienceCorrectAnswers >= score/4){
                science_bt.setVisibility(View.GONE);
                science_iv.setVisibility(View.VISIBLE);
            }
            if (team1SportsCorrectAnswers >= score/4) {
                sports_bt.setVisibility(View.GONE);
                sports_iv.setVisibility(View.VISIBLE);
            }
            if (team1GeographyCorrectAnswers >= score/4) {
                geography_bt.setVisibility(View.GONE);
                geography_iv.setVisibility(View.VISIBLE);
            }
            if (team1GeneralCorrectAnswers >= score/4) {
                general_bt.setVisibility(View.GONE);
                general_iv.setVisibility(View.VISIBLE);
            }
        }else {
            if (team2ScienceCorrectAnswers >= score/4){
                science_bt.setVisibility(View.GONE);
                science_iv.setVisibility(View.VISIBLE);
            }
            if (team2SportsCorrectAnswers >= score/4) {
                sports_bt.setVisibility(View.GONE);
                sports_iv.setVisibility(View.VISIBLE);
            }
            if (team2GeographyCorrectAnswers >= score/4) {
                geography_bt.setVisibility(View.GONE);
                geography_iv.setVisibility(View.VISIBLE);
            }
            if (team2GeneralCorrectAnswers >= score/4) {
                general_bt.setVisibility(View.GONE);
                general_iv.setVisibility(View.VISIBLE);
            }
        }


        //set team names and scores and playing team to TextViews
        if (!language.equals("English")) {
            score_bt.setText("ΣΚΟΡ");
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
        sports_bt.setClickable(false);
        science_bt.setClickable(false);


    }
    public void ReturnFromScore(View view){
        score_layout.setVisibility(View.GONE);
        geography_bt.setClickable(true);
        general_bt.setClickable(true);
        sports_bt.setClickable(true);
        science_bt.setClickable(true);
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