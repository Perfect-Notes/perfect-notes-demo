package com.androidexample.perfectnotes.reminderDb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "label")
    public String label;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public Reminder(String date,String time, String label){
        this.date = date;
        this.time = time;
        this.label = label;
    }


}
