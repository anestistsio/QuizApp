package com.uop.quizapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.os.Handler;
import android.os.Looper;
import com.uop.quizapp.ActivityDataStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.uop.quizapp.util.BitmapUtils;
import com.uop.quizapp.util.SoundUtils;
import com.uop.quizapp.util.DoubleBackPressExitHandler;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class MainGame extends AppCompatActivity {

    private TextView question_tv, selected_category_tv, answer_tv, answeris_tv, timer_tv,changing_team_tv;
    private ImageButton correct_bt, incorrect_bt,changing_team_bt;
    private Button show_hide_bt;
    private String which_button, selectedCategory,language;
    private GameState gs;
    Bitmap team2bitmap, team1bitmap;
    //this answers_is_boolean checks if question ended to prevent startTimer.onFinish run
    private boolean answers_is_boolean = true;
    private int  timeInSeconds;
    private int score;
    private View changing_team_layout,top;
    private boolean is_ok_pressed = false;
    private CountDownTimer count;
    private boolean isMute;
    private long time_int;
    MediaPlayer ticking_sound;
    private ProgressBar loading_pb;
    private com.uop.quizapp.viewmodels.MainGameViewModel viewModel;
    private DoubleBackPressExitHandler backPressExitHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Set fullscreen
        setContentView(R.layout.activity_main_game);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        backPressExitHandler = new DoubleBackPressExitHandler(this);
        // call this method to initialize the start values
        initializing();
        viewModel = new ViewModelProvider(this).get(com.uop.quizapp.viewmodels.MainGameViewModel.class);
        //this method takes the questions straight from DB and fills an arraylist for the chosen category
        loadQuestionsForCategory(selectedCategory);
    }
    /**
     * Load a question for the given category and start the game.
     */
    public void loadQuestionsForCategory(String selectedCategory) {
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

        loading_pb.setVisibility(View.VISIBLE);
        question_tv.setVisibility(View.INVISIBLE);
        show_hide_bt.setVisibility(View.INVISIBLE);
        answer_tv.setVisibility(View.GONE);
        answeris_tv.setVisibility(View.GONE);
        viewModel.loadRandomQuestion(categoryKey, gs.displayedQuestionIds, new com.uop.quizapp.viewmodels.MainGameViewModel.QuestionCallback() {
            @Override
            public void onLoaded(Questions question) {
                gs.displayedQuestionIds.add(question.get_id());
                ActivityDataStore.getInstance().setGameState(gs);
                ArrayList<Questions> list = new ArrayList<>();
                list.add(question);
                startGameWithQuestions(list);
            }

            @Override
            public void onError() {
                Toast.makeText(MainGame.this, "Error loading questions", Toast.LENGTH_SHORT).show();
                loading_pb.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Begin the question round with the provided list of questions.
     */
    public void startGameWithQuestions(ArrayList<Questions> questions) {
        loading_pb.setVisibility(View.GONE);
        question_tv.setVisibility(View.VISIBLE);
        show_hide_bt.setVisibility(View.VISIBLE);

        if (questions.isEmpty()) {
            // Handle the case where the list is empty
            if (!language.equals("English")){
                Toast.makeText(this, "Δεν υπάρχουν άλλες ερωτήσεις σε αυτή την κατηγορία", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No questions available for this category", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        Questions randomQuestion = questions.get(0);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startTimer();
            // Display the question
            question_tv.setText(randomQuestion.getQuestion());
            answer_tv.setText(randomQuestion.getAnswer());
        }, 300);
    }

    /**
     * Handle the player's answer and update scores accordingly.
     */
    public void handleAnswer(View view) {
        //turing answers_is_boolean to false because we don't want onFinish run
        answers_is_boolean = false;

        //set buttons not visible to avoid double clicking
        incorrect_bt.setVisibility(View.GONE);
        correct_bt.setVisibility(View.GONE);
        //initialize correct and incorrect sounds

        //checks which button is clicked (correct or incorrect)
        which_button = String.valueOf(view.getId());
        Intent intent = new Intent(MainGame.this, SelectCategory.class);
        if (which_button.equals(String.valueOf(correct_bt.getId()))) {
            if (time_int < 10000) {
                ticking_sound.stop();
            }
            if (gs.team2Score < gs.score - 1) {
                gs.lastChance = true;
            }
            //checks if team 1 finished all the questions and change to team 2 for a last chance
            if (gs.team1Score >= gs.score -1 && gs.lastChance && gs.playingTeam.equals(gs.team1Name)){
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
                    if (gs.playingTeam.equals("Ομάδα 1") || gs.playingTeam.equals("Ομάδα 2")) {
                        changing_team_tv.setText("Το κινητό αλλάζει χέρια γιατί η : " + gs.playingTeam + " τελείωσε ");
                    }else {
                        changing_team_tv.setText("Το κινητό αλλάζει χέρια γιατί η ομάδα : " + gs.playingTeam + " τελείωσε ");
                    }
                } else {
                        changing_team_tv.setText("The phone is changing hands because : " + gs.playingTeam + " finished");
                }

            }
            //if correct button clicked then raise the score to the winning team and play correct_sound
            SoundUtils.play(this, R.raw.correct_sound, isMute);
            if (count != null) {
                count.cancel();
            }
            //wait 0.5 s to play sound properly
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (gs.playingTeam.equals(gs.team1Name)) {
                gs.team1Score++;
                switch (selectedCategory) {
                    case Category.NATIONAL:
                        gs.team1NationalCorrectAnswers++;
                        break;
                    case Category.CLUBS:
                        gs.team1ClubsCorrectAnswers++;
                        break;
                    case Category.GEOGRAPHY:
                        gs.team1GeographyCorrectAnswers++;
                        break;
                    case Category.GENERAL:
                        gs.team1GeneralCorrectAnswers++;
                        break;
                }
            } else {
                gs.team2Score++;
                switch (selectedCategory) {
                    case Category.NATIONAL:
                        gs.team2NationalCorrectAnswers++;
                        break;
                    case Category.CLUBS:
                        gs.team2ClubsCorrectAnswers++;
                        break;
                    case Category.GEOGRAPHY:
                        gs.team2GeographyCorrectAnswers++;
                        break;
                    case Category.GENERAL:
                        gs.team2GeneralCorrectAnswers++;
                        break;
                }
            }
            // incorrect button clicked
        } else {
            // If the incorrect button was clicked
            if (time_int < 10000) {
                ticking_sound.stop();
            }
            SoundUtils.play(this, R.raw.incorrect_sound, isMute);
            if (count != null) {
                count.cancel();
            }

            // When team 2 loses during their last chance the game should end
            if (gs.team1Score >= gs.score && gs.lastChance && gs.playingTeam.equals(gs.team2Name)) {
                gs.lastChance = false;
                ActivityDataStore db = ActivityDataStore.getInstance();
                if (team1bitmap != null) {
                    gs.team1byte = BitmapUtils.toByteArray(team1bitmap);
                } else {
                    gs.team1byte = null;
                }
                if (team2bitmap != null) {
                    gs.team2byte = BitmapUtils.toByteArray(team2bitmap);
                } else {
                    gs.team2byte = null;
                }
                db.setGameState(gs);
                startActivity(intent);
                return;
            }

            //checks if team 2 lost in a normal round
            if (!(gs.team1Score >= gs.score && gs.lastChance)){
                if (gs.team1Score != gs.score) {
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
                        if (gs.playingTeam.equals("Ομάδα 1") || gs.playingTeam.equals("Ομάδα 2")) {
                            changing_team_tv.setText("Το κινητό αλλάζει χέρια γιατί η : " + gs.playingTeam + " έχασε ");
                        }else {
                            changing_team_tv.setText("Το κινητό αλλάζει χέρια γιατί η ομάδα : " + gs.playingTeam + " έχασε ");
                        }
                    } else {
                        changing_team_tv.setText("the phone now is changing hands because : " + gs.playingTeam.toUpperCase() + " lost");
                    }
                }
            }

            //initialize the playing_team
            if (gs.playingTeam.equals(gs.team1Name)) {
                gs.playingTeam = gs.team2Name;
            } else {
                gs.playingTeam = gs.team1Name;
            }
        }
        //check if team 1 reached first the 12
        if (gs.team1Score == gs.score) {
            gs.playingTeam = gs.team2Name;
        }
        ActivityDataStore db = ActivityDataStore.getInstance();
        db.setGameState(gs);
        //passing the images back to SelectedCategory.class
        if (team1bitmap != null) {
            gs.team1byte = BitmapUtils.toByteArray(team1bitmap);
        } else {
            gs.team1byte = null;
        }
        if (team2bitmap != null) {
            gs.team2byte = BitmapUtils.toByteArray(team2bitmap);
        } else {
            gs.team2byte = null;
        }
        db.setGameState(gs);
        //we set visible this layout when the user clicks the incorrect button
        if (changing_team_layout.getVisibility() == View.VISIBLE){
            //if the user clicks ok we pass in SelectedCategory.java
            changing_team_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundUtils.play(MainGame.this, R.raw.click_sound, isMute);
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
        loading_pb = findViewById(R.id.loading_pb);
        TextView playing_team_tv = findViewById(R.id.playing_team_tv);

        //pass selected category from SelectCategory.class to MainGame.class
        ActivityDataStore db = ActivityDataStore.getInstance();
        gs = db.getGameState();
        selectedCategory = gs.selectedCategory;
        language = gs.selectedLanguage;
        isMute = gs.isMute;
        timeInSeconds = gs.timeInSeconds;
        if (gs.playingTeam.equals(gs.team1Name)) {
            playing_team_tv.setText(gs.team1Name);
        }else {
            playing_team_tv.setText(gs.team2Name);
        }
        if (!language.equals("English")) {
            answeris_tv.setText("Σωστή απάντηση");
            show_hide_bt.setText("Δείξε");
        }else {
            show_hide_bt.setText("show");
        }
        //getting the images for the teams
        byte[] team1byte = gs.team1byte;
        if (team1byte != null) {
            team1bitmap = BitmapUtils.fromByteArray(team1byte);
        }
        byte[] team2byte = gs.team2byte;
        if (team2byte != null) {
            team2bitmap = BitmapUtils.fromByteArray(team2byte);

        }
        if (gs.playingTeam.equals(gs.team1Name)) {
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

    /**
     * Toggle visibility of the answer text and control buttons.
     */
    public void toggleAnswer(View view) {
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
                    handleAnswer(incorrect_bt);
                }

            }
        };
        count.start();
    }
    @Override
    public void onBackPressed() {
        if (backPressExitHandler.onBackPressed()) {
            if (count != null) {
                count.cancel();
            }
            finishAndRemoveTask();
            this.finishAffinity();
        }
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        if (count != null) {
            count.cancel();
        }
    }

}