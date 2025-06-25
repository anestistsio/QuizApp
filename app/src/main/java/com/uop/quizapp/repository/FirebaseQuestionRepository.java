package com.uop.quizapp.repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uop.quizapp.Questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Firebase-backed implementation of {@link QuestionRepository}.
 */
public class FirebaseQuestionRepository implements QuestionRepository {
    private final DatabaseReference rootRef;

    public FirebaseQuestionRepository() {
        rootRef = FirebaseDatabase
                .getInstance("https://quizapp-41598-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference();
    }

    @Override
    public void getQuestionsByCategory(String category, QuestionsCallback callback) {
        rootRef.child("questions").child(category)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        List<Questions> list = new ArrayList<>();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Questions q = child.getValue(Questions.class);
                            if (q != null) list.add(q);
                        }
                        callback.onQuestionsLoaded(list);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        callback.onError(error.toException());
                    }
                });
    }
}
