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
     * Track the ids of questions shown during the current match. This is used
     * to avoid repeating questions without modifying the remote database.
     */
    public java.util.Set<Integer> displayedQuestionIds = new java.util.HashSet<>();
}
