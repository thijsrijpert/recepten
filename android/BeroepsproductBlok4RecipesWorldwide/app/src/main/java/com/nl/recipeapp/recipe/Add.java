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

import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Country;
import com.nl.recipeapp.model.Ingredient;
import com.nl.recipeapp.model.Recipe;
import com.nl.recipeapp.model.Religion;

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
    private Spinner spinner_mealTypes, spinner_countries, spinner_religions, spinner_dayparts, spinner_ingredients;
    private ArrayList<String> arraylist_mealtypeNames, arraylist_mealtypes, arraylist_religionNames, arraylist_countryNames, arraylist_daypartNames, arraylist_dayparts, arraylist_ingredientNames;
    private ArrayList<Country> arraylist_countries;
    private ArrayList<Religion> arraylist_religions;

    // Variables for the RecyclerView
    private RecyclerView recyclerview_ingredients;
    private ArrayList<Ingredient> arraylist_ingredients;
    private ArrayList<Ingredient> arraylist_ingredients_recyclerview;
    private ArrayAdapter<String> arrayAdapter_ingredients;

    // Variables for the database connection
    private AddConnector addConnector;

    public Add() {
        arraylist_ingredients_recyclerview = new ArrayList<>();
        arraylist_ingredients = new ArrayList<>();
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
        arraylist_mealtypeNames = new ArrayList<>();
        arraylist_mealtypes = new ArrayList<>();
        arraylist_religionNames = new ArrayList<>();
        arraylist_countryNames = new ArrayList<>();
        arraylist_daypartNames = new ArrayList<>();
        arraylist_dayparts = new ArrayList<>();
        arraylist_ingredientNames = new ArrayList<>();

        // Create the connector that will pass requests towards the database
        addConnector = new AddConnector(this.getContext());
        addConnector.setView(view);
        addConnector.initializeEditTexts();

        // Launch the initialization methods
        initializeSpinners();
        initializeInputFields();
        initializeButtons();
        initializeRecyclerView();
        refreshIngredientsSpinner();

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
        spinner_dayparts = view.findViewById(R.id.addRecipe_spinner_recipeMealDaypart);
        spinner_ingredients = view.findViewById(R.id.addRecipe_spinner_bindIngredient);

        // Fill the ArrayLists which contain the String objects used in the dropdown lists
        initializeArrayLists();

        // Create and set the adapters for the spinners
        ArrayAdapter<String> arrayAdapter_mealtypes = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_mealtypeNames);
        spinner_mealTypes.setAdapter(arrayAdapter_mealtypes);

        ArrayAdapter<String> arrayAdapter_countries = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_countryNames);
        spinner_countries.setAdapter(arrayAdapter_countries);

        ArrayAdapter<String> arrayAdapter_religions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_religionNames);
        spinner_religions.setAdapter(arrayAdapter_religions);

        ArrayAdapter<String> arrayAdapter_dayparts = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_daypartNames);
        spinner_dayparts.setAdapter(arrayAdapter_dayparts);

        // Create the Ingredients ArrayLists
        arraylist_ingredientNames = new ArrayList<>();
        arraylist_ingredients.clear();

        // Makes sure which ingredients are displayed:
        // - Administrators should see all ingredients: approved as well as unapproved
        // - Users should see some ingredients: approved and only the unapproved ones they submitted
        if (((MainActivity)getActivity()).getCurrentUser() == null) {
            // If the user is not logged in, display approved Ingredients only
            arraylist_ingredients = addConnector.getApprovedIngredients();
        } else if (((MainActivity)getActivity()).getCurrentUser().isAdministrator()) {
            // If the user is also an Administrator, display ALL ingredients
            arraylist_ingredients = addConnector.getAllIngredients();
        } else {
            // If the user is not an Administrator, display approved ingredients + unapproved ingredients submitted by THIS user
            arraylist_ingredients = addConnector.getIngredientsForSpecificUser(((MainActivity)getActivity()).getCurrentUser().getUsername());
        }

        for (int c = 0; c < arraylist_ingredients.size(); c++) {
            arraylist_ingredientNames.add(arraylist_ingredients.get(c).getName());
        }

        arrayAdapter_ingredients = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_ingredientNames) {
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(!arraylist_ingredients.get(position).isApproved()) {
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

        arrayAdapter_ingredients.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_ingredients.setAdapter(arrayAdapter_ingredients);

        // Refreshes the spinner that contains the Ingredients. This has to be done so it stays up-to-date when an administrator approves/denies an ingredient or when a new ingredient is added
        spinner_ingredients.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    refreshIngredientsSpinner();
                }
                return false;
            }
        });
    }

    /**
     * Initializes the ArrayLists, used in the Spinners. This method is called once in the onCreate() and again every time the onStart() is called to refresh its contents
     * Every time this method is called, the ArrayLists with names are cleared first to prevent the list from filling with the wrong (or duplicate) items
     */
    private void initializeArrayLists() {
        arraylist_mealtypes = addConnector.getMealTypes();
        arraylist_mealtypeNames.clear();
        arraylist_mealtypeNames.add("Selecteer een maaltijdsoort");
        arraylist_mealtypeNames.addAll(arraylist_mealtypes);

        arraylist_dayparts = addConnector.getDayparts();
        arraylist_daypartNames.clear();
        arraylist_daypartNames.add("Selecteer een tijdvak");
        arraylist_daypartNames.addAll(arraylist_dayparts);

        arraylist_countries = addConnector.getCountries();
        arraylist_countryNames.clear();
        arraylist_countryNames.add("Selecteer een land");
        for (int c = 0; c < arraylist_countries.size(); c++) {
            arraylist_countryNames.add(arraylist_countries.get(c).getName());
        }

        arraylist_religions = addConnector.getReligions();
        arraylist_religionNames.clear();
        arraylist_religionNames.add("Selecteer een religie");
        for (int c = 0; c < arraylist_religions.size(); c++) {
            arraylist_religionNames.add(arraylist_religions.get(c).getName());
        }
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
                textview_recipeDescriptionCharacterCount.setText(edittext_recipeDescription.getText().length() + " / 65535", null);

                if (edittext_recipeDescription.getText().length() > 65535) {
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
                    ArrayList<Recipe> arraylist_recipes = addConnector.getRecipes();
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
                    Recipe recipe = new Recipe(null, edittext_recipeName.getText().toString(), edittext_recipeDescription.getText().toString(), countryCode, ((MainActivity)getActivity()).getCurrentUser().getUsername(), spinner_mealTypes.getSelectedItem().toString(), religionId, spinner_dayparts.getSelectedItem().toString());
                    boolean value = addConnector.addRecipe(recipe);

                    // Reset the Spinners and EditTexts to let the User know the data has been sent to the Administrator
                    if (value) {
                        spinner_mealTypes.setSelection(0);
                        spinner_countries.setSelection(0);
                        spinner_religions.setSelection(0);
                        spinner_dayparts.setSelection(0);
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

    /**
     * Refreshes the Ingredients Spinner (this is done when touching the Spinner and when inflating this Fragment so the Spinner has the most recent Ingredients)
     */
    private void refreshIngredientsSpinner() {
        arraylist_ingredientNames.clear();
        arraylist_ingredients.clear();

        if (((MainActivity)getActivity()).getCurrentUser() == null) {
            // If the user is not logged in, display approved Ingredients only
            arraylist_ingredients = addConnector.getApprovedIngredients();
        } else if (((MainActivity)getActivity()).getCurrentUser().isAdministrator()) {
            // If the user is also an Administrator, display ALL ingredients
            arraylist_ingredients = addConnector.getAllIngredients();
        } else {
            // If the user is not an Administrator, display approved ingredients + unapproved ingredients submitted by THIS user
            arraylist_ingredients = addConnector.getIngredientsForSpecificUser(((MainActivity)getActivity()).getCurrentUser().getUsername());
        }

        for (int c = 0; c < arraylist_ingredients.size(); c++) {
            arraylist_ingredientNames.add(arraylist_ingredients.get(c).getName());
        }

        arrayAdapter_ingredients.notifyDataSetChanged();
    }
}
