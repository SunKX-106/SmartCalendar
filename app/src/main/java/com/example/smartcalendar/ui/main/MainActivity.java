package com.example.smartcalendar.ui.main;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.smartcalendar.R;
import com.example.smartcalendar.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(com.example.smartcalendar.utils.LocaleHelper.applySavedLocale(newBase));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ✅ 获取 NavController（负责 Fragment 导航）
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // ✅ 让底部导航栏与 NavController 绑定
        BottomNavigationView bottomNav = binding.bottomNav;
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}
