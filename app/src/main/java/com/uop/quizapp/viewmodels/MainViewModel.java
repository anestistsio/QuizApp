package com.uop.quizapp.viewmodels;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import androidx.lifecycle.ViewModel;

import com.uop.quizapp.GameState;

import java.io.ByteArrayOutputStream;

public class MainViewModel extends ViewModel {
    public GameState createInitialGameState(String team1Name,
                                            String team2Name,
                                            boolean isMute,
                                            int score,
                                            int timeInSeconds,
                                            Bitmap team1bitmap,
                                            Bitmap team2bitmap,
                                            String language,
                                            boolean lastChance) {
        GameState gs = new GameState();
        gs.team1Name = team1Name;
        gs.team2Name = team2Name;
        gs.team1Score = 0;
        gs.team2Score = 0;
        gs.score = score;
        gs.playingTeam = team1Name;
        gs.team1NationalCorrectAnswers = 0;
        gs.team2NationalCorrectAnswers = 0;
        gs.team1ClubsCorrectAnswers = 0;
        gs.team2ClubsCorrectAnswers = 0;
        gs.team1GeographyCorrectAnswers = 0;
        gs.team2GeographyCorrectAnswers = 0;
        gs.team1GeneralCorrectAnswers = 0;
        gs.team2GeneralCorrectAnswers = 0;
        gs.timeInSeconds = timeInSeconds;
        gs.lastChance = lastChance;
        gs.isMute = isMute;
        if (team1bitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team1bitmap.compress(CompressFormat.JPEG, 100, bytes);
            gs.team1byte = bytes.toByteArray();
        }
        if (team2bitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team2bitmap.compress(CompressFormat.JPEG, 100, bytes);
            gs.team2byte = bytes.toByteArray();
        }
        gs.selectedLanguage = language == null ? "English" : language;
        return gs;
    }
}
