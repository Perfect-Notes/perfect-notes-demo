package com.androidexample.perfectnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todoDb.db";
    public static final String TABLE_NAME = "TODO_TABLE";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "POS";
    public static final String COL_3 = "SUBJECT";
    public static final String COL_4 = "DESCRIPTION";

    public ToDoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " INTEGER," + COL_3 + " TEXT," + COL_4 + "TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
