package com.androidexample.perfectnotes.dbTimeTable;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.androidexample.perfectnotes.TimeTable;

import java.util.List;

@Dao
public interface TimeTableDao {

@Query("SELECT * FROM timetable")
List<TimeTable> getAllTimeTables();
    @Insert
    void insert(TimeTable timeTable);

    @Update
    void updateTimeTable(TimeTable timeTable);

    @Delete
    void deleteTimeTable(TimeTable timeTable);
}
