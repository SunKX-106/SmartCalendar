package com.example.smartcalendar.ui.settings;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartcalendar.ui.main.MainActivity;
import com.example.smartcalendar.R;
import com.example.smartcalendar.utils.LocaleHelper;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView tvLanguage = view.findViewById(R.id.tvLanguage);
        tvLanguage.setOnClickListener(v -> showLanguageDialog());

        return view;
    }

    private void showLanguageDialog() {
        String[] languages = {getString(R.string.language_zh), getString(R.string.language_en)};
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.settings_language)
                .setItems(languages, (dialog, which) -> {
                    if (which == 0)
                        LocaleHelper.setLocale(requireContext(), "zh");
                    else
                        LocaleHelper.setLocale(requireContext(), "en");

                    // 重启 MainActivity 应用语言变化
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                })
                .show();
    }
}
