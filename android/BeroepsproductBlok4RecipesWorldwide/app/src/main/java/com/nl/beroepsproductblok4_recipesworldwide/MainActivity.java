package com.nl.beroepsproductblok4_recipesworldwide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.main_viewpager);
        TabLayout tabLayout = findViewById(R.id.main_tablayout);
        MainActivityPagerAdapter adapter = new MainActivityPagerAdapter(getSupportFragmentManager(), this.getBaseContext());

        initializeViewPager(viewPager, tabLayout, adapter);
    }

    /**
     * Initializes the viewpager
     * @param pager The ViewPager object that has to be initialized
     * @param tabLayout The TabLayout object that has to be initialized
     * @param adapter The FragmentAdapter that specifies the fragments in the TabLayout object
     */
    public void initializeViewPager(ViewPager pager, TabLayout tabLayout, FragmentStatePagerAdapter adapter){
        pager.setAdapter(adapter);
        pager.setCurrentItem(2);
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
