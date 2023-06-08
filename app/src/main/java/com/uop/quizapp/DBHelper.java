package com.uop.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uop.quizapp.DBContract.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "Questions_db.db";

    // Database Version
    private static final int DATABASE_VERSION = 7;
    private SQLiteDatabase db;
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create the tables
        this.db = db;
        final String SQL_CREATE_SCIENCE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ScienceTable.TABLE_NAME + " ( " +
                ScienceTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ScienceTable.COLUMN_QUESTION + " TEXT, " +
                ScienceTable.COLUMN_ANSWER + " TEXT, " +
                ScienceTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GEOGRAPHY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GeographyTable.TABLE_NAME + " ( " +
                GeographyTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GeographyTable.COLUMN_QUESTION + " TEXT, " +
                GeographyTable.COLUMN_ANSWER + " TEXT, " +
                GeographyTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GENERAL_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GeneralTable.TABLE_NAME + " ( " +
                GeneralTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GeneralTable.COLUMN_QUESTION + " TEXT, " +
                GeneralTable.COLUMN_ANSWER + " TEXT, " +
                GeneralTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_SPORTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                SportsTable.TABLE_NAME + " ( " +
                SportsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SportsTable.COLUMN_QUESTION + " TEXT, " +
                SportsTable.COLUMN_ANSWER + " TEXT, " +
                SportsTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GREEK_SCIENCE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GreekScienceTable.TABLE_NAME + " ( " +
                GreekScienceTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GreekScienceTable.COLUMN_QUESTION + " TEXT, " +
                GreekScienceTable.COLUMN_ANSWER + " TEXT, " +
                GreekScienceTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GREEK_GEOGRAPHY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GreekGeographyTable.TABLE_NAME + " ( " +
                GreekGeographyTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GreekGeographyTable.COLUMN_QUESTION + " TEXT, " +
                GreekGeographyTable.COLUMN_ANSWER + " TEXT, " +
                GreekGeographyTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GREEK_GENERAL_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GreekGeneralTable.TABLE_NAME + " ( " +
                GreekGeneralTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GreekGeneralTable.COLUMN_QUESTION + " TEXT, " +
                GreekGeneralTable.COLUMN_ANSWER + " TEXT, " +
                GreekGeneralTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GREEK_SPORTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GreekSportsTable.TABLE_NAME + " ( " +
                GreekSportsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
                new String[]{"id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
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
                new String[]{"id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
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
                new String[]{"id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
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
                new String[]{"id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
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
                new String[]{"id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
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
                new String[]{"id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
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
                new String[]{"id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
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
                new String[]{"id", "question", "answer", "displayed"},
                "displayed = 0",  // Fetch only questions where displayed is false
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
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
                "id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GeneralTable.TABLE_NAME,
                values,
                "id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                SportsTable.TABLE_NAME,
                values,
                "id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GeographyTable.TABLE_NAME,
                values,
                "id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GreekScienceTable.TABLE_NAME,
                values,
                "id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GreekGeneralTable.TABLE_NAME,
                values,
                "id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GreekSportsTable.TABLE_NAME,
                values,
                "id = ?",
                new String[]{String.valueOf(question.get_id())}
        );
        db.update(
                GreekGeographyTable.TABLE_NAME,
                values,
                "id = ?",
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
    // fill all the tables with questions from txt files
    private void fillGeneralTable(){
        try {
            InputStream inputStream = context.getAssets().open("GeneralTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addGeneral(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fillScienceTable(){

        try {
            InputStream inputStream = context.getAssets().open("ScienceTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addScience(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fillSportsTable(){

        try {
            InputStream inputStream = context.getAssets().open("SportsTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addSports(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void fillGeographyTable(){

        try {
            InputStream inputStream = context.getAssets().open("GeographyTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addGeography(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillGreekGeneralTable() {
        try {
            InputStream inputStream = context.getAssets().open("GreekGeneralTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addGreekGeneral(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void fillGreekScienceTable(){

        try {
            InputStream inputStream = context.getAssets().open("GreekScienceTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addGreekScience(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fillGreekSportsTable(){

        try {
            InputStream inputStream = context.getAssets().open("GreekSportsTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addGreekSports(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void fillGreekGeographyTable(){
        try {
            InputStream inputStream = context.getAssets().open("GreekGeographyTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addGreekGeography(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}