package com.uop.quizapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity{
    private static final String CHANNEL_ID = "myFirebaseChannel";
    private byte[] team1byte,team2byte;
    private EditText team1_et,team2_et;
    private String t1n,t2n,language; //team 1 name and team 2 name
    private int team1NationalCorrectAnswers = 0;
    private int team2NationalCorrectAnswers = 0;
    private int team1ClubsCorrectAnswers = 0;
    private int team2ClubsCorrectAnswers = 0;
    private int team1GeographyCorrectAnswers = 0;
    private int team2GeographyCorrectAnswers = 0;
    private int team1GeneralCorrectAnswers = 0;
    private int team2GeneralCorrectAnswers = 0;
    private Bitmap team1bitmap,team2bitmap,bitmap;
    private ImageView team1_iv,team2_iv;
    private int id , timeInSeconds = 60;
    private boolean lastChance = true;
    // 3 questions per category by default (total score = 12)
    private int score = 12;
    private boolean isMute = false;
    private boolean restart_boolean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Set fullscreen
        setContentView(R.layout.activity_main);
        //set orientation portrait locked
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initializing();
        createNotificationChannel();
        firebase_initialization();


    }
    private void initializing(){
        //reset all the displayed values to false in DB
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.resetAllDisplayedValues();

        team1_et = findViewById(R.id.team1_et);
        team2_et = findViewById(R.id.team2_et);
        team1_iv = findViewById(R.id.team1_iv);
        team2_iv = findViewById(R.id.team2_iv);


        //if the user finish one game and press restart we retrieve all his settings
        restart_boolean = getIntent().getBooleanExtra("restart_boolean",false);
        if (restart_boolean) {
            language = getIntent().getStringExtra("selected_language");
            t1n = getIntent().getStringExtra("t1n");
            t2n = getIntent().getStringExtra("t2n");
            team1_et.setText(t1n);
            team2_et.setText(t2n);
            if (!language.equals("English")) {
                team1_et.setHint("Όνομα Ομάδας 1");
                team2_et.setHint("Όνομα Ομάδας 2");
            }
            isMute = getIntent().getExtras().getBoolean("isMute");
            score = getIntent().getExtras().getInt("score");
            timeInSeconds = getIntent().getExtras().getInt("timeInSeconds");
            Bundle ex = getIntent().getExtras();
            team1byte = ex.getByteArray("team1byte");
            if (team1byte != null) {
                team1bitmap = BitmapFactory.decodeByteArray(team1byte, 0, team1byte.length);
                team1_iv.setImageBitmap(team1bitmap);
            }
            team2byte = ex.getByteArray("team2byte");
            if (team2byte != null) {
                team2bitmap = BitmapFactory.decodeByteArray(team2byte, 0, team2byte.length);
                team2_iv.setImageBitmap(team2bitmap);
            }

        }



        // here we check if language is greek and we change the hints from team1/2_et
        if (!restart_boolean) {
            language = getIntent().getStringExtra("selected_language");
            if (language != null) {
                isMute = getIntent().getExtras().getBoolean("isMute");
                score = (getIntent().getExtras().getInt("questionsPerCategory")) * 4;
                //getting the time in seconds
                timeInSeconds = getIntent().getExtras().getInt("timeInSeconds");
                if (language.equals("Ελληνικά")) {
                    team1_et.setHint("Όνομα Ομάδας 1");
                    team2_et.setHint("Όνομα Ομάδας 2");
                }
                String t1_et = getIntent().getStringExtra("t1_et");
                if (t1_et != null){
                    team1_et.setText(t1_et);
                }
                String t2_et = getIntent().getStringExtra("t2_et");
                if (t2_et != null) {
                    team2_et.setText(t2_et);
                }
                Bundle ex = getIntent().getExtras();
                team1byte = ex.getByteArray("team1byte");
                team2byte = ex.getByteArray("team2byte");
                if (team1byte != null) {
                        team1bitmap = BitmapFactory.decodeByteArray(team1byte, 0, team1byte.length);
                        team1_iv.setImageBitmap(team1bitmap);
                }
                if (team2byte != null) {
                    team2bitmap = BitmapFactory.decodeByteArray(team2byte, 0, team2byte.length);
                    team2_iv.setImageBitmap(team2bitmap);
                }


            } else {
                language = "English";
            }
        }
    }
        public void select_category(View view) {
            final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
            //Check if both team names entered
            t1n = team1_et.getText().toString();
            t2n = team2_et.getText().toString();
            if (t1n.equals("")){
                if (language.equals("English")) {
                    t1n = "Team 1";
                }else {
                    t1n = "Ομάδα 1";
                }
            }
            if (t2n.equals("")){
                if (language.equals("English")) {
                    t2n = "Team 2";
                }else {
                    t2n = "Ομάδα 2";
                }
            }
            if (t1n.equals(t2n)) {
                //error same name handler
                if (!language.equals("English")) {
                    Toast.makeText(MainActivity.this,"Παρακαλώ δώστε διαφορετικά ονόματα ομάδας", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"Please enter different team names", Toast.LENGTH_SHORT).show();
                }
            }else{
                    if (!isMute) {
                        click_sound.start();
                    }
                    Intent intent = new Intent(MainActivity.this, SelectCategory.class);
                    //pass team names and scores and playing team to SelectedCategory.class
                    intent.putExtra("team1Name", t1n);
                    intent.putExtra("team2Name", t2n);
                    intent.putExtra("team1Score", 0);
                    intent.putExtra("team2Score", 0);
                    intent.putExtra("score", score);
                    //team1 starts by default
                    intent.putExtra("playing_team", t1n);
                    // passing initialized 0 values for correct answered counters for each category for each team
                    intent.putExtra("team1NationalCorrectAnswers", team1NationalCorrectAnswers);
                    intent.putExtra("team2NationalCorrectAnswers", team2NationalCorrectAnswers);
                    intent.putExtra("team1ClubsCorrectAnswers", team1ClubsCorrectAnswers);
                    intent.putExtra("team2ClubsCorrectAnswers", team2ClubsCorrectAnswers);
                    intent.putExtra("team1GeographyCorrectAnswers", team1GeographyCorrectAnswers);
                    intent.putExtra("team2GeographyCorrectAnswers", team2GeographyCorrectAnswers);
                    intent.putExtra("team1GeneralCorrectAnswers", team1GeneralCorrectAnswers);
                    intent.putExtra("team2GeneralCorrectAnswers", team2GeneralCorrectAnswers);
                    //pass the time in seconds
                    intent.putExtra("timeInSeconds",timeInSeconds);
                    intent.putExtra("lastChance",lastChance);
                    intent.putExtra("isMute",isMute);
                    //passing the images of the teams to SelectedCategory.class
                    if (team1bitmap != null){
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        team1bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
                        team1byte = bytes.toByteArray();
                        intent.putExtra("team1byte",team1byte);
                    }else {
                        intent.putExtra("team1byte", (byte[]) null);
                    }
                    if (team2bitmap != null){
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        team2bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
                        team2byte = bytes.toByteArray();
                        intent.putExtra("team2byte",team2byte);
                    }else {
                        intent.putExtra("team2byte", (byte[]) null);
                    }
                        //here we check if the user selected language from Settings.java
                        if (language == null){
                            //English is the default language
                            intent.putExtra("selected_language","English");
                        }else {
                            intent.putExtra("selected_language", language);
                        }
                    startActivity(intent);
                    finish();
            }
    }


    //with this methods we ask for permission to open the camera and we also take the picture and assign it to the image views
    public void TakePicture(View view){

        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this , new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Launch the camera intent to capture an image
        startActivityForResult(intent, 100,null);

        //we put the pressed button's id in this variable
        id=view.getId();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            if (data != null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                //we check which button is pressed to put the image to the right image view
                switch (id) {
                    case R.id.team1_iv:
                        team1bitmap = bitmap;
                        team1_iv.setImageBitmap(team1bitmap);
                        break;
                    case R.id.team2_iv:
                        team2bitmap = bitmap;
                        team2_iv.setImageBitmap(team2bitmap);
                        break;
                }
            }

        }else {
            Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
        }
    }


    // this method creates a notification channel
    private void createNotificationChannel() {
            CharSequence channelName = "Firebase Channel";
            String channelDescription = "Channel for Firebase Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
    }
    //this method initialize firebase cloud messaging
    private void firebase_initialization() {
        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }

                    }
                });
    }
    //this method goes to Settings activity
    public void settings(View view){
        final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
        if (!isMute) {
            click_sound.start();
        }
        Intent intent = new Intent(MainActivity.this,Settings.class);
        intent.putExtra("selected_language", language);
        intent.putExtra("timeInSeconds", timeInSeconds);
        intent.putExtra("questionsPerCategory", score);
        intent.putExtra("isMute",isMute);
        intent.putExtra("t1_et",team1_et.getText().toString());
        intent.putExtra("t2_et",team2_et.getText().toString());
        if (team1bitmap != null){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team1bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
            team1byte = bytes.toByteArray();
            intent.putExtra("team1byte",team1byte);
        }
        if (team2bitmap != null){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team2bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
            team2byte = bytes.toByteArray();
            intent.putExtra("team2byte",team2byte);
        }
        startActivity(intent);
    }
    public void shareMain(View view) {
        final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
        if (!isMute) {
            click_sound.start();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String subject = "Download this app";
        String body = "https://google.play.com";
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(intent, "Share using"));
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAndRemoveTask();
            this.finishAffinity();

        }
        if(!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK twice to exit", Toast.LENGTH_SHORT).show();
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }
    //this method hides the soft keyboard when you touch anywhere else
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
