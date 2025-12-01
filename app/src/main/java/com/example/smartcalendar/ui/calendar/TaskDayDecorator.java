package com.example.smartcalendar.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;
import java.util.Set;

public class TaskDayDecorator implements DayViewDecorator {

    private final Set<CalendarDay> dates;
    private final int color;

    public TaskDayDecorator(Set<CalendarDay> dates, Context context) {
        this.dates = new HashSet<>(dates);
        this.color = Color.parseColor("#9C27B0"); // 紫色点标记
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(color));
        view.addSpan(new DotSpan(8, color));
    }
}
