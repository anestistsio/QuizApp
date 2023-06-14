package com.uop.quizapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private EditText team1_et,team2_et;
    private String t1n,t2n,language; //team 1 name and team 2 name
    private int team1ScienceCorrectAnswers = 0;
    private int team2ScienceCorrectAnswers = 0;
    private int team1SportsCorrectAnswers = 0;
    private int team2SportsCorrectAnswers = 0;
    private int team1GeographyCorrectAnswers = 0;
    private int team2GeographyCorrectAnswers = 0;
    private int team1GeneralCorrectAnswers = 0;
    private int team2GeneralCorrectAnswers = 0;
    private Bitmap team1bitmap,team2bitmap,bitmap;
    private ImageView team1_iv,team2_iv;
    private ImageButton team1_im,team2_im;
    private int id , timeInSeconds = 60;
    private boolean lastChance = true;
    //4 questions per category by default
    private int score = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        team1_im = findViewById(R.id.team1_im);
        team2_im = findViewById(R.id.team2_im);
        // here we check if language is greek and we change the hints from team1/2_et
        language = getIntent().getStringExtra("selected_language");
        if (language != null) {
            score = (getIntent().getExtras().getInt("questionsPerCategory")) * 4;
            //getting the time in seconds
            timeInSeconds = getIntent().getExtras().getInt("timeInSeconds");
            if (language.equals("Ελληνικά")) {
                team1_et.setHint("Όνομα Ομάδας 1");
                team2_et.setHint("Όνομα Ομάδας 2");
            }
        }else{
            language = "English";
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
                    click_sound.start();
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
                    intent.putExtra("team1ScienceCorrectAnswers", team1ScienceCorrectAnswers);
                    intent.putExtra("team2ScienceCorrectAnswers", team2ScienceCorrectAnswers);
                    intent.putExtra("team1SportsCorrectAnswers", team1SportsCorrectAnswers);
                    intent.putExtra("team2SportsCorrectAnswers", team2SportsCorrectAnswers);
                    intent.putExtra("team1GeographyCorrectAnswers", team1GeographyCorrectAnswers);
                    intent.putExtra("team2GeographyCorrectAnswers", team2GeographyCorrectAnswers);
                    intent.putExtra("team1GeneralCorrectAnswers", team1GeneralCorrectAnswers);
                    intent.putExtra("team2GeneralCorrectAnswers", team2GeneralCorrectAnswers);
                    //pass the time in seconds
                    intent.putExtra("timeInSeconds",timeInSeconds);
                    intent.putExtra("lastChance",lastChance);


                    //passing the images of the teams to SelectedCategory.class
                    if (team1bitmap != null){
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        team1bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
                        byte[] team1byte = bytes.toByteArray();
                        intent.putExtra("team1byte",team1byte);
                    }else {
                        intent.putExtra("team1byte", (byte[]) null);
                    }
                    if (team2bitmap != null){
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        team2bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
                        byte[] team2byte = bytes.toByteArray();
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
        if (requestCode == 100 && resultCode == RESULT_OK && data != null){
             bitmap = (Bitmap) data.getExtras().get("data");
           //we check which button is pressed to put the image to the right image view
            switch (id)
            {
                case R.id.team1_im:
                    team1bitmap = bitmap;
                    team1_iv.setImageBitmap(team1bitmap);
                    team1_im.setVisibility(View.GONE);
                    break;
                case R.id.team2_im:
                    team2bitmap = bitmap;
                    team2_iv.setImageBitmap(team2bitmap);
                    team2_im.setVisibility(View.GONE);
                    break;
            }

        }
    }
    // this method creates a notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Firebase Channel";
            String channelDescription = "Channel for Firebase Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
        click_sound.start();
        Intent intent = new Intent(MainActivity.this,Settings.class);
        startActivity(intent);
    }
    public void shareMain(View view) {
        final MediaPlayer click_sound = MediaPlayer.create(this,R.raw.click_sound);
        click_sound.start();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String Body = "Download this app";
        String Sub = "https://google.play.com";
        intent.putExtra(Intent.EXTRA_TEXT, Body);
        intent.putExtra(Intent.EXTRA_TEXT, Sub);
        startActivity(Intent.createChooser(intent, "Share using"));
    }


    @Override
    public void onBackPressed() {

    }
}
