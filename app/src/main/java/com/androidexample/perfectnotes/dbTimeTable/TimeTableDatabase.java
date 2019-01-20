package com.androidexample.perfectnotes.dbTimeTable;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.androidexample.perfectnotes.TimeTable;


@Database(entities = {TimeTable.class},version = 1,exportSchema = false)
public abstract class TimeTableDatabase extends RoomDatabase {

    public abstract TimeTableDao timeTableDao();

}
