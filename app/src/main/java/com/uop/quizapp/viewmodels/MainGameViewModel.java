package com.uop.quizapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.uop.quizapp.Questions;
import com.uop.quizapp.repository.FirebaseQuestionRepository;
import com.uop.quizapp.repository.QuestionRepository;

import java.util.List;
import java.util.Random;

public class MainGameViewModel extends ViewModel {
    private final QuestionRepository repository;

    public MainGameViewModel() {
        this(new FirebaseQuestionRepository());
    }

    public MainGameViewModel(QuestionRepository repository) {
        this.repository = repository;
    }

    public interface QuestionCallback {
        void onLoaded(Questions question);
        void onError();
    }

    public void loadRandomQuestion(String categoryKey, QuestionCallback callback) {
        repository.getQuestionsByCategory(categoryKey, new QuestionRepository.QuestionsCallback() {
            @Override
            public void onQuestionsLoaded(List<Questions> qs) {
                if (qs == null || qs.isEmpty()) {
                    callback.onError();
                    return;
                }
                Random random = new Random();
                Questions randomQuestion = qs.get(random.nextInt(qs.size()));
                randomQuestion.setDisplayed(true);
                repository.updateQuestionDisplayed(categoryKey, randomQuestion);
                callback.onLoaded(randomQuestion);
            }

            @Override
            public void onError(Exception error) {
                callback.onError();
            }
        });
    }
}
