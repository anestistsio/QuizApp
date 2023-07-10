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
    private static final int DATABASE_VERSION = 11;
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
        final String SQL_CREATE_NATIONAL_TABLE = "CREATE TABLE IF NOT EXISTS " +
                NationalTable.TABLE_NAME + " ( " +
                NationalTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NationalTable.COLUMN_QUESTION + " TEXT, " +
                NationalTable.COLUMN_ANSWER + " TEXT, " +
                NationalTable.COLUMN_DISPLAYED + " INTEGER" +
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
        final String SQL_CREATE_CLUBS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ClubsTable.TABLE_NAME + " ( " +
                ClubsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ClubsTable.COLUMN_QUESTION + " TEXT, " +
                ClubsTable.COLUMN_ANSWER + " TEXT, " +
                ClubsTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";
        final String SQL_CREATE_GREEK_NATIONAL_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GreekNationalTable.TABLE_NAME + " ( " +
                GreekNationalTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GreekNationalTable.COLUMN_QUESTION + " TEXT, " +
                GreekNationalTable.COLUMN_ANSWER + " TEXT, " +
                GreekNationalTable.COLUMN_DISPLAYED + " INTEGER" +
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
        final String SQL_CREATE_GREEK_CLUBS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                GreekClubsTable.TABLE_NAME + " ( " +
                GreekClubsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GreekClubsTable.COLUMN_QUESTION + " TEXT, " +
                GreekClubsTable.COLUMN_ANSWER + " TEXT, " +
                GreekClubsTable.COLUMN_DISPLAYED + " INTEGER" +
                ")";

        // create the tables
        db.execSQL(SQL_CREATE_NATIONAL_TABLE);
        fillNationalTable();
        db.execSQL(SQL_CREATE_GEOGRAPHY_TABLE);
        fillGeographyTable();
        db.execSQL(SQL_CREATE_GENERAL_TABLE);
        fillGeneralTable();
        db.execSQL(SQL_CREATE_CLUBS_TABLE);
        fillClubsTable();
        db.execSQL(SQL_CREATE_GREEK_NATIONAL_TABLE);
        fillGreekNationalTable();
        db.execSQL(SQL_CREATE_GREEK_GEOGRAPHY_TABLE);
        fillGreekGeographyTable();
        db.execSQL(SQL_CREATE_GREEK_GENERAL_TABLE);
        fillGreekGeneralTable();
        db.execSQL(SQL_CREATE_GREEK_CLUBS_TABLE);
        fillGreekClubsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NationalTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GeneralTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ClubsTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GeographyTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GreekNationalTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GreekGeneralTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GreekClubsTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GreekGeographyTable.TABLE_NAME);
        // create fresh table
        onCreate(db);
    }
    //this 4 methods called in fill-//-Table and they are adding the the rows in the tables
    private void addNational(Questions national_questions) {
        ContentValues cv = new ContentValues();
        cv.put(NationalTable.COLUMN_QUESTION, national_questions.getQuestion());
        cv.put(NationalTable.COLUMN_ANSWER, national_questions.getAnswer());
        cv.put(NationalTable.COLUMN_ID, national_questions.get_id());
        cv.put(NationalTable.COLUMN_DISPLAYED, false);
        db.insert(NationalTable.TABLE_NAME, null, cv);
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

    private void addClubs(Questions clubs_questions) {
        ContentValues cv = new ContentValues();
        cv.put(ClubsTable.COLUMN_QUESTION, clubs_questions.getQuestion());
        cv.put(ClubsTable.COLUMN_ANSWER, clubs_questions.getAnswer());
        cv.put(ClubsTable.COLUMN_ID, clubs_questions.get_id());
        cv.put(ClubsTable.COLUMN_DISPLAYED, false);
        db.insert(ClubsTable.TABLE_NAME, null, cv);
        cv.clear();
    }
    //this 4 methods called in fill Greek -//-Table and they are adding the the rows in the tables
    private void addGreekNational(Questions greek_national_questions) {
        ContentValues cv = new ContentValues();
        cv.put(GreekNationalTable.COLUMN_QUESTION, greek_national_questions.getQuestion());
        cv.put(GreekNationalTable.COLUMN_ANSWER, greek_national_questions.getAnswer());
        cv.put(GreekNationalTable.COLUMN_ID, greek_national_questions.get_id());
        cv.put(GreekNationalTable.COLUMN_DISPLAYED, false);
        db.insert(GreekNationalTable.TABLE_NAME, null, cv);
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

    private void addGreekClubs(Questions greek_clubs_questions) {
        ContentValues cv = new ContentValues();
        cv.put(GreekClubsTable.COLUMN_QUESTION, greek_clubs_questions.getQuestion());
        cv.put(GreekClubsTable.COLUMN_ANSWER, greek_clubs_questions.getAnswer());
        cv.put(GreekClubsTable.COLUMN_ID, greek_clubs_questions.get_id());
        cv.put(GreekClubsTable.COLUMN_DISPLAYED, false);
        db.insert(GreekClubsTable.TABLE_NAME, null, cv);
        cv.clear();
    }
//these 8 methods are called in MainGame.fill_arraylist method to return the filled up arrays from each category for both languages
    public List<Questions> getAllNational() {
        ArrayList<Questions> nationalList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                NationalTable.TABLE_NAME,
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
                nationalList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return nationalList;
    }

    public List<Questions> getAllClubs() {
        ArrayList<Questions> clubsList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                ClubsTable.TABLE_NAME,
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
                clubsList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return clubsList;
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
    public List<Questions> getAllGreekNational() {
        ArrayList<Questions> GreekNationalList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                GreekNationalTable.TABLE_NAME,
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
                GreekNationalList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return GreekNationalList;
    }

    public List<Questions> getAllGreekClubs() {
        ArrayList<Questions> GreekClubsList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                GreekClubsTable.TABLE_NAME,
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
                GreekClubsList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return GreekClubsList;
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
                NationalTable.TABLE_NAME,
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
                ClubsTable.TABLE_NAME,
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
                GreekNationalTable.TABLE_NAME,
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
                GreekClubsTable.TABLE_NAME,
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
        db.update(NationalTable.TABLE_NAME, values, null, null);
        db.update(GeneralTable.TABLE_NAME, values, null, null);
        db.update(GeographyTable.TABLE_NAME, values, null, null);
        db.update(ClubsTable.TABLE_NAME, values, null, null);
        db.update(GreekNationalTable.TABLE_NAME, values, null, null);
        db.update(GreekGeneralTable.TABLE_NAME, values, null, null);
        db.update(GreekGeographyTable.TABLE_NAME, values, null, null);
        db.update(GreekClubsTable.TABLE_NAME, values, null, null);
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

    private void fillNationalTable(){

        try {
            InputStream inputStream = context.getAssets().open("NationalTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addNational(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fillClubsTable(){

        try {
            InputStream inputStream = context.getAssets().open("ClubsTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addClubs(q);
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



    private void fillGreekNationalTable(){

        try {
            InputStream inputStream = context.getAssets().open("GreekNationalTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addGreekNational(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fillGreekClubsTable(){

        try {
            InputStream inputStream = context.getAssets().open("GreekClubsTable.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                addGreekClubs(q);
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