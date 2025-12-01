package com.example.smartcalendar.ui.tasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.data.entity.Event;
import com.example.smartcalendar.databinding.ItemEventBinding;

public class TaskListAdapter extends ListAdapter<Event, TaskListAdapter.ViewHolder> {

    protected TaskListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Event> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Event>() {
                @Override
                public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEventBinding binding = ItemEventBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = getItem(position);
        holder.bind(event);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemEventBinding binding;

        public ViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Event event) {
            binding.textTitle.setText(event.title);
            binding.textDescription.setText(event.description);
        }
    }
}
