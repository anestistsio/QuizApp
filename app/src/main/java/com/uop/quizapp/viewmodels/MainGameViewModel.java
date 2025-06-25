package com.uop.quizapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.uop.quizapp.ActivityDataStore;
import com.uop.quizapp.GameState;
import com.uop.quizapp.Questions;
import com.uop.quizapp.repository.FirebaseQuestionRepository;
import com.uop.quizapp.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

                ActivityDataStore ds = ActivityDataStore.getInstance();
                GameState gs = ds.getGameState();
                Set<Integer> used = null;
                if (gs != null) {
                    used = gs.displayedQuestionIds.get(categoryKey);
                }
                if (used == null) {
                    used = new HashSet<>();
                }

                List<Questions> available = new ArrayList<>();
                for (Questions q : qs) {
                    if (!used.contains(q.get_id())) {
                        available.add(q);
                    }
                }

                if (available.isEmpty()) {
                    callback.onError();
                    return;
                }

                Random random = new Random();
                Questions randomQuestion = available.get(random.nextInt(available.size()));

                used.add(randomQuestion.get_id());
                if (gs != null) {
                    gs.displayedQuestionIds.put(categoryKey, used);
                    ds.setGameState(gs);
                }

                callback.onLoaded(randomQuestion);
            }

            @Override
            public void onError(Exception error) {
                callback.onError();
            }
        });
    }
}
