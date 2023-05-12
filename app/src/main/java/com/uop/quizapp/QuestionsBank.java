package com.uop.quizapp;

import java.util.ArrayList;
import java.util.List;

public class QuestionsBank {

    private static List<QuestionsList> SportsQuestions() {
        final List<QuestionsList> questionsLists = new ArrayList<>();

        // Create object of QuestionsList class and pass a questions along with answer
        final QuestionsList question1 = new QuestionsList("Sport1?","1");
        final QuestionsList question2 = new QuestionsList("Sport2?","2");
        final QuestionsList question3 = new QuestionsList("Sport3?","3");

        // add all questions to List<QuestionsList>
        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);

        return questionsLists;
    }

    private static List<QuestionsList> GeneralQuestions() {
        final List<QuestionsList> questionsLists = new ArrayList<>();

        // Create object of QuestionsList class and pass a questions along with answer
        final QuestionsList question1 = new QuestionsList("General1?","1");
        final QuestionsList question2 = new QuestionsList("General2?","2");
        final QuestionsList question3 = new QuestionsList("General3?","3");

        // add all questions to List<QuestionsList>
        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);

        return questionsLists;
    }
    private static List<QuestionsList> ScienceQuestions() {
        final List<QuestionsList> questionsLists = new ArrayList<>();

        // Create object of QuestionsList class and pass a questions along with answer
        final QuestionsList question1 = new QuestionsList("Science1?","1");
        final QuestionsList question2 = new QuestionsList("Science2?","2");
        final QuestionsList question3 = new QuestionsList("Science3?","3");

        // add all questions to List<QuestionsList>
        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);

        return questionsLists;
    }
    private static List<QuestionsList> GeographyQuestions() {
        final List<QuestionsList> questionsLists = new ArrayList<>();

        // Create object of QuestionsList class and pass a questions along with answer
        final QuestionsList question1 = new QuestionsList("Geography1?","1");
        final QuestionsList question2 = new QuestionsList("Geography2?","2");
        final QuestionsList question3 = new QuestionsList("Geography3?","3");

        // add all questions to List<QuestionsList>
        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);

        return questionsLists;
    }
    public static List<QuestionsList> getQuestions(String selectedCategory) {
        switch (selectedCategory) {
            case "Sports":
                return SportsQuestions();
            case "General":
                return GeneralQuestions();
            case "Science":
                return ScienceQuestions();
            default:
                return GeographyQuestions();
        }
    }
}
