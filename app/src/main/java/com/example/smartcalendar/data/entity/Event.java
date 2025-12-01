package com.example.smartcalendar.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;           // 事件标题
    public String description;     // 事件描述
    public long dateTime;          // 时间（时间戳格式）
    public boolean isDone;         // 是否完成
    public boolean hasReminder;    // 是否设置提醒
    public long reminderTime;      // 提醒时间戳
}
