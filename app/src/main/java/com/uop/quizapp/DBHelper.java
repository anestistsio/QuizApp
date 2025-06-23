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
        // Drop all existing tables
        db.execSQL("DROP TABLE IF EXISTS " + NationalTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GeneralTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClubsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GeographyTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GreekNationalTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GreekGeneralTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GreekClubsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GreekGeographyTable.TABLE_NAME);

        // Recreate the tables and repopulate them
        onCreate(db);
    }
    // Generic insertion method used by all categories
    private void addQuestionToTable(Questions question, String tableName, String columnQuestion, String columnAnswer, String columnId, String columnDisplayed) {
        ContentValues cv = new ContentValues();
        cv.put(columnQuestion, question.getQuestion());
        cv.put(columnAnswer, question.getAnswer());
        cv.put(columnId, question.get_id());
        cv.put(columnDisplayed, false);
        db.insert(tableName, null, cv);
        cv.clear();
    }

    //these 8 methods are kept for backwards compatibility
    private void addNational(Questions q) {
        addQuestionToTable(q, NationalTable.TABLE_NAME, NationalTable.COLUMN_QUESTION,
                NationalTable.COLUMN_ANSWER, NationalTable.COLUMN_ID, NationalTable.COLUMN_DISPLAYED);
    }

    private void addGeneral(Questions q) {
        addQuestionToTable(q, GeneralTable.TABLE_NAME, GeneralTable.COLUMN_QUESTION,
                GeneralTable.COLUMN_ANSWER, GeneralTable.COLUMN_ID, GeneralTable.COLUMN_DISPLAYED);
    }

    private void addGeography(Questions q) {
        addQuestionToTable(q, GeographyTable.TABLE_NAME, GeographyTable.COLUMN_QUESTION,
                GeographyTable.COLUMN_ANSWER, GeographyTable.COLUMN_ID, GeographyTable.COLUMN_DISPLAYED);
    }

    private void addClubs(Questions q) {
        addQuestionToTable(q, ClubsTable.TABLE_NAME, ClubsTable.COLUMN_QUESTION,
                ClubsTable.COLUMN_ANSWER, ClubsTable.COLUMN_ID, ClubsTable.COLUMN_DISPLAYED);
    }

    private void addGreekNational(Questions q) {
        addQuestionToTable(q, GreekNationalTable.TABLE_NAME, GreekNationalTable.COLUMN_QUESTION,
                GreekNationalTable.COLUMN_ANSWER, GreekNationalTable.COLUMN_ID, GreekNationalTable.COLUMN_DISPLAYED);
    }

    private void addGreekGeneral(Questions q) {
        addQuestionToTable(q, GreekGeneralTable.TABLE_NAME, GreekGeneralTable.COLUMN_QUESTION,
                GreekGeneralTable.COLUMN_ANSWER, GreekGeneralTable.COLUMN_ID, GreekGeneralTable.COLUMN_DISPLAYED);
    }

    private void addGreekGeography(Questions q) {
        addQuestionToTable(q, GreekGeographyTable.TABLE_NAME, GreekGeographyTable.COLUMN_QUESTION,
                GreekGeographyTable.COLUMN_ANSWER, GreekGeographyTable.COLUMN_ID, GreekGeographyTable.COLUMN_DISPLAYED);
    }

    private void addGreekClubs(Questions q) {
        addQuestionToTable(q, GreekClubsTable.TABLE_NAME, GreekClubsTable.COLUMN_QUESTION,
                GreekClubsTable.COLUMN_ANSWER, GreekClubsTable.COLUMN_ID, GreekClubsTable.COLUMN_DISPLAYED);
    }
//these methods return questions for each category
    private List<Questions> getQuestionsFromTable(String tableName) {
        ArrayList<Questions> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                tableName,
                new String[]{"id", "question", "answer", "displayed"},
                "displayed = 0",
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
                list.add(new Questions(id, question, answer, displayed));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Questions> getAllNational() {
        return getQuestionsFromTable(NationalTable.TABLE_NAME);
    }

    public List<Questions> getAllClubs() {
        return getQuestionsFromTable(ClubsTable.TABLE_NAME);
    }

    public List<Questions> getAllGeneral() {
        return getQuestionsFromTable(GeneralTable.TABLE_NAME);
    }

    public List<Questions> getAllGeography() {
        return getQuestionsFromTable(GeographyTable.TABLE_NAME);
    }

    //Greek
    public List<Questions> getAllGreekNational() {
        return getQuestionsFromTable(GreekNationalTable.TABLE_NAME);
    }

    public List<Questions> getAllGreekClubs() {
        return getQuestionsFromTable(GreekClubsTable.TABLE_NAME);
    }

    public List<Questions> getAllGreekGeneral() {
        return getQuestionsFromTable(GreekGeneralTable.TABLE_NAME);
    }

    public List<Questions> getAllGreekGeography() {
        return getQuestionsFromTable(GreekGeographyTable.TABLE_NAME);
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
    // Generic loader used by the individual fill methods
    private void loadFromAsset(String fileName, java.util.function.Consumer<Questions> adder) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String question = parts[1];
                String answer = parts[2];
                boolean displayed = Boolean.parseBoolean(parts[3]);

                Questions q = new Questions(id, question, answer, displayed);
                adder.accept(q);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fill all the tables with questions from txt files
    private void fillGeneralTable(){
        loadFromAsset("GeneralTable.txt", this::addGeneral);
    }

    private void fillNationalTable(){
        loadFromAsset("NationalTable.txt", this::addNational);
    }

    private void fillClubsTable(){
        loadFromAsset("ClubsTable.txt", this::addClubs);
    }

    private void fillGeographyTable(){
        loadFromAsset("GeographyTable.txt", this::addGeography);
    }

    private void fillGreekGeneralTable() {
        loadFromAsset("GreekGeneralTable.txt", this::addGreekGeneral);
    }

    private void fillGreekNationalTable(){
        loadFromAsset("GreekNationalTable.txt", this::addGreekNational);
    }

    private void fillGreekClubsTable(){
        loadFromAsset("GreekClubsTable.txt", this::addGreekClubs);
    }

    private void fillGreekGeographyTable(){
        loadFromAsset("GreekGeographyTable.txt", this::addGreekGeography);
    }


}