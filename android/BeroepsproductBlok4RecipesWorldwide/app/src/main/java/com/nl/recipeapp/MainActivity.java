package com.nl.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.nl.recipeapp.admin.Admin;
import com.nl.recipeapp.model.User;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private User currentUser;

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
        pager.setCurrentItem(1);
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

    /**
     * This method is used to start new activities from fragments. I have not found a way to do this directly from the fragment itself, so this'll have to do for now
     * @param view The view of the fragment (I assume)
     * @param activity The activity that has to be started (specified further below)
     */
    public void activitySwitcher(View view, int activity) {
        Intent intent;

        switch (activity) {
            // 1 == Administrator
            case 1:
                intent = new Intent(view.getContext(), Admin.class);
//                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                break;
            default:
                intent = new Intent(view.getContext(), MainActivity.class);
                break;
        }

        startActivity(intent);
    }

    /**
     * Returns the currently logged in user
     * @return The User object of the currently logged in user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the user in a class variable, so other methods in other classes can ask if someone is logged in (and if necessary, who is logged in)
     * @param user The user object that has to be set as the currently logged in user
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }
}
