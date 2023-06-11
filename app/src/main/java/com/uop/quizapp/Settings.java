package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private Spinner language_sp;
    private String [] languages = new String[]{"Ελληνικά","English"};
    private SeekBar TimePerQuestion_sb;
    private TextView TimeInSeconds_tv;
    private int timeInSeconds = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        language_sp = findViewById(R.id.language_sp);
        ArrayAdapter<CharSequence> language_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,languages);
        language_sp.setAdapter(language_adapter);
        TimePerQuestion_sb = findViewById(R.id.TimePerQuestion_sb);
        TimeInSeconds_tv = findViewById(R.id.TimeInSeconds_tv);
        TimeInSeconds_tv.setText(timeInSeconds + "s");

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

    }
    public void save(View view) {
        final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
        click_sound.start();
        String language = language_sp.getSelectedItem().toString();
        Intent intent = new Intent(Settings.this, MainActivity.class);
        intent.putExtra("selected_language", language);
        intent.putExtra("timeInSeconds",timeInSeconds);
        startActivity(intent);
        finish();
    }

}