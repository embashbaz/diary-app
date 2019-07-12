package com.example.diaryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import com.example.diaryapp.databaseTemplate.entrriesMainTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class activity_note extends AppCompatActivity {

    Cursor mCursor;
    public static  String ENTRY="ENTRY";
    private  int indexpassed;
    private  dbOpenHelper openhelper;
    private TextView mDate;
    private EditText mSubject;
    private EditText mTitle;
    private EditText mText;
    private boolean deleteTrue;

    private static int dateid;
    private static  int subjectid;
    private static int titleid;
    private  static int textid;
    private int a=-1;
    private boolean newentry;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        setContentView(R.layout.activity_note);
        openhelper= new dbOpenHelper(this);

        mDate = findViewById(R.id.date_textview);
        mSubject = findViewById(R.id.subject_editview);
        mTitle = findViewById(R.id.title_editview);
        mText = findViewById(R.id.content_editview);

        intentReceived();
        if(!newentry) {
            loadnote();
            setdata();
        }

    }

    private void setdata() {
        dateid = mCursor.getColumnIndex(entrriesMainTable.DATE_COLOMN);
        subjectid = mCursor.getColumnIndex(entrriesMainTable.SUBJECT_COLUMS);
        titleid = mCursor.getColumnIndex(entrriesMainTable.TITLE_COLUMN);
        textid= mCursor.getColumnIndex(entrriesMainTable.TEXT_COLUMN);



        mDate.setText(mCursor.getString(dateid));
        mSubject.setText(mCursor.getString(subjectid));
        mTitle.setText(mCursor.getString(titleid));
        mText.setText(mCursor.getString(textid));


    }

    private void loadnote() {

        Intent intent = getIntent();

        indexpassed=intent.getIntExtra(ENTRY, a);

        SQLiteDatabase db = openhelper.getReadableDatabase();

        String selection = entrriesMainTable._ID+" = ?";
        String[] selectionArg={Integer.toString(indexpassed)};
        String[] columns = {entrriesMainTable.DATE_COLOMN,
                            entrriesMainTable.SUBJECT_COLUMS,
                            entrriesMainTable.TITLE_COLUMN,
                            entrriesMainTable.TEXT_COLUMN};
        mCursor= db.query(entrriesMainTable.TABLE_NAME,columns,selection,selectionArg,null,null,null);
        mCursor.moveToNext();

    }
    void  intentReceived(){

        Intent intent = getIntent();

        indexpassed=intent.getIntExtra(ENTRY, a);
        newentry = indexpassed ==a;
        if (newentry){
            currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            mDate.setText(currentDate);
            ContentValues values = new ContentValues();


            values.put(entrriesMainTable.DATE_COLOMN, currentDate);
            values.put(entrriesMainTable.SUBJECT_COLUMS, "");
            values.put(entrriesMainTable.TITLE_COLUMN,"");
            values.put(entrriesMainTable.TEXT_COLUMN,"");

            SQLiteDatabase db = openhelper.getWritableDatabase();
            indexpassed = (int) db.insert(entrriesMainTable.TABLE_NAME,null,values);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(deleteTrue) {
            deleteFromDb();
        }else {
            savetodatabase();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        openhelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_entry_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id==R.id.delete) {
            deleteTrue = true;
            finish();
        }
        return deleteTrue;
    }

    private void savetodatabase() {

        String date = mDate.getText().toString();
        String subject = mSubject.getText().toString();
        String title = mTitle.getText().toString();
        String text = mText.getText().toString();

        StoreInDb(date, subject, title, text);
    }

    private void StoreInDb(String date, String subject, String title, String text) {

        String selection = entrriesMainTable._ID + " = ?";
        String[] selectionArg = {Integer.toString(indexpassed)};

        ContentValues values = new ContentValues();
        values.put(entrriesMainTable.DATE_COLOMN,date);
        values.put(entrriesMainTable.SUBJECT_COLUMS, subject);
        values.put(entrriesMainTable.TITLE_COLUMN,title);
        values.put(entrriesMainTable.TEXT_COLUMN,text);

        SQLiteDatabase db = openhelper.getWritableDatabase();
        db.update(entrriesMainTable.TABLE_NAME,values,selection,selectionArg);

    }
    private void deleteFromDb() {
        String selection = entrriesMainTable._ID + " = ?";
        String[] selectionArg = {Integer.toString(indexpassed)};
        SQLiteDatabase db = openhelper.getWritableDatabase();
        db.delete(entrriesMainTable.TABLE_NAME,selection,selectionArg);

    }
}
