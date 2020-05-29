package com.nl.recipeapp.recipe;


import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nl.recipeapp.CharacterCountListener;
import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Country;
import com.nl.recipeapp.model.Ingredient;
import com.nl.recipeapp.model.Mealtype;
import com.nl.recipeapp.model.Recipe;
import com.nl.recipeapp.model.Religion;
import com.nl.recipeapp.model.TimeOfDay;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add extends Fragment {
    private View view;
    private TextView textview_recipeDescriptionCharacterCount;
    private EditText edittext_recipeName, edittext_recipeDescription;
    private Button button_bindIngredient, button_addNewIngredient, button_applyRecipe;

    // Variables for the Spinners
    private Spinner spinner_mealTypes, spinner_countries, spinner_religions, spinner_timeofday, spinner_ingredients;

    private ArrayAdapter<Religion> arrayAdapter_religions;
    private ArrayList<Religion> arraylist_religions;

    private ArrayAdapter<Country> arrayAdapter_countries;
    private ArrayList<Country> arraylist_countries;

    private ArrayAdapter<Mealtype> arrayAdapter_mealtypes;
    private ArrayList<Mealtype> arraylist_mealtypes;

    private ArrayAdapter<TimeOfDay> arrayAdapter_timeofday;
    private ArrayList<TimeOfDay> arraylist_timeofday;

    // Variables for the RecyclerView
    private RecyclerView recyclerview_ingredients;
    private ArrayList<Ingredient> arraylist_ingredients;
    private ArrayList<Ingredient> arraylist_ingredients_recyclerview;
    private ArrayAdapter<Ingredient> arrayadapter_ingredients;

    // Variables for the database connection
    private AddConnector addConnector;

    public Add() {
        arraylist_ingredients_recyclerview = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        // Initialize the Class variables
        edittext_recipeName = view.findViewById(R.id.addRecipe_edittext_recipeName);
        edittext_recipeDescription = view.findViewById(R.id.addRecipe_edittext_description);
        textview_recipeDescriptionCharacterCount = view.findViewById(R.id.addRecipe_textview_recipeDescriptionCharacterCount);

        button_bindIngredient = view.findViewById(R.id.addRecipe_btn_bindIngredient);
        button_addNewIngredient = view.findViewById(R.id.addRecipe_btn_addNewIngredient);
        button_applyRecipe = view.findViewById(R.id.addRecipe_btn_applyRecipe);

        // Initialize the ArrayLists
        arraylist_mealtypes = new ArrayList<>();
        arraylist_religions = new ArrayList<>();
        arraylist_countries = new ArrayList<>();
        arraylist_timeofday = new ArrayList<>();
        arraylist_ingredients = new ArrayList<>();

        // Create the connector that will pass requests towards the database
        addConnector = new AddConnector(this.getContext());
        addConnector.setAddRecipe(this);
        addConnector.setView(view);
        addConnector.initializeEditTexts();

        // Launch the initialization methods
        initializeSpinners();
        initializeInputFields();
        initializeButtons();
        initializeRecyclerView();

        return view;
    }

    /**
     * Gets called when the user resumes on this screen after initializing
     */
    public void onStart() {
        super.onStart();
        initializeArrayLists();  // Initializes the Spinner ArrayLists, which are used in the ArrayAdapters
    }

    /**
     * Initializes the Spinners on this Fragment
     */
    private void initializeSpinners() {
        // Initialize the Spinner objects
        spinner_mealTypes = view.findViewById(R.id.addRecipe_spinner_mealtype);
        spinner_countries = view.findViewById(R.id.addRecipe_spinner_recipeCountry);
        spinner_religions = view.findViewById(R.id.addRecipe_spinner_recipeReligion);
        spinner_timeofday = view.findViewById(R.id.addRecipe_spinner_recipeMealDaypart);
        spinner_ingredients = view.findViewById(R.id.addRecipe_spinner_bindIngredient);

        // Fill the ArrayLists which contain the String objects used in the dropdown lists
        initializeArrayLists();

        // Create and set the adapters for the spinners
        arrayAdapter_mealtypes = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_mealtypes);
        spinner_mealTypes.setAdapter(arrayAdapter_mealtypes);

        arrayAdapter_countries = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_countries);
        spinner_countries.setAdapter(arrayAdapter_countries);

        arrayAdapter_religions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_religions);
        spinner_religions.setAdapter(arrayAdapter_religions);

        arrayAdapter_timeofday = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_timeofday);
        spinner_timeofday.setAdapter(arrayAdapter_timeofday);

        arrayadapter_ingredients = new ArrayAdapter<Ingredient>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_ingredients) {
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(arraylist_ingredients.get(position).isApproved() == 0) {
                    // Set the item text color
                    tv.setTextColor(Color.RED);
                }
                else {
                    // Set the alternate item text color
//                    tv.setTextColor(Color.parseColor("#404041"));
                    tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorText));
                }
                return view;
            }
        };

        arrayadapter_ingredients.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_ingredients.setAdapter(arrayadapter_ingredients);
    }

    /**
     * Initializes the ArrayLists, used in the Spinners. This method is called once in the onCreate() and again every time the onStart() is called to refresh its contents
     * Every time this method is called, the ArrayLists with names are cleared first to prevent the list from filling with the wrong (or duplicate) items
     */
    private void initializeArrayLists() {
        addConnector.getTimeOfDay("AddRecipe");
        addConnector.getMealTypes("AddRecipe");
        addConnector.getCountries("AddRecipe");
        addConnector.getReligions("AddRecipe");

        // Makes sure which ingredients are displayed:
        // - Administrators should see all ingredients: approved as well as unapproved
        // - Users should see some ingredients: approved and only the unapproved ones they submitted
        if (((MainActivity)getActivity()).getCurrentUser() == null) {
            // If the user is not logged in, display approved Ingredients only
            addConnector.getApprovedIngredients("AddRecipe");
        } else if (((MainActivity)getActivity()).getCurrentUser().isAdministrator()) {
            // If the user is also an Administrator, display ALL ingredients
            addConnector.getAllIngredients("AddRecipe");
        } else {
            // If the user is not an Administrator, display approved ingredients + unapproved ingredients submitted by THIS user
            addConnector.getIngredientsForSpecificUser(((MainActivity)getActivity()).getCurrentUser().getUsername(), "AddRecipe");
        }
    }

    /**
     * Initializes the EditText elements on this Fragment
     */
    private void initializeInputFields() {
        // Create the listener on recipeDescription's EditText, so the character counter changes when the user is typing
        edittext_recipeDescription.addTextChangedListener(new CharacterCountListener(textview_recipeDescriptionCharacterCount, edittext_recipeDescription));
    }

    /**
     * Initializes the buttons which handle the following things:
     * - Binding Ingredients
     * - Adding new Ingredients (this is just a fragment switch to Add (package Ingredient)
     * - Applying a recipe
     */
    private void initializeButtons() {
        button_bindIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checks if the chosen Ingredient from the Spinner is already in the RecyclerView. If it is, display a Toast, otherwise add the Ingredient to the RecyclerView
                for (int c = 0; c < arraylist_ingredients_recyclerview.size(); c++) {
                    if (arraylist_ingredients_recyclerview.get(c).getName().equals(spinner_ingredients.getSelectedItem().toString())) {
                        Toast.makeText(getActivity(), "Het gekozen ingrediënt is al gekoppeld", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // If an Ingredient has not been found in the RecyclerView, it will be added right here
                for (int c = 0; c < arraylist_ingredients.size(); c++) {
                    if (arraylist_ingredients.get(c).getName().equals(spinner_ingredients.getSelectedItem().toString())) {
                        arraylist_ingredients_recyclerview.add(arraylist_ingredients.get(c));
                    }
                }

                // Make sure the RecyclerView gets refreshed
                recyclerview_ingredients.requestLayout();
            }
        });

        button_addNewIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getViewPager().setCurrentItem(4);
            }
        });

        button_applyRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if a user is currently logged in. If not, display a Toast. Otherwise, move on.
                if (((MainActivity)getActivity()).getCurrentUser() == null) {
                    Toast.makeText(getActivity(), "U moet ingelogd zijn om een recept toe te voegen", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // First, check if all the fields are filled in. If not, display a Toast accordingly
                    if (edittext_recipeName.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "U moet een receptnaam invullen", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Second, check if the description field isn't more than 255 characters. If it is, display a Toast
                    if (edittext_recipeDescription.getText().length() > 65535) {
                        Toast.makeText(getActivity(), "Uw receptomschrijving mag niet meer dan 65.535 karakters bevatten", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Third, check if there is at least one Technology bound to the Company. If there isn't display a Toast
                    if (arraylist_ingredients_recyclerview.isEmpty()) {
                        Toast.makeText(getActivity(), "U moet minimaal één ingrediënt hebben gekoppeld", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Prepare values to create the new Recipe: extract the Country code
                    String countryCode = "0";
                    for (int c = 0; c < arraylist_countries.size(); c++) {
                        if (arraylist_countries.get(c).getName().equals(spinner_countries.getSelectedItem().toString())) {
                            countryCode = arraylist_countries.get(c).getCountryCode();
                        }
                    }

                    // Prepare values to create the new Recipe: extract the Religion id
                    String religionId = "0";
                    for (int c = 0; c < arraylist_religions.size(); c++) {
                        if (arraylist_religions.get(c).getName().equals(spinner_religions.getSelectedItem().toString())) {
                            religionId = arraylist_religions.get(c).getId();
                        }
                    }

                    // Create the Recipe object and send it to the database
                    Recipe recipe = new Recipe(null, edittext_recipeName.getText().toString(), edittext_recipeDescription.getText().toString(), countryCode, ((MainActivity)getActivity()).getCurrentUser().getUsername(), spinner_mealTypes.getSelectedItem().toString(), religionId, spinner_timeofday.getSelectedItem().toString(), "0");
                    boolean value = addConnector.addRecipe(recipe);

                    // Reset the Spinners and EditTexts to let the User know the data has been sent to the Administrator
                    if (value) {
                        spinner_mealTypes.setSelection(0);
                        spinner_countries.setSelection(0);
                        spinner_religions.setSelection(0);
                        spinner_timeofday.setSelection(0);
                        spinner_ingredients.setSelection(0);
                        edittext_recipeName.setText("");
                        edittext_recipeDescription.setText("");
                        recyclerview_ingredients.requestLayout(); // Makes sure the recyclerView gets refreshed
                    }
                }
            }
        });
    }

    /**
     * Initializes the RecyclerView that holds the Ingredients
     */
    private void initializeRecyclerView() {
        recyclerview_ingredients = view.findViewById(R.id.addRecipe_recyclerView);
        AddRecyclerViewAdapter addRecipeRecyclerViewAdapter = new AddRecyclerViewAdapter(arraylist_ingredients_recyclerview, view.getContext(), recyclerview_ingredients);
        recyclerview_ingredients.setAdapter(addRecipeRecyclerViewAdapter);
        recyclerview_ingredients.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    // Religion
    public ArrayList<Religion> getArrayList_religions() {
        return arraylist_religions;
    }

    public ArrayAdapter<Religion> getArrayAdapter_religions() {
        return arrayAdapter_religions;
    }

    // Country
    public ArrayList<Country> getArrayList_countries() {
        return arraylist_countries;
    }

    public ArrayAdapter<Country> getArrayAdapter_countries() {
        return arrayAdapter_countries;
    }

    // Mealtype
    public ArrayList<Mealtype> getArrayList_mealtypes() {
        return arraylist_mealtypes;
    }

    public ArrayAdapter<Mealtype> getArrayAdapter_mealtypes() {
        return arrayAdapter_mealtypes;
    }

    // TimeOfDay
    public ArrayList<TimeOfDay> getArrayList_timeofday() {
        return arraylist_timeofday;
    }

    public ArrayAdapter<TimeOfDay> getArrayAdapter_timeofday() {
        return arrayAdapter_timeofday;
    }

    // Ingredient
    public ArrayList<Ingredient> getArrayList_ingredients() {
        return arraylist_ingredients;
    }

    public ArrayAdapter<Ingredient> getArrayAdapter_ingredients() {
        return arrayadapter_ingredients;
    }
}
