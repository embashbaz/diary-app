package com.example.diaryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.diaryapp.databaseTemplate.entrriesMainTable;

public class dbOpenHelper extends SQLiteOpenHelper {
            public static final  String Database_name = "diaryApp.db";
            public static int version = 1;

    public dbOpenHelper(@Nullable Context context) {
        super(context, Database_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(entrriesMainTable.SQL_CREATE_TABLE);

        sampleEntry(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    void sampleEntry(SQLiteDatabase db){
        String date="00/00/0000";
        String subject="Sample entry";
        String title="delete";
        String text="delete this after installation";

        ContentValues values = new ContentValues();

        values.put(entrriesMainTable.DATE_COLOMN, date);
        values.put(entrriesMainTable.SUBJECT_COLUMS, subject);
        values.put(entrriesMainTable.TITLE_COLUMN, title);
        values.put(entrriesMainTable.TEXT_COLUMN, text);

        long newRowid = db.insert(entrriesMainTable.TABLE_NAME, null, values);





    }
}
