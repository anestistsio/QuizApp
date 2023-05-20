package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class SelectCategory extends AppCompatActivity {
    private TextView  teamplay_tv,team1_name_tv,team2_name_tv,team1_score_tv,team2_score_tv;
    private String selectedCategory,playing_team,t1n,t2n;//t1n = team1name and t1s = team2score
    private int t1s,t2s;
    private int team1ScienceCorrectAnswers;
    private int team2ScienceCorrectAnswers;
    private int team1SportsCorrectAnswers;
    private int team2SportsCorrectAnswers;
    private int team1GeographyCorrectAnswers;
    private int team2GeographyCorrectAnswers;
    private int team1GeneralCorrectAnswers;
    private int team2GeneralCorrectAnswers;
    private Bitmap team1bitmap,team2bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
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
            case R.id.science_bt:
                selectedCategory = "Science";
                break;
            case R.id.general_bt:
                selectedCategory = "General";
                break;
            case R.id.sport_bt:
                selectedCategory = "Sports";
                break;
            case R.id.geography_bt:
                selectedCategory= "Geography";
                break;
        }
        click_sound.start();
        //pass selected category to MainGame.class
        Intent intent = new Intent(SelectCategory.this, MainGame.class);
        intent.putExtra("selectedCategory", selectedCategory);
        intent.putExtra("playing_team", playing_team);
        intent.putExtra("team1Name",t1n);
        intent.putExtra("team2Name",t2n);
        intent.putExtra("team1Score",t1s);
        intent.putExtra("team2Score",t2s);
        // passing values for correct answered counters for each category for each team
        intent.putExtra("team1ScienceCorrectAnswers", team1ScienceCorrectAnswers);
        intent.putExtra("team2ScienceCorrectAnswers", team2ScienceCorrectAnswers);
        intent.putExtra("team1SportsCorrectAnswers", team1SportsCorrectAnswers);
        intent.putExtra("team2SportsCorrectAnswers", team2SportsCorrectAnswers);
        intent.putExtra("team1GeographyCorrectAnswers", team1GeographyCorrectAnswers);
        intent.putExtra("team2GeographyCorrectAnswers", team2GeographyCorrectAnswers);
        intent.putExtra("team1GeneralCorrectAnswers", team1GeneralCorrectAnswers);
        intent.putExtra("team2GeneralCorrectAnswers", team2GeneralCorrectAnswers);
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
        ImageButton science_bt = findViewById(R.id.science_bt);
        ImageButton general_bt = findViewById(R.id.general_bt);
        ImageButton geography_bt = findViewById(R.id.geography_bt);
        ImageButton sports_bt = findViewById(R.id.sport_bt);
        ImageView science_iv = findViewById(R.id.science_iv);
        ImageView general_iv = findViewById(R.id.general_iv);
        ImageView geography_iv = findViewById(R.id.geography_iv);
        ImageView sports_iv = findViewById(R.id.sport_iv);
        ImageView team1_im = findViewById(R.id.team1_im);
        ImageView team2_im = findViewById(R.id.team2_im);
        //hiding the image views to display only the image buttons
        science_iv.setVisibility(View.GONE);
        general_iv.setVisibility(View.GONE);
        geography_iv.setVisibility(View.GONE);
        sports_iv.setVisibility(View.GONE);


        //take the team names and scores and playing team
        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");
        playing_team = getIntent().getExtras().getString("playing_team");
        //retrieving the values for correct answered counters for each category for each team
        team1ScienceCorrectAnswers =  getIntent().getExtras().getInt("team1ScienceCorrectAnswers");
        team2ScienceCorrectAnswers =  getIntent().getExtras().getInt("team2ScienceCorrectAnswers");
        team1SportsCorrectAnswers =  getIntent().getExtras().getInt("team1SportsCorrectAnswers");
        team2SportsCorrectAnswers =  getIntent().getExtras().getInt("team2SportsCorrectAnswers");
        team1GeographyCorrectAnswers =  getIntent().getExtras().getInt("team1GeographyCorrectAnswers");
        team2GeographyCorrectAnswers =  getIntent().getExtras().getInt("team2GeographyCorrectAnswers");
        team1GeneralCorrectAnswers =  getIntent().getExtras().getInt("team1GeneralCorrectAnswers");
        team2GeneralCorrectAnswers =  getIntent().getExtras().getInt("team2GeneralCorrectAnswers");
        //getting the images for two teams
        Bundle ex = getIntent().getExtras();
        byte[] team1byte = ex.getByteArray("team1byte");
        if (team1byte != null) {
            team1bitmap = BitmapFactory.decodeByteArray(team1byte, 0, team1byte.length);
            team1_im.setImageBitmap(team1bitmap);
        }else {
            team1_im.setVisibility(View.GONE);
        }
        byte[] team2byte = ex.getByteArray("team2byte");
        if (team2byte != null) {
            team2bitmap = BitmapFactory.decodeByteArray(team2byte, 0, team2byte.length);
            team2_im.setImageBitmap(team2bitmap);
        }else {
            team2_im.setVisibility(View.GONE);
        }
        // if the score of any team reach 12 this teams win
        if ((t1s >= 12||t2s >= 12)){
            Intent intent = new Intent(SelectCategory.this, GameOver.class);
            intent.putExtra("team1Name",t1n);
            intent.putExtra("team2Name",t2n);
            intent.putExtra("team1Score",t1s);
            intent.putExtra("team2Score",t2s);
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
            }else {
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
        //checks if any team answered all 3 questions from any category and if yes it disables the button
        if (playing_team.equals(t1n)){
            if (team1ScienceCorrectAnswers >= 3){
                science_bt.setVisibility(View.GONE);
                science_iv.setVisibility(View.VISIBLE);
            }
            if (team1SportsCorrectAnswers >= 3) {
                sports_bt.setVisibility(View.GONE);
                sports_iv.setVisibility(View.VISIBLE);
            }
            if (team1GeographyCorrectAnswers >= 3) {
                geography_bt.setVisibility(View.GONE);
                geography_iv.setVisibility(View.VISIBLE);
            }
            if (team1GeneralCorrectAnswers >= 3) {
                general_bt.setVisibility(View.GONE);
                general_iv.setVisibility(View.VISIBLE);
            }
        }else {
            if (team2ScienceCorrectAnswers >= 3){
                science_bt.setVisibility(View.GONE);
                science_iv.setVisibility(View.VISIBLE);
            }
            if (team2SportsCorrectAnswers >= 3) {
                sports_bt.setVisibility(View.GONE);
                sports_iv.setVisibility(View.VISIBLE);
            }
            if (team2GeographyCorrectAnswers >= 3) {
                geography_bt.setVisibility(View.GONE);
                geography_iv.setVisibility(View.VISIBLE);
            }
            if (team2GeneralCorrectAnswers >= 3) {
                general_bt.setVisibility(View.GONE);
                general_iv.setVisibility(View.VISIBLE);
            }
        }


        //set team names and scores and playing team to TextViews
        teamplay_tv.setText("Playing team: " + playing_team);
        team1_name_tv.setText(t1n);
        team2_name_tv.setText(t2n);
        team1_score_tv.setText(String.valueOf(t1s));
        team2_score_tv.setText(String.valueOf(t2s));
    }
    @Override
    public void onBackPressed() {

    }

}