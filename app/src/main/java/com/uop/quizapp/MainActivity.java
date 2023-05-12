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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            }else {
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
                    startActivity(intent);
                    finish();
            }
    }
}
