package com.nl.recipeapp.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.nl.recipeapp.R;

public class Admin extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        TextView textview_loggedInUser = findViewById(R.id.administrator_textview_loggedInUser);
        textview_loggedInUser.setText(getIntent().getStringExtra("USERNAME") + " is ingelogd.");

        viewPager = findViewById(R.id.administrator_viewpager);
        TabLayout tabLayout = findViewById(R.id.administrator_tablayout);
        AdminPagerAdapter adapter = new AdminPagerAdapter(getSupportFragmentManager(), this.getBaseContext());

        initializeViewPager(viewPager, tabLayout, adapter);
    }

    /**
     * Initializes the viewpager
     * @param pager The ViewPager object that has to be initialized
     * @param tabLayout The TabLayout object that has to be initialized
     * @param adapter The FragmentAdapter that specifies the fragments in the TabLayout object
     */
    public void initializeViewPager(ViewPager pager, TabLayout tabLayout, FragmentStatePagerAdapter adapter) {
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        tabLayout.setupWithViewPager(pager);
        tabLayout.bringToFront();
    }

    /**
     * Used to switch to different tabs
     * @return The ViewPager object
     */
    public ViewPager getViewPager() {
        return viewPager;
    }
}
