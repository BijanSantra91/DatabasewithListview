package com.example.bijan.databasewithlistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bijan on 1/2/2017.
 */

public class MyDatabase {
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    public  MyDatabase(Context context){
        //myHelper = new MyHelper(context, "techpalle.db", null, 1);
        //3 JAN 2017 - releasing next version of databsae - changed by bijan
        myHelper = new MyHelper(context, "techpalle.db", null, 2);
    }

    public  void  open(){
        sqLiteDatabase = myHelper.getWritableDatabase();
    }

    public  void insertStudent(String name, String sub){
        ContentValues contentValues = new ContentValues();
        contentValues.put("sname", name);
        contentValues.put("ssub", sub);
        sqLiteDatabase.insert("student", null, contentValues);
    }

    public Cursor quaryStudent(){
        Cursor cursor = null;
        cursor = sqLiteDatabase.query("student", null, null, null, null, null, null);
        return cursor;
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public  class MyHelper extends SQLiteOpenHelper{

        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table student(_id integer primary key, sname text, ssub text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            switch (newVersion){
                case 2:
                    //Bijan - adding jobs table for 2nd release of database
                    sqLiteDatabase.execSQL("create table jobs(_id integer primary key, jdesc text);");
                    //sqLiteDatabase.execSQL("drop table student if exists;");
                    sqLiteDatabase.execSQL("alter table student add colunmn scomp text;");
                    break;
            }
        }
    }
}
