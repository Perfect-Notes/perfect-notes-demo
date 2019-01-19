package com.androidexample.perfectnotes;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Reminder.class},version = 1)
public abstract class ReminderDatabase extends RoomDatabase {

    public abstract ReminderDoa reminderDoa();

}
