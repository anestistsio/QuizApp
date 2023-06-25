package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private String[] languages = new String[]{"English", "Ελληνικά"};
    private SeekBar TimePerQuestion_sb, QPerCat_sb;
    private TextView TimeInSeconds_tv, QPerCat_tv, language_tv;
    private ImageButton language_bt;
    private int timeInSeconds = 60;
    private int questionsPerCategory = 3;
    private boolean isMute = false;
    private ImageButton mute_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        language_bt = findViewById(R.id.language_bt);
        language_tv = findViewById(R.id.language_tv);
        TimePerQuestion_sb = findViewById(R.id.TimePerQuestion_sb);
        TimeInSeconds_tv = findViewById(R.id.TimeInSeconds_tv);
        TimeInSeconds_tv.setText(timeInSeconds + "s");

        QPerCat_sb = findViewById(R.id.QPerCat_sb);
        QPerCat_tv = findViewById(R.id.QPerCat_tv);
        QPerCat_tv.setText(String.valueOf(questionsPerCategory));
        mute_bt = findViewById(R.id.mute_bt);

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

    public void language_select(View view) {
        if (language_tv.getText().equals("English")) {
            language_bt.setImageResource(R.drawable.greek);
            language_tv.setText("Ελληνικά");
        } else {
            language_bt.setImageResource(R.drawable.english);
            language_tv.setText("English");
        }
    }

    public void save(View view) {
        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.click_sound);
        if (!isMute) {
            click_sound.start();
        }
        String language = language_tv.getText().toString();
        Intent intent = new Intent(Settings.this, MainActivity.class);
        intent.putExtra("selected_language", language);
        intent.putExtra("timeInSeconds", timeInSeconds);
        intent.putExtra("questionsPerCategory", questionsPerCategory);
        intent.putExtra("isMute",isMute);
        startActivity(intent);
        finish();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAndRemoveTask();
            this.finishAffinity();

        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK twice to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }
    public void mute(View view) {
        if (!isMute) {
            mute_bt.setImageDrawable(getResources().getDrawable(R.drawable.mute));
            isMute = true;
        } else {
            mute_bt.setImageDrawable(getResources().getDrawable(R.drawable.umute));
            isMute = false;
        }
    }

}
