package com.nl.recipeapp.admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nl.recipeapp.R;
import com.nl.recipeapp.religie.Add;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminGeneralOptions extends Fragment {
    private FragmentTransaction transaction;

    private Fragment fragment_addTimeOfDay, fragment_editTimeOfDay, fragment_addReligion, fragment_editReligion, fragment_addCountry, fragment_editCountry, fragment_addMealtype, fragment_editMealtype;

    public AdminGeneralOptions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_general_options, container, false);

        // Initialize the fragment
        fragment_addTimeOfDay = new com.nl.recipeapp.admin.tijdvakken.Add();
        fragment_editTimeOfDay = new com.nl.recipeapp.admin.tijdvakken.Edit();
        fragment_addCountry = new com.nl.recipeapp.admin.country.Add();
        fragment_editCountry = new com.nl.recipeapp.admin.country.Edit();
        fragment_addReligion = new Add();
//        fragment_editReligion = new EditReligie();
        fragment_addMealtype = new com.nl.recipeapp.admin.mealtype.Add();
        fragment_editMealtype = new com.nl.recipeapp.admin.mealtype.Edit();

        // Initialize the buttons and their click listeners
        Button button_addTijdvak = view.findViewById(R.id.addGeneralOptions_button_addTijdvak);
        button_addTijdvak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction("AddTimeOfDay");
            }
        });

        Button button_editTijdvak = view.findViewById(R.id.addGeneralOptions_button_editTijdvak);
        button_editTijdvak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction("EditTimeOfDay");
            }
        });

        Button button_addReligion = view.findViewById(R.id.addGeneralOptions_button_addReligion);
        button_addReligion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction("AddReligion");
            }
        });

        Button button_editReligion = view.findViewById(R.id.addGeneralOptions_button_editReligion);
        button_editReligion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction("EditReligion");
            }
        });

        Button button_addCountry = view.findViewById(R.id.addGeneralOptions_button_addCountry);
        button_addCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction("AddCountry");
            }
        });

        Button button_editCountry = view.findViewById(R.id.addGeneralOptions_button_editCountry);
        button_editCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction("EditCountry");
            }
        });

        Button button_addMealtype = view.findViewById(R.id.addGeneralOptions_button_addMealtype);
        button_addMealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction("AddMealtype");
            }
        });

        Button button_editMealtype = view.findViewById(R.id.addGeneralOptions_button_editMealtype);
        button_editMealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction("EditMealtype");
            }
        });

        return view;
    }

    private void fragmentTransaction(String fragment) {
        transaction = getActivity().getSupportFragmentManager().beginTransaction();

        switch (fragment) {
            case "AddTimeOfDay":
                transaction.replace(R.id.addGeneralOptions_framelayout, fragment_addTimeOfDay);
            break;
            case "EditTimeOfDay":
                transaction.replace(R.id.addGeneralOptions_framelayout, fragment_editTimeOfDay);
            break;
            case "AddReligion":
                transaction.replace(R.id.addGeneralOptions_framelayout, fragment_addReligion);
            break;
            case "EditReligion":
//                transaction.replace(R.id.addGeneralOptions_framelayout, fragment_editReligion);
            break;
            case "AddCountry":
                transaction.replace(R.id.addGeneralOptions_framelayout, fragment_addCountry);
            break;
            case "EditCountry":
                transaction.replace(R.id.addGeneralOptions_framelayout, fragment_editCountry);
            break;
            case "AddMealtype":
                transaction.replace(R.id.addGeneralOptions_framelayout, fragment_addMealtype);
            break;
            case "EditMealtype":
                transaction.replace(R.id.addGeneralOptions_framelayout, fragment_editMealtype);
            break;
        }

        transaction.commit();
    }

}
