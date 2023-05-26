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
    private static final int DATABASE_VERSION = 7;
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
        final String SQL_CREATE_GREEK_SCIENCE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GreekScienceTable.TABLE_NAME + " ( " +
                GreekScienceTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GreekScienceTable.COLUMN_QUESTION + " TEXT, " +
                GreekScienceTable.COLUMN_ANSWER + " TEXT, " +
                GreekScienceTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GREEK_GEOGRAPHY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GreekGeographyTable.TABLE_NAME + " ( " +
                GreekGeographyTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GreekGeographyTable.COLUMN_QUESTION + " TEXT, " +
                GreekGeographyTable.COLUMN_ANSWER + " TEXT, " +
                GreekGeographyTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GREEK_GENERAL_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GreekGeneralTable.TABLE_NAME + " ( " +
                GreekGeneralTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GreekGeneralTable.COLUMN_QUESTION + " TEXT, " +
                GreekGeneralTable.COLUMN_ANSWER + " TEXT, " +
                GreekGeneralTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GREEK_SPORTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GreekSportsTable.TABLE_NAME + " ( " +
                GreekSportsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GreekSportsTable.COLUMN_QUESTION + " TEXT, " +
                GreekSportsTable.COLUMN_ANSWER + " TEXT, " +
                GreekSportsTable.COLUMN_DISPLAYED + " INTEGER" +
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
        db.execSQL(SQL_CREATE_GREEK_SCIENCE_TABLE);
        fillGreekScienceTable();
        db.execSQL(SQL_CREATE_GREEK_GEOGRAPHY_TABLE);
        fillGreekGeographyTable();
        db.execSQL(SQL_CREATE_GREEK_GENERAL_TABLE);
        fillGreekGeneralTable();
        db.execSQL(SQL_CREATE_GREEK_SPORTS_TABLE);
        fillGreekSportsTable();

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
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GreekScienceTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GreekGeneralTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GreekSportsTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GreekGeographyTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
    }
    //this 4 methods called in fill-//-Table and they are adding the the rows in the tables
    private void addScience(Questions science_questions) {
        ContentValues cv = new ContentValues();
        cv.put(ScienceTable.COLUMN_QUESTION, science_questions.getQuestion());
        cv.put(ScienceTable.COLUMN_ANSWER, science_questions.getAnswer());
        cv.put(ScienceTable.COLUMN_ID, science_questions.get_id());
        cv.put(ScienceTable.COLUMN_DISPLAYED, false);
        db.insert(ScienceTable.TABLE_NAME, null, cv);
        cv.clear();
    }

    private void addGeneral(Questions general_questions) {
        ContentValues cv = new ContentValues();
        cv.put(GeneralTable.COLUMN_QUESTION, general_questions.getQuestion());
        cv.put(GeneralTable.COLUMN_ANSWER, general_questions.getAnswer());
        cv.put(GeneralTable.COLUMN_ID, general_questions.get_id());
        cv.put(GeneralTable.COLUMN_DISPLAYED, false);
        db.insert(GeneralTable.TABLE_NAME, null, cv);
        cv.clear();
    }

    private void addGeography(Questions geography_questions) {
        ContentValues cv = new ContentValues();
        cv.put(GeographyTable.COLUMN_QUESTION, geography_questions.getQuestion());
        cv.put(GeographyTable.COLUMN_ANSWER, geography_questions.getAnswer());
        cv.put(GeographyTable.COLUMN_ID, geography_questions.get_id());
        cv.put(GeographyTable.COLUMN_DISPLAYED, false);
        db.insert(GeographyTable.TABLE_NAME, null, cv);
        cv.clear();
    }

    private void addSports(Questions sports_questions) {
        ContentValues cv = new ContentValues();
        cv.put(SportsTable.COLUMN_QUESTION, sports_questions.getQuestion());
        cv.put(SportsTable.COLUMN_ANSWER, sports_questions.getAnswer());
        cv.put(SportsTable.COLUMN_ID, sports_questions.get_id());
        cv.put(SportsTable.COLUMN_DISPLAYED, false);
        db.insert(SportsTable.TABLE_NAME, null, cv);
        cv.clear();
    }
    //this 4 methods called in fill Greek -//-Table and they are adding the the rows in the tables
    private void addGreekScience(Questions greek_science_questions) {
        ContentValues cv = new ContentValues();
        cv.put(GreekScienceTable.COLUMN_QUESTION, greek_science_questions.getQuestion());
        cv.put(GreekScienceTable.COLUMN_ANSWER, greek_science_questions.getAnswer());
        cv.put(GreekScienceTable.COLUMN_ID, greek_science_questions.get_id());
        cv.put(GreekScienceTable.COLUMN_DISPLAYED, false);
        db.insert(GreekScienceTable.TABLE_NAME, null, cv);
        cv.clear();
    }

    private void addGreekGeneral(Questions greek_general_questions) {
        ContentValues cv = new ContentValues();
        cv.put(GreekGeneralTable.COLUMN_QUESTION, greek_general_questions.getQuestion());
        cv.put(GreekGeneralTable.COLUMN_ANSWER, greek_general_questions.getAnswer());
        cv.put(GreekGeneralTable.COLUMN_ID, greek_general_questions.get_id());
        cv.put(GreekGeneralTable.COLUMN_DISPLAYED, false);
        db.insert(GreekGeneralTable.TABLE_NAME, null, cv);
        cv.clear();
    }

    private void addGreekGeography(Questions greek_geography_questions) {
        ContentValues cv = new ContentValues();
        cv.put(GreekGeographyTable.COLUMN_QUESTION, greek_geography_questions.getQuestion());
        cv.put(GreekGeographyTable.COLUMN_ANSWER, greek_geography_questions.getAnswer());
        cv.put(GreekGeographyTable.COLUMN_ID, greek_geography_questions.get_id());
        cv.put(GreekGeographyTable.COLUMN_DISPLAYED, false);
        db.insert(GreekGeographyTable.TABLE_NAME, null, cv);
        cv.clear();
    }

    private void addGreekSports(Questions greek_sports_questions) {
        ContentValues cv = new ContentValues();
        cv.put(GreekSportsTable.COLUMN_QUESTION, greek_sports_questions.getQuestion());
        cv.put(GreekSportsTable.COLUMN_ANSWER, greek_sports_questions.getAnswer());
        cv.put(GreekSportsTable.COLUMN_ID, greek_sports_questions.get_id());
        cv.put(GreekSportsTable.COLUMN_DISPLAYED, false);
        db.insert(GreekSportsTable.TABLE_NAME, null, cv);
        cv.clear();
    }
//these 8 methods are called in MainGame.fill_arraylist method to return the filled up arrays from each category for both languages
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
    //Greek
    public List<Questions> getAllGreekScience() {
        ArrayList<Questions> GreekScienceList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                GreekScienceTable.TABLE_NAME,
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
                GreekScienceList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return GreekScienceList;
    }

    public List<Questions> getAllGreekSports() {
        ArrayList<Questions> GreekSportsList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                GreekSportsTable.TABLE_NAME,
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
                GreekSportsList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return GreekSportsList;
    }

    public List<Questions> getAllGreekGeneral() {
        ArrayList<Questions> GreekGeneralList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                GreekGeneralTable.TABLE_NAME,
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
                GreekGeneralList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return GreekGeneralList;
    }

    public List<Questions> getAllGreekGeography() {
        ArrayList<Questions> GreekGeographyList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                GreekGeographyTable.TABLE_NAME,
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
                GreekGeographyList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return GreekGeographyList;
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
        db.update(
                GreekScienceTable.TABLE_NAME,
                values,
                "_id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GreekGeneralTable.TABLE_NAME,
                values,
                "_id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GreekSportsTable.TABLE_NAME,
                values,
                "_id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GreekGeographyTable.TABLE_NAME,
                values,
                "_id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
    }
// this method is called in MainActivity to reset the displayed values to false  when the game starts
    public void resetAllDisplayedValues() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("displayed", 0); // Set displayed value to false
        db.update(ScienceTable.TABLE_NAME, values, null, null);
        db.update(GeneralTable.TABLE_NAME, values, null, null);
        db.update(GeographyTable.TABLE_NAME, values, null, null);
        db.update(SportsTable.TABLE_NAME, values, null, null);
        db.update(GreekScienceTable.TABLE_NAME, values, null, null);
        db.update(GreekGeneralTable.TABLE_NAME, values, null, null);
        db.update(GreekGeographyTable.TABLE_NAME, values, null, null);
        db.update(GreekSportsTable.TABLE_NAME, values, null, null);
        db.close();
    }
    // fill all the tables with questions
    private void fillGeneralTable(){
        Questions q1 = new Questions(1, "What is the most spoken language in the world?", "Mandarin", false);
        addGeneral(q1);
        Questions q2 = new Questions(2, "Who wrote the famous novel 'Pride and Prejudice'?", "Jane Austen", false);
        addGeneral(q2);
        Questions q3 = new Questions(3, "What is the capital city of Canada?", "Ottawa", false);
        addGeneral(q3);
        Questions q4 = new Questions(4, "Who painted the Mona Lisa?", "Leonardo da Vinci", false);
        addGeneral(q4);
        Questions q5 = new Questions(5, "Which planet is known as the 'Red Planet'?", "Mars", false);
        addGeneral(q5);
        Questions q6 = new Questions(6, "What is the largest organ in the human body?", "Skin", false);
        addGeneral(q6);
        Questions q7 = new Questions(7, "In which country is the Great Barrier Reef located?", "Australia", false);
        addGeneral(q7);
        Questions q8 = new Questions(8, "Which artist is famous for cutting off a part of his own ear?", "Vincent van Gogh", false);
        addGeneral(q8);
        Questions q9 = new Questions(9, "Who was the first man to walk on the moon?", "Neil Armstrong", false);
        addGeneral(q9);
        Questions q10 = new Questions(10, "What is the national flower of Japan?", "Cherry blossom", false);
        addGeneral(q10);
        Questions q11 = new Questions(11, "Which animal is known as the 'King of the Jungle'?", "Lion", false);
        addGeneral(q11);
        Questions q12 = new Questions(12, "Who is the author of the Harry Potter series?", "J.K. Rowling", false);
        addGeneral(q12);
        Questions q13 = new Questions(13, "What is the currency of Brazil?", "Brazilian Real", false);
        addGeneral(q13);
        Questions q14 = new Questions(14, "Which famous playwright wrote Romeo and Juliet?", "William Shakespeare", false);
        addGeneral(q14);
        Questions q15 = new Questions(15, "Which musical instrument has 88 keys?", "Piano", false);
        addGeneral(q15);
        Questions q16 = new Questions(16, "What is the tallest mountain in the world?", "Mount Everest", false);
        addGeneral(q16);
        Questions q17 = new Questions(17, "What is the national bird of the United States?", "Bald eagle", false);
        addGeneral(q17);
        Questions q18 = new Questions(18, "Who painted the ceiling of the Sistine Chapel?", "Michelangelo", false);
        addGeneral(q18);
        Questions q19 = new Questions(19, "What is the largest ocean in the world?", "Pacific Ocean", false);
        addGeneral(q19);
        Questions q20 = new Questions(20, "Who was the Greek god of music?", "Apollo", false);
        addGeneral(q20);

    }

    private void fillScienceTable(){

        Questions q1001 = new Questions(1001, "What is the largest organ in the human body?", "Skin", false);
        addScience(q1001);
        Questions q1002 = new Questions(1002, "What is the process by which plants convert sunlight into energy?", "Photosynthesis", false);
        addScience(q1002);
        Questions q1003 = new Questions(1003, "What is the chemical symbol for gold?", "Au", false);
        addScience(q1003);
        Questions q1004 = new Questions(1004, "What is the smallest unit of matter?", "Atom", false);
        addScience(q1004);
        Questions q1005 = new Questions(1005, "What is the formula for the force of gravity?", "F = G * (m1 * m2) / r^2", false);
        addScience(q1005);
        Questions q1006 = new Questions(1006, "What is the process by which water changes from a liquid to a gas?", "Evaporation", false);
        addScience(q1006);
        Questions q1007 = new Questions(1007, "What is the main component of Earth's atmosphere?", "Nitrogen", false);
        addScience(q1007);
        Questions q1008 = new Questions(1008, "What is the smallest planet in our solar system?", "Mercury", false);
        addScience(q1008);
        Questions q1009 = new Questions(1009, "What is the unit of measurement for electric current?", "Ampere", false);
        addScience(q1009);
        Questions q1010 = new Questions(1010, "What is the largest planet in our solar system?", "Jupiter", false);
        addScience(q1010);
        Questions q1011 = new Questions(1011, "What is the process by which a solid changes directly into a gas without passing through the liquid state?", "Sublimation", false);
        addScience(q1011);
        Questions q1012 = new Questions(1012, "What is the study of heredity and variation in organisms?", "Genetics", false);
        addScience(q1012);
        Questions q1013 = new Questions(1013, "What is the formula for calculating density?", "Density = Mass / Volume", false);
        addScience(q1013);
        Questions q1014 = new Questions(1014, "What is the largest bone in the human body?", "Femur", false);
        addScience(q1014);
        Questions q1015 = new Questions(1015, "What is the process by which plants release water vapor into the atmosphere?", "Transpiration", false);
        addScience(q1015);
        Questions q1016 = new Questions(1016, "What is the unit of measurement for frequency?", "Hertz", false);
        addScience(q1016);
        Questions q1017 = new Questions(1017, "What is the basic building block of proteins?", "Amino acid", false);
        addScience(q1017);
        Questions q1018 = new Questions(1018, "What is the chemical formula for water?", "H2O", false);
        addScience(q1018);
        Questions q1019 = new Questions(1019, "What is the study of the Earth's physical structure and substance?", "Geology", false);
        addScience(q1019);
        Questions q1020 = new Questions(1020, "What is the force that opposes the motion of objects through a fluid?", "Drag", false);
        addScience(q1020);

    }

    private void fillSportsTable(){

        Questions q2001 = new Questions(2001, "Which country has won the most Olympic gold medals?", "United States", false);
        addSports(q2001);
        Questions q2002 = new Questions(2002, "Which sport is Cristiano Ronaldo associated with?", "Football (Soccer)", false);
        addSports(q2002);
        Questions q2003 = new Questions(2003, "In which sport do teams compete for the Stanley Cup?", "Ice Hockey", false);
        addSports(q2003);
        Questions q2004 = new Questions(2004, "Who is the most decorated Olympian of all time?", "Michael Phelps", false);
        addSports(q2004);
        Questions q2005 = new Questions(2005, "Which country won the 2018 FIFA World Cup?", "France", false);
        addSports(q2005);
        Questions q2006 = new Questions(2006, "What is the national sport of Canada?", "Ice Hockey", false);
        addSports(q2006);
        Questions q2007 = new Questions(2007, "Which tennis player has won the most Grand Slam titles in history?", "Roger Federer", false);
        addSports(q2007);
        Questions q2008 = new Questions(2008, "Which sport is played at Wimbledon?", "Tennis", false);
        addSports(q2008);
        Questions q2009 = new Questions(2009, "Who holds the record for the most home runs in Major League Baseball (MLB)?", "Barry Bonds", false);
        addSports(q2009);
        Questions q2010 = new Questions(2010, "In which country were the first modern Olympic Games held?", "Greece", false);
        addSports(q2010);
        Questions q2011 = new Questions(2011, "Which country won the most recent FIFA Women's World Cup in 2019?", "United States", false);
        addSports(q2011);
        Questions q2012 = new Questions(2012, "Who is considered the greatest basketball player of all time?", "Michael Jordan", false);
        addSports(q2012);
        Questions q2013 = new Questions(2013, "Which sport is associated with the Super Bowl?", "American Football", false);
        addSports(q2013);
        Questions q2014 = new Questions(2014, "Which country is known for its dominance in the sport of cricket?", "India", false);
        addSports(q2014);
        Questions q2015 = new Questions(2015, "Which golfer has the most career major championships?", "Jack Nicklaus", false);
        addSports(q2015);
        Questions q2016 = new Questions(2016, "Which country hosted the 2016 Summer Olympics?", "Brazil", false);
        addSports(q2016);
        Questions q2017 = new Questions(2017, "Who is the fastest man in recorded history?", "Usain Bolt", false);
        addSports(q2017);
        Questions q2018 = new Questions(2018, "Which sport is associated with the Ashes series?", "Cricket", false);
        addSports(q2018);
        Questions q2019 = new Questions(2019, "Which country won the most recent Rugby World Cup in 2019?", "South Africa", false);
        addSports(q2019);
        Questions q2020 = new Questions(2020, "Who is the most successful Formula One driver of all time?", "Lewis Hamilton", false);
        addSports(q2020);

    }
    private void fillGeographyTable(){

        Questions q3001 = new Questions(3001, "What is the smallest country in the world?", "Vatican City", false);
        addGeography(q3001);
        Questions q3002 = new Questions(3002, "Which continent is the largest by land area?", "Asia", false);
        addGeography(q3002);
        Questions q3003 = new Questions(3003, "What is the capital city of France?", "Paris", false);
        addGeography(q3003);
        Questions q3004 = new Questions(3004, "What is the longest river in the world?", "Nile River", false);
        addGeography(q3004);
        Questions q3005 = new Questions(3005, "Which country is known as the Land of the Rising Sun?", "Japan", false);
        addGeography(q3005);
        Questions q3006 = new Questions(3006, "What is the largest desert in the world?", "Sahara Desert", false);
        addGeography(q3006);
        Questions q3007 = new Questions(3007, "What is the capital city of Brazil?", "Brasília", false);
        addGeography(q3007);
        Questions q3008 = new Questions(3008, "Which mountain range is located in South America?", "Andes Mountains", false);
        addGeography(q3008);
        Questions q3009 = new Questions(3009, "Which country is known for its fjords?", "Norway", false);
        addGeography(q3009);
        Questions q3010 = new Questions(3010, "What is the largest ocean in the world?", "Pacific Ocean", false);
        addGeography(q3010);
        Questions q3011 = new Questions(3011, "Which city is known as the Big Apple?", "New York City", false);
        addGeography(q3011);
        Questions q3012 = new Questions(3012, "What is the capital city of Australia?", "Canberra", false);
        addGeography(q3012);
        Questions q3013 = new Questions(3013, "Which country is known for its tulips and windmills?", "Netherlands", false);
        addGeography(q3013);
        Questions q3014 = new Questions(3014, "What is the largest country in South America?", "Brazil", false);
        addGeography(q3014);
        Questions q3015 = new Questions(3015, "Which continent is known as the 'Dark Continent'?", "Africa", false);
        addGeography(q3015);
        Questions q3016 = new Questions(3016, "What is the capital city of Russia?", "Moscow", false);
        addGeography(q3016);
        Questions q3017 = new Questions(3017, "Which country is known for the Great Barrier Reef?", "Australia", false);
        addGeography(q3017);
        Questions q3018 = new Questions(3018, "Which river flows through the Grand Canyon?", "Colorado River", false);
        addGeography(q3018);
        Questions q3019 = new Questions(3019, "What is the highest mountain in North America?", "Mount Denali", false);
        addGeography(q3019);
        Questions q3020 = new Questions(3020, "Which country is known for the Pyramids of Giza?", "Egypt", false);
        addGeography(q3020);
    }

    private void fillGreekGeneralTable(){
        Questions q5001 = new Questions(5001, "Ποια είναι η πιο ομιλούμενη γλώσσα στον κόσμο;", "Μανδαρινικά", false);
        addGreekGeneral(q5001);
        Questions q5002 = new Questions(5002, "Ποιος έγραψε το διάσημο μυθιστόρημα 'Υπερηφάνεια και προκατάληψη';", "Τζέιν Όστεν", false);
        addGreekGeneral(q5002);
        Questions q5003 = new Questions(5003, "Ποια είναι η πρωτεύουσα του Καναδά;", "Οττάβα", false);
        addGreekGeneral(q5003);
        Questions q5004 = new Questions(5004, "Ποιος ζωγράφισε τη Μόνα Λίζα;", "Λεονάρντο ντα Βίντσι", false);
        addGreekGeneral(q5004);
        Questions q5005 = new Questions(5005, "Ποιος πλανήτης είναι γνωστός ως 'Κόκκινος Πλανήτης';", "Άρης", false);
        addGreekGeneral(q5005);
        Questions q5006 = new Questions(5006, "Ποιο είναι το μεγαλύτερο όργανο στο ανθρώπινο σώμα;", "Δέρμα", false);
        addGreekGeneral(q5006);
        Questions q5007 = new Questions(5007, "Σε ποια χώρα βρίσκεται το Μεγάλο Κοραλλιογενές Ρηχό;", "Αυστραλία", false);
        addGreekGeneral(q5007);
        Questions q5008 = new Questions(5008, "Ποιος καλλιτέχνης είναι γνωστός για το γεγονός ότι κόβει ένα μέρος του αυτί του;", "Βίνσεντ βαν Γκογκ", false);
        addGreekGeneral(q5008);
        Questions q5009 = new Questions(5009, "Ποιος ήταν ο πρώτος άνθρωπος που περπάτησε στη Σελήνη;", "Νηλ Άρμστρονγκ", false);
        addGreekGeneral(q5009);
        Questions q5010 = new Questions(5010, "Ποια είναι η εθνική λουλούδα της Ιαπωνίας;", "Ανθοκρέμα", false);
        addGreekGeneral(q5010);
        Questions q5011 = new Questions(5011, "Ποιο ζώο είναι γνωστό ως 'Βασιλιάς της Ζούγκλας';", "Λιοντάρι", false);
        addGreekGeneral(q5011);
        Questions q5012 = new Questions(5012, "Ποιος είναι ο συγγραφέας της σειράς βιβλίων Ο Χάρι Πότερ;", "Τζ.Κ. Ρόουλινγκ", false);
        addGreekGeneral(q5012);
        Questions q5013 = new Questions(5013, "Ποιο είναι η νόμισμα της Βραζιλίας;", "Βραζιλιάνο ρεάλ", false);
        addGreekGeneral(q5013);
        Questions q5014 = new Questions(5014, "Ποιος διάσημος θεατρικός συγγραφέας έγραψε τη Ρωμαίος και Ιουλιέτα;", "Ουίλιαμ Σαίξπηρ", false);
        addGreekGeneral(q5014);
        Questions q5015 = new Questions(5015, "Ποιο μουσικό όργανο έχει 88 πλήκτρα;", "Πιάνο", false);
        addGreekGeneral(q5015);
        Questions q5016 = new Questions(5016, "Ποια είναι η ψηλότερη βουνοκορφή στον κόσμο;", "Όρος Έβερεστ", false);
        addGreekGeneral(q5016);
        Questions q5017 = new Questions(5017, "Ποιο είναι το εθνικό πουλί των Ηνωμένων Πολιτειών;", "Λευκοκεφαλής αετός", false);
        addGreekGeneral(q5017);
        Questions q5018 = new Questions(5018, "Ποιος ζωγράφισε την οροφή της Σιξτίνας Καπέλα;", "Μιχαήλ Άγγελος", false);
        addGreekGeneral(q5018);
        Questions q5019 = new Questions(5019, "Ποιος είναι ο μεγαλύτερος ωκεανός στον κόσμο;", "Ειρηνικός Ωκεανός", false);
        addGreekGeneral(q5019);
        Questions q5020 = new Questions(5020, "Ποιος ήταν ο θεός της μουσικής στην αρχαία Ελλάδα;", "Απόλλωνας", false);
        addGreekGeneral(q5020);

    }

    private void fillGreekScienceTable(){

        Questions q6001 = new Questions(6001, "Ποιο είναι το μεγαλύτερο όργανο στο ανθρώπινο σώμα;", "Δέρμα", false);
        addGreekScience(q6001);
        Questions q6002 = new Questions(6002, "Ποια είναι η διαδικασία με την οποία οι φυτά μετατρέπουν το ηλιακό φως σε ενέργεια;", "Φωτοσύνθεση", false);
        addGreekScience(q6002);
        Questions q6003 = new Questions(6003, "Ποιο είναι το χημικό σύμβολο του χρυσού;", "Au", false);
        addGreekScience(q6003);
        Questions q6004 = new Questions(6004, "Ποια είναι η μικρότερη μονάδα της ύλης;", "Άτομο", false);
        addGreekScience(q6004);
        Questions q6005 = new Questions(6005, "Ποιά είναι η σχέση για τη δύναμη της βαρύτητας;", "F = G * (m1 * m2) / r^2", false);
        addGreekScience(q6005);
        Questions q6006 = new Questions(6006, "Ποια είναι η διαδικασία με την οποία το νερό μεταβαίνει από υγρή κατάσταση σε αέρια;", "Ατμοποίηση", false);
        addGreekScience(q6006);
        Questions q6007 = new Questions(6007, "Ποιος είναι ο κύριος συστατικός της ατμόσφαιρας της Γης;", "Άζωτο", false);
        addGreekScience(q6007);
        Questions q6008 = new Questions(6008, "Ποιος είναι ο μικρότερος πλανήτης στο ηλιακό μας σύστημα;", "Ερμής", false);
        addGreekScience(q6008);
        Questions q6009 = new Questions(6009, "Ποια είναι η μονάδα μέτρησης του ηλεκτρικού ρεύματος;", "Αμπέρ", false);
        addGreekScience(q6009);
        Questions q6010 = new Questions(6010, "Ποιος είναι ο μεγαλύτερος πλανήτης στο ηλιακό μας σύστημα;", "Δίας", false);
        addGreekScience(q6010);
        Questions q6011 = new Questions(6011, "Ποια είναι η διαδικασία με την οποία ένα στερεό ύλη μεταβαίνει άμεσα σε αέρια κατάσταση χωρίς να περνάει από την υγρή κατάσταση;", "Ατμίσματιση", false);
        addGreekScience(q6011);
        Questions q6012 = new Questions(6012, "Ποια είναι η μελέτη της κληρονομικότητας και των ποικιλιών στους οργανισμούς;", "Γενετική", false);
        addGreekScience(q6012);
        Questions q6013 = new Questions(6013, "Ποια είναι η σχέση για τον υπολογισμό της πυκνότητας;", "Πυκνότητα = Μάζα / Όγκος", false);
        addGreekScience(q6013);
        Questions q6014 = new Questions(6014, "Ποιο είναι το μεγαλύτερο οστό στο ανθρώπινο σώμα;", "Μηριαίο", false);
        addGreekScience(q6014);
        Questions q6015 = new Questions(6015, "Ποια είναι η διαδικασία με την οποία τα φυτά απελευθερώνουν υδατική ατμόσφαιρα;", "Ατμοσφαιρική απώλεια", false);
        addGreekScience(q6015);
        Questions q6016 = new Questions(6016, "Ποια είναι η μονάδα μέτρησης της συχνότητας;", "Χερτζ", false);
        addGreekScience(q6016);
        Questions q6017 = new Questions(6017, "Ποια είναι η βασική δομική μονάδα των πρωτεϊνών;", "Αμινοξύ", false);
        addGreekScience(q6017);
        Questions q6018 = new Questions(6018, "Ποια είναι η χημική τύπος του νερού;", "Η2Ο", false);
        addGreekScience(q6018);
        Questions q6019 = new Questions(6019, "Ποια είναι η μελέτη της φυσικής δομής και ουσίας της Γης;", "Γεωλογία", false);
        addGreekScience(q6019);
        Questions q6020 = new Questions(6020, "Ποια είναι η δύναμη που αντιστέκεται στην κίνηση των αντικειμένων μέσα από ένα ρευστό;", "Αντίσταση", false);
        addGreekScience(q6020);

    }

    private void fillGreekSportsTable(){

        Questions q7001 = new Questions(7001, "Ποια χώρα έχει κερδίσει τα περισσότερα χρυσά Ολυμπιακά μετάλλια;", "Ηνωμένες Πολιτείες", false);
        addGreekSports(q7001);
        Questions q7002 = new Questions(7002, "Με ποιο άθλημα συνδέεται ο Cristiano Ronaldo;", "Ποδόσφαιρο (Ποδόσφαιρο)", false);
        addGreekSports(q7002);
        Questions q7003 = new Questions(7003, "Σε ποιο άθλημα οι ομάδες ανταγωνίζονται για το Stanley Cup;", "Πάγος Χόκεϊ", false);
        addGreekSports(q7003);
        Questions q7004 = new Questions(7004, "Ποιος είναι ο αθλητής με τα περισσότερα βραβεία στους Ολυμπιακούς Αγώνες όλων των εποχών;", "Michael Phelps", false);
        addGreekSports(q7004);
        Questions q7005 = new Questions(7005, "Ποια χώρα κέρδισε το Παγκόσμιο Κύπελλο FIFA του 2018;", "Γαλλία", false);
        addGreekSports(q7005);
        Questions q7006 = new Questions(7006, "Ποιο είναι το εθνικό άθλημα του Καναδά;", "Πάγος Χόκεϊ", false);
        addGreekSports(q7006);
        Questions q7007 = new Questions(7007, "Ποιος τενίστας έχει κερδίσει τα περισσότερα τίτλους Grand Slam στην ιστορία;", "Roger Federer", false);
        addGreekSports(q7007);
        Questions q7008 = new Questions(7008, "Σε ποιο άθλημα παίζεται το Wimbledon;", "Τένις", false);
        addGreekSports(q7008);
        Questions q7009 = new Questions(7009, "Ποιος κατέχει το ρεκόρ των περισσότερων home runs στο Major League Baseball (MLB);", "Barry Bonds", false);
        addGreekSports(q7009);
        Questions q7010 = new Questions(7010, "Σε ποια χώρα διεξήχθησαν οι πρώτοι μοντέρνοι Ολυμπιακοί Αγώνες;", "Ελλάδα", false);
        addGreekSports(q7010);
        Questions q7011 = new Questions(7011, "Ποια χώρα κέρδισε το πιο πρόσφατο Παγκόσμιο Κύπελλο Γυναικών FIFA το 2019;", "Ηνωμένες Πολιτείες", false);
        addGreekSports(q7011);
        Questions q7012 = new Questions(7012, "Ποιος θεωρείται ο μεγαλύτερος μπασκετμπολίστας όλων των εποχών;", "Michael Jordan", false);
        addGreekSports(q7012);
        Questions q7013 = new Questions(7013, "Με ποιο άθλημα συνδέεται το Super Bowl;", "Αμερικανικό Ποδόσφαιρο", false);
        addGreekSports(q7013);
        Questions q7014 = new Questions(7014, "Ποια χώρα είναι γνωστή για την κυριαρχία της στο άθλημα του κρίκετ;", "Ινδία", false);
        addGreekSports(q7014);
        Questions q7015 = new Questions(7015, "Ποιος γκόλφερ έχει τα περισσότερα πρωταθλήματα μεγάλων τουρνουά στην καριέρα του;", "Jack Nicklaus", false);
        addGreekSports(q7015);
        Questions q7016 = new Questions(7016, "Ποια χώρα φιλοξένησε τους Θερινούς Ολυμπιακούς Αγώνες του 2016;", "Βραζιλία", false);
        addGreekSports(q7016);
        Questions q7017 = new Questions(7017, "Ποιος είναι ο γρηγορότερος άνθρωπος στην καταγεγραμμένη ιστορία;", "Usain Bolt", false);
        addGreekSports(q7017);
        Questions q7018 = new Questions(7018, "Με ποιο άθλημα συνδέεται η σειρά Ashes;", "Κρίκετ", false);
        addGreekSports(q7018);
        Questions q7019 = new Questions(7019, "Ποια χώρα κέρδισε το πιο πρόσφατο Παγκόσμιο Κύπελλο Ράγκμπι το 2019;", "Νότια Αφρική", false);
        addGreekSports(q7019);
        Questions q7020 = new Questions(7020, "Ποιος είναι ο πιο επιτυχημένος οδηγός της Φόρμουλα 1 όλων των εποχών;", "Lewis Hamilton", false);
        addGreekSports(q7020);

    }
    private void fillGreekGeographyTable(){


        Questions q8001 = new Questions(8001, "Ποια είναι η μικρότερη χώρα στον κόσμο;", "Πόλη του Βατικανού", false);
        addGreekGeography(q8001);
        Questions q8002 = new Questions(8002, "Ποιος ηπειρος είναι ο μεγαλύτερος κατά έκταση;", "Ασία", false);
        addGreekGeography(q8002);
        Questions q8003 = new Questions(8003, "Ποια είναι η πρωτεύουσα της Γαλλίας;", "Παρίσι", false);
        addGreekGeography(q8003);
        Questions q8004 = new Questions(8004, "Ποιος είναι ο μεγαλύτερος ποταμός στον κόσμο;", "Ποταμός Νείλος", false);
        addGreekGeography(q8004);
        Questions q8005 = new Questions(8005, "Ποια χώρα είναι γνωστή ως η 'Χώρα του Ανατολικού Ηλίου';", "Ιαπωνία", false);
        addGreekGeography(q8005);
        Questions q8006 = new Questions(8006, "Ποια είναι η μεγαλύτερη έρημος στον κόσμο;", "Έρημος της Σαχάρας", false);
        addGreekGeography(q8006);
        Questions q8007 = new Questions(8007, "Ποια είναι η πρωτεύουσα της Βραζιλίας;", "Μπραζίλια", false);
        addGreekGeography(q8007);
        Questions q8008 = new Questions(8008, "Ποια οροσειρά βρίσκεται στη Νότια Αμερική;", "Οροσειρά των Άνδεων", false);
        addGreekGeography(q8008);
        Questions q8009 = new Questions(8009, "Ποια χώρα είναι γνωστή για τις φιορδ;", "Νορβηγία", false);
        addGreekGeography(q8009);
        Questions q8010 = new Questions(8010, "Ποιος είναι ο μεγαλύτερος ωκεανός στον κόσμο;", "Ειρηνικός Ωκεανός", false);
        addGreekGeography(q8010);
        Questions q8011 = new Questions(8011, "Ποια πόλη είναι γνωστή ως το 'Μεγάλο Μήλο';", "Νέα Υόρκη", false);
        addGreekGeography(q8011);
        Questions q8012 = new Questions(8012, "Ποια είναι η πρωτεύουσα της Αυστραλίας;", "Καμπέρα", false);
        addGreekGeography(q8012);
        Questions q8013 = new Questions(8013, "Ποια χώρα είναι γνωστή για τα τουλίπες και τους ανεμόμυλους;", "Ολλανδία", false);
        addGreekGeography(q8013);
        Questions q8014 = new Questions(8014, "Ποια είναι η μεγαλύτερη χώρα στη Νότια Αμερική;", "Βραζιλία", false);
        addGreekGeography(q8014);
        Questions q8015 = new Questions(8015, "Ποια ηπειρος είναι γνωστή ως το 'Σκοτεινός Ήπειρος';", "Αφρική", false);
        addGreekGeography(q8015);
        Questions q8016 = new Questions(8016, "Ποια είναι η πρωτεύουσα της Ρωσίας;", "Μόσχα", false);
        addGreekGeography(q8016);
        Questions q8017 = new Questions(8017, "Ποια χώρα είναι γνωστή για το Μεγάλο Εμπόδιο Ρηχού;", "Αυστραλία", false);
        addGreekGeography(q8017);
        Questions q8018 = new Questions(8018, "Ποιος ποταμός διασχίζει το Φαράγγι του Κολοράντο;", "Ποταμός Κολοράντο", false);
        addGreekGeography(q8018);
        Questions q8019 = new Questions(8019, "Ποιος είναι ο ψηλότερος βουνό στη Βόρεια Αμερική;", "Όρος Ντενάλι", false);
        addGreekGeography(q8019);
        Questions q8020 = new Questions(8020, "Ποια χώρα είναι γνωστή για τις Πυραμίδες της Γκίζα;", "Αίγυπτος", false);
        addGreekGeography(q8020);
    }


}