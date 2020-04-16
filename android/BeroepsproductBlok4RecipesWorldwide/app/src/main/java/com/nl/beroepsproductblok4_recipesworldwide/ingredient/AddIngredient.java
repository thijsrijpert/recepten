package com.nl.beroepsproductblok4_recipesworldwide.ingredient;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nl.beroepsproductblok4_recipesworldwide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIngredient extends Fragment {
    private View view;

    public AddIngredient() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_ingredient, container, false);



        return view;
    }

}
