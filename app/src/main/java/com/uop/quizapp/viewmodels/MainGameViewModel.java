package com.uop.quizapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.uop.quizapp.Questions;
import com.uop.quizapp.repository.FirebaseQuestionRepository;
import com.uop.quizapp.repository.QuestionRepository;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;

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

    public void loadRandomQuestion(String categoryKey,
                                  java.util.Map<String, java.util.Set<Integer>> usedIdsMap,
                                  QuestionCallback callback) {
        repository.getQuestionsByCategory(categoryKey, new QuestionRepository.QuestionsCallback() {
            @Override
            public void onQuestionsLoaded(List<Questions> qs) {
                if (qs == null || qs.isEmpty()) {
                    callback.onError();
                    return;
                }
                Set<Integer> usedIds = usedIdsMap.get(categoryKey);
                if (usedIds == null) {
                    usedIds = new HashSet<>();
                    usedIdsMap.put(categoryKey, usedIds);
                }
                List<Questions> available = new ArrayList<>();
                for (Questions q : qs) {
                    if (!usedIds.contains(q.get_id())) {
                        available.add(q);
                    }
                }
                if (available.isEmpty()) {
                    callback.onError();
                    return;
                }
                Random random = new Random();
                Questions randomQuestion = available.get(random.nextInt(available.size()));
                callback.onLoaded(randomQuestion);
            }

            @Override
            public void onError(Exception error) {
                callback.onError();
            }
        });
    }
}
