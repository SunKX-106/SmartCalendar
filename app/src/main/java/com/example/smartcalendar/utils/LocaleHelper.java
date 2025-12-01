package com.example.smartcalendar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class LocaleHelper {

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_LANGUAGE = "language";

    // 获取当前语言
    public static String getLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_LANGUAGE, "zh");
    }

    // 保存语言偏好
    private static void saveLanguage(Context context, String langCode) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_LANGUAGE, langCode);
        editor.apply();
    }

    // 设置语言
    public static Context setLocale(Context context, String langCode) {
        saveLanguage(context, langCode);

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.createConfigurationContext(config);
        } else {
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            return context;
        }
    }

    // 在应用启动时调用，应用用户偏好语言
    public static Context applySavedLocale(Context context) {
        String langCode = getLanguage(context);
        return setLocale(context, langCode);
    }
}
