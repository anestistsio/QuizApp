package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SelectCategory extends AppCompatActivity {
    private TextView  teamplay_tv,team1_name_tv,team2_name_tv,team1_score_tv,team2_score_tv;
    private ImageButton general_bt, geography_bt, sport_bt, science_bt;
    private String selectedCategory,playing_team,t1n,t2n;//t1n = team1name and t1s = team2score
    private int t1s,t2s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        teamplay_tv = findViewById(R.id.teamplay_tv);
        general_bt = (ImageButton) findViewById(R.id.general_bt);
        geography_bt = (ImageButton) findViewById(R.id.geography_bt);
        sport_bt = (ImageButton) findViewById(R.id.sport_bt);
        science_bt = (ImageButton) findViewById(R.id.science_bt);
        team1_name_tv = findViewById(R.id.team1_name_tv);
        team2_name_tv = findViewById(R.id.team2_name_tv);
        team1_score_tv =findViewById(R.id.team1_score_tv);
        team2_score_tv = findViewById(R.id.team2_score_tv);

        //take the team names and scores and playing team
        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");
        playing_team = getIntent().getExtras().getString("playing_team");

        if ((t1s == 12||t2s == 12)){
            Intent intent = new Intent(SelectCategory.this, GameOver.class);
            intent.putExtra("team1Name",t1n);
            intent.putExtra("team2Name",t2n);
            intent.putExtra("team1Score",t1s);
            intent.putExtra("team2Score",t2s);
            startActivity(intent);
        }
        //set team names and scores and playing team to TextViews
        teamplay_tv.setText("Playing team: " + playing_team);
        team1_name_tv.setText(t1n);
        team2_name_tv.setText(t2n);
        team1_score_tv.setText(String.valueOf(t1s));
        team2_score_tv.setText(String.valueOf(t2s));
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

        startActivity(intent);
    }
    @Override
    public void onBackPressed() {

    }
}