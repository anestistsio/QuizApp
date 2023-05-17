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
    private String which_button,playing_team,t1n,t2n;
    private int t1s,t2s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        selected_category_tv = findViewById(R.id.selected_category_tv);
        question_tv = findViewById(R.id.question_tv);
        answer_tv = findViewById(R.id.answer_tv);
        correct_bt = findViewById(R.id.correct_bt);

        //pass selected category from SelectedCategory.class to MainGame.class
        String selectedCategory = getIntent().getExtras().getString("selectedCategory");
        selected_category_tv.setText(selectedCategory);

        //playing team ,score and team names are
        playing_team = getIntent().getExtras().getString("playing_team");
        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");

        fill_arraylist(selectedCategory);
    }
    public void fill_arraylist(String selectedCategory){
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<Questions> questions = new ArrayList<>();

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

           //wait 1 s to play sound properly
            try {
                Thread.sleep(500); // wait for 1000 milliseconds (1 second)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (playing_team.equals(t1n)){
                t1s++;
            }else {
                t2s++;
            }
        }else{
            //if the incorrect button clicked then change playing team and play incorrect sound
            incorrect_sound.start();

            //wait 1 s to play sound properly
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
        startActivity(intent);

    }
}