package com.example.smartcalendar.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.smartcalendar.data.dao.EventDao;
import com.example.smartcalendar.data.database.AppDatabase;
import com.example.smartcalendar.data.entity.Event;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {

    private final EventDao eventDao;
    private final LiveData<List<Event>> allEvents;
    private final ExecutorService executorService;

    public EventRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAllEvents();
        executorService = Executors.newSingleThreadExecutor();

        // 调试阶段：运行一次插入测试数据，之后注释掉
        executorService.execute(() -> {
            eventDao.deleteAll(); // 清空旧的，防止重复
//            Event e = new Event();
//            e.title = "测试任务 1";
//            e.description = "这是数据库自动加载的内容";
//            e.dateTime = System.currentTimeMillis();
//            eventDao.insert(e);
        });
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public void insert(Event event) {
        executorService.execute(() -> eventDao.insert(event));
    }

    public void update(Event event) {
        executorService.execute(() -> eventDao.update(event));
    }

    public void delete(Event event) {
        executorService.execute(() -> eventDao.delete(event));
    }

}
