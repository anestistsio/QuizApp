package com.uop.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGame extends AppCompatActivity {

    private TextView question_tv, selected_category_tv, answer_tv;
    private ImageButton correct_bt, incorrect_bt;
    private List<QuestionsList> questionsLists = new ArrayList<>();
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
        incorrect_bt = findViewById(R.id.incorrect_bt);

        //pass selected category from SelecteCategory.class 2 to MainGame.class
        String selectedCategory = getIntent().getExtras().getString("selectedCategory");
        selected_category_tv.setText(selectedCategory);
        //playing team ,score and team names are
        playing_team = getIntent().getExtras().getString("playing_team");
        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");

        questionsLists = QuestionsBank.getQuestions(selectedCategory);
        Startgame();
    }
    public void Startgame() {
        int index;
        //random number generator
        Random rand = new Random();
        index = rand.nextInt(2);

        question_tv.setText(questionsLists.get(index).getQuestion());
        answer_tv.setText(questionsLists.get(index).getAnswer());

        //TODO this hashmap is not in use yet

    }
    //questions_end(View view) method starts when either correct or incorrect button is clicked
    public void question_end (View view) {
       //checks which button is clicked (correct or incorrect)
        which_button = String.valueOf(view.getId());
        Intent intent = new Intent( MainGame.this,SelectCategory.class);
        if (which_button.equals(String.valueOf(correct_bt.getId()))){
            //if correct button clicked then raise the score to the winning team
            if (playing_team.equals(t1n)){
                t1s++;
            }else {
                t2s++;
            }
        }else{
            //if the incorret button clicked then change playing team
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