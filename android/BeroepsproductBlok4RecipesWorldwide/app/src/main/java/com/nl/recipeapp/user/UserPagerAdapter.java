package com.nl.recipeapp.user;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nl.recipeapp.R;

public class UserPagerAdapter extends FragmentStatePagerAdapter {
    private final Context context;

    public UserPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new com.nl.recipeapp.user.recipe.Manage();
            case 1:
                return new com.nl.recipeapp.user.review.Manage();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return context.getString(R.string.tabs_user_recipe);
            case 1:
                return context.getString(R.string.tabs_user_review);
            default:
                return null;
        }
    }
}