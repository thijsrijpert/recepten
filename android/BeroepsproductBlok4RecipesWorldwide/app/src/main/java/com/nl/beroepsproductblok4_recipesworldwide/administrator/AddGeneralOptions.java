package com.nl.beroepsproductblok4_recipesworldwide.administrator;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.nl.beroepsproductblok4_recipesworldwide.R;
import com.nl.beroepsproductblok4_recipesworldwide.administrator.country.AddCountry;
import com.nl.beroepsproductblok4_recipesworldwide.administrator.country.EditCountry;
import com.nl.beroepsproductblok4_recipesworldwide.administrator.mealtype.AddMealtype;
import com.nl.beroepsproductblok4_recipesworldwide.administrator.mealtype.EditMealtype;
import com.nl.beroepsproductblok4_recipesworldwide.administrator.tijdvakken.AddTimeOfDay;
import com.nl.beroepsproductblok4_recipesworldwide.administrator.tijdvakken.EditTimeOfDay;
import com.nl.beroepsproductblok4_recipesworldwide.religie.AddReligie;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddGeneralOptions extends Fragment {
    private FragmentTransaction transaction;

    private Fragment fragment_addTimeOfDay, fragment_editTimeOfDay, fragment_addReligion, fragment_editReligion, fragment_addCountry, fragment_editCountry, fragment_addMealtype, fragment_editMealtype;

    public AddGeneralOptions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_general_options, container, false);

        // Initialize the fragment
        fragment_addTimeOfDay = new AddTimeOfDay();
        fragment_editTimeOfDay = new EditTimeOfDay();
        fragment_addCountry = new AddCountry();
        fragment_editCountry = new EditCountry();
        fragment_addReligion = new AddReligie();
//        fragment_editReligion = new EditReligie();
        fragment_addMealtype = new AddMealtype();
        fragment_editMealtype = new EditMealtype();

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
