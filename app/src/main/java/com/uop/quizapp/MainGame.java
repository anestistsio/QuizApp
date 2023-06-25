package com.uop.quizapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
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

    private TextView question_tv, selected_category_tv, answer_tv, answeris_tv, timer_tv,changing_team_tv;
    private ImageButton correct_bt, incorrect_bt,changing_team_bt;
    private Button show_hide_bt;
    private String which_button, playing_team, t1n, t2n, selectedCategory,language;
    private int t1s, t2s, team1ScienceCorrectAnswers, team2ScienceCorrectAnswers, team1SportsCorrectAnswers, team2SportsCorrectAnswers, team1GeographyCorrectAnswers, team2GeographyCorrectAnswers, team1GeneralCorrectAnswers, team2GeneralCorrectAnswers;
    Bitmap team2bitmap, team1bitmap;
    private ImageView timer_iv;
    //this answers_is_boolean checks if question ended to prevent startTimer.onFinish run
    private boolean answers_is_boolean = true;
    private int  timeInSeconds;
    private boolean lastChance;
    private int score;
    private View changing_team_layout;
    private boolean is_ok_pressed = false;
    private CountDownTimer count;
    private boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // call this method to initialize the start values
        initializing();
        //this method takes the questions straight from DB and fills an arraylist for the chosen category
        fill_arraylist(selectedCategory);

    }
    public void fill_arraylist(String selectedCategory) {
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<Questions> questions = null;

        //checks the language to fill with the right table the arraylist and also set the selected_category_tv with the right language category name
        switch (selectedCategory) {
            case DBContract.ScienceTable.TABLE_NAME:
                if (language.equals("English")) {
                    questions = (ArrayList<Questions>) dbHelper.getAllScience();
                    selected_category_tv.setText(DBContract.ScienceTable.TABLE_NAME);
                }else {
                    questions = (ArrayList<Questions>) dbHelper.getAllGreekScience();
                    selected_category_tv.setText(DBContract.GreekScienceTable.TABLE_NAME);
                }
                break;
            case DBContract.SportsTable.TABLE_NAME:
                if (language.equals("English")) {
                    questions = (ArrayList<Questions>) dbHelper.getAllSports();
                    selected_category_tv.setText(DBContract.SportsTable.TABLE_NAME);
                }else {
                    questions = (ArrayList<Questions>) dbHelper.getAllGreekSports();
                    selected_category_tv.setText(DBContract.GreekSportsTable.TABLE_NAME);
                }
                break;
            case DBContract.GeographyTable.TABLE_NAME:
                if (language.equals("English")) {
                    questions = (ArrayList<Questions>) dbHelper.getAllGeography();
                    selected_category_tv.setText(DBContract.GeographyTable.TABLE_NAME);
                }else {
                    questions = (ArrayList<Questions>) dbHelper.getAllGreekGeography();
                    selected_category_tv.setText(DBContract.GreekGeographyTable.TABLE_NAME);
                }
                break;
            case DBContract.GeneralTable.TABLE_NAME:
                if (language.equals("English")) {
                    questions = (ArrayList<Questions>) dbHelper.getAllGeneral();
                    selected_category_tv.setText(DBContract.GeneralTable.TABLE_NAME);
                }else {
                    questions = (ArrayList<Questions>) dbHelper.getAllGreekGeneral();
                    selected_category_tv.setText(DBContract.GreekGeneralTable.TABLE_NAME);
                }
                break;
        }
        Startgame(questions);
    }

    public void Startgame(ArrayList<Questions> questions) {
        startTimer();
        if (questions.isEmpty()) {
            // Handle the case where the list is empty
            if (!language.equals("English")){
                Toast.makeText(this, "Δεν υπάρχουν άλλες ερωτήσης σε αυτή την κατηγορία", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No questions available for this category", Toast.LENGTH_SHORT).show();
            }
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
        //turing answers_is_boolean to false because we don't want onFinish run
        answers_is_boolean = false;

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
            if (t2s < score - 1) {
                lastChance = true;
            }
            //checks if team 1 finished all the questions and change to team 2 for a last chance
            if (t1s >= score -1 && lastChance && playing_team.equals(t1n)){
                changing_team_layout.setVisibility(View.VISIBLE);
                if (!language.equals("English")) {
                    changing_team_tv.setText("Το κινητό αλλάζει χέρια γιατί η ομάδα : " + playing_team.toUpperCase() + " τελείωσε ");
                } else {
                    changing_team_tv.setText("The phone is changing hands because : " + playing_team.toUpperCase() + " finished");
                }

            }
            //if correct button clicked then raise the score to the winning team and play correct_sound
            if (!isMute) {
                correct_sound.start();
            }
            count.cancel();
            //wait 0.5 s to play sound properly
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (playing_team.equals(t1n)) {
                t1s++;
                switch (selectedCategory) {
                    case DBContract.ScienceTable.TABLE_NAME:
                        team1ScienceCorrectAnswers++;
                        break;
                    case DBContract.SportsTable.TABLE_NAME:
                        team1SportsCorrectAnswers++;
                        break;
                    case DBContract.GeographyTable.TABLE_NAME:
                        team1GeographyCorrectAnswers++;
                        break;
                    case DBContract.GeneralTable.TABLE_NAME:
                        team1GeneralCorrectAnswers++;
                        break;
                }
            } else {
                t2s++;
                switch (selectedCategory) {
                    case DBContract.ScienceTable.TABLE_NAME:
                        team2ScienceCorrectAnswers++;
                        break;
                    case DBContract.SportsTable.TABLE_NAME:
                        team2SportsCorrectAnswers++;
                        break;
                    case DBContract.GeographyTable.TABLE_NAME:
                        team2GeographyCorrectAnswers++;
                        break;
                    case DBContract.GeneralTable.TABLE_NAME:
                        team2GeneralCorrectAnswers++;
                        break;
                }
            }
            // incorrect button clicked
        } else {
            //if the incorrect button clicked then change playing team and play incorrect sound
            if (!isMute) {
                incorrect_sound.start();
            }
            count.cancel();
            //wait 0.5 s to play sound properly
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //checks if team 2 lost in last chance
            if (!(t1s >= score && lastChance)){
                if (t1s != score) {
                    changing_team_layout.setVisibility(View.VISIBLE);
                    if (!language.equals("English")) {
                        changing_team_tv.setText("Το κινητό αλλάζει χέρια γιατι η ομάδα : " + playing_team.toUpperCase() + " έχασε");
                    } else {
                        changing_team_tv.setText("the phone now is changing hands because : " + playing_team.toUpperCase() + " lost");
                    }
                }
            }

            //initialize the playing_team
            if (playing_team.equals(t1n)) {
                playing_team = t2n;
            } else {
                playing_team = t1n;
            }
        }
        //check if team 1 reached first the 12
        if (t1s == score) {
            playing_team = t2n;
            intent.putExtra("playing_team", playing_team);
        } else{
            intent.putExtra("playing_team", playing_team);
        }
        intent.putExtra("team1Name", t1n);
        intent.putExtra("team2Name", t2n);
        intent.putExtra("team1Score", t1s);
        intent.putExtra("team2Score", t2s);
        intent.putExtra("score", score);
        intent.putExtra("lastChance",lastChance);
        // passing values for correct answered counters for each category for each team
        intent.putExtra("team1ScienceCorrectAnswers", team1ScienceCorrectAnswers);
        intent.putExtra("team2ScienceCorrectAnswers", team2ScienceCorrectAnswers);
        intent.putExtra("team1SportsCorrectAnswers", team1SportsCorrectAnswers);
        intent.putExtra("team2SportsCorrectAnswers", team2SportsCorrectAnswers);
        intent.putExtra("team1GeographyCorrectAnswers", team1GeographyCorrectAnswers);
        intent.putExtra("team2GeographyCorrectAnswers", team2GeographyCorrectAnswers);
        intent.putExtra("team1GeneralCorrectAnswers", team1GeneralCorrectAnswers);
        intent.putExtra("team2GeneralCorrectAnswers", team2GeneralCorrectAnswers);
        //passing selected_language and time in seconds
        intent.putExtra("selected_language",language);
        intent.putExtra("isMute",isMute);
        intent.putExtra("timeInSeconds", timeInSeconds);
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
        //we set visible this layout when the user clicks the incorrect button
        if (changing_team_layout.getVisibility() == View.VISIBLE){
            final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.click_sound);
            //if the user clicks ok we pass in SelectedCategory.java
            changing_team_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isMute) {
                        click_sound.start();
                    }
                    startActivity(intent);
                }
            });
        }else {
            startActivity(intent);
        }

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
        changing_team_bt = findViewById(R.id.changing_team_bt);
        changing_team_tv = findViewById(R.id.changing_team_tv);
        changing_team_layout = findViewById(R.id.changing_team_layout);
        TextView playing_team_tv = findViewById(R.id.playing_team_tv);

        //pass selected category from SelectedCategory.class to MainGame.class
        selectedCategory = getIntent().getExtras().getString("selectedCategory");

        //playing team ,score and team names are
        playing_team = getIntent().getExtras().getString("playing_team");
        t1n = getIntent().getExtras().getString("team1Name");
        t2n = getIntent().getExtras().getString("team2Name");
        t1s = getIntent().getExtras().getInt("team1Score");
        t2s = getIntent().getExtras().getInt("team2Score");
        t1n = getIntent().getExtras().getString("team1Name");
        score = getIntent().getExtras().getInt("score");
        lastChance = getIntent().getExtras().getBoolean("lastChance");

        //retrieving the values for correct answered counters for each category for each team
        team1ScienceCorrectAnswers = getIntent().getExtras().getInt("team1ScienceCorrectAnswers");
        team2ScienceCorrectAnswers = getIntent().getExtras().getInt("team2ScienceCorrectAnswers");
        team1SportsCorrectAnswers = getIntent().getExtras().getInt("team1SportsCorrectAnswers");
        team2SportsCorrectAnswers = getIntent().getExtras().getInt("team2SportsCorrectAnswers");
        team1GeographyCorrectAnswers = getIntent().getExtras().getInt("team1GeographyCorrectAnswers");
        team2GeographyCorrectAnswers = getIntent().getExtras().getInt("team2GeographyCorrectAnswers");
        team1GeneralCorrectAnswers = getIntent().getExtras().getInt("team1GeneralCorrectAnswers");
        team2GeneralCorrectAnswers = getIntent().getExtras().getInt("team2GeneralCorrectAnswers");
        //getting the selected_language from SelectCategory.java
        language = getIntent().getExtras().getString("selected_language");
        isMute = getIntent().getExtras().getBoolean("isMute");
        //getting the time in seconds
        timeInSeconds =  getIntent().getExtras().getInt("timeInSeconds");
        if (playing_team.equals(t1n)) {
            playing_team_tv.setText(t1n);
        }else {
            playing_team_tv.setText(t2n);
        }
        if (!language.equals("English")) {
            answeris_tv.setText("Σωστή απάντηση");
            show_hide_bt.setText("Δείξε");
        }else {
            show_hide_bt.setText("show");
        }
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
                playing_team_image.setImageDrawable(getResources().getDrawable(R.drawable.user_im));
            }
        } else {
            if (team2bitmap != null) {
                playing_team_image.setImageBitmap(team2bitmap);
            } else {
                playing_team_image.setImageDrawable(getResources().getDrawable(R.drawable.user_im));
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
            if (!language.equals("English")) {
                show_hide_bt.setText("Κρύψε");
            }else {
                show_hide_bt.setText("hide");
            }

        } else {
            answer_tv.setVisibility(View.GONE);
            correct_bt.setVisibility(View.GONE);
            incorrect_bt.setVisibility(View.GONE);
            answeris_tv.setVisibility(View.GONE);
            if (!language.equals("English")) {
                show_hide_bt.setText("Δείξε");
            }else {
                show_hide_bt.setText("show");
            }

        }
    }

    private void startTimer() {
        //initialize the time end sound
        final MediaPlayer ticking_sound = MediaPlayer.create(this ,R.raw.ticking_sound);
        count = new CountDownTimer((timeInSeconds * 1000), 1000) {
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
            if(answers_is_boolean){
                if (millisUntilFinished < 10000) {
                    timer_tv.setTextColor(Color.rgb(186,37,37));
                    if (!isMute) {
                        ticking_sound.start();
                    }
                }
            }else{
                if (!isMute) {
                    ticking_sound.stop();
                }
            }
            }

            @Override
            public void onFinish() {
                //check if question_end called before executing
                if(answers_is_boolean) {
                    if (!isMute) {
                        ticking_sound.stop();
                    }
                    question_end(incorrect_bt);
                }

            }
        };
        count.start();
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            count.cancel();
            finishAndRemoveTask();
            this.finishAffinity();

        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK twice to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        count.cancel();
    }

}