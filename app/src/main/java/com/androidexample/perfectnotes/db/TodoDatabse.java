package com.androidexample.perfectnotes.db;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import com.androidexample.perfectnotes.Todo;

@Database(entities = {Todo.class},version = 1)
public abstract class TodoDatabse extends RoomDatabase {

    public abstract TodoDao todoDao();
}
