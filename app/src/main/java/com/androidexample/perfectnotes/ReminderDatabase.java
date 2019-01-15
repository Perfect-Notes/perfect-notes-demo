package com.androidexample.perfectnotes;

import android.arch.persistence.room.RoomDatabase;

public abstract class ReminderDatabase extends RoomDatabase {

    public abstract ReminderDoa reminderDoa();

}
