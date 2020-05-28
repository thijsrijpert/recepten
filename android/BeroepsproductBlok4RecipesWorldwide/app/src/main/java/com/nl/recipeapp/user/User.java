package com.nl.recipeapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.nl.recipeapp.R;

public class User extends AppCompatActivity {
    // Variables for the ViewPager and related elements
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        viewpager = findViewById(R.id.user_viewpager);
        TabLayout tablayout = findViewById(R.id.user_tablayout);
        UserPagerAdapter userPagerAdapter = new UserPagerAdapter(getSupportFragmentManager(), this.getBaseContext());
        initializeViewPager(viewpager, tablayout, userPagerAdapter);
    }

    private void initializeViewPager(ViewPager viewpager, TabLayout tablayout, FragmentStatePagerAdapter adapter) {
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        tablayout.setupWithViewPager(viewpager);
        tablayout.bringToFront();
    }
}
