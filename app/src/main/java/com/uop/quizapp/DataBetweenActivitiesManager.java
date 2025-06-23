package com.uop.quizapp;

public class DataBetweenActivitiesManager {
    private static DataBetweenActivitiesManager instance;
    private final java.util.Map<String, Object> store = new java.util.HashMap<>();

    private DataBetweenActivitiesManager() {}

    public static synchronized DataBetweenActivitiesManager getInstance() {
        if (instance == null) {
            instance = new DataBetweenActivitiesManager();
        }
        return instance;
    }

    public synchronized void put(String key, Object value) {
        store.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public synchronized <T> T get(String key) {
        return (T) store.get(key);
    }

    public synchronized void clear() {
        store.clear();
    }

    public synchronized GameState getGameState() {
        return (GameState) store.get("gameState");
    }

    public synchronized void setGameState(GameState gameState) {
        store.put("gameState", gameState);
    }
}
