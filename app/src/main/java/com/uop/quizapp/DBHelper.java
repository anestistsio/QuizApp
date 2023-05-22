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
    private static final int DATABASE_VERSION = 6;
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
// this method is called in MainActivity to reset the displayed values to false  when the game starts
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


}