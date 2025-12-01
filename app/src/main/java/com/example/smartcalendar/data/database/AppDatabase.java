package com.example.smartcalendar.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.smartcalendar.data.dao.EventDao;
import com.example.smartcalendar.data.entity.Event;

@Database(entities = {Event.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract EventDao eventDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "smart_calendar_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
