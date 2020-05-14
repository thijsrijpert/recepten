package com.nl.recipeapp.admin.recipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Country;
import com.nl.recipeapp.model.Recipe;
import com.nl.recipeapp.model.Religion;
import com.nl.recipeapp.recipe.AddConnector;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Manage extends Fragment {
    // Class variables for general use
    private View view;
    private Connector connector;
    private AddConnector addConnector;

    // Class variables (A means these variables are for approving or denying a recipe)
    private EditText edittext_A_name, edittext_A_description, edittext_A_username;
    private Spinner spinner_A_unapprovedRecipes, spinner_A_mealtype, spinner_A_religion, spinner_A_country, spinner_A_dayparts;
    private Button button_A_deny, button_A_approve;
    private RecyclerView recyclerview_A_ingredients;

    // Class variables (B means these variables are for managing a recipe)
    private EditText edittext_B_name, edittext_B_description, edittext_B_username;
    private Spinner spinner_B_approvedRecipes, spinner_B_mealtype, spinner_B_religion, spinner_B_country, spinner_B_dayparts;
    private Button button_B_saveChanges;
    private RecyclerView recyclerview_B_ingredients;

    public Manage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_manage_recipes, container, false);

        connector = new Connector(this.getContext()); // Create the webserver connector for transferring queries and getting data from the database
        addConnector = new AddConnector(this.getContext()); // Create the webserver connector for getting data to fill the Spinners on this page

        // Start the initialization methods for A: Approving or denying recipes
        initializeViewContent_A_EditText();
        initializeViewContent_A_RecyclerView();
        initializeViewContent_A_Spinners();
        initializeViewContent_A_Buttons();

        // Start the initialization methods for B: Managing recipes
        initializeViewContent_B_EditText();
        initializeViewContent_B_RecyclerView();
        initializeViewContent_B_Spinners();
        initializeViewContent_B_Buttons();

        return view;
    }

    ////// INITIALIZATION METHODS FOR PART A: APPROVING OR DENYING RECIPES //////
    /**
     * Initialize the EditTexts for viewing information in EditTexts (like a name, description etc.)
     */
    private void initializeViewContent_A_EditText() {
        // Initialize the EditTexts
        edittext_A_name = view.findViewById(R.id.manageRecipes_A_edittext_recipeName);
        edittext_A_username = view.findViewById(R.id.manageRecipes_A_edittext_addedByUser);
        edittext_A_description = view.findViewById(R.id.manageRecipes_A_edittext_description);
    }

    /**
     * Initialize the RecyclerView for showing ingredients belonging to a recipe
     */
    private void initializeViewContent_A_RecyclerView() {
        // Initialize the RecyclerView
        recyclerview_A_ingredients = view.findViewById(R.id.manageRecipes_A_recyclerView);
    }

    /**
     * Initialize the Spinners for showing the data related to the selected recipe. This data can be changed before approval.
     */
    private void initializeViewContent_A_Spinners() {
        // Create an ArrayList<Recipe> which contains all the unapproved recipes
        ArrayList<Recipe> arraylist_unapprovedRecipes = connector.getUnapprovedRecipes();

        // Initialize the Spinners
        spinner_A_unapprovedRecipes = view.findViewById(R.id.manageRecipes_A_spinner_unapprovedRecipes);
        spinner_A_mealtype = view.findViewById(R.id.manageRecipes_A_spinner_mealtype);
        spinner_A_religion = view.findViewById(R.id.manageRecipes_A_spinner_recipeReligion);
        spinner_A_country = view.findViewById(R.id.manageRecipes_A_spinner_recipeCountry);
        spinner_A_dayparts = view.findViewById(R.id.manageRecipes_A_spinner_recipeMealDaypart);

        // Initialize the Spinner ArrayLists, which are used in the ArrayAdapters
        ArrayList<String> arraylist_mealtypeNames = addConnector.getMealTypes();
        ArrayList<String> arraylist_daypartNames = addConnector.getDayparts();

        ArrayList<String> arraylist_unapprovedRecipeNames = new ArrayList<>();
        for (int c = 0; c < arraylist_unapprovedRecipes.size(); c++) {
            arraylist_unapprovedRecipeNames.add(arraylist_unapprovedRecipes.get(c).getName());
        }

        ArrayList<Country> arraylist_countries = addConnector.getCountries();
        ArrayList<String> arraylist_countryNames = new ArrayList<>();
        for (int c = 0; c < arraylist_countries.size(); c++) {
            arraylist_countryNames.add(arraylist_countries.get(c).getName());
        }

        ArrayList<Religion> arraylist_religions = addConnector.getReligions();
        ArrayList<String> arraylist_religionNames = new ArrayList<>();
        for (int c = 0; c < arraylist_religions.size(); c++) {
            arraylist_religionNames.add(arraylist_religions.get(c).getName());
        }

        // Initialize the Spinner Adapters
        ArrayAdapter<String> arrayadapter_unapprovedRecipes = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_unapprovedRecipeNames);
        spinner_A_unapprovedRecipes.setAdapter(arrayadapter_unapprovedRecipes);

        ArrayAdapter<String> arrayadapter_mealtype = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_mealtypeNames);
        spinner_A_mealtype.setAdapter(arrayadapter_mealtype);

        ArrayAdapter<String> arrayadapter_religion = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_religionNames);
        spinner_A_religion.setAdapter(arrayadapter_religion);

        ArrayAdapter<String> arrayadapter_country = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_countryNames);
        spinner_A_country.setAdapter(arrayadapter_country);

        ArrayAdapter<String> arrayadapter_dayparts = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_daypartNames);
        spinner_A_dayparts.setAdapter(arrayadapter_dayparts);
    }

    /**
     * Initialize the Buttons for approving or denying recipes
     */
    private void initializeViewContent_A_Buttons() {
        // Initialize the Buttons
        button_A_approve = view.findViewById(R.id.manageRecipes_A_button_approveRecipe);
        button_A_deny = view.findViewById(R.id.manageRecipes_A_button_denyRecipe);
    }



    ////// INITIALIZATION METHODS FOR PART A: APPROVING OR DENYING RECIPES //////
    /**
     * Initialize the EditText input fields for changing text related to a recipe
     */
    private void initializeViewContent_B_EditText() {
        // Initialize the EditTexts
        edittext_B_name = view.findViewById(R.id.manageRecipes_B_edittext_recipeName);
        edittext_B_username = view.findViewById(R.id.manageRecipes_B_edittext_addedByUser);
        edittext_B_description = view.findViewById(R.id.manageRecipes_B_edittext_description);
    }

    /**
     * Initialize the recyclerview for removing Ingredients related to a recipe
     */
    private void initializeViewContent_B_RecyclerView() {
        recyclerview_B_ingredients = view.findViewById(R.id.manageRecipes_B_recyclerView);
    }

    /**
     * Initialize the Spinners for changing data related to a recipe
     */
    private void initializeViewContent_B_Spinners() {
        // Create an ArrayList<Recipe> which contains all the approved recipes
        ArrayList<Recipe> arraylist_approvedRecipes = connector.getApprovedRecipes();

        // Initialize the Spinners
        spinner_B_approvedRecipes = view.findViewById(R.id.manageRecipes_B_spinner_approvedRecipes);
        spinner_B_mealtype = view.findViewById(R.id.manageRecipes_B_spinner_mealtype);
        spinner_B_religion = view.findViewById(R.id.manageRecipes_B_spinner_recipeReligion);
        spinner_B_country = view.findViewById(R.id.manageRecipes_B_spinner_recipeCountry);
        spinner_B_dayparts = view.findViewById(R.id.manageRecipes_B_spinner_recipeMealDaypart);
    }

    /**
     * Initialize the buttons for saving changes made to approved recipes
     */
    private void initializeViewContent_B_Buttons() {
        // Initialize the Buttons
        button_B_saveChanges = view.findViewById(R.id.manageRecipes_B_button_applyRecipeChanges);
    }
}
