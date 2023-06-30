package com.uop.quizapp;

import android.provider.BaseColumns;

public class DBContract {

    public DBContract(){}

    public static class NationalTable implements BaseColumns{
        public static final String TABLE_NAME = "National";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }
    public static class GeneralTable implements BaseColumns{

        public static final String TABLE_NAME = "General";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }
    public static class ClubsTable implements BaseColumns{

        public static final String TABLE_NAME = "Clubs";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }
    public static class GeographyTable implements BaseColumns{

        public static final String TABLE_NAME = "Geography";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }
    public static class GreekNationalTable implements BaseColumns{
        public static final String TABLE_NAME = "Εθνικές";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }
    public static class GreekGeneralTable implements BaseColumns{

        public static final String TABLE_NAME = "Γενικές";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }
    public static class GreekClubsTable implements BaseColumns{

        public static final String TABLE_NAME = "Συλλόγοι";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }
    public static class GreekGeographyTable implements BaseColumns{

        public static final String TABLE_NAME = "Γεωγραφία";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }

}
