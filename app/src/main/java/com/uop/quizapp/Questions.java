package com.uop.quizapp;
//this is a class that contains setters and getters for every db table columns
public class Questions {
    private String question;
    private String  answer;
    private boolean displayed;
    private int id;

    public Questions(){}


    public Questions(int id, String question, String answer, boolean displayed) {
        this.question = question;
        this.answer = answer;
        this.displayed = displayed;
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public boolean getDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

}
