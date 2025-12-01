package com.example.smartcalendar.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartcalendar.R;
import com.example.smartcalendar.data.entity.Event;
import com.example.smartcalendar.ui.tasks.EventViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalendarFragment extends Fragment {

    private EventViewModel eventViewModel;
    private MaterialCalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = view.findViewById(R.id.calendarView);

        calendarView.setSelectionColor(ContextCompat.getColor(requireContext(), R.color.purple_200));
        calendarView.setDateSelected(CalendarDay.today(), true);

        // ✅ 使用 Activity 级别作用域共享 ViewModel
        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        // ✅ 监听任务数据变化
        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), this::updateCalendarMarkers);

        return view;
    }

    // 更新日历上的任务标记
    private void updateCalendarMarkers(List<Event> events) {
        calendarView.removeDecorators(); // 先清空旧标记

        Set<CalendarDay> taskDays = new HashSet<>();
        for (Event e : events) {
            CalendarDay day = CalendarDay.from(new java.util.Date(e.dateTime));
            taskDays.add(day);
        }

        // 添加标记装饰器
        calendarView.addDecorator(new TaskDayDecorator(taskDays, requireContext()));
    }
}
