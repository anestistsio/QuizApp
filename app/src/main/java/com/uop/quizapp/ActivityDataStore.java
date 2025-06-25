package com.uop.quizapp;

import java.util.List;

import com.uop.quizapp.Questions;

/**
 * Singleton key-value store used to pass data between activities.
 */
public class ActivityDataStore {
    private static ActivityDataStore instance;
    private final java.util.Map<String, Object> store = new java.util.HashMap<>();

    private ActivityDataStore() {}

    /**
     * Retrieve the singleton instance.
     */
    public static synchronized ActivityDataStore getInstance() {
        if (instance == null) {
            instance = new ActivityDataStore();
        }
        return instance;
    }

    /**
     * Store an arbitrary value with the given key.
     */
    public synchronized void put(String key, Object value) {
        store.put(key, value);
    }

    /**
     * Retrieve a stored value by key.
     */
    @SuppressWarnings("unchecked")
    public synchronized <T> T get(String key) {
        return (T) store.get(key);
    }

    /**
     * Remove all stored values.
     */
    public synchronized void clear() {
        store.clear();
    }

    /**
     * Convenience getter for the shared {@link GameState} object.
     */
    public synchronized GameState getGameState() {
        return (GameState) store.get("gameState");
    }

    /**
     * Store the shared {@link GameState} object.
     */
    public synchronized void setGameState(GameState gameState) {
        store.put("gameState", gameState);
    }

    /**
     * Store a list of questions for the given category.
     */
    public synchronized void putQuestions(String category, java.util.List<Questions> qs) {
        store.put("questions_" + category, qs);
    }

    /**
     * Retrieve cached questions for the given category.
     */
    @SuppressWarnings("unchecked")
    public synchronized java.util.List<Questions> getQuestions(String category) {
        Object obj = store.get("questions_" + category);
        if (obj == null) return null;
        return (java.util.List<Questions>) obj;
    }
}
