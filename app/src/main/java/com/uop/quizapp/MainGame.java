package com.uop.quizapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainGame extends AppCompatActivity {

    private TextView question_tv, selected_category_tv, answer_tv, answeris_tv, timer_tv;
    private ImageButton correct_bt, incorrect_bt;
    private Button show_hide_bt;
    private String which_button, playing_team, t1n, t2n, selectedCategory;
    private int t1s, t2s, team1ScienceCorrectAnswers, team2ScienceCorrectAnswers, team1SportsCorrectAnswers, team2SportsCorrectAnswers, team1GeographyCorrectAnswers, team2GeographyCorrectAnswers, team1GeneralCorrectAnswers, team2GeneralCorrectAnswers;
    Bitmap team2bitmap, team1bitmap;
    private ImageView timer_iv;
    //this flag checks if question ended to prevent startTimer.onFinish run
    private boolean flag = true;
    private int  totalTimeInMins = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        // call this method to initialize the start values
        initializing();
        //this method takes the questions straight from DB and fills an arraylist for the chosen category
        fill_arraylist(selectedCategory);

    }

    public void fill_arraylist(String selectedCategory) {
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<Questions> questions = null;

        switch (selectedCategory) {
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
        startTimer();
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
    public void question_end(View view) {
        //turing flag to false because we don't want onFinish run
        flag = false;

        //set buttons not visible to avoid double clicking
        incorrect_bt.setVisibility(View.GONE);
        correct_bt.setVisibility(View.GONE);
        //initialize correct and incorrect sounds
        final MediaPlayer correct_sound = MediaPlayer.create(this, R.raw.correct_sound);
        final MediaPlayer incorrect_sound = MediaPlayer.create(this, R.raw.incorrect_sound);

        //checks which button is clicked (correct or incorrect)
        which_button = String.valueOf(view.getId());
        Intent intent = new Intent(MainGame.this, SelectCategory.class);
        if (which_button.equals(String.valueOf(correct_bt.getId()))) {
            //if correct button clicked then raise the score to the winning team and play correct_sound
            correct_sound.start();

            //wait 0.5 s to play sound properly
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (playing_team.equals(t1n)) {
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
            } else {
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
        } else {
            //if the incorrect button clicked then change playing team and play incorrect sound
            incorrect_sound.start();
            //wait 0.5 s to play sound properly
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "the phone now is changing hands because " + playing_team + " lost", Toast.LENGTH_LONG).show();

            //initialize the playing_team
            if (playing_team.equals(t1n)) {
                playing_team = t2n;
            } else {
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
        //passing the images back to SelectedCategory.class
        if (team1bitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team1bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] team1byte = bytes.toByteArray();
            intent.putExtra("team1byte", team1byte);
        } else {
            intent.putExtra("team1byte", (byte[]) null);
        }
        if (team2bitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team2bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] team2byte = bytes.toByteArray();
            intent.putExtra("team2byte", team2byte);
        } else {
            intent.putExtra("team2byte", (byte[]) null);
        }

        startActivity(intent);

    }

    //this method is called in the onCreate method to initialize things
    private void initializing() {
        selected_category_tv = findViewById(R.id.selected_category_tv);
        question_tv = findViewById(R.id.question_tv);
        answer_tv = findViewById(R.id.answer_tv);
        correct_bt = findViewById(R.id.correct_bt);
        incorrect_bt = findViewById(R.id.incorrect_bt);
        ImageView playing_team_image = findViewById(R.id.playing_team_image);
        show_hide_bt = findViewById(R.id.show_hide_bt);
        answeris_tv = findViewById(R.id.answeris_tv);
        timer_tv = findViewById(R.id.timer_tv);
        timer_iv = findViewById(R.id.timer_iv);

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
        team1ScienceCorrectAnswers = getIntent().getExtras().getInt("team1ScienceCorrectAnswers");
        team2ScienceCorrectAnswers = getIntent().getExtras().getInt("team2ScienceCorrectAnswers");
        team1SportsCorrectAnswers = getIntent().getExtras().getInt("team1SportsCorrectAnswers");
        team2SportsCorrectAnswers = getIntent().getExtras().getInt("team2SportsCorrectAnswers");
        team1GeographyCorrectAnswers = getIntent().getExtras().getInt("team1GeographyCorrectAnswers");
        team2GeographyCorrectAnswers = getIntent().getExtras().getInt("team2GeographyCorrectAnswers");
        team1GeneralCorrectAnswers = getIntent().getExtras().getInt("team1GeneralCorrectAnswers");
        team2GeneralCorrectAnswers = getIntent().getExtras().getInt("team2GeneralCorrectAnswers");

        //by default the 2 buttons and the answer are hidden
        answer_tv.setVisibility(View.GONE);
        correct_bt.setVisibility(View.GONE);
        incorrect_bt.setVisibility(View.GONE);
        answeris_tv.setVisibility(View.GONE);
        show_hide_bt.setText("show");

        //getting the images for the teams
        Bundle ex = getIntent().getExtras();

        byte[] team1byte = ex.getByteArray("team1byte");
        if (team1byte != null) {
            team1bitmap = BitmapFactory.decodeByteArray(team1byte, 0, team1byte.length);
        }
        byte[] team2byte = ex.getByteArray("team2byte");
        if (team2byte != null) {
            team2bitmap = BitmapFactory.decodeByteArray(team2byte, 0, team2byte.length);

        }
        if (playing_team.equals(t1n)) {
            if (team1bitmap != null) {
                playing_team_image.setImageBitmap(team1bitmap);
            } else {
                playing_team_image.setVisibility(View.GONE);
            }
        } else {
            if (team2bitmap != null) {
                playing_team_image.setImageBitmap(team2bitmap);
            } else {
                playing_team_image.setVisibility(View.GONE);
            }

        }
    }

    // this method is called by the show_hide_bt
    public void ShowHide(View view) {
        if (answer_tv.getVisibility() == View.GONE) {
            answer_tv.setVisibility(View.VISIBLE);
            correct_bt.setVisibility(View.VISIBLE);
            incorrect_bt.setVisibility(View.VISIBLE);
            answeris_tv.setVisibility(View.VISIBLE);
            show_hide_bt.setText("hide");

        } else {
            answer_tv.setVisibility(View.GONE);
            correct_bt.setVisibility(View.GONE);
            incorrect_bt.setVisibility(View.GONE);
            answeris_tv.setVisibility(View.GONE);
            show_hide_bt.setText("show");

        }
    }

    private void startTimer() {
        //initialize the time end sound
        final MediaPlayer time_end = MediaPlayer.create(this ,R.raw.time_end);
        final MediaPlayer ticking_sound = MediaPlayer.create(this ,R.raw.ticking_sound);
        CountDownTimer count = new CountDownTimer((totalTimeInMins * 60L) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //making the time into the right format
                String time = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                //set the time in the timer_tv
                timer_tv.setText(time);
            if(flag){
                if (millisUntilFinished < 20000) {
                    timer_tv.setTextColor(Color.RED);
                    ticking_sound.start();
                }
            }else{
                ticking_sound.stop();
            }
            }

            @Override
            public void onFinish() {
                //check if question_end called before executing
                if(flag) {
                    //play the time_end sound
                    time_end.start();
                    timer_tv.setVisibility(View.GONE);
                    timer_iv.setImageResource(R.drawable.red_timer);
                }

            }
        };
        count.start();
    }
}