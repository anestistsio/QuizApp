package com.uop.quizapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.uop.quizapp.FirebaseDBHelper;
import com.uop.quizapp.Questions;
import com.google.firebase.database.DatabaseError;

import java.util.List;
import java.util.Random;

public class MainGameViewModel extends ViewModel {
    public interface QuestionCallback {
        void onLoaded(Questions question);
        void onError();
    }

    public void loadRandomQuestion(String categoryKey, QuestionCallback callback) {
        FirebaseDBHelper dbHelper = new FirebaseDBHelper();
        dbHelper.getQuestionsByCategory(categoryKey, new FirebaseDBHelper.QuestionsCallback() {
            @Override
            public void onQuestionsLoaded(List<Questions> qs) {
                if (qs == null || qs.isEmpty()) {
                    callback.onError();
                    return;
                }
                Random random = new Random();
                Questions randomQuestion = qs.get(random.nextInt(qs.size()));
                randomQuestion.setDisplayed(true);
                dbHelper.updateQuestionDisplayed(categoryKey, randomQuestion);
                callback.onLoaded(randomQuestion);
            }

            @Override
            public void onError(DatabaseError error) {
                callback.onError();
            }
        });
    }
}
