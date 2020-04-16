package com.nl.beroepsproductblok4_recipesworldwide.administrator;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nl.beroepsproductblok4_recipesworldwide.R;
import com.nl.beroepsproductblok4_recipesworldwide.administrator.tijdvakken.AddTijdvak;

public class AdministratorPagerAdapter extends FragmentStatePagerAdapter {

    private final Context context;

    public AdministratorPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AddTijdvak();
            case 1:
                return new AddTijdvak();
            case 2:
                return new AddTijdvak();
            case 3:
                return new AddTijdvak();
            case 4:
                return new AddTijdvak();
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
