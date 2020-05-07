package com.nl.recipeapp.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Country;
import com.nl.recipeapp.model.Recipe;
import com.nl.recipeapp.model.Religion;
import com.nl.recipeapp.recipe.AddConnector;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private Spinner spinner_mealtype, spinner_country, spinner_religion, spinner_timeOfDay;
    private RecyclerView recyclerView;
    private AddConnector addRecipe_webserverConnector;
    private ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize the ArrayLists, used for initializing the RecyclerView
        recipes = new ArrayList<>();

        // Initialize the class variables
        addRecipe_webserverConnector = new AddConnector(this);

        initializeSpinners();
        initializeRecyclerView();
    }

    /**
     * Initializes the Spinners, used for adding parameters to the search options of the user.
     */
    private void initializeSpinners() {
        spinner_mealtype = findViewById(R.id.SearchRecipe_spinner_mealtype);
        spinner_country = findViewById(R.id.searchRecipe_spinner_recipeCountry);
        spinner_religion = findViewById(R.id.searchRecipe_spinner_recipeReligion);
        spinner_timeOfDay = findViewById(R.id.searchRecipe_spinner_recipeMealDaypart);

        // Initialize the Spinner ArrayLists, which are used in the ArrayAdapters
        ArrayList<String> arraylist_mealtypeNames = addRecipe_webserverConnector.getMealTypes();
        ArrayList<String> arraylist_daypartNames = addRecipe_webserverConnector.getDayparts();

        ArrayList<Country> arraylist_countries = addRecipe_webserverConnector.getCountries();
        ArrayList<String> arraylist_countryNames = new ArrayList<>();
        for (int c = 0; c < arraylist_countries.size(); c++) {
            arraylist_countryNames.add(arraylist_countries.get(c).getName());
        }

        ArrayList<Religion> arraylist_religions = addRecipe_webserverConnector.getReligions();
        ArrayList<String> arraylist_religionNames = new ArrayList<>();
        for (int c = 0; c < arraylist_religions.size(); c++) {
            arraylist_religionNames.add(arraylist_religions.get(c).getName());
        }

        // Initialize the Spinner Adapters
        ArrayAdapter<String> arrayadapter_mealtype = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arraylist_mealtypeNames);
        spinner_mealtype.setAdapter(arrayadapter_mealtype);

        ArrayAdapter<String> arrayadapter_religion = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arraylist_religionNames);
        spinner_religion.setAdapter(arrayadapter_religion);

        ArrayAdapter<String> arrayadapter_country = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arraylist_countryNames);
        spinner_country.setAdapter(arrayadapter_country);

        ArrayAdapter<String> arrayadapter_dayparts = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arraylist_daypartNames);
        spinner_timeOfDay.setAdapter(arrayadapter_dayparts);
    }


    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.searchRecipe_recyclerView);
        com.nl.recipeapp.search.Search_RecyclerViewAdapter searchRecyclerViewAdapter = new com.nl.recipeapp.search.Search_RecyclerViewAdapter(recipes);
        recyclerView.setAdapter(searchRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
