package com.uop.quizapp;

/**
 * Holds constant category names used throughout the app. These were previously
 * provided by DBContract but the local SQLite database has been removed.
 */
public final class Category {
    private Category() {}

    // English category names (used as keys in Firebase)
    public static final String NATIONAL = "National";
    public static final String GENERAL = "General";
    public static final String CLUBS = "Clubs";
    public static final String GEOGRAPHY = "Geography";

    // Greek translations used for UI
    public static final String GREEK_NATIONAL = "Εθνικές";
    public static final String GREEK_GENERAL = "Γενικές";
    public static final String GREEK_CLUBS = "Συλλόγοι";
    public static final String GREEK_GEOGRAPHY = "Γεωγραφία";
}
