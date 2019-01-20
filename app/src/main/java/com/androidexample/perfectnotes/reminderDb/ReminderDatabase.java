package com.androidexample.perfectnotes.reminderDb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Reminder.class},version = 2)
public abstract class ReminderDatabase extends RoomDatabase {

    public abstract ReminderDoa reminderDoa();

}
