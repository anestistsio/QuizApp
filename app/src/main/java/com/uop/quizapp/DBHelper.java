package com.uop.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uop.quizapp.DBContract.*;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "Questions_db.db";

    // Database Version
    private static final int DATABASE_VERSION = 2;
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create the tables
        this.db = db;
        final String SQL_CREATE_SCIENCE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ScienceTable.TABLE_NAME + " ( " +
                ScienceTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ScienceTable.COLUMN_QUESTION + " TEXT, " +
                ScienceTable.COLUMN_ANSWER + " TEXT, " +
                ScienceTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GEOGRAPHY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GeographyTable.TABLE_NAME + " ( " +
                GeographyTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GeographyTable.COLUMN_QUESTION + " TEXT, " +
                GeographyTable.COLUMN_ANSWER + " TEXT, " +
                GeographyTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GENERAL_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GeneralTable.TABLE_NAME + " ( " +
                GeneralTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GeneralTable.COLUMN_QUESTION + " TEXT, " +
                GeneralTable.COLUMN_ANSWER + " TEXT, " +
                GeneralTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_SPORTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                SportsTable.TABLE_NAME + " ( " +
                SportsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SportsTable.COLUMN_QUESTION + " TEXT, " +
                SportsTable.COLUMN_ANSWER + " TEXT, " +
                SportsTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";

        // create the tables
        db.execSQL(SQL_CREATE_SCIENCE_TABLE);
        fillScienceTable();
        db.execSQL(SQL_CREATE_GEOGRAPHY_TABLE);
        fillGeographyTable();
        db.execSQL(SQL_CREATE_GENERAL_TABLE);
        fillGeneralTable();
        db.execSQL(SQL_CREATE_SPORTS_TABLE);
        fillSportsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ScienceTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GeneralTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + SportsTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GeographyTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
    }
    //this 4 methods called in fill-//-Table and they are adding the the rows in the tables
    private void addScience(Questions science_questions) {
        ContentValues cv = new ContentValues();
        cv.put(ScienceTable.COLUMN_QUESTION, science_questions.getQuestion());
        cv.put(ScienceTable.COLUMN_ANSWER, science_questions.getAnswer());
        cv.put(ScienceTable.COLUMN_DISPLAYED, false);
        db.insert(ScienceTable.TABLE_NAME, null, cv);
        cv.clear();
    }

    private void addGeneral(Questions general_questions) {
        ContentValues cv = new ContentValues();
        cv.put(GeneralTable.COLUMN_QUESTION, general_questions.getQuestion());
        cv.put(GeneralTable.COLUMN_ANSWER, general_questions.getAnswer());
        cv.put(GeneralTable.COLUMN_DISPLAYED, false);
        db.insert(GeneralTable.TABLE_NAME, null, cv);
        cv.clear();
    }

    private void addGeography(Questions geography_questions) {
        ContentValues cv = new ContentValues();
        cv.put(GeographyTable.COLUMN_QUESTION, geography_questions.getQuestion());
        cv.put(GeographyTable.COLUMN_ANSWER, geography_questions.getAnswer());
        cv.put(GeographyTable.COLUMN_DISPLAYED, false);
        db.insert(GeographyTable.TABLE_NAME, null, cv);
        cv.clear();
    }

    private void addSports(Questions sports_questions) {
        ContentValues cv = new ContentValues();
        cv.put(SportsTable.COLUMN_QUESTION, sports_questions.getQuestion());
        cv.put(SportsTable.COLUMN_ANSWER, sports_questions.getAnswer());
        cv.put(SportsTable.COLUMN_DISPLAYED, false);
        db.insert(SportsTable.TABLE_NAME, null, cv);
        cv.clear();
    }

//these 4 methods are called in on create to fill up the tables using the add-//- methods
    private void fillScienceTable(){

        Questions q11 = new Questions(1, "What is the largest organ in the human body?", "Skin", false);
        addScience(q11);
        Questions q12 = new Questions(2, "What is the atomic number of carbon?", "6",  false);
        addScience(q12);
        Questions q13 = new Questions(3, "What is the name of the force that opposes motion?", "Friction", false);
        addScience(q13);
        Questions q14 = new Questions(4, "What is the name of the process by which plants make their own food?", "Photosynthesis", false);
        addScience(q14);
        Questions q15 = new Questions(5, "What is the name of the smallest particle in an element?", "Atom", false);
        addScience(q15);
        Questions q16 = new Questions(6, "What is the name of the process by which water turns into vapor?", "Evaporation", false);
        addScience(q16);
        Questions q17 = new Questions(7, "What is the name of the first man to walk on the moon?", "Neil Armstrong",  false);
        addScience(q17);
        Questions q18 = new Questions(8, "What is the largest planet in our solar system?", "Jupiter",  false);
        addScience(q18);
        Questions q19 = new Questions(9, "What is the name of the force that holds atoms together in a molecule?", "Chemical bond",  false);
        addScience(q19);
        Questions q20 = new Questions(10, "What is the name of the phenomenon that causes a moving object to follow a curved path?", "Centrifugal force",  false);
        addScience(q20);

    }
    private void fillGeographyTable(){

        Questions q31 = new Questions(1, "What is the smallest country in the world?", "Vatican City",  false);
        addGeography(q31);
        Questions q32 = new Questions(2, "What is the largest desert in the world?", "Sahara",  false);
        addGeography(q32);
        Questions q33 = new Questions(3, "What is the highest waterfall in the world?", "Angel Falls",  false);
        addGeography(q33);
        Questions q34 = new Questions(4, "What is the longest river in Africa?", "Nile",  false);
        addGeography(q34);
        Questions q35 = new Questions(5, "What is the deepest ocean trench?", "Mariana Trench",  false);
        addGeography(q35);
        Questions q36 = new Questions(6, "What is the capital of Brazil?", "Brasília",  false);
        addGeography(q36);
        Questions q37 = new Questions(7, "What is the world's largest archipelago?", "Indonesia",  false);
        addGeography(q37);
        Questions q38 = new Questions(8, "What is the highest mountain range in South America?", "Andes", false);
        addGeography(q38);
        Questions q39 = new Questions(9, "What is the southernmost point of Africa called?", "Cape Agulhas", false);
        addGeography(q39);
        Questions q40 = new Questions(10, "What is the only continent that exists entirely in the Southern Hemisphere?", "Antarctica", false);
        addGeography(q40);
    }
    private void fillGeneralTable(){

        Questions q1 = new Questions(1, "What is the smallest planet in our solar system?", "Mercury", false);
        addGeneral(q1);
        Questions q2 = new Questions(2, "What is the largest organ in the human body?", "Skin", false);
        addGeneral(q2);
        Questions q3 = new Questions(3, "What is the name of the first man to walk on the moon?", "Neil Armstrong", false);
        addGeneral(q3);
        Questions q4 = new Questions(4, "What is the hottest continent on Earth?", "Africa", false);
        addGeneral(q4);
        Questions q5 = new Questions(5, "What is the capital of Canada?", "Ottawa", false);
        addGeneral(q5);
        Questions q6 = new Questions(6, "What is the chemical symbol for sodium?", "Na", false);
        addGeneral(q6);
        Questions q7 = new Questions(7, "What is the most common element in the Earth's atmosphere?", "Nitrogen", false);
        addGeneral(q7);
        Questions q8 = new Questions(8, "What is the highest waterfall in the world?", "Angel Falls", false);
        addGeneral(q8);
        Questions q9 = new Questions(9, "What is the name of the largest ocean on Earth?", "Pacific", false);
        addGeneral(q9);
        Questions q10 = new Questions(10, "What is the most spoken language in the world?", "Mandarin",  false);
        addGeneral(q10);
    }

    private void fillSportsTable(){

        Questions q21 = new Questions(1, "Which country has won the most Olympic gold medals?", "United States", false);
        addSports(q21);
        Questions q22 = new Questions(2, "Who is the fastest man in the world?", "Usain Bolt", false);
        addSports(q22);
        Questions q23 = new Questions(3, "Which country has won the most World Cups in football?", "Brazil",  false);
        addSports(q23);
        Questions q24 = new Questions(4, "Which sport is played at Wimbledon?", "Tennis", false);
        addSports(q24);
        Questions q25 = new Questions(5, "Who was the first African American to win a gold medal at the Olympics?", "Jesse Owens",  false);
        addSports(q25);
        Questions q26 = new Questions(6, "Which country hosted the first modern Olympic Games?", "Greece",  false);
        addSports(q26);
        Questions q27 = new Questions(7, "Who won the men's singles title at the 2021 Australian Open?", "Novak Djokovic", false);
        addSports(q27);
        Questions q28 = new Questions(8, "Who won the FIFA Women's World Cup in 2019?", "United States",  false);
        addSports(q28);
        Questions q29 = new Questions(9, "Who won the Tour de France in 2020?", "Tadej Pogacar",  false);
        addSports(q29);
        Questions q30 = new Questions(10, "Which country hosted the 2018 Winter Olympics?", "South Korea", false);
        addSports(q30);

    }

//these 4 methods are called in MainGame.fill_arraylist method to return the filled up arrays from each category
    public List<Questions> getAllScience() {
        ArrayList<Questions> scienceList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                ScienceTable.TABLE_NAME,
                new String[]{"_id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));
                boolean displayed = cursor.getInt(cursor.getColumnIndexOrThrow("displayed")) == 1;

                Questions q = new Questions(id,question, answer, displayed);
                scienceList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return scienceList;
    }

    public List<Questions> getAllSports() {
        ArrayList<Questions> sportsList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                SportsTable.TABLE_NAME,
                new String[]{"_id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));
                boolean displayed = cursor.getInt(cursor.getColumnIndexOrThrow("displayed")) == 1;

                Questions q = new Questions(id,question, answer, displayed);
                sportsList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return sportsList;
    }

    public List<Questions> getAllGeneral() {
        ArrayList<Questions> generalList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                GeneralTable.TABLE_NAME,
                new String[]{"_id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));
                boolean displayed = cursor.getInt(cursor.getColumnIndexOrThrow("displayed")) == 1;

                Questions q = new Questions(id,question, answer, displayed);
                generalList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return generalList;
    }

    public List<Questions> getAllGeography() {
        ArrayList<Questions> geographyList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                GeographyTable.TABLE_NAME,
                new String[]{"_id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));
                boolean displayed = cursor.getInt(cursor.getColumnIndexOrThrow("displayed")) == 1;

                Questions q = new Questions(id,question, answer, displayed);
                geographyList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return geographyList;
    }
    public void updateQuestion(Questions question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("displayed", question.getDisplayed() ? 1 : 0);  // Convert boolean to integer (1 for true, 0 for false)

        db.update(
                ScienceTable.TABLE_NAME,
                values,
                "_id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GeneralTable.TABLE_NAME,
                values,
                "_id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                SportsTable.TABLE_NAME,
                values,
                "_id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GeographyTable.TABLE_NAME,
                values,
                "_id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
    }
// this method is called in MainActivity to reset the displayed values from the DataBase to false  when the game starts
    public void resetAllDisplayedValues() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("displayed", 0); // Set displayed value to false
        db.update(ScienceTable.TABLE_NAME, values, null, null);
        db.update(GeneralTable.TABLE_NAME, values, null, null);
        db.update(GeographyTable.TABLE_NAME, values, null, null);
        db.update(SportsTable.TABLE_NAME, values, null, null);
        db.close();
    }

}