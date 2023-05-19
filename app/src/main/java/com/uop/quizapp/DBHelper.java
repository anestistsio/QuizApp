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
    private static final int DATABASE_VERSION = 5;
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
        Questions q2 = new Questions(2, "What is the capital of France?", "Paris", false);
        addGeneral(q2);
        Questions q3 = new Questions(3, "Which planet is known as the Red Planet?", "Mars", false);
        addGeneral(q3);
        Questions q4 = new Questions(4, "What is the largest ocean in the world?", "Pacific Ocean", false);
        addGeneral(q4);
        Questions q5 = new Questions(5, "What is the chemical symbol for gold?", "Au", false);
        addGeneral(q5);
        Questions q6 = new Questions(6, "Who painted the Mona Lisa?", "Leonardo da Vinci", false);
        addGeneral(q6);
        Questions q7 = new Questions(7, "What is the largest land animal?", "African elephant", false);
        addGeneral(q7);
        Questions q8 = new Questions(8, "What is the capital of Spain?", "Madrid", false);
        addGeneral(q8);
        Questions q9 = new Questions(9, "Which country is known as the Land of the Rising Sun?", "Japan", false);
        addGeneral(q9);
        Questions q10 = new Questions(10, "What is the symbol for the element oxygen?", "O", false);
        addGeneral(q10);
        Questions q11 = new Questions(11, "Which country is famous for its tulips and windmills?", "Netherlands", false);
        addGeneral(q11);
        Questions q12 = new Questions(12, "What is the largest planet in our solar system?", "Jupiter", false);
        addGeneral(q12);
        Questions q13 = new Questions(13, "Who wrote the play 'Romeo and Juliet'?", "William Shakespeare", false);
        addGeneral(q13);
        Questions q14 = new Questions(14, "Which country is home to the kangaroo?", "Australia", false);
        addGeneral(q14);
        Questions q15 = new Questions(15, "What is the symbol for the element carbon?", "C", false);
        addGeneral(q15);
        Questions q16 = new Questions(16, "Which city is known as the Big Apple?", "New York City", false);
        addGeneral(q16);
        Questions q17 = new Questions(17, "Who invented the telephone?", "Alexander Graham Bell", false);
        addGeneral(q17);
        Questions q18 = new Questions(18, "What is the currency of Japan?", "Japanese yen", false);
        addGeneral(q18);
        Questions q19 = new Questions(19, "Which country is famous for the Great Wall?", "China", false);
        addGeneral(q19);
        Questions q20 = new Questions(20, "What is the symbol for the element hydrogen?", "H", false);
        addGeneral(q20);
        Questions q21 = new Questions(21, "What is the largest continent?", "Asia", false);
        addGeneral(q21);
        Questions q22 = new Questions(22, "Who painted the ceiling of the Sistine Chapel?", "Michelangelo", false);
        addGeneral(q22);
        Questions q23 = new Questions(23, "Which animal is known as the 'King of the Jungle'?", "Lion", false);
        addGeneral(q23);
        Questions q24 = new Questions(24, "What is the capital of Italy?", "Rome", false);
        addGeneral(q24);
        Questions q25 = new Questions(25, "Which country is known for the pyramids?", "Egypt", false);
        addGeneral(q25);
        Questions q26 = new Questions(26, "What is the symbol for the element helium?", "He", false);
        addGeneral(q26);
        Questions q27 = new Questions(27, "Which city is known as the City of Love?", "Paris", false);
        addGeneral(q27);
        Questions q28 = new Questions(28, "Who discovered gravity?", "Isaac Newton", false);
        addGeneral(q28);
        Questions q29 = new Questions(29, "What is the currency of the United Kingdom?", "British pound", false);
        addGeneral(q29);
        Questions q30 = new Questions(30, "What is the symbol for the element nitrogen?", "N", false);
        addGeneral(q30);
        Questions q31 = new Questions(31, "Which country is famous for the Taj Mahal?", "India", false);
        addGeneral(q31);
        Questions q32 = new Questions(32, "What is the smallest planet in our solar system?", "Mercury", false);
        addGeneral(q32);
        Questions q33 = new Questions(33, "Who wrote the novel 'Pride and Prejudice'?", "Jane Austen", false);
        addGeneral(q33);
        Questions q34 = new Questions(34, "Which country is home to the Great Barrier Reef?", "Australia", false);
        addGeneral(q34);
        Questions q35 = new Questions(35, "What is the symbol for the element calcium?", "Ca", false);
        addGeneral(q35);
        Questions q36 = new Questions(36, "Which city is known as the Windy City?", "Chicago", false);
        addGeneral(q36);
        Questions q37 = new Questions(37, "Who invented the light bulb?", "Thomas Edison", false);
        addGeneral(q37);
        Questions q38 = new Questions(38, "What is the currency of Canada?", "Canadian dollar", false);
        addGeneral(q38);
        Questions q39 = new Questions(39, "Which country is famous for the Colosseum?", "Italy", false);
        addGeneral(q39);
        Questions q40 = new Questions(40, "What is the symbol for the element iron?", "Fe", false);
        addGeneral(q40);
        Questions q41 = new Questions(41, "What is the largest country in South America?", "Brazil", false);
        addGeneral(q41);
        Questions q42 = new Questions(42, "Who painted the Starry Night?", "Vincent van Gogh", false);
        addGeneral(q42);
        Questions q43 = new Questions(43, "Which animal is known as the 'Gentle Giant'?", "Elephant", false);
        addGeneral(q43);
        Questions q44 = new Questions(44, "What is the capital of Australia?", "Canberra", false);
        addGeneral(q44);
        Questions q45 = new Questions(45, "Which country is known for the Statue of Liberty?", "United States", false);
        addGeneral(q45);
        Questions q46 = new Questions(46, "What is the symbol for the element silver?", "Ag", false);
        addGeneral(q46);
        Questions q47 = new Questions(47, "Which city is known as the Eternal City?", "Rome", false);
        addGeneral(q47);
        Questions q48 = new Questions(48, "Who discovered penicillin?", "Alexander Fleming", false);
        addGeneral(q48);
        Questions q49 = new Questions(49, "What is the currency of France?", "Euro", false);
        addGeneral(q49);
        Questions q50 = new Questions(50, "What is the symbol for the element potassium?", "K", false);
        addGeneral(q50);
        Questions q51 = new Questions(51, "Which country is famous for the Eiffel Tower?", "France", false);
        addGeneral(q51);
        Questions q52 = new Questions(52, "What is the largest ocean predator?", "Great white shark", false);
        addGeneral(q52);
        Questions q53 = new Questions(53, "What is the capital of Canada?", "Ottawa", false);
        addGeneral(q53);
        Questions q54 = new Questions(54, "Who wrote the novel 'To Kill a Mockingbird'?", "Harper Lee", false);
        addGeneral(q54);
        Questions q55 = new Questions(55, "Which country is home to the Amazon rainforest?", "Brazil", false);
        addGeneral(q55);
        Questions q56 = new Questions(56, "What is the symbol for the element sodium?", "Na", false);
        addGeneral(q56);
        Questions q57 = new Questions(57, "Which city is known as the City of Lights?", "Paris", false);
        addGeneral(q57);
        Questions q58 = new Questions(58, "Who developed the theory of relativity?", "Albert Einstein", false);
        addGeneral(q58);
        Questions q59 = new Questions(59, "What is the currency of Germany?", "Euro", false);
        addGeneral(q59);
        Questions q60 = new Questions(60, "What is the symbol for the element uranium?", "U", false);
        addGeneral(q60);
        Questions q61 = new Questions(61, "Which country is famous for the Acropolis?", "Greece", false);
        addGeneral(q61);
        Questions q62 = new Questions(62, "What is the largest bird in the world?", "Ostrich", false);
        addGeneral(q62);
        Questions q63 = new Questions(63, "What is the capital of Brazil?", "Brasília", false);
        addGeneral(q63);
        Questions q64 = new Questions(64, "Which country is known for the Niagara Falls?", "Canada", false);
        addGeneral(q64);
        Questions q65 = new Questions(65, "What is the symbol for the element gold?", "Au", false);
        addGeneral(q65);
        Questions q66 = new Questions(66, "Which city is known as the City of Brotherly Love?", "Philadelphia", false);
        addGeneral(q66);
        Questions q67 = new Questions(67, "Who invented the theory of evolution?", "Charles Darwin", false);
        addGeneral(q67);
        Questions q68 = new Questions(68, "What is the currency of China?", "Renminbi", false);
        addGeneral(q68);
        Questions q69 = new Questions(69, "Which country is famous for the Christ the Redeemer statue?", "Brazil", false);
        addGeneral(q69);
        Questions q70 = new Questions(70, "What is the symbol for the element mercury?", "Hg", false);
        addGeneral(q70);
        Questions q71 = new Questions(71, "What is the largest country in Africa?", "Algeria", false);
        addGeneral(q71);
        Questions q72 = new Questions(72, "Who painted the Last Supper?", "Leonardo da Vinci", false);
        addGeneral(q72);
        Questions q73 = new Questions(73, "Which animal is known as the 'Ship of the Desert'?", "Camel", false);
        addGeneral(q73);
        Questions q74 = new Questions(74, "What is the capital of Russia?", "Moscow", false);
        addGeneral(q74);
        Questions q75 = new Questions(75, "Which country is known for the Angkor Wat temple?", "Cambodia", false);
        addGeneral(q75);
        Questions q76 = new Questions(76, "What is the symbol for the element lead?", "Pb", false);
        addGeneral(q76);
        Questions q77 = new Questions(77, "Which city is known as the City of Canals?", "Venice", false);
        addGeneral(q77);
        Questions q78 = new Questions(78, "Who discovered the theory of gravity?", "Isaac Newton", false);
        addGeneral(q78);
        Questions q79 = new Questions(79, "What is the currency of Brazil?", "Brazilian real", false);
        addGeneral(q79);
        Questions q80 = new Questions(80, "What is the symbol for the element tin?", "Sn", false);
        addGeneral(q80);
        Questions q81 = new Questions(81, "Which country is famous for the Amazon River?", "Brazil", false);
        addGeneral(q81);
        Questions q82 = new Questions(82, "What is the tallest mountain in the world?", "Mount Everest", false);
        addGeneral(q82);
        Questions q83 = new Questions(83, "Who wrote the novel '1984'?", "George Orwell", false);
        addGeneral(q83);
        Questions q84 = new Questions(84, "Which country is home to the kiwi bird?", "New Zealand", false);
        addGeneral(q84);
        Questions q85 = new Questions(85, "What is the symbol for the element phosphorus?", "P", false);
        addGeneral(q85);
        Questions q86 = new Questions(86, "Which city is known as the City of Music?", "Vienna", false);
        addGeneral(q86);
        Questions q87 = new Questions(87, "Who developed the theory of relativity?", "Albert Einstein", false);
        addGeneral(q87);
        Questions q88 = new Questions(88, "What is the currency of India?", "Indian rupee", false);
        addGeneral(q88);
        Questions q89 = new Questions(89, "Which country is famous for the Hagia Sophia?", "Turkey", false);
        addGeneral(q89);
        Questions q90 = new Questions(90, "What is the symbol for the element silver?", "Ag", false);
        addGeneral(q90);
        Questions q91 = new Questions(91, "Which country is the largest producer of coffee?", "Brazil", false);
        addGeneral(q91);
        Questions q92 = new Questions(92, "What is the capital of Germany?", "Berlin", false);
        addGeneral(q92);
        Questions q93 = new Questions(93, "Who wrote the novel 'The Great Gatsby'?", "F. Scott Fitzgerald", false);
        addGeneral(q93);
        Questions q94 = new Questions(94, "Which country is home to the Great Wall of China?", "China", false);
        addGeneral(q94);
        Questions q95 = new Questions(95, "What is the symbol for the element copper?", "Cu", false);
        addGeneral(q95);
        Questions q96 = new Questions(96, "Which city is known as the City of Dreams?", "Mumbai", false);
        addGeneral(q96);
        Questions q97 = new Questions(97, "Who discovered the theory of general relativity?", "Albert Einstein", false);
        addGeneral(q97);
        Questions q98 = new Questions(98, "What is the currency of Mexico?", "Mexican peso", false);
        addGeneral(q98);
        Questions q99 = new Questions(99, "Which country is famous for the Great Sphinx?", "Egypt", false);
        addGeneral(q99);
        Questions q100 = new Questions(100, "What is the symbol for the element zinc?", "Zn", false);
        addGeneral(q100);
    }

    private void fillScienceTable(){

        Questions q1001 = new Questions(1001, "What is the largest organ in the human body?", "Skin", false);
        addScience(q1001);
        Questions q1002 = new Questions(1002, "What is the primary function of the mitochondria?", "To produce energy (ATP)", false);
        addScience(q1002);
        Questions q1003 = new Questions(1003, "What is the chemical symbol for water?", "H2O", false);
        addScience(q1003);
        Questions q1004 = new Questions(1004, "What is the smallest unit of matter?", "Atom", false);
        addScience(q1004);
        Questions q1005 = new Questions(1005, "What is the largest planet in our solar system?", "Jupiter", false);
        addScience(q1005);
        Questions q1006 = new Questions(1006, "What is the process by which plants convert sunlight into energy?", "Photosynthesis", false);
        addScience(q1006);
        Questions q1007 = new Questions(1007, "What is the primary gas that makes up the Earth's atmosphere?", "Nitrogen", false);
        addScience(q1007);
        Questions q1008 = new Questions(1008, "What is the study of heredity and variation in organisms?", "Genetics", false);
        addScience(q1008);
        Questions q1009 = new Questions(1009, "What is the largest bone in the human body?", "Femur", false);
        addScience(q1009);
        Questions q1010 = new Questions(1010, "What is the basic unit of life?", "Cell", false);
        addScience(q1010);
        Questions q1011 = new Questions(1011, "What is the process by which plants release water vapor into the air?", "Transpiration", false);
        addScience(q1011);
        Questions q1012 = new Questions(1012, "What is the chemical formula for table salt?", "NaCl", false);
        addScience(q1012);
        Questions q1013 = new Questions(1013, "What is the study of the Earth's physical structure and substance?", "Geology", false);
        addScience(q1013);
        Questions q1014 = new Questions(1014, "What is the process by which ice turns into water vapor without melting?", "Sublimation", false);
        addScience(q1014);
        Questions q1015 = new Questions(1015, "What is the force that opposes the motion of objects through a fluid?", "Drag", false);
        addScience(q1015);
        Questions q1016 = new Questions(1016, "What is the process by which an organism changes over time to better suit its environment?", "Adaptation", false);
        addScience(q1016);
        Questions q1017 = new Questions(1017, "What is the study of the interaction between organisms and their environment?", "Ecology", false);
        addScience(q1017);
        Questions q1018 = new Questions(1018, "What is the unit of measurement for electrical resistance?", "Ohm", false);
        addScience(q1018);
        Questions q1019 = new Questions(1019, "What is the process by which plants and some other organisms convert sunlight into chemical energy?", "Photosynthesis", false);
        addScience(q1019);
        Questions q1020 = new Questions(1020, "What is the largest organ inside the human body?", "Liver", false);
        addScience(q1020);
        Questions q1021 = new Questions(1021, "What is the study of the origin, structure, and development of the universe?", "Cosmology", false);
        addScience(q1021);
        Questions q1022 = new Questions(1022, "What is the process by which an animal sheds its outer layer of skin?", "Molting", false);
        addScience(q1022);
        Questions q1023 = new Questions(1023, "What is the fundamental particle that carries a positive electrical charge?", "Proton", false);
        addScience(q1023);
        Questions q1024 = new Questions(1024, "What is the study of the motion of objects and the forces acting on them?", "Physics", false);
        addScience(q1024);
        Questions q1025 = new Questions(1025, "What is the primary pigment responsible for capturing light energy in plants?", "Chlorophyll", false);
        addScience(q1025);
        Questions q1026 = new Questions(1026, "What is the process by which an organism breaks down food to release energy?", "Cellular respiration", false);
        addScience(q1026);
        Questions q1027 = new Questions(1027, "What is the force that attracts objects toward the center of the Earth?", "Gravity", false);
        addScience(q1027);
        Questions q1028 = new Questions(1028, "What is the study of the structure and function of living organisms?", "Biology", false);
        addScience(q1028);
        Questions q1029 = new Questions(1029, "What is the process by which liquid water changes into a gas?", "Evaporation", false);
        addScience(q1029);
        Questions q1030 = new Questions(1030, "What is the layer of the Earth's atmosphere where weather occurs?", "Troposphere", false);
        addScience(q1030);
        Questions q1031 = new Questions(1031, "What is the process by which an organism produces offspring similar to itself?", "Reproduction", false);
        addScience(q1031);
        Questions q1032 = new Questions(1032, "What is the primary gas that makes up the Earth's atmosphere?", "Nitrogen", false);
        addScience(q1032);
        Questions q1033 = new Questions(1033, "What is the study of the composition, structure, properties, and reactions of matter?", "Chemistry", false);
        addScience(q1033);
        Questions q1034 = new Questions(1034, "What is the process by which plants take in carbon dioxide and release oxygen?", "Photosynthesis", false);
        addScience(q1034);
        Questions q1035 = new Questions(1035, "What is the body's first line of defense against pathogens?", "Immune system", false);
        addScience(q1035);
        Questions q1036 = new Questions(1036, "What is the branch of science that deals with the study of the Earth's atmosphere?", "Meteorology", false);
        addScience(q1036);
        Questions q1037 = new Questions(1037, "What is the process by which rocks, minerals, and soil are carried away by wind, water, or ice?", "Erosion", false);
        addScience(q1037);
        Questions q1038 = new Questions(1038, "What is the unit of measurement for electric current?", "Ampere", false);
        addScience(q1038);
        Questions q1039 = new Questions(1039, "What is the process by which an organism obtains and uses food for energy?", "Nutrition", false);
        addScience(q1039);
        Questions q1040 = new Questions(1040, "What is the layer of the Earth's atmosphere where the ozone layer is located?", "Stratosphere", false);
        addScience(q1040);
        Questions q1041 = new Questions(1041, "What is the study of the properties and behavior of matter and energy?", "Physics", false);
        addScience(q1041);
        Questions q1042 = new Questions(1042, "What is the process by which an organism's body changes during its lifetime?", "Development", false);
        addScience(q1042);
        Questions q1043 = new Questions(1043, "What is the process by which an organism's body maintains a stable internal environment?", "Homeostasis", false);
        addScience(q1043);
        Questions q1044 = new Questions(1044, "What is the unit of measurement for frequency?", "Hertz", false);
        addScience(q1044);
        Questions q1045 = new Questions(1045, "What is the study of the Earth's physical features, climate, and weather patterns?", "Geography", false);
        addScience(q1045);
        Questions q1046 = new Questions(1046, "What is the process by which an organism undergoes a series of changes in form during its life cycle?", "Metamorphosis", false);
        addScience(q1046);
        Questions q1047 = new Questions(1047, "What is the process by which an organism breaks down food and absorbs nutrients?", "Digestion", false);
        addScience(q1047);
        Questions q1048 = new Questions(1048, "What is the unit of measurement for electric potential difference?", "Volt", false);
        addScience(q1048);
        Questions q1049 = new Questions(1049, "What is the process by which organisms convert oxygen and glucose into carbon dioxide and water to release energy?", "Cellular respiration", false);
        addScience(q1049);
        Questions q1050 = new Questions(1050, "What is the layer of the Earth's atmosphere that protects against harmful ultraviolet radiation?", "Ozone layer", false);
        addScience(q1050);
        Questions q1051 = new Questions(1051, "What is the study of the Earth's physical features, climate, and weather patterns?", "Geography", false);
        addScience(q1051);
        Questions q1052 = new Questions(1052, "What is the process by which an organism changes its physical or behavioral characteristics to survive in its environment?", "Adaptation", false);
        addScience(q1052);
        Questions q1053 = new Questions(1053, "What is the smallest unit of life?", "Cell", false);
        addScience(q1053);
        Questions q1054 = new Questions(1054, "What is the process by which an organism's body repairs and replaces damaged cells?", "Regeneration", false);
        addScience(q1054);
        Questions q1055 = new Questions(1055, "What is the unit of measurement for temperature?", "Celsius", false);
        addScience(q1055);
        Questions q1056 = new Questions(1056, "What is the study of the Earth's physical structure and substance?", "Geology", false);
        addScience(q1056);
        Questions q1057 = new Questions(1057, "What is the process by which an organism converts food into usable energy?", "Metabolism", false);
        addScience(q1057);
        Questions q1058 = new Questions(1058, "What is the process by which an organism produces offspring similar to itself?", "Reproduction", false);
        addScience(q1058);
        Questions q1059 = new Questions(1059, "What is the unit of measurement for force?", "Newton", false);
        addScience(q1059);
        Questions q1060 = new Questions(1060, "What is the layer of the Earth's atmosphere where meteors burn up upon entry?", "Mesosphere", false);
        addScience(q1060);
        Questions q1061 = new Questions(1061, "What is the study of the structure and function of the nervous system?", "Neuroscience", false);
        addScience(q1061);
        Questions q1062 = new Questions(1062, "What is the process by which an organism converts sunlight into chemical energy?", "Photosynthesis", false);
        addScience(q1062);
        Questions q1063 = new Questions(1063, "What is the force that holds atoms together in a molecule?", "Chemical bond", false);
        addScience(q1063);
        Questions q1064 = new Questions(1064, "What is the study of the interactions between living organisms and their environment?", "Ecology", false);
        addScience(q1064);
        Questions q1065 = new Questions(1065, "What is the process by which plants take in carbon dioxide and release oxygen?", "Photosynthesis", false);
        addScience(q1065);
        Questions q1066 = new Questions(1066, "What is the body's defense system against pathogens and foreign substances?", "Immune system", false);
        addScience(q1066);
        Questions q1067 = new Questions(1067, "What is the branch of science that deals with the study of the Earth's atmosphere?", "Meteorology", false);
        addScience(q1067);
        Questions q1068 = new Questions(1068, "What is the process by which rocks, minerals, and soil are carried away by wind, water, or ice?", "Erosion", false);
        addScience(q1068);
        Questions q1069 = new Questions(1069, "What is the unit of measurement for electric current?", "Ampere", false);
        addScience(q1069);
        Questions q1070 = new Questions(1070, "What is the process by which an organism obtains and uses food for energy?", "Nutrition", false);
        addScience(q1070);
        Questions q1071 = new Questions(1071, "What is the layer of the Earth's atmosphere where the ozone layer is located?", "Stratosphere", false);
        addScience(q1071);
        Questions q1072 = new Questions(1072, "What is the study of the properties and behavior of matter and energy?", "Physics", false);
        addScience(q1072);
        Questions q1073 = new Questions(1073, "What is the process by which an organism's body changes during its lifetime?", "Development", false);
        addScience(q1073);
        Questions q1074 = new Questions(1074, "What is the process by which an organism's body maintains a stable internal environment?", "Homeostasis", false);
        addScience(q1074);
        Questions q1075 = new Questions(1075, "What is the unit of measurement for frequency?", "Hertz", false);
        addScience(q1075);
        Questions q1076 = new Questions(1076, "What is the study of the composition, structure, properties, and reactions of matter?", "Chemistry", false);
        addScience(q1076);
        Questions q1077 = new Questions(1077, "What is the process by which plants take in carbon dioxide and release oxygen?", "Photosynthesis", false);
        addScience(q1077);
        Questions q1078 = new Questions(1078, "What is the body's first line of defense against pathogens?", "Immune system", false);
        addScience(q1078);
        Questions q1079 = new Questions(1079, "What is the branch of science that deals with the study of the Earth's atmosphere?", "Meteorology", false);
        addScience(q1079);
        Questions q1080 = new Questions(1080, "What is the process by which rocks, minerals, and soil are carried away by wind, water, or ice?", "Erosion", false);
        addScience(q1080);
        Questions q1081 = new Questions(1081, "What is the unit of measurement for electric current?", "Ampere", false);
        addScience(q1081);
        Questions q1082 = new Questions(1082, "What is the process by which an organism obtains and uses food for energy?", "Nutrition", false);
        addScience(q1082);
        Questions q1083 = new Questions(1083, "What is the layer of the Earth's atmosphere where the ozone layer is located?", "Stratosphere", false);
        addScience(q1083);
        Questions q1084 = new Questions(1084, "What is the study of the properties and behavior of matter and energy?", "Physics", false);
        addScience(q1084);
        Questions q1085 = new Questions(1085, "What is the process by which an organism's body changes during its lifetime?", "Development", false);
        addScience(q1085);
        Questions q1086 = new Questions(1086, "What is the process by which an organism's body maintains a stable internal environment?", "Homeostasis", false);
        addScience(q1086);
        Questions q1087 = new Questions(1087, "What is the unit of measurement for frequency?", "Hertz", false);
        addScience(q1087);
        Questions q1088 = new Questions(1088, "What is the study of the composition, structure, properties, and reactions of matter?", "Chemistry", false);
        addScience(q1088);
        Questions q1089 = new Questions(1089, "What is the process by which plants take in carbon dioxide and release oxygen?", "Photosynthesis", false);
        addScience(q1089);
        Questions q1090 = new Questions(1090, "What is the body's first line of defense against pathogens?", "Immune system", false);
        addScience(q1090);
        Questions q1091 = new Questions(1091, "What is the branch of science that deals with the study of the Earth's atmosphere?", "Meteorology", false);
        addScience(q1091);
        Questions q1092 = new Questions(1092, "What is the process by which rocks, minerals, and soil are carried away by wind, water, or ice?", "Erosion", false);
        addScience(q1092);
        Questions q1093 = new Questions(1093, "What is the unit of measurement for electric current?", "Ampere", false);
        addScience(q1093);
        Questions q1094 = new Questions(1094, "What is the process by which an organism obtains and uses food for energy?", "Nutrition", false);
        addScience(q1094);
        Questions q1095 = new Questions(1095, "What is the layer of the Earth's atmosphere where the ozone layer is located?", "Stratosphere", false);
        addScience(q1095);
        Questions q1096 = new Questions(1096, "What is the study of the properties and behavior of matter and energy?", "Physics", false);
        addScience(q1096);
        Questions q1097 = new Questions(1097, "What is the process by which an organism's body changes during its lifetime?", "Development", false);
        addScience(q1097);
        Questions q1098 = new Questions(1098, "What is the process by which an organism's body maintains a stable internal environment?", "Homeostasis", false);
        addScience(q1098);
        Questions q1099 = new Questions(1099, "What is the unit of measurement for frequency?", "Hertz", false);
        addScience(q1099);
        Questions q1100 = new Questions(1100, "What is the study of the composition, structure, properties, and reactions of matter?", "Chemistry", false);
        addScience(q1100);

    }

    private void fillSportsTable(){

        Questions q2001 = new Questions(2001, "Which country has won the most Olympic gold medals?", "United States", false);
        addSports(q2001);
        Questions q2002 = new Questions(2002, "In which sport can you score a 'hole in one'?", "Golf", false);
        addSports(q2002);
        Questions q2003 = new Questions(2003, "What is the national sport of Canada?", "Lacrosse", false);
        addSports(q2003);
        Questions q2004 = new Questions(2004, "Which sport uses a shuttlecock?", "Badminton", false);
        addSports(q2004);
        Questions q2005 = new Questions(2005, "Which country won the first FIFA World Cup in 1930?", "Uruguay", false);
        addSports(q2005);
        Questions q2006 = new Questions(2006, "How many players are there on a baseball team?", "9", false);
        addSports(q2006);
        Questions q2007 = new Questions(2007, "Who holds the record for the most home runs in a single MLB season?", "Barry Bonds", false);
        addSports(q2007);
        Questions q2008 = new Questions(2008, "What is the highest possible score in a single game of ten-pin bowling?", "300", false);
        addSports(q2008);
        Questions q2009 = new Questions(2009, "Which country is known for inventing the sport of basketball?", "United States", false);
        addSports(q2009);
        Questions q2010 = new Questions(2010, "What is the diameter of a basketball hoop in inches?", "18 inches", false);
        addSports(q2010);
        Questions q2011 = new Questions(2011, "Which tennis player has won the most Grand Slam titles?", "Roger Federer", false);
        addSports(q2011);
        Questions q2012 = new Questions(2012, "What is the nickname of the New York Yankees in Major League Baseball?", "The Bronx Bombers", false);
        addSports(q2012);
        Questions q2013 = new Questions(2013, "In what year did the first modern Olympic Games take place?", "1896", false);
        addSports(q2013);
        Questions q2014 = new Questions(2014, "Which athlete has the nickname 'The Greatest' and is known for his boxing career?", "Muhammad Ali", false);
        addSports(q2014);
        Questions q2015 = new Questions(2015, "What is the name of the trophy awarded to the winner of the FIFA World Cup?", "The Jules Rimet Trophy", false);
        addSports(q2015);
        Questions q2016 = new Questions(2016, "Who holds the record for the most goals scored in a single season in the NHL?", "Wayne Gretzky", false);
        addSports(q2016);
        Questions q2017 = new Questions(2017, "What is the distance of a marathon race in kilometers?", "42.195 kilometers", false);
        addSports(q2017);
        Questions q2018 = new Questions(2018, "Which country has won the most FIFA World Cup titles?", "Brazil", false);
        addSports(q2018);
        Questions q2019 = new Questions(2019, "Who is the all-time leading scorer in NBA history?", "Kareem Abdul-Jabbar", false);
        addSports(q2019);
        Questions q2020 = new Questions(2020, "Which country is the birthplace of the sport of rugby?", "England", false);
        addSports(q2020);
        Questions q2021 = new Questions(2021, "Who is the most decorated Olympian of all time?", "Michael Phelps", false);
        addSports(q2021);
        Questions q2022 = new Questions(2022, "What is the diameter of a standard ice hockey puck?", "3 inches", false);
        addSports(q2022);
        Questions q2023 = new Questions(2023, "Which country has won the most FIFA Women's World Cup titles?", "United States", false);
        addSports(q2023);
        Questions q2024 = new Questions(2024, "Who holds the record for the most home runs in a single season in MLB?", "Barry Bonds", false);
        addSports(q2024);
        Questions q2025 = new Questions(2025, "What is the highest possible break in snooker?", "147", false);
        addSports(q2025);
        Questions q2026 = new Questions(2026, "Which country won the UEFA Euro 2020 tournament?", "Italy", false);
        addSports(q2026);
        Questions q2027 = new Questions(2027, "What is the height of an Olympic diving platform in meters?", "10 meters", false);
        addSports(q2027);
        Questions q2028 = new Questions(2028, "Who holds the record for the most goals scored in a single season in La Liga?", "Lionel Messi", false);
        addSports(q2028);
        Questions q2029 = new Questions(2029, "In which country were the first modern Olympic Games held?", "Greece", false);
        addSports(q2029);
        Questions q2030 = new Questions(2030, "What is the official ball used in the sport of basketball called?", "Spalding", false);
        addSports(q2030);
        Questions q2031 = new Questions(2031, "Which tennis player has won the most Wimbledon titles?", "Roger Federer", false);
        addSports(q2031);
        Questions q2032 = new Questions(2032, "Who is the all-time leading scorer in the history of the NBA?", "Kareem Abdul-Jabbar", false);
        addSports(q2032);
        Questions q2033 = new Questions(2033, "What is the diameter of a standard basketball hoop in inches?", "18 inches", false);
        addSports(q2033);
        Questions q2034 = new Questions(2034, "Which country won the UEFA Euro 2016 tournament?", "Portugal", false);
        addSports(q2034);
        Questions q2035 = new Questions(2035, "Who holds the record for the most home runs in a single MLB season?", "Barry Bonds", false);
        addSports(q2035);
        Questions q2036 = new Questions(2036, "What is the highest possible break in snooker?", "147", false);
        addSports(q2036);
        Questions q2037 = new Questions(2037, "Which country won the FIFA Women's World Cup in 2019?", "United States", false);
        addSports(q2037);
        Questions q2038 = new Questions(2038, "Who holds the record for the most goals scored in a single season in the Premier League?", "Alan Shearer", false);
        addSports(q2038);
        Questions q2039 = new Questions(2039, "What is the diameter of a standard soccer ball in inches?", "8.65-8.85 inches", false);
        addSports(q2039);
        Questions q2040 = new Questions(2040, "Which country won the Copa America 2021 tournament?", "Argentina", false);
        addSports(q2040);
        Questions q2041 = new Questions(2041, "Who is the all-time leading scorer in the history of the NHL?", "Wayne Gretzky", false);
        addSports(q2041);
        Questions q2042 = new Questions(2042, "What is the height of the net in a game of tennis?", "3 feet", false);
        addSports(q2042);
        Questions q2043 = new Questions(2043, "Which country has won the most Rugby World Cup titles?", "New Zealand", false);
        addSports(q2043);
        Questions q2044 = new Questions(2044, "Who is the most decorated Olympian of all time?", "Michael Phelps", false);
        addSports(q2044);
        Questions q2045 = new Questions(2045, "What is the weight of a standard shot put for men in kilograms?", "7.26 kilograms", false);
        addSports(q2045);
        Questions q2046 = new Questions(2046, "Which country has won the most Davis Cup titles in tennis?", "United States", false);
        addSports(q2046);
        Questions q2047 = new Questions(2047, "What is the official ball used in the sport of cricket called?", "Cricket ball", false);
        addSports(q2047);
        Questions q2048 = new Questions(2048, "Who is the all-time leading scorer in the history of the NBA?", "Kareem Abdul-Jabbar", false);
        addSports(q2048);
        Questions q2049 = new Questions(2049, "In which country were the first modern Olympic Games held?", "Greece", false);
        addSports(q2049);
        Questions q2050 = new Questions(2050, "What is the official ball used in the sport of basketball called?", "Spalding", false);
        addSports(q2050);
        Questions q2051 = new Questions(2051, "Which tennis player has won the most Wimbledon titles?", "Roger Federer", false);
        addSports(q2051);
        Questions q2052 = new Questions(2052, "Who is the all-time leading scorer in the history of the NBA?", "Kareem Abdul-Jabbar", false);
        addSports(q2052);
        Questions q2053 = new Questions(2053, "What is the diameter of a standard basketball hoop in inches?", "18 inches", false);
        addSports(q2053);
        Questions q2054 = new Questions(2054, "Which country won the UEFA Euro 2016 tournament?", "Portugal", false);
        addSports(q2054);
        Questions q2055 = new Questions(2055, "Who holds the record for the most home runs in a single MLB season?", "Barry Bonds", false);
        addSports(q2055);
        Questions q2056 = new Questions(2056, "What is the highest possible break in snooker?", "147", false);
        addSports(q2056);
        Questions q2057 = new Questions(2057, "Which country won the FIFA Women's World Cup in 2019?", "United States", false);
        addSports(q2057);
        Questions q2058 = new Questions(2058, "Who holds the record for the most goals scored in a single season in the Premier League?", "Alan Shearer", false);
        addSports(q2058);
        Questions q2059 = new Questions(2059, "What is the diameter of a standard soccer ball in inches?", "8.65-8.85 inches", false);
        addSports(q2059);
        Questions q2060 = new Questions(2060, "Which country won the Copa America 2021 tournament?", "Argentina", false);
        addSports(q2060);
        Questions q2061 = new Questions(2061, "Who is the all-time leading scorer in the history of the NHL?", "Wayne Gretzky", false);
        addSports(q2061);
        Questions q2062 = new Questions(2062, "What is the height of the net in a game of tennis?", "3 feet", false);
        addSports(q2062);
        Questions q2063 = new Questions(2063, "Which country has won the most Rugby World Cup titles?", "New Zealand", false);
        addSports(q2063);
        Questions q2064 = new Questions(2064, "Who is the most decorated Olympian of all time?", "Michael Phelps", false);
        addSports(q2064);
        Questions q2065 = new Questions(2065, "What is the weight of a standard shot put for men in kilograms?", "7.26 kilograms", false);
        addSports(q2065);
        Questions q2066 = new Questions(2066, "Which country has won the most Davis Cup titles in tennis?", "United States", false);
        addSports(q2066);
        Questions q2067 = new Questions(2067, "What is the official ball used in the sport of cricket called?", "Cricket ball", false);
        addSports(q2067);
        Questions q2068 = new Questions(2068, "Who is the all-time leading scorer in the history of the NBA?", "Kareem Abdul-Jabbar", false);
        addSports(q2068);
        Questions q2069 = new Questions(2069, "In which country were the first modern Olympic Games held?", "Greece", false);
        addSports(q2069);
        Questions q2070 = new Questions(2070, "What is the official ball used in the sport of basketball called?", "Spalding", false);
        addSports(q2070);
        Questions q2071 = new Questions(2071, "Which tennis player has won the most Wimbledon titles?", "Roger Federer", false);
        addSports(q2071);
        Questions q2072 = new Questions(2072, "Who is the all-time leading scorer in the history of the NBA?", "Kareem Abdul-Jabbar", false);
        addSports(q2072);
        Questions q2073 = new Questions(2073, "What is the diameter of a standard basketball hoop in inches?", "18 inches", false);
        addSports(q2073);
        Questions q2074 = new Questions(2074, "Which country won the UEFA Euro 2016 tournament?", "Portugal", false);
        addSports(q2074);
        Questions q2075 = new Questions(2075, "Who holds the record for the most home runs in a single MLB season?", "Barry Bonds", false);
        addSports(q2075);
        Questions q2076 = new Questions(2076, "What is the highest possible break in snooker?", "147", false);
        addSports(q2076);
        Questions q2077 = new Questions(2077, "Which country won the FIFA Women's World Cup in 2019?", "United States", false);
        addSports(q2077);
        Questions q2078 = new Questions(2078, "Who holds the record for the most goals scored in a single season in the Premier League?", "Alan Shearer", false);
        addSports(q2078);
        Questions q2079 = new Questions(2079, "What is the diameter of a standard soccer ball in inches?", "8.65-8.85 inches", false);
        addSports(q2079);
        Questions q2080 = new Questions(2080, "Which country won the Copa America 2021 tournament?", "Argentina", false);
        addSports(q2080);
        Questions q2081 = new Questions(2081, "Who is the all-time leading scorer in the history of the NHL?", "Wayne Gretzky", false);
        addSports(q2081);
        Questions q2082 = new Questions(2082, "What is the height of the net in a game of tennis?", "3 feet", false);
        addSports(q2082);
        Questions q2083 = new Questions(2083, "Which country has won the most Rugby World Cup titles?", "New Zealand", false);
        addSports(q2083);
        Questions q2084 = new Questions(2084, "Who is the most decorated Olympian of all time?", "Michael Phelps", false);
        addSports(q2084);
        Questions q2085 = new Questions(2085, "What is the weight of a standard shot put for men in kilograms?", "7.26 kilograms", false);
        addSports(q2085);
        Questions q2086 = new Questions(2086, "Which country has won the most Davis Cup titles in tennis?", "United States", false);
        addSports(q2086);
        Questions q2087 = new Questions(2087, "What is the official ball used in the sport of cricket called?", "Cricket ball", false);
        addSports(q2087);
        Questions q2088 = new Questions(2088, "Who is the all-time leading scorer in the history of the NBA?", "Kareem Abdul-Jabbar", false);
        addSports(q2088);
        Questions q2089 = new Questions(2089, "In which country were the first modern Olympic Games held?", "Greece", false);
        addSports(q2089);
        Questions q2090 = new Questions(2090, "What is the official ball used in the sport of basketball called?", "Spalding", false);
        addSports(q2090);
        Questions q2091 = new Questions(2091, "Which tennis player has won the most Wimbledon titles?", "Roger Federer", false);
        addSports(q2091);
        Questions q2092 = new Questions(2092, "Who is the all-time leading scorer in the history of the NBA?", "Kareem Abdul-Jabbar", false);
        addSports(q2092);
        Questions q2093 = new Questions(2093, "What is the diameter of a standard basketball hoop in inches?", "18 inches", false);
        addSports(q2093);
        Questions q2094 = new Questions(2094, "Which country won the UEFA Euro 2016 tournament?", "Portugal", false);
        addSports(q2094);
        Questions q2095 = new Questions(2095, "Who holds the record for the most home runs in a single MLB season?", "Barry Bonds", false);
        addSports(q2095);
        Questions q2096 = new Questions(2096, "What is the highest possible break in snooker?", "147", false);
        addSports(q2096);
        Questions q2097 = new Questions(2097, "Which country won the FIFA Women's World Cup in 2019?", "United States", false);
        addSports(q2097);
        Questions q2098 = new Questions(2098, "Who holds the record for the most goals scored in a single season in the Premier League?", "Alan Shearer", false);
        addSports(q2098);
        Questions q2099 = new Questions(2099, "What is the diameter of a standard soccer ball in inches?", "8.65-8.85 inches", false);
        addSports(q2099);
        Questions q2100 = new Questions(2100, "Which country won the Copa America 2021 tournament?", "Argentina", false);
        addSports(q2100);

    }
    private void fillGeographyTable(){

        Questions q3001 = new Questions(3001, "What is the smallest country in the world?", "Vatican City", false);
        addGeography(q3001);
        Questions q3002 = new Questions(3002, "What is the largest ocean in the world?", "Pacific Ocean", false);
        addGeography(q3002);
        Questions q3003 = new Questions(3003, "What is the capital city of France?", "Paris", false);
        addGeography(q3003);
        Questions q3004 = new Questions(3004, "What is the longest river in the world?", "Nile River", false);
        addGeography(q3004);
        Questions q3005 = new Questions(3005, "What is the highest mountain in the world?", "Mount Everest", false);
        addGeography(q3005);
        Questions q3006 = new Questions(3006, "What is the capital city of Australia?", "Canberra", false);
        addGeography(q3006);
        Questions q3007 = new Questions(3007, "What is the largest desert in the world?", "Sahara Desert", false);
        addGeography(q3007);
        Questions q3008 = new Questions(3008, "What is the capital city of Canada?", "Ottawa", false);
        addGeography(q3008);
        Questions q3009 = new Questions(3009, "What is the highest waterfall in the world?", "Angel Falls", false);
        addGeography(q3009);
        Questions q3010 = new Questions(3010, "What is the capital city of Japan?", "Tokyo", false);
        addGeography(q3010);
        Questions q3011 = new Questions(3011, "What is the largest country in the world by land area?", "Russia", false);
        addGeography(q3011);
        Questions q3012 = new Questions(3012, "What is the capital city of Brazil?", "Brasília", false);
        addGeography(q3012);
        Questions q3013 = new Questions(3013, "What is the largest lake in Africa?", "Lake Victoria", false);
        addGeography(q3013);
        Questions q3014 = new Questions(3014, "What is the capital city of Egypt?", "Cairo", false);
        addGeography(q3014);
        Questions q3015 = new Questions(3015, "What is the highest capital city in the world?", "La Paz, Bolivia", false);
        addGeography(q3015);
        Questions q3016 = new Questions(3016, "What is the largest island in the world?", "Greenland", false);
        addGeography(q3016);
        Questions q3017 = new Questions(3017, "What is the capital city of South Africa?", "Pretoria", false);
        addGeography(q3017);
        Questions q3018 = new Questions(3018, "What is the largest country in South America?", "Brazil", false);
        addGeography(q3018);
        Questions q3019 = new Questions(3019, "What is the capital city of Italy?", "Rome", false);
        addGeography(q3019);
        Questions q3020 = new Questions(3020, "What is the largest city in the United States?", "New York City", false);
        addGeography(q3020);
        Questions q3021 = new Questions(3021, "What is the capital city of China?", "Beijing", false);
        addGeography(q3021);
        Questions q3022 = new Questions(3022, "What is the largest continent in the world?", "Asia", false);
        addGeography(q3022);
        Questions q3023 = new Questions(3023, "What is the capital city of Argentina?", "Buenos Aires", false);
        addGeography(q3023);
        Questions q3024 = new Questions(3024, "What is the deepest ocean in the world?", "Pacific Ocean", false);
        addGeography(q3024);
        Questions q3025 = new Questions(3025, "What is the capital city of Spain?", "Madrid", false);
        addGeography(q3025);
        Questions q3026 = new Questions(3026, "What is the largest country in Europe?", "Russia", false);
        addGeography(q3026);
        Questions q3027 = new Questions(3027, "What is the capital city of India?", "New Delhi", false);
        addGeography(q3027);
        Questions q3028 = new Questions(3028, "What is the largest river in Europe?", "Volga River", false);
        addGeography(q3028);
        Questions q3029 = new Questions(3029, "What is the capital city of Germany?", "Berlin", false);
        addGeography(q3029);
        Questions q3030 = new Questions(3030, "What is the highest capital city in Europe?", "Madrid, Spain", false);
        addGeography(q3030);
        Questions q3031 = new Questions(3031, "What is the capital city of Saudi Arabia?", "Riyadh", false);
        addGeography(q3031);
        Questions q3032 = new Questions(3032, "What is the largest country in Oceania?", "Australia", false);
        addGeography(q3032);
        Questions q3033 = new Questions(3033, "What is the capital city of Turkey?", "Ankara", false);
        addGeography(q3033);
        Questions q3034 = new Questions(3034, "What is the deepest lake in the world?", "Lake Baikal", false);
        addGeography(q3034);
        Questions q3035 = new Questions(3035, "What is the capital city of South Korea?", "Seoul", false);
        addGeography(q3035);
        Questions q3036 = new Questions(3036, "What is the largest country in North America?", "Canada", false);
        addGeography(q3036);
        Questions q3037 = new Questions(3037, "What is the capital city of Indonesia?", "Jakarta", false);
        addGeography(q3037);
        Questions q3038 = new Questions(3038, "What is the highest mountain in North America?", "Mount McKinley", false);
        addGeography(q3038);
        Questions q3039 = new Questions(3039, "What is the capital city of Russia?", "Moscow", false);
        addGeography(q3039);
        Questions q3040 = new Questions(3040, "What is the largest country in Africa?", "Algeria", false);
        addGeography(q3040);
        Questions q3041 = new Questions(3041, "What is the capital city of Mexico?", "Mexico City", false);
        addGeography(q3041);
        Questions q3042 = new Questions(3042, "What is the longest mountain range in the world?", "Andes", false);
        addGeography(q3042);
        Questions q3043 = new Questions(3043, "What is the capital city of Thailand?", "Bangkok", false);
        addGeography(q3043);
        Questions q3044 = new Questions(3044, "What is the largest waterfall in North America?", "Niagara Falls", false);
        addGeography(q3044);
        Questions q3045 = new Questions(3045, "What is the capital city of Peru?", "Lima", false);
        addGeography(q3045);
        Questions q3046 = new Questions(3046, "What is the smallest continent in the world?", "Australia", false);
        addGeography(q3046);
        Questions q3047 = new Questions(3047, "What is the capital city of Nigeria?", "Abuja", false);
        addGeography(q3047);
        Questions q3048 = new Questions(3048, "What is the largest river in South America?", "Amazon River", false);
        addGeography(q3048);
        Questions q3049 = new Questions(3049, "What is the capital city of South Korea?", "Seoul", false);
        addGeography(q3049);
        Questions q3050 = new Questions(3050, "What is the highest mountain in South America?", "Mount Aconcagua", false);
        addGeography(q3050);
        Questions q3051 = new Questions(3051, "What is the capital city of Spain?", "Madrid", false);
        addGeography(q3051);
        Questions q3052 = new Questions(3052, "What is the largest country in the Middle East?", "Saudi Arabia", false);
        addGeography(q3052);
        Questions q3053 = new Questions(3053, "What is the capital city of China?", "Beijing", false);
        addGeography(q3053);
        Questions q3054 = new Questions(3054, "What is the longest river in South America?", "Amazon River", false);
        addGeography(q3054);
        Questions q3055 = new Questions(3055, "What is the capital city of Argentina?", "Buenos Aires", false);
        addGeography(q3055);
        Questions q3056 = new Questions(3056, "What is the largest country in the Caribbean?", "Cuba", false);
        addGeography(q3056);
        Questions q3057 = new Questions(3057, "What is the capital city of Vietnam?", "Hanoi", false);
        addGeography(q3057);
        Questions q3058 = new Questions(3058, "What is the largest island country in the world?", "Indonesia", false);
        addGeography(q3058);
        Questions q3059 = new Questions(3059, "What is the capital city of Sweden?", "Stockholm", false);
        addGeography(q3059);
        Questions q3060 = new Questions(3060, "What is the largest city in South America?", "São Paulo", false);
        addGeography(q3060);
        Questions q3061 = new Questions(3061, "What is the capital city of Pakistan?", "Islamabad", false);
        addGeography(q3061);
        Questions q3062 = new Questions(3062, "What is the longest river in Europe?", "Volga River", false);
        addGeography(q3062);
        Questions q3063 = new Questions(3063, "What is the capital city of Norway?", "Oslo", false);
        addGeography(q3063);
        Questions q3064 = new Questions(3064, "What is the largest country in Southeast Asia?", "Indonesia", false);
        addGeography(q3064);
        Questions q3065 = new Questions(3065, "What is the capital city of Iran?", "Tehran", false);
        addGeography(q3065);
        Questions q3066 = new Questions(3066, "What is the highest mountain in Africa?", "Mount Kilimanjaro", false);
        addGeography(q3066);
        Questions q3067 = new Questions(3067, "What is the capital city of Switzerland?", "Bern", false);
        addGeography(q3067);
        Questions q3068 = new Questions(3068, "What is the largest country in the Arabian Peninsula?", "Saudi Arabia", false);
        addGeography(q3068);
        Questions q3069 = new Questions(3069, "What is the capital city of Greece?", "Athens", false);
        addGeography(q3069);
        Questions q3070 = new Questions(3070, "What is the largest archipelago in the world?", "Indonesia", false);
        addGeography(q3070);
        Questions q3071 = new Questions(3071, "What is the capital city of Portugal?", "Lisbon", false);
        addGeography(q3071);
        Questions q3072 = new Questions(3072, "What is the highest capital city in South America?", "La Paz, Bolivia", false);
        addGeography(q3072);
        Questions q3073 = new Questions(3073, "What is the capital city of Morocco?", "Rabat", false);
        addGeography(q3073);
        Questions q3074 = new Questions(3074, "What is the largest country in Central America?", "Nicaragua", false);
        addGeography(q3074);
        Questions q3075 = new Questions(3075, "What is the capital city of Cambodia?", "Phnom Penh", false);
        addGeography(q3075);
        Questions q3076 = new Questions(3076, "What is the highest mountain in Europe?", "Mount Elbrus", false);
        addGeography(q3076);
        Questions q3077 = new Questions(3077, "What is the capital city of Hungary?", "Budapest", false);
        addGeography(q3077);
        Questions q3078 = new Questions(3078, "What is the largest waterfall in Africa?", "Victoria Falls", false);
        addGeography(q3078);
        Questions q3079 = new Questions(3079, "What is the capital city of Kenya?", "Nairobi", false);
        addGeography(q3079);
        Questions q3080 = new Questions(3080, "What is the largest city in Europe?", "Moscow", false);
        addGeography(q3080);
        Questions q3081 = new Questions(3081, "What is the capital city of Chile?", "Santiago", false);
        addGeography(q3081);
        Questions q3082 = new Questions(3082, "What is the largest island in the Mediterranean Sea?", "Sicily", false);
        addGeography(q3082);
        Questions q3083 = new Questions(3083, "What is the capital city of Ukraine?", "Kyiv", false);
        addGeography(q3083);
        Questions q3084 = new Questions(3084, "What is the largest country in the Balkans?", "Romania", false);
        addGeography(q3084);
        Questions q3085 = new Questions(3085, "What is the capital city of Australia?", "Canberra", false);
        addGeography(q3085);
        Questions q3086 = new Questions(3086, "What is the largest lake in North America?", "Lake Superior", false);
        addGeography(q3086);
        Questions q3087 = new Questions(3087, "What is the capital city of Finland?", "Helsinki", false);
        addGeography(q3087);
        Questions q3088 = new Questions(3088, "What is the largest country in the Caribbean?", "Cuba", false);
        addGeography(q3088);
        Questions q3089 = new Questions(3089, "What is the capital city of Ecuador?", "Quito", false);
        addGeography(q3089);
        Questions q3090 = new Questions(3090, "What is the largest city in Africa?", "Lagos", false);
        addGeography(q3090);
        Questions q3091 = new Questions(3091, "What is the capital city of Ireland?", "Dublin", false);
        addGeography(q3091);
        Questions q3092 = new Questions(3092, "What is the largest lake in South America?", "Lake Titicaca", false);
        addGeography(q3092);
        Questions q3093 = new Questions(3093, "What is the capital city of Myanmar?", "Naypyidaw", false);
        addGeography(q3093);
        Questions q3094 = new Questions(3094, "What is the largest city in the Middle East?", "Cairo", false);
        addGeography(q3094);
        Questions q3095 = new Questions(3095, "What is the capital city of New Zealand?", "Wellington", false);
        addGeography(q3095);
        Questions q3096 = new Questions(3096, "What is the largest river in Asia?", "Yangtze River", false);
        addGeography(q3096);
        Questions q3097 = new Questions(3097, "What is the capital city of Colombia?", "Bogotá", false);
        addGeography(q3097);
        Questions q3098 = new Questions(3098, "What is the largest city in Oceania?", "Sydney", false);
        addGeography(q3098);
        Questions q3099 = new Questions(3099, "What is the capital city of Belgium?", "Brussels", false);
        addGeography(q3099);
        Questions q3100 = new Questions(3100, "What is the largest island in the Caribbean?", "Cuba", false);
        addGeography(q3100);
    }


}