package com.uop.quizapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDBHelper {
    private final DatabaseReference rootRef;

    public FirebaseDBHelper() {
        rootRef = FirebaseDatabase.getInstance("https://quizapp-41598-default-rtdb.europe-west1.firebasedatabase.app").getReference();
    }

    public interface QuestionsCallback {
        void onQuestionsLoaded(List<Questions> questions);
        void onError(DatabaseError error);
    }

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
                        callback.onError(error);
                    }
                });
    }

    public void updateQuestionDisplayed(String category, Questions question) {
        rootRef.child("questions").child(category)
                .child(String.valueOf(question.get_id()))
                .child("displayed").setValue(question.getDisplayed());
    }

    public void resetAllDisplayedValues() {
        rootRef.child("questions").get().addOnSuccessListener(snapshot -> {
            for (DataSnapshot cat : snapshot.getChildren()) {
                for (DataSnapshot q : cat.getChildren()) {
                    q.getRef().child("displayed").setValue(false);
                }
            }
        });
    }
}
