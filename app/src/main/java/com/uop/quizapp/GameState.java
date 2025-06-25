package com.uop.quizapp;

import java.io.Serializable;

public class GameState implements Serializable {
    public String team1Name;
    public String team2Name;
    public int team1Score;
    public int team2Score;
    public String playingTeam;
    public int team1NationalCorrectAnswers;
    public int team2NationalCorrectAnswers;
    public int team1ClubsCorrectAnswers;
    public int team2ClubsCorrectAnswers;
    public int team1GeographyCorrectAnswers;
    public int team2GeographyCorrectAnswers;
    public int team1GeneralCorrectAnswers;
    public int team2GeneralCorrectAnswers;
    public int score;
    public int timeInSeconds;
    public boolean lastChance;
    public boolean isMute;
    public String selectedLanguage;
    public byte[] team1byte;
    public byte[] team2byte;
    public String selectedCategory;

    /**
     * Track the ids of questions shown during the current match per category.
     * Keys are the category names and values are sets of question ids already
     * presented for that category. This avoids repeating questions even when
     * multiple devices are playing simultaneously without modifying the remote
     * database.
     */
    public java.util.Map<String, java.util.Set<Integer>> displayedQuestionIdsByCategory =
            new java.util.HashMap<>();
}
