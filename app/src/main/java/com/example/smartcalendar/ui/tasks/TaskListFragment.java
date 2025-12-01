package com.example.smartcalendar.ui.tasks;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.smartcalendar.R;
import com.example.smartcalendar.data.entity.Event;
import com.example.smartcalendar.databinding.FragmentTaskListBinding;

import java.util.Calendar;

public class TaskListFragment extends Fragment {

    private FragmentTaskListBinding binding;
    private EventViewModel eventViewModel;
    private TaskListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentTaskListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.fabAdd.setVisibility(View.VISIBLE);
        binding.fabAdd.bringToFront(); // 保证悬浮按钮在最上层

        // 初始化 RecyclerView
        adapter = new TaskListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        // ✅ 使用 Activity 级别作用域，让数据与 CalendarFragment 共享
        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        // 监听数据库变化
        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
            adapter.submitList(events);
        });

        // 点击悬浮按钮添加任务
        binding.fabAdd.setOnClickListener(v -> showAddTaskDialog());

        return view;
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);

        EditText inputTitle = dialogView.findViewById(R.id.inputTitle);
        EditText inputDesc = dialogView.findViewById(R.id.inputDesc);
        Button btnSelectDate = dialogView.findViewById(R.id.btnSelectDate);

        final long[] selectedTime = {System.currentTimeMillis()};

        btnSelectDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(
                    getContext(),
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth, 0, 0, 0);
                        selectedTime[0] = calendar.getTimeInMillis();
                        btnSelectDate.setText("已选择: " + (month + 1) + "月" + dayOfMonth + "日");
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        });

        builder.setPositiveButton("保存", (dialog, which) -> {
            String title = inputTitle.getText().toString().trim();
            String desc = inputDesc.getText().toString().trim();

            if (!title.isEmpty()) {
                Event e = new Event();
                e.title = title;
                e.description = desc;
                e.dateTime = selectedTime[0];
                eventViewModel.insert(e);
            }
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
