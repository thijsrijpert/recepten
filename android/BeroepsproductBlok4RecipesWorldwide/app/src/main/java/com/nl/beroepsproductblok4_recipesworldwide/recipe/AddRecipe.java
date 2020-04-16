package com.nl.beroepsproductblok4_recipesworldwide.recipe;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nl.beroepsproductblok4_recipesworldwide.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecipe extends Fragment {
    private View view;
    private TextView textview_recipeDescriptionCharacterCount;
    private EditText edittext_recipeName, edittext_recipeDescription;
    private Button button_bindIngredient, button_addNewIngredient, button_applyRecipe;

    // Variables for the Spinners
    private Spinner spinner_mealTypes, spinner_countries, spinner_religions, spinner_dayparts, spinner_ingredients;
    private ArrayList<String> arraylist_mealTypes, arraylist_countryNames, arraylist_religionNames, arraylist_daypartNames, arraylist_ingredientNames;

    // Variables for the RecyclerView
    private RecyclerView recyclerview_ingredients;
    private ArrayList<String> arraylist_ingredients; // Note: This datatype has to be changed to 'Ingredient' when this class is made
    private ArrayList<String> arraylist_ingredients_recyclerview; // Note: This datatype has to be changed to 'Ingredient' when this class is made

    // Variables for the database connection
    private RecipeHTTP recipeHTTP;

    public AddRecipe() {
        arraylist_ingredients_recyclerview = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        // Initialize the Class variables
        edittext_recipeName = view.findViewById(R.id.addRecipe_edittext_recipeName);
        edittext_recipeDescription = view.findViewById(R.id.addRecipe_edittext_description);
        textview_recipeDescriptionCharacterCount = view.findViewById(R.id.addRecipe_textview_receptomschrijvingCharacterCount);

        button_bindIngredient = view.findViewById(R.id.addRecipe_btn_bindIngredient);
        button_addNewIngredient = view.findViewById(R.id.addRecipe_btn_addNewIngredient);
        button_applyRecipe = view.findViewById(R.id.addRecipe_btn_applyRecipe);

        // Launch the initialization methods
        initializeSpinners();
        initializeInputFields();
        initializeButtons();
        initializeRecyclerView();
        refreshIngredientsSpinner();

        recipeHTTP = new RecipeHTTP(this.getContext());
        recipeHTTP.addRecipe();

        return view;
    }

    // Test Data

    /**
     * Initializes the Spinners on this Fragment
     */
    private void initializeSpinners() {
        // Initialize the Spinner objects
        spinner_mealTypes = view.findViewById(R.id.addRecipe_spinner_mealtype);
        spinner_countries = view.findViewById(R.id.addRecipe_spinner_recipeCountry);
        spinner_religions = view.findViewById(R.id.addRecipe_spinner_recipeReligion);
        spinner_dayparts = view.findViewById(R.id.addRecipe_spinner_recipeMealDaypart);
        spinner_ingredients = view.findViewById(R.id.addRecipe_spinner_bindIngredient);

        // Fill the ArrayLists which contain the String objects used in the dropdown lists
//        arraylist_mealTypes = ;
//        arraylist_countryNames = ;
//        arraylist_religionNames = ;
//        arraylist_daypartNames = ;
//        arraylist_ingredientNames = new ArrayList<>();
//
//        for (int c = 0; c < arraylist_ingredients.size(); c++) {
//            arraylist_ingredientNames.add(arraylist_ingredients.get(c));
//        }

        // Create and set the adapters for the spinners
//        ArrayAdapter<String> mealTypesAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_mealTypes);
//        spinner_mealTypes.setAdapter(mealTypesAdapter);
//
//        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_countryNames);
//        spinner_countries.setAdapter(countriesAdapter);
//
//        ArrayAdapter<String> religionsAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_religionNames);
//        spinner_religions.setAdapter(religionsAdapter);
//
//        ArrayAdapter<String> daypartsAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_daypartNames);
//        spinner_dayparts.setAdapter(daypartsAdapter);
//
//        ArrayAdapter<String> ingredientsAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_ingredientNames);
//        spinner_ingredients.setAdapter(ingredientsAdapter);

        // Make sure there is a method available that will check which ingredients are displayed:
        // - Administrators should see all ingredients: approved as well as unapproved
        // - Users should see some ingredients: approved and only the unapproved ones they submitted
    }

    /**
     * Initializes the EditText elements on this Fragment
     */
    private void initializeInputFields() {
        // Create the listener on recipeDescription's EditText, so the character counter changes when the user is typing
        edittext_recipeDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textview_recipeDescriptionCharacterCount.setText(edittext_recipeDescription.getText().length() + " / 255", null);

                if (edittext_recipeDescription.getText().length() > 255) {
                    textview_recipeDescriptionCharacterCount.setTextColor(Color.RED);
                } else {
                    textview_recipeDescriptionCharacterCount.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initializeButtons() {
        button_bindIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checks if the chosen Ingredient from the Spinner is already in the RecyclerView. If it is, display a Toast, otherwise add the Ingredient to the RecyclerView
                for (int c = 0; c < arraylist_ingredients_recyclerview.size(); c++) {
                    if (arraylist_ingredients_recyclerview.get(c).equals(spinner_ingredients.getSelectedItem().toString())) {
                        Toast.makeText(getActivity(), "Het gekozen ingrediënt is al gekoppeld", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // If an Ingredient has not been found in the RecyclerView, it will be added right here
                for (int c = 0; c < arraylist_ingredients.size(); c++) {
                    if (arraylist_ingredients.get(c).equals(spinner_ingredients.getSelectedItem().toString())) {
                        arraylist_ingredients_recyclerview.add(arraylist_ingredients.get(c));
                    }
                }

                // Make sure the RecyclerView gets refreshed
                recyclerview_ingredients.requestLayout();
            }
        });
    }


    private void initializeRecyclerView() {
        recyclerview_ingredients = view.findViewById(R.id.addRecipe_recyclerView);
        AddRecipe_RecyclerViewAdapter addRecipeRecyclerViewAdapter = new AddRecipe_RecyclerViewAdapter(arraylist_ingredients_recyclerview, view.getContext(), recyclerview_ingredients);
        recyclerview_ingredients.setAdapter(addRecipeRecyclerViewAdapter);
        recyclerview_ingredients.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void refreshIngredientsSpinner() {

    }
}
