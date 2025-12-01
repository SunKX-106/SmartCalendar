package com.example.smartcalendar.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smartcalendar.data.entity.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM events")
    void deleteAll();

    @Query("SELECT * FROM events ORDER BY dateTime ASC")
    LiveData<List<Event>> getAllEvents();
}
