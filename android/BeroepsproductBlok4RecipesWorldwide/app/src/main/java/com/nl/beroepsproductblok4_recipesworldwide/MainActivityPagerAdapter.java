package com.nl.beroepsproductblok4_recipesworldwide;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nl.beroepsproductblok4_recipesworldwide.home.Home;
import com.nl.beroepsproductblok4_recipesworldwide.ingredient.AddIngredient;
import com.nl.beroepsproductblok4_recipesworldwide.recipe.AddRecipe;

public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {
    private final Context context;

    public MainActivityPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Home();
            case 1:
                return new Home();
            case 2:
                return new Home();
            case 3:
                return new AddRecipe();
            case 4:
                return new AddIngredient();
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
                return context.getString(R.string.tabs_home_register);
            case 1:
                return context.getString(R.string.tabs_home_login);
            case 2:
                return context.getString(R.string.tabs_home_home);
            case 3:
                return context.getString(R.string.tabs_home_addRecipe);
            case 4:
                return context.getString(R.string.tabs_home_addIngredient);
            default:
                return null;
        }
    }
}
