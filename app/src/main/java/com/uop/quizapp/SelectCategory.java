package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.uop.quizapp.ActivityDataStore;


import com.uop.quizapp.util.BitmapUtils;
import com.uop.quizapp.util.SoundUtils;
import com.uop.quizapp.util.DoubleBackPressExitHandler;

public class SelectCategory extends AppCompatActivity {
    private TextView  teamplay_tv,team1_name_tv,team2_name_tv,team1_score_tv,team2_score_tv, team1_geography, team1_general, team1_national, team1_clubs, team2_geography, team2_general, team2_national, team2_clubs;
    private String selectedCategory,language;
    private GameState gs;
    private int timeInSeconds;
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
    private DoubleBackPressExitHandler backPressExitHandler;
    private com.uop.quizapp.viewmodels.SelectCategoryViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Set fullscreen
        setContentView(R.layout.activity_select_category);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        backPressExitHandler = new DoubleBackPressExitHandler(this);
        viewModel = new ViewModelProvider(this).get(com.uop.quizapp.viewmodels.SelectCategoryViewModel.class);
        // call this method to initialize the start values
        // and check if any team wins or if any team answers 3 correct questions of any category
        initializing();

    }
    /**
     * Start the quiz using the category button that was pressed.
     */
    public void startGame(View view) {
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
        SoundUtils.play(this, R.raw.click_sound, isMute);
        //pass selected category to MainGame.class
        Intent intent = new Intent(SelectCategory.this, MainGame.class);
        ActivityDataStore db = ActivityDataStore.getInstance();
        gs = db.getGameState();
        gs = viewModel.applySelectedCategory(gs, selectedCategory, team1bitmap, team2bitmap);
        db.setGameState(gs);
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
        ActivityDataStore db = ActivityDataStore.getInstance();
        gs = db.getGameState();
        score = gs.score;
        lastChance = gs.lastChance;

        //set the score to the score board

        team1_geography.setText(gs.team1GeographyCorrectAnswers + "/" + score/4);
        team1_general.setText(gs.team1GeneralCorrectAnswers + "/" + score/4);
        team1_national.setText(gs.team1NationalCorrectAnswers + "/" + score/4);
        team1_clubs.setText(gs.team1ClubsCorrectAnswers + "/" + score/4);
        team2_geography.setText(gs.team2GeographyCorrectAnswers + "/" + score/4);
        team2_general.setText(gs.team2GeneralCorrectAnswers + "/" + score/4);
        team2_national.setText(gs.team2NationalCorrectAnswers + "/" + score/4);
        team2_clubs.setText(gs.team2ClubsCorrectAnswers + "/" + score/4);

        //getting the selected_language from MainActivity.java or from MainGame.java
        language = gs.selectedLanguage;
        isMute = gs.isMute;
        timeInSeconds = gs.timeInSeconds;
        //getting the images for two teams
        team1byte = gs.team1byte;
        if (team1byte != null) {
            team1bitmap = BitmapUtils.fromByteArray(team1byte);
            team1_im.setImageBitmap(team1bitmap);
        }else {
            team1_im.setImageDrawable(getResources().getDrawable(R.drawable.user_im));
        }
        team2byte = gs.team2byte;
        if (team2byte != null) {
            team2bitmap = BitmapUtils.fromByteArray(team2byte);
            team2_im.setImageBitmap(team2bitmap);
        }else {
            team2_im.setImageDrawable(getResources().getDrawable(R.drawable.user_im));
        }

        //checks which team is playing to put the right image in the playing_team_im
        if (gs.playingTeam.equals(gs.team1Name)){
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
        if (gs.playingTeam.equals(gs.team1Name)){
            if (gs.team1NationalCorrectAnswers >= score/4){
                national_bt.setClickable(false);
                national_bt.setImageDrawable(getResources().getDrawable(R.drawable.national_icon_disabled));
            }
            if (gs.team1ClubsCorrectAnswers >= score/4) {
                clubs_bt.setClickable(false);
                clubs_bt.setImageDrawable(getResources().getDrawable(R.drawable.clubs_icon_disabled));
            }
            if (gs.team1GeographyCorrectAnswers >= score/4) {
                geography_bt.setClickable(false);
                geography_bt.setImageDrawable(getResources().getDrawable(R.drawable.geography_icon_disabled));
            }
            if (gs.team1GeneralCorrectAnswers >= score/4) {
                general_bt.setClickable(false);
                general_bt.setImageDrawable(getResources().getDrawable(R.drawable.general_icon_disabled));
            }
        }else {
            if (gs.team2NationalCorrectAnswers >= score/4){
                national_bt.setClickable(false);
                national_bt.setImageDrawable(getResources().getDrawable(R.drawable.national_icon_disabled));
            }
            if (gs.team2ClubsCorrectAnswers >= score/4) {
                clubs_bt.setClickable(false);
                clubs_bt.setImageDrawable(getResources().getDrawable(R.drawable.clubs_icon_disabled));
            }
            if (gs.team2GeographyCorrectAnswers >= score/4) {
                geography_bt.setClickable(false);
                geography_bt.setImageDrawable(getResources().getDrawable(R.drawable.geography_icon_disabled));
            }
            if (gs.team2GeneralCorrectAnswers >= score/4) {
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
        teamplay_tv.setText(gs.playingTeam);
        team1_name_tv.setText(gs.team1Name);
        team2_name_tv.setText(gs.team2Name);
        team1_score_tv.setText(String.valueOf(gs.team1Score));
        team2_score_tv.setText(String.valueOf(gs.team2Score));

        // if team 1 reach first the score the team 2 has one last chance
        if (gs.team1Score >= score && lastChance){
            lastChance = false;
        } else if (gs.team2Score >= score) {
            GameEnd();
        } else if (gs.team1Score >= score) {
            GameEnd();
        }

    }
    @Override
    public void onBackPressed() {
        if (backPressExitHandler.onBackPressed()) {
            finishAndRemoveTask();
            this.finishAffinity();
        }
    }
    /**
     * Display the score overlay and disable category buttons.
     */
    public void showScoreOverlay(View view){
        score_layout.setVisibility(View.VISIBLE);
        geography_bt.setClickable(false);
        general_bt.setClickable(false);
        clubs_bt.setClickable(false);
        national_bt.setClickable(false);
        shadow_v.setVisibility(View.GONE);


    }
    /**
     * Hide the score overlay and re-enable category buttons.
     */
    public void hideScoreOverlay(View view){
        score_layout.setVisibility(View.GONE);
        geography_bt.setClickable(true);
        general_bt.setClickable(true);
        clubs_bt.setClickable(true);
        national_bt.setClickable(true);
        shadow_v.setVisibility(View.VISIBLE);
    }
    private void GameEnd(){
        Intent intent = new Intent(SelectCategory.this, GameOver.class);
        ActivityDataStore db = ActivityDataStore.getInstance();
        gs = db.getGameState();
        if (gs == null) {
            gs = new GameState();
        }
        db.setGameState(gs);
        //passing the winning team image
        if (gs.team1Score > gs.team2Score){
            if (team1bitmap != null){
                db.put("winning_byte", BitmapUtils.toByteArray(team1bitmap));
            }else {
                db.put("winning_byte", null);
            }
        } else if (gs.team1Score == gs.team2Score) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.draw);
            db.put("winning_byte", BitmapUtils.toByteArray(bitmap));

        } else {
            if (team2bitmap != null){
                db.put("winning_byte", BitmapUtils.toByteArray(team2bitmap));
            }else {
                db.put("winning_byte", null);
            }
        }
        startActivity(intent);
    }

}