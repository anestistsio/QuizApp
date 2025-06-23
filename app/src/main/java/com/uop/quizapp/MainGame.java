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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.firebase.database.DatabaseError;

public class MainGame extends AppCompatActivity {

    private TextView question_tv, selected_category_tv, answer_tv, answeris_tv, timer_tv,changing_team_tv;
    private ImageButton correct_bt, incorrect_bt,changing_team_bt;
    private Button show_hide_bt;
    private String which_button, playing_team, t1n, t2n, selectedCategory,language;
    private int t1s, t2s, team1NationalCorrectAnswers, team2NationalCorrectAnswers, team1ClubsCorrectAnswers, team2ClubsCorrectAnswers, team1GeographyCorrectAnswers, team2GeographyCorrectAnswers, team1GeneralCorrectAnswers, team2GeneralCorrectAnswers;
    Bitmap team2bitmap, team1bitmap;
    //this answers_is_boolean checks if question ended to prevent startTimer.onFinish run
    private boolean answers_is_boolean = true;
    private int  timeInSeconds;
    private boolean lastChance;
    private int score;
    private View changing_team_layout,top;
    private boolean is_ok_pressed = false;
    private CountDownTimer count;
    private boolean isMute;
    private long time_int;
    MediaPlayer ticking_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Set fullscreen
        setContentView(R.layout.activity_main_game);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // call this method to initialize the start values
        initializing();
        //this method takes the questions straight from DB and fills an arraylist for the chosen category
        fill_arraylist(selectedCategory);
    }
    public void fill_arraylist(String selectedCategory) {
        FirebaseDBHelper dbHelper = new FirebaseDBHelper();
        String categoryKey = selectedCategory;
        if (language.equals("English")) {
            selected_category_tv.setText(categoryKey);
        } else {
            switch (categoryKey) {
                case Category.NATIONAL:
                    selected_category_tv.setText(Category.GREEK_NATIONAL);
                    break;
                case Category.CLUBS:
                    selected_category_tv.setText(Category.GREEK_CLUBS);
                    break;
                case Category.GEOGRAPHY:
                    selected_category_tv.setText(Category.GREEK_GEOGRAPHY);
                    break;
                case Category.GENERAL:
                    selected_category_tv.setText(Category.GREEK_GENERAL);
                    break;
            }
        }

        dbHelper.getQuestionsByCategory(categoryKey, new FirebaseDBHelper.QuestionsCallback() {
            @Override
            public void onQuestionsLoaded(List<Questions> qs) {
                Startgame(new ArrayList<>(qs));
            }

            @Override
            public void onError(DatabaseError error) {
                Toast.makeText(MainGame.this, "Error loading questions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Startgame(ArrayList<Questions> questions) {
        startTimer();
        if (questions.isEmpty()) {
            // Handle the case where the list is empty
            if (!language.equals("English")){
                Toast.makeText(this, "Δεν υπάρχουν άλλες ερωτήσεις σε αυτή την κατηγορία", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No questions available for this category", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        FirebaseDBHelper dbHelper = new FirebaseDBHelper();
        // Select a random question from the list
        Random random = new Random();
        Questions randomQuestion = questions.get(random.nextInt(questions.size()));

        // Update the displayed value to true in the database
        randomQuestion.setDisplayed(true);
        dbHelper.updateQuestionDisplayed(selectedCategory, randomQuestion);

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
         MediaPlayer correct_sound = MediaPlayer.create(this, R.raw.correct_sound);
         MediaPlayer incorrect_sound = MediaPlayer.create(this, R.raw.incorrect_sound);

        //checks which button is clicked (correct or incorrect)
        which_button = String.valueOf(view.getId());
        Intent intent = new Intent(MainGame.this, SelectCategory.class);
        if (which_button.equals(String.valueOf(correct_bt.getId()))) {
            if (time_int < 10000) {
                ticking_sound.stop();
            }
            if (t2s < score - 1) {
                lastChance = true;
            }
            //checks if team 1 finished all the questions and change to team 2 for a last chance
            if (t1s >= score -1 && lastChance && playing_team.equals(t1n)){
                changing_team_layout.setVisibility(View.VISIBLE);
                question_tv.setVisibility(View.GONE);
                selected_category_tv.setVisibility(View.GONE);
                answeris_tv.setVisibility(View.GONE);
                answer_tv.setVisibility(View.GONE);
                show_hide_bt.setVisibility(View.GONE);
                correct_bt.setVisibility(View.GONE);
                incorrect_bt.setVisibility(View.GONE);

                changing_team_layout.setBackground(getDrawable(R.drawable.red_card));
                if (!language.equals("English")) {
                    if (playing_team.equals("Ομάδα 1") || playing_team.equals("Ομάδα 2")) {
                        changing_team_tv.setText("Το κινητό αλλάζει χέρια γιατί η : " + playing_team + " τελείωσε ");
                    }else {
                        changing_team_tv.setText("Το κινητό αλλάζει χέρια γιατί η ομάδα : " + playing_team + " τελείωσε ");
                    }
                } else {
                        changing_team_tv.setText("The phone is changing hands because : " + playing_team + " finished");
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
                    case Category.NATIONAL:
                        team1NationalCorrectAnswers++;
                        break;
                    case Category.CLUBS:
                        team1ClubsCorrectAnswers++;
                        break;
                    case Category.GEOGRAPHY:
                        team1GeographyCorrectAnswers++;
                        break;
                    case Category.GENERAL:
                        team1GeneralCorrectAnswers++;
                        break;
                }
            } else {
                t2s++;
                switch (selectedCategory) {
                    case Category.NATIONAL:
                        team2NationalCorrectAnswers++;
                        break;
                    case Category.CLUBS:
                        team2ClubsCorrectAnswers++;
                        break;
                    case Category.GEOGRAPHY:
                        team2GeographyCorrectAnswers++;
                        break;
                    case Category.GENERAL:
                        team2GeneralCorrectAnswers++;
                        break;
                }
            }
            // incorrect button clicked
        } else {
            //if the incorrect button clicked then change playing team and play incorrect sound
            if (time_int < 10000) {
                ticking_sound.stop();
            }
            if (!isMute) {
                incorrect_sound.start();
            }
            count.cancel();

            //checks if team 2 lost in last chance
            if (!(t1s >= score && lastChance)){
                if (t1s != score) {
                    changing_team_layout.setVisibility(View.VISIBLE);
                    question_tv.setVisibility(View.GONE);
                    selected_category_tv.setVisibility(View.GONE);
                    answeris_tv.setVisibility(View.GONE);
                    answer_tv.setVisibility(View.GONE);
                    show_hide_bt.setVisibility(View.GONE);
                    correct_bt.setVisibility(View.GONE);
                    incorrect_bt.setVisibility(View.GONE);

                    changing_team_layout.setBackground(getDrawable(R.drawable.yellow_card));
                    if (!language.equals("English")) {
                        if (playing_team.equals("Ομάδα 1") || playing_team.equals("Ομάδα 2")) {
                            changing_team_tv.setText("Το κινητό αλλάζει χέρια γιατί η : " + playing_team + " έχασε ");
                        }else {
                            changing_team_tv.setText("Το κινητό αλλάζει χέρια γιατί η ομάδα : " + playing_team + " έχασε ");
                        }
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
        }
        DataBetweenActivitiesManager db = DataBetweenActivitiesManager.getInstance();
        db.put("playing_team", playing_team);
        db.put("team1Name", t1n);
        db.put("team2Name", t2n);
        db.put("team1Score", t1s);
        db.put("team2Score", t2s);
        db.put("score", score);
        db.put("lastChance", lastChance);
        db.put("team1NationalCorrectAnswers", team1NationalCorrectAnswers);
        db.put("team2NationalCorrectAnswers", team2NationalCorrectAnswers);
        db.put("team1ClubsCorrectAnswers", team1ClubsCorrectAnswers);
        db.put("team2ClubsCorrectAnswers", team2ClubsCorrectAnswers);
        db.put("team1GeographyCorrectAnswers", team1GeographyCorrectAnswers);
        db.put("team2GeographyCorrectAnswers", team2GeographyCorrectAnswers);
        db.put("team1GeneralCorrectAnswers", team1GeneralCorrectAnswers);
        db.put("team2GeneralCorrectAnswers", team2GeneralCorrectAnswers);
        db.put("selected_language", language);
        db.put("isMute", isMute);
        db.put("timeInSeconds", timeInSeconds);
        //passing the images back to SelectedCategory.class
        if (team1bitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team1bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] team1byte = bytes.toByteArray();
            db.put("team1byte", team1byte);
        } else {
            db.put("team1byte", null);
        }
        if (team2bitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team2bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] team2byte = bytes.toByteArray();
            db.put("team2byte", team2byte);
        } else {
            db.put("team2byte", null);
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
        changing_team_bt = findViewById(R.id.changing_team_bt);
        changing_team_tv = findViewById(R.id.changing_team_tv);
        changing_team_layout = findViewById(R.id.changing_team_layout);
        top = findViewById(R.id.top);
        TextView playing_team_tv = findViewById(R.id.playing_team_tv);

        //pass selected category from SelectCategory.class to MainGame.class
        DataBetweenActivitiesManager db = DataBetweenActivitiesManager.getInstance();
        selectedCategory = db.get("selectedCategory");

        //playing team ,score and team names are
        playing_team = db.get("playing_team");
        t1n = db.get("team1Name");
        t2n = db.get("team2Name");
        t1s = db.get("team1Score");
        t2s = db.get("team2Score");
        score = db.get("score");
        lastChance = db.get("lastChance");

        //retrieving the values for correct answered counters for each category for each team
        team1NationalCorrectAnswers = db.get("team1NationalCorrectAnswers");
        team2NationalCorrectAnswers = db.get("team2NationalCorrectAnswers");
        team1ClubsCorrectAnswers = db.get("team1ClubsCorrectAnswers");
        team2ClubsCorrectAnswers = db.get("team2ClubsCorrectAnswers");
        team1GeographyCorrectAnswers = db.get("team1GeographyCorrectAnswers");
        team2GeographyCorrectAnswers = db.get("team2GeographyCorrectAnswers");
        team1GeneralCorrectAnswers = db.get("team1GeneralCorrectAnswers");
        team2GeneralCorrectAnswers = db.get("team2GeneralCorrectAnswers");
        //getting the selected_language from SelectCategory.java
        language = db.get("selected_language");
        isMute = db.get("isMute");
        //getting the time in seconds
        timeInSeconds = db.get("timeInSeconds");
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
        byte[] team1byte = db.get("team1byte");
        if (team1byte != null) {
            team1bitmap = BitmapFactory.decodeByteArray(team1byte, 0, team1byte.length);
        }
        byte[] team2byte = db.get("team2byte");
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
        ticking_sound = MediaPlayer.create(this ,R.raw.ticking_sound);
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
                time_int = millisUntilFinished;
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
                    if (time_int < 10000) {
                        ticking_sound.stop();
                    }
                }
            }
            }

            @Override
            public void onFinish() {
                //check if question_end called before executing
                if(answers_is_boolean) {
                    if (!isMute) {
                        if (time_int < 10000) {
                            ticking_sound.stop();
                        }
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