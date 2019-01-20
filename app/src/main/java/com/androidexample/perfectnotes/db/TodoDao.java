package com.androidexample.perfectnotes.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.androidexample.perfectnotes.Todo;
import java.util.List;
@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo")
    List<Todo> getAllTodos();

    @Query("SELECT * FROM todo")
    Todo getTodo();



    @Insert
    void insert(Todo todo);

    @Update
    void updateTodo(Todo todo);
    @Delete
    void deleteTodo(Todo todo);
}
