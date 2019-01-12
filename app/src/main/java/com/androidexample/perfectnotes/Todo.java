package com.androidexample.perfectnotes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Todo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="subject")
    public String subject;
    @ColumnInfo(name ="description")
    public String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Todo(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }
}
