package com.uop.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Settings extends AppCompatActivity {

    Spinner language_sp;
    String [] languages = new String[]{"Ελληνικά","English"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initializing();


    }

    private void initializing(){
        language_sp = findViewById(R.id.language_sp);
        ArrayAdapter<CharSequence> language_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,languages);
        language_sp.setAdapter(language_adapter);
    }


    public void save(View view) {
        String language = language_sp.getSelectedItem().toString();
        Intent intent = new Intent(Settings.this, MainActivity.class);
        intent.putExtra("selected_language", language);
        startActivity(intent);
        finish();
    }

}