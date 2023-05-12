package com.uop.quizapp;

public class QuestionsList {

    private final String question;
    private final String answer;

    public QuestionsList(String question, String answer) {
        this.question = question;
        this.answer = answer;

    }
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

}
