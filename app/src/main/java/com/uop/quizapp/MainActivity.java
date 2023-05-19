package com.uop.quizapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    private EditText team1_et,team2_et;
    private String t1n,t2n; //team 1 name and team 2 name
    private int team1ScienceCorrectAnswers = 0;
    private int team2ScienceCorrectAnswers = 0;
    private int team1SportsCorrectAnswers = 0;
    private int team2SportsCorrectAnswers = 0;
    private int team1GeographyCorrectAnswers = 0;
    private int team2GeographyCorrectAnswers = 0;
    private int team1GeneralCorrectAnswers = 0;
    private int team2GeneralCorrectAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reset all the displayed values to false in DB
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.resetAllDisplayedValues();

        team1_et = findViewById(R.id.team1_et);
        team2_et = findViewById(R.id.team2_et);

    }
        public void select_category(View view) {
            //initialize click sound
            final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
                //Check if both team names entered
            if (TextUtils.isEmpty(team1_et.getText()) || TextUtils.isEmpty(team2_et.getText())){
                    //error message
                    Toast.makeText(MainActivity.this,"Please enter team names", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.equals(team1_et.getText(),team2_et.getText())) {
                //error same name handler
                Toast.makeText(MainActivity.this, "Please enter different team names", Toast.LENGTH_SHORT).show();
            }else{
                    click_sound.start();
                    Intent intent = new Intent(MainActivity.this, SelectCategory.class);
                    //pass team names and scores and playing team to SelectedCategory.class
                    t1n = team1_et.getText().toString();
                    t2n = team2_et.getText().toString();
                    intent.putExtra("team1Name", t1n);
                    intent.putExtra("team2Name", t2n);
                    intent.putExtra("team1Score", 0);
                    intent.putExtra("team2Score", 0);
                    //team1 starts by default
                    intent.putExtra("playing_team", t1n);
                    // passing initialized 0 values for correct answered counters for each category for each team
                    intent.putExtra("team1ScienceCorrectAnswers", team1ScienceCorrectAnswers);
                    intent.putExtra("team2ScienceCorrectAnswers", team2ScienceCorrectAnswers);
                    intent.putExtra("team1SportsCorrectAnswers", team1SportsCorrectAnswers);
                    intent.putExtra("team2SportsCorrectAnswers", team2SportsCorrectAnswers);
                    intent.putExtra("team1GeographyCorrectAnswers", team1GeographyCorrectAnswers);
                    intent.putExtra("team2GeographyCorrectAnswers", team2GeographyCorrectAnswers);
                    intent.putExtra("team1GeneralCorrectAnswers", team1GeneralCorrectAnswers);
                    intent.putExtra("team2GeneralCorrectAnswers", team2GeneralCorrectAnswers);
                    startActivity(intent);
                    finish();
            }
    }
}
