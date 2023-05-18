package com.uop.quizapp;

import android.provider.BaseColumns;

public class DBContract {

    public DBContract(){}

    public static class ScienceTable implements BaseColumns{
        public static final String TABLE_NAME = "science";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }
    public static class GeneralTable implements BaseColumns{

        public static final String TABLE_NAME = "general";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }
    public static class SportsTable implements BaseColumns{

        public static final String TABLE_NAME = "sports";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }
    public static class GeographyTable implements BaseColumns{

        public static final String TABLE_NAME = "geography";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_DISPLAYED = "displayed";
    }

}
