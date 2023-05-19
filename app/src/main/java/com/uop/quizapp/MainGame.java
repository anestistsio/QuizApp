package com.uop.quizapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGame extends AppCompatActivity {

    private TextView question_tv, selected_category_tv, answer_tv;
    private ImageButton correct_bt;
    private String which_button,playing_team,t1n,t2n,selectedCategory;
    private int t1s,t2s;
    private int team1ScienceCorrectAnswers;
    private int team2ScienceCorrectAnswers;
    private int team1SportsCorrectAnswers;
    private int team2SportsCorrectAnswers;
    private int team1GeographyCorrectAnswers;
    private int team2GeographyCorrectAnswers;
    private int team1GeneralCorrectAnswers;
    private int team2GeneralCorrectAnswers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        // call this method to initialize the start values
        initializing();
        //this method takes the questions straight from DB and fills an arraylist for the chosen category
        fill_arraylist(selectedCategory);

    }
    public void fill_arraylist(String selectedCategory){
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<Questions> questions = null;

        switch (selectedCategory)
        {
            case "Science":
                questions = (ArrayList<Questions>) dbHelper.getAllScience();
                break;
            case "Sports":
                questions = (ArrayList<Questions>) dbHelper.getAllSports();
                break;
            case "Geography":
                questions = (ArrayList<Questions>) dbHelper.getAllGeography();
                break;
            case "General":
                questions = (ArrayList<Questions>) dbHelper.getAllGeneral();
                break;
        }
        Startgame(questions);
    }
    public void Startgame(ArrayList<Questions> questions) {
        if (questions.isEmpty()) {
            // Handle the case where the list is empty
            Toast.makeText(this, "No questions available for this category", Toast.LENGTH_SHORT).show();
            return;
        }

        DBHelper dbHelper = new DBHelper(this);
        // Select a random question from the list
        Random random = new Random();
        Questions randomQuestion = questions.get(random.nextInt(questions.size()));

        // Update the displayed value to true in the database
        randomQuestion.setDisplayed(true);
        dbHelper.updateQuestion(randomQuestion);

        // Display the question
        question_tv.setText(randomQuestion.getQuestion());
        answer_tv.setText(randomQuestion.getAnswer());
    }

    //questions_end method starts when either correct or incorrect button is clicked
    public void question_end (View view) {
        //initialize correct and incorrect sounds
        final MediaPlayer correct_sound = MediaPlayer.create(this,R.raw.correct_sound);
        final MediaPlayer incorrect_sound = MediaPlayer.create(this,R.raw.incorrect_sound);

        //checks which button is clicked (correct or incorrect)
        which_button = String.valueOf(view.getId());
        Intent intent = new Intent( MainGame.this,SelectCategory.class);
        if (which_button.equals(String.valueOf(correct_bt.getId()))){
            //if correct button clicked then raise the score to the winning team and play correct_sound
            correct_sound.start();

           //wait 0.5 s to play sound properly
            try {
                Thread.sleep(500); // wait for 1000 milliseconds (1 second)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (playing_team.equals(t1n)){
                t1s++;
                switch (selectedCategory) {
                    case "Science":
                        team1ScienceCorrectAnswers++;
                        break;
                    case "Sports":
                        team1SportsCorrectAnswers++;
                        break;
                    case "Geography":
                        team1GeographyCorrectAnswers++;
                        break;
                    case "General":
                        team1GeneralCorrectAnswers++;
                        break;
                }
            }else {
                t2s++;
                switch (selectedCategory) {
                    case "Science":
                        team2ScienceCorrectAnswers++;
                        break;
                    case "Sports":
                        team2SportsCorrectAnswers++;
                        break;
                    case "Geography":
                        team2GeographyCorrectAnswers++;
                        break;
                    case "General":
                        team2GeneralCorrectAnswers++;
                        break;
                }
            }
        }else{
            //if the incorrect button clicked then change playing team and play incorrect sound
            incorrect_sound.start();

            //wait 0.5 s to play sound properly
            try {
                Thread.sleep(500); // wait for 1000 milliseconds (1 second)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "the phone now is changing hands because " + playing_team + " lost", Toast.LENGTH_LONG).show();
            //intent.putExtra("playing_team", playing_team);
            if (playing_team.equals(t1n)){
                playing_team = t2n;
            }else {
                playing_team = t1n;
            }
        }
        //pass those things back to SelectCategory.class
        intent.putExtra("playing_team", playing_team);
        intent.putExtra("team1Name", t1n);
        intent.putExtra("team2Name", t2n);
        intent.putExtra("team1Score", t1s);
        intent.putExtra("team2Score", t2s);
        // passing values for correct answered counters for each category for each team
        intent.putExtra("team1ScienceCorrectAnswers", team1ScienceCorrectAnswers);
        intent.putExtra("team2ScienceCorrectAnswers", team2ScienceCorrectAnswers);
        intent.putExtra("team1SportsCorrectAnswers", team1SportsCorrectAnswers);
        intent.putExtra("team2SportsCorrectAnswers", team2SportsCorrectAnswers);
        intent.putExtra("team1GeographyCorrectAnswers", team1GeographyCorrectAnswers);
        intent.putExtra("team2GeographyCorrectAnswers", team2GeographyCorrectAnswers);
        intent.putExtra("team1GeneralCorrectAnswers", team1GeneralCorrectAnswers);
        intent.putExtra("team2GeneralCorrectAnswers", team2GeneralCorrectAnswers);

        startActivity(intent);

    }
    private void initializing(){
        selected_category_tv = findViewById(R.id.selected_category_tv);
        question_tv = findViewById(R.id.question_tv);
        answer_tv = findViewById(R.id.answer_tv);
        correct_bt = findViewById(R.id.correct_bt);

        //pass selected category from SelectedCategory.class to MainGame.class
        selectedCategory = getIntent().getExtras().getString("selectedCategory");
        selected_category_tv.setText(selectedCategory);

        //playing team ,score and team names are
        playing_team = getIntent().getExtras().getString("playing_team");
        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");
        t1n = getIntent().getExtras().getString("team1Name");
        //retrieving the values for correct answered counters for each category for each team
        team1ScienceCorrectAnswers =  getIntent().getExtras().getInt("team1ScienceCorrectAnswers");
        team2ScienceCorrectAnswers =  getIntent().getExtras().getInt("team2ScienceCorrectAnswers");
        team1SportsCorrectAnswers =  getIntent().getExtras().getInt("team1SportsCorrectAnswers");
        team2SportsCorrectAnswers =  getIntent().getExtras().getInt("team2SportsCorrectAnswers");
        team1GeographyCorrectAnswers =  getIntent().getExtras().getInt("team1GeographyCorrectAnswers");
        team2GeographyCorrectAnswers =  getIntent().getExtras().getInt("team2GeographyCorrectAnswers");
        team1GeneralCorrectAnswers =  getIntent().getExtras().getInt("team1GeneralCorrectAnswers");
        team2GeneralCorrectAnswers =  getIntent().getExtras().getInt("team2GeneralCorrectAnswers");
    }
}