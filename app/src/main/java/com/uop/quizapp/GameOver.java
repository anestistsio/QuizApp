package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.uop.quizapp.ActivityDataStore;
import com.uop.quizapp.util.BitmapUtils;
import com.uop.quizapp.util.SoundUtils;
import com.uop.quizapp.util.DoubleBackPressExitHandler;

public class GameOver extends AppCompatActivity {
    private TextView winning_tv,team1Name_tv,team2Name_tv,team1Score_tv,team2Score_tv;
    private int t1s,t2s,score,timeInSeconds,questionsPerCategory;
    private String t1n,t2n,winning_team,language;
    private boolean isMute;
    byte[] team1byte;
    byte[] team2byte;
    private DoubleBackPressExitHandler backPressExitHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Set fullscreen
        setContentView(R.layout.activity_game_over);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        backPressExitHandler = new DoubleBackPressExitHandler(this);
        // call this method to initialize the start values
        initializing();

    }
    private void initializing(){
        ActivityDataStore db = ActivityDataStore.getInstance();
        GameState gs = db.getGameState();
        isMute = gs != null && gs.isMute;
        SoundUtils.play(this, R.raw.win_sound, isMute);
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
            Bitmap winning_image = BitmapUtils.fromByteArray(winning_byte);
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

    /**
     * Exit the app after the game is over.
     */
    public void closeGame(View view) {
        //initialize click sound
        SoundUtils.play(this, R.raw.click_sound, isMute);
        finishAffinity();
        finish();
    }
    /**
     * Start a new game using the previously selected options.
     */
    public void restartGame(View view) {
        //initialize click sound
        SoundUtils.play(this, R.raw.click_sound, isMute);
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        ActivityDataStore db = ActivityDataStore.getInstance();
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
    /**
     * Share final results using an implicit send intent.
     */
    public void shareResults(View view) {
        SoundUtils.play(this, R.raw.click_sound, isMute);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String subject = "Download this app";
        String body = "https://google.play.com";
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(intent, "Share using"));
    }
    @Override
    public void onBackPressed() {
        if (backPressExitHandler.onBackPressed()) {
            finishAndRemoveTask();
            this.finishAffinity();
        }
    }
}