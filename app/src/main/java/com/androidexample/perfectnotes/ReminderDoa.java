package com.androidexample.perfectnotes;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ReminderDoa {


    @Query("SELECT * FROM Reminder")
    List<Reminder> getAllReminders();

    @Query("SELECT * FROM Reminder")
    Reminder getReminder();

    @Insert
    void insert(Reminder reminder);

    @Delete
    void deleteReminder(Reminder reminder);

    @Update
    void updateReminder(Reminder reminder);
}
