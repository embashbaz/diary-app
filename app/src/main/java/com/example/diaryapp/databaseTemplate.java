package com.example.diaryapp;

import android.provider.BaseColumns;

public final class databaseTemplate {


    private databaseTemplate(){}
    //

    public  static final class  entrriesMainTable implements BaseColumns {

        public static final String TABLE_NAME="diary_app";
        public static final String DATE_COLOMN= "date";
        public static final String SUBJECT_COLUMS="subject";
        public static final String TITLE_COLUMN="title";
        public static final String TEXT_COLUMN="tex";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +"("
                +_ID+ " INTEGER PRIMARY KEY,"+ DATE_COLOMN + " TEXT NOT NULL,"+
                SUBJECT_COLUMS +" TEXT NOT NULL,"+ TITLE_COLUMN +" TEXT NOT NULL,"+
                TEXT_COLUMN+ " TEXT)";

    }
}
