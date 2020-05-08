package com.nl.recipeapp.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.nl.recipeapp.GeneralMethods;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Ingredient;
import com.nl.recipeapp.model.Recipe;

import java.util.ArrayList;

public class DetailedView extends AppCompatActivity {
    private TextView textview_name;
    private EditText edittext_username, edittext_mealtype, edittext_country, edittext_religion, edittext_timeOfDay, edittext_description;
    private GeneralMethods generalMethods;
    private AddConnector connector;

    private RecyclerView recyclerview;
    private DetailedViewRecyclerViewAdapter recyclerviewAdapter;
    private ArrayList<Ingredient> arraylist_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        // Initialize the General Methods class, that's used for using similar methods
        generalMethods = new GeneralMethods(this);
        connector = new AddConnector(this);

        // Get the SerializableExtra (The given Recipe)
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("KEY");

        // Initialize the Ingredients ArrayList, which is used for displaying the Recipe's Ingredients
        arraylist_ingredients = connector.getIngredientsForSpecificRecipe(recipe.getId());

        initializeInputFields();
        initializeRecyclerView();

        // Set the Recipe's data in the input fields
        setInputFieldContents(recipe);
    }

    private void initializeInputFields() {
        textview_name = findViewById(R.id.recipeDetailedView_textview_recipeName);

        edittext_username = findViewById(R.id.recipeDetailedView_edittext_addedByUser);
        edittext_mealtype = findViewById(R.id.recipeDetailedView_edittext_mealtype);
        edittext_country = findViewById(R.id.recipeDetailedView_edittext_recipeCountry);
        edittext_religion = findViewById(R.id.recipeDetailedView_edittext_recipeReligion);
        edittext_timeOfDay = findViewById(R.id.recipeDetailedView_edittext_recipeMealDaypart);
        edittext_description = findViewById(R.id.recipeDetailedView_edittext_description);
    }

    private void setInputFieldContents(Recipe recipe) {
        textview_name.setText(recipe.getName());

        edittext_username.setText(recipe.getUsername());
        edittext_mealtype.setText(recipe.getMealtype_name());
        edittext_country.setText(generalMethods.getCountryNameFromCode(recipe.getCountrycode()));
        edittext_religion.setText(generalMethods.getReligionNameFromId(recipe.getReligion_id()));
        edittext_timeOfDay.setText(recipe.getTimeOfDay());
        edittext_description.setText(recipe.getDescription());

    }

    private void initializeRecyclerView() {
        recyclerview = findViewById(R.id.recipeDetailedView_recyclerView);
        recyclerviewAdapter = new DetailedViewRecyclerViewAdapter(arraylist_ingredients);
        recyclerview.setAdapter(recyclerviewAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}
