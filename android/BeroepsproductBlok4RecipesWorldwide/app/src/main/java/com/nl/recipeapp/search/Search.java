package com.nl.recipeapp.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nl.recipeapp.GeneralMethods;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Country;
import com.nl.recipeapp.model.Recipe;
import com.nl.recipeapp.model.Religion;
import com.nl.recipeapp.recipe.AddConnector;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private Spinner spinner_mealtype, spinner_country, spinner_religion, spinner_timeOfDay;
    private Button button_search;

    // Variables which are of use in the RecyclerView
    private RecyclerView recyclerview;
    private ArrayList<Recipe> recipes;
    private com.nl.recipeapp.search.Search_RecyclerViewAdapter recyclerviewAdapter;

    // Create the webconnectors
    private AddConnector connector_addRecipe;
    private Connector connector_search;

    // Variables for the ArrayLists
    ArrayList<String> arraylist_mealtypeNames, arraylist_daypartNames, arraylist_countryNames, arraylist_religionNames;
    ArrayList<Country> arraylist_countries;
    ArrayList<Religion> arraylist_religions;

    private GeneralMethods generalMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize the ArrayLists, used for initializing the RecyclerView and Spinners
        recipes = new ArrayList<>();
        arraylist_mealtypeNames = new ArrayList<>();
        arraylist_daypartNames = new ArrayList<>();
        arraylist_countryNames = new ArrayList<>();
        arraylist_religionNames = new ArrayList<>();
        arraylist_countries = new ArrayList<>();
        arraylist_religions = new ArrayList<>();

        // Initialize the connectors, used for getting and sending data to the database
        connector_addRecipe = new AddConnector(this);
        connector_search = new Connector(this);

        // Initialize the General Methods class, that's used for using similar methods
        generalMethods = new GeneralMethods(this);

        initializeSpinners();
        initializeButtons();
        initializeRecyclerView();
    }

    /**
     * Gets called when the user resumes on this screen after initializing
     */
    public void onStart() {
        super.onStart();
        initializeArrayLists();  // Initializes the Spinner ArrayLists, which are used in the ArrayAdapters
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
        initializeArrayLists();

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

    /**
     * Initializes the button that handles the search to recipes
     */
    private void initializeButtons() {
        button_search = findViewById(R.id.Search_btn_SearchDbase);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Extract the countrycode and religion id from the Spinners
                String countrycode = generalMethods.getCountryCodeFromName(spinner_country.getSelectedItem().toString());
                String religionId = generalMethods.getReligionIdFromName(spinner_religion.getSelectedItem().toString());

                String mealtype = "";
                if (spinner_mealtype.getSelectedItem().toString().equals("Selecteer een maaltijdsoort")) {
                    mealtype = "null";
                } else {
                    mealtype = spinner_mealtype.getSelectedItem().toString();
                }

                String timeOfDay = "";
                if (spinner_timeOfDay.getSelectedItem().toString().equals("Selecteer een tijdvak")) {
                    timeOfDay = "null";
                } else {
                    timeOfDay = spinner_timeOfDay.getSelectedItem().toString();
                }

                recipes = connector_search.searchRecipe(mealtype, countrycode, religionId, timeOfDay);
                recyclerviewAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Initializes the RecyclerView that holds the searched recipes
     */
    private void initializeRecyclerView() {
        recyclerview = findViewById(R.id.searchRecipe_recyclerView);
        recyclerviewAdapter = new com.nl.recipeapp.search.Search_RecyclerViewAdapter(recipes);
        recyclerview.setAdapter(recyclerviewAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Initializes the ArrayLists, used in the Spinners. This method is called once in the onCreate() and again every time the onStart() is called to refresh its contents
     * Every time this method is called, the ArrayLists with names are cleared first to prevent the list from filling with the wrong (or duplicate) items
     */
    private void initializeArrayLists() {
        ArrayList<String> arraylist_mealtypes = connector_addRecipe.getMealTypes();
        arraylist_mealtypeNames.clear();
        arraylist_mealtypeNames.add("Selecteer een maaltijdsoort");
        arraylist_mealtypeNames.addAll(arraylist_mealtypes);

        ArrayList<String> arraylist_dayparts = connector_addRecipe.getDayparts();
        arraylist_daypartNames.clear();
        arraylist_daypartNames.add("Selecteer een tijdvak");
        arraylist_daypartNames.addAll(arraylist_dayparts);

        arraylist_countries = connector_addRecipe.getCountries();
        arraylist_countryNames.clear();
        arraylist_countryNames.add("Selecteer een land");
        for (int c = 0; c < arraylist_countries.size(); c++) {
            arraylist_countryNames.add(arraylist_countries.get(c).getName());
        }

        arraylist_religions = connector_addRecipe.getReligions();
        arraylist_religionNames.clear();
        arraylist_religionNames.add("Selecteer een religie");
        for (int c = 0; c < arraylist_religions.size(); c++) {
            arraylist_religionNames.add(arraylist_religions.get(c).getName());
        }
    }
}
