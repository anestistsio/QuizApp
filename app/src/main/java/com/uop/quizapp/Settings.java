package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.uop.quizapp.ActivityDataStore;

public class Settings extends AppCompatActivity {

    private String[] languages = new String[]{"English", "Ελληνικά"};
    private SeekBar TimePerQuestion_sb, QPerCat_sb;
    private TextView TimeInSeconds_tv, QPerCat_tv, language_tv,TimePerQuestion_tv,QPerCatText_tv;
    private ImageButton language_bt,mute_bt;
    private int timeInSeconds = 60;
    private int questionsPerCategory = 3;
    private boolean isMute = false;
    private Button save_bt;
    private String language,t1_et,t2_et;
    private byte[] team1byte;
    private byte[] team2byte;
    private int preselected_timeInSeconds,preselected_questionsPerCategory;
    private boolean preselected_isMute;
    private String preselected_language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Set fullscreen
        setContentView(R.layout.activity_settings);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        language_bt = findViewById(R.id.language_bt);
        language_tv = findViewById(R.id.language_tv);
        TimePerQuestion_sb = findViewById(R.id.TimePerQuestion_sb);
        TimeInSeconds_tv = findViewById(R.id.TimeInSeconds_tv);
        save_bt = findViewById(R.id.save_button);
        TimePerQuestion_tv = findViewById(R.id.TimePerQuestion_tv);
        QPerCatText_tv = findViewById(R.id.QPerCatText_tv);

        TimeInSeconds_tv.setText(timeInSeconds + "s");

        QPerCat_sb = findViewById(R.id.QPerCat_sb);
        QPerCat_tv = findViewById(R.id.QPerCat_tv);
        QPerCat_tv.setText(String.valueOf(questionsPerCategory));
        mute_bt = findViewById(R.id.mute_bt);


        ActivityDataStore db = ActivityDataStore.getInstance();
        Integer qpcObj = db.get("questionsPerCategory");
        if (qpcObj != null) {
            questionsPerCategory = qpcObj;
        } else {
            Integer scoreObj = db.get("score");
            if (scoreObj != null) {
                questionsPerCategory = scoreObj / 4;
            }
        }

        Integer timeObj = db.get("timeInSeconds");
        if (timeObj != null) {
            timeInSeconds = timeObj;
        }

        String langObj = db.get("selected_language");
        if (langObj != null) {
            language = langObj;
        }

        Boolean muteObj = db.get("isMute");
        if (muteObj != null) {
            isMute = muteObj;
        }

        t1_et = db.get("t1_et");
        t2_et = db.get("t2_et");

        preselected_language = language;
        preselected_timeInSeconds = timeInSeconds;
        preselected_questionsPerCategory = questionsPerCategory;
        preselected_isMute = isMute;

        team1byte = db.get("team1byte");
        team2byte = db.get("team2byte");


        //Here we check if the user has already changed the setting and got back again, in this case we put the right values
        if (language != null) {
            if (language.equals("English")) {
                language_bt.setImageDrawable(getResources().getDrawable(R.drawable.english));
                language_tv.setText("EN");
                language = ("English");
                TimePerQuestion_tv.setText("Time Per Question");
                QPerCatText_tv.setText("Questions Per Category");
                save_bt.setText("SAVE");
            }else {
                language_bt.setImageDrawable(getResources().getDrawable(R.drawable.greek));
                language_tv.setText("ΕΛ");
                language = ("Ελληνικά");
                TimePerQuestion_tv.setText("Χρόνος Ανα Κατηγορία");
                QPerCatText_tv.setText("Ερωτήσεις Ανα Κατηγορία");
                save_bt.setText("ΣΩΣΕ");
            }
            if(isMute){
                mute_bt.setImageDrawable(getResources().getDrawable(R.drawable.mute));
            }
            TimePerQuestion_sb.setProgress(timeInSeconds - 20);
            QPerCat_sb.setProgress(questionsPerCategory - 1);
            TimeInSeconds_tv.setText(String.valueOf(timeInSeconds) + "s");
            QPerCat_tv.setText(String.valueOf(questionsPerCategory));
        }



        //Time per Question seekBar listener
        TimePerQuestion_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeInSeconds = 20 + progress;
                TimeInSeconds_tv.setText(String.valueOf(timeInSeconds) + "s");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        QPerCat_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                questionsPerCategory = progress + 1;
                QPerCat_tv.setText(String.valueOf(questionsPerCategory));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    /**
     * Toggle between English and Greek language options.
     */
    public void toggleLanguage(View view) {
        if (language_tv.getText().equals("EN")) {
            language_bt.setImageResource(R.drawable.greek);
            language_tv.setText("ΕΛ");
            language = "Ελληνικά";
            TimePerQuestion_tv.setText("Χρόνος Ανα Ερώτηση");
            QPerCatText_tv.setText("Ερωτήσεις Ανα Κατηγορία");
            save_bt.setText("ΣΩΣΕ");

        } else {
            language_bt.setImageResource(R.drawable.english);
            language_tv.setText("EN");
            language = "English";
            TimePerQuestion_tv.setText("Time Per Question");
            QPerCatText_tv.setText("Questions Per Category");
            save_bt.setText("SAVE");
        }
    }

    /**
     * Persist the user's selections and return to the main screen.
     */
    public void saveSettings(View view) {
        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.click_sound);
        if (!isMute) {
            click_sound.start();
        }
        Intent intent = new Intent(Settings.this, MainActivity.class);
        ActivityDataStore db = ActivityDataStore.getInstance();
        db.put("selected_language", language);
        db.put("timeInSeconds", timeInSeconds);
        db.put("questionsPerCategory", questionsPerCategory);
        db.put("isMute", isMute);
        db.put("t1_et", t1_et);
        db.put("t2_et", t2_et);
        if (team1byte != null){
            db.put("team1byte", team1byte);
            intent.putExtra("team1byte", team1byte);
        }
        if (team2byte != null){
            db.put("team2byte", team2byte);
            intent.putExtra("team2byte", team2byte);
        }
        intent.putExtra("selected_language", language);
        intent.putExtra("timeInSeconds", timeInSeconds);
        intent.putExtra("questionsPerCategory", questionsPerCategory);
        intent.putExtra("isMute", isMute);
        intent.putExtra("t1_et", t1_et);
        intent.putExtra("t2_et", t2_et);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Settings.this, MainActivity.class);
        ActivityDataStore db = ActivityDataStore.getInstance();
        db.put("selected_language", preselected_language);
        db.put("timeInSeconds", preselected_timeInSeconds);
        db.put("questionsPerCategory", preselected_questionsPerCategory);
        db.put("isMute", preselected_isMute);
        db.put("t1_et", t1_et);
        db.put("t2_et", t2_et);
        if (team1byte != null){
            db.put("team1byte", team1byte);
            intent.putExtra("team1byte", team1byte);
        }
        if (team2byte != null){
            db.put("team2byte", team2byte);
            intent.putExtra("team2byte", team2byte);
        }
        intent.putExtra("selected_language", preselected_language);
        intent.putExtra("timeInSeconds", preselected_timeInSeconds);
        intent.putExtra("questionsPerCategory", preselected_questionsPerCategory);
        intent.putExtra("isMute", preselected_isMute);
        intent.putExtra("t1_et", t1_et);
        intent.putExtra("t2_et", t2_et);
        startActivity(intent);
        finish();
    }
    /**
     * Discard changes and navigate back to the main screen.
     */
    public void discardChanges(View view){
        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.click_sound);
        if (!isMute) {
            click_sound.start();
        }
        Intent intent = new Intent(Settings.this, MainActivity.class);
        ActivityDataStore db2 = ActivityDataStore.getInstance();
        db2.put("selected_language", preselected_language);
        db2.put("timeInSeconds", preselected_timeInSeconds);
        db2.put("questionsPerCategory", preselected_questionsPerCategory);
        db2.put("isMute", preselected_isMute);
        db2.put("t1_et", t1_et);
        db2.put("t2_et", t2_et);
        if (team1byte != null){
            db2.put("team1byte", team1byte);
            intent.putExtra("team1byte", team1byte);
        }
        if (team2byte != null){
            db2.put("team2byte", team2byte);
            intent.putExtra("team2byte", team2byte);
        }
        intent.putExtra("selected_language", preselected_language);
        intent.putExtra("timeInSeconds", preselected_timeInSeconds);
        intent.putExtra("questionsPerCategory", preselected_questionsPerCategory);
        intent.putExtra("isMute", preselected_isMute);
        intent.putExtra("t1_et", t1_et);
        intent.putExtra("t2_et", t2_et);
        startActivity(intent);
        finish();
    }
    /**
     * Toggle sound effects on or off.
     */
    public void toggleMute(View view) {
        if (!isMute) {
            mute_bt.setImageDrawable(getResources().getDrawable(R.drawable.mute));
            isMute = true;
        } else {
            mute_bt.setImageDrawable(getResources().getDrawable(R.drawable.unmute));
            isMute = false;
        }
    }

}
