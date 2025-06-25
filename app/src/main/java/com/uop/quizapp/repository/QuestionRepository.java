package com.uop.quizapp.repository;

import com.uop.quizapp.Questions;

import java.util.List;

/**
 * Abstraction for loading and updating quiz questions.
 */
public interface QuestionRepository {
    interface QuestionsCallback {
        void onQuestionsLoaded(List<Questions> questions);
        void onError(Exception error);
    }

    void getQuestionsByCategory(String category, QuestionsCallback callback);
}
