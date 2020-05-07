package com.nl.recipeapp.admin;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nl.recipeapp.R;
import com.nl.recipeapp.admin.recipe.Manage;
import com.nl.recipeapp.admin.tijdvakken.Add;

public class AdminPagerAdapter extends FragmentStatePagerAdapter {

    private final Context context;

    public AdminPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AdminGeneralOptions();
            case 1:
                return new Add();
            case 2:
                return new Manage();
            case 3:
                return new com.nl.recipeapp.admin.ingredients.Manage();
            case 4:
                return new Add();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return context.getString(R.string.tabs_administrator_addTijdvak);
            case 1:
                return context.getString(R.string.tabs_administrator_manageUsers);
            case 2:
                return context.getString(R.string.tabs_administrator_manageRecipes);
            case 3:
                return context.getString(R.string.tabs_administrator_manageIngredients);
            case 4:
                return context.getString(R.string.tabs_administrator_other);
            default:
                return null;
        }
    }
}
