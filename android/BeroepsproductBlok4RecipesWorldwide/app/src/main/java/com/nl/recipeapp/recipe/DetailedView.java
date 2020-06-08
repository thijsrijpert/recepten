package com.nl.recipeapp.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nl.recipeapp.GeneralMethods;
import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;
import com.nl.recipeapp.SharedPreferencesManager;
import com.nl.recipeapp.model.Ingredient;
import com.nl.recipeapp.model.Recipe;
import com.nl.recipeapp.model.Review;
import com.nl.recipeapp.model.User;
import com.nl.recipeapp.review.Add;

import java.util.ArrayList;

public class DetailedView extends AppCompatActivity {
    private TextView textview_name;
    private EditText edittext_username, edittext_mealtype, edittext_country, edittext_religion, edittext_timeOfDay, edittext_description;
    private GeneralMethods generalMethods;
    private AddConnector connector_addRecipe;
    private Button button_addReview;

    private RecyclerView recyclerview_ingredients, recyclerview_reviews;
    private DetailedViewRecyclerViewAdapter_Ingredients recyclerviewAdapter_ingredients;
    private DetailedViewRecyclerViewAdapter_Reviews recyclerviewAdapter_reviews;
    private ArrayList<Ingredient> arraylist_ingredients;
    private ArrayList<Review> arraylist_reviews;

    private Recipe recipe;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        currentUser = SharedPreferencesManager.getInstance(this).getPref();

        // Create the ArrayLists so no errors will occur later on
        arraylist_ingredients = new ArrayList<>();
        arraylist_reviews = new ArrayList<>();

        // Initialize the General Methods class, that's used for using similar methods
        generalMethods = new GeneralMethods(this);
        generalMethods.setDetailedView(this);

        connector_addRecipe = new AddConnector(this);
        connector_addRecipe.setDetailedViewRecipe(this);

        // Get the SerializableExtra (The given Recipe)
        recipe = (Recipe) getIntent().getSerializableExtra("KEY");

        // Initialize the Ingredients and Reviews ArrayLists, which is used for displaying the Recipe's Ingredients and Reviews
        initializeInputFields();
        initializeButtons();
        initializeRecyclerViews();
        initializeArrayLists();

        // Set the Recipe's data in the input fields
        setInputFieldContents();
    }

    /**
     * Gets called when the user resumes on this screen after initializing
     */
    public void onStart() {
        super.onStart();
        initializeArrayLists();  // Initializes the Spinner ArrayLists, which are used in the ArrayAdapters
    }

    private void initializeArrayLists() {
        connector_addRecipe.getIngredientsForSpecificRecipe(recipe.getId(), "RecipeDetailedView");
        connector_addRecipe.getReviewsForSpecificRecipe(recipe.getId(), "RecipeDetailedView");
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

    public void setInputFieldContents() {
        textview_name.setText(recipe.getName());

        edittext_username.setText(recipe.getUsername());
        edittext_mealtype.setText(recipe.getMealtypeName());
        edittext_timeOfDay.setText(recipe.getTimeOfDay());
        edittext_description.setText(recipe.getDescription());

        generalMethods.getCountryNameFromCode(recipe.getCountryCode(), "DetailedView");
        generalMethods.getReligionNameFromId(recipe.getReligionId(), "DetailedView");
    }

    private void initializeButtons() {
        button_addReview = findViewById(R.id.recipeDetailedView_button_addReview);
        button_addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if a user is logged in
                if (currentUser == null) {
                    Toast.makeText(getApplicationContext(), "U moet ingelogd zijn voordat u een review kunt plaatsen.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), Add.class);
                intent.putExtra("RECIPE_ID", recipe.getId());
                startActivity(intent);
            }
        });
    }

    private void initializeRecyclerViews() {
        recyclerview_ingredients = findViewById(R.id.recipeDetailedView_recyclerViewIngredients);
        recyclerviewAdapter_ingredients = new DetailedViewRecyclerViewAdapter_Ingredients(arraylist_ingredients);
        recyclerview_ingredients.setAdapter(recyclerviewAdapter_ingredients);
        recyclerview_ingredients.setLayoutManager(new LinearLayoutManager(this));

        recyclerview_reviews = findViewById(R.id.recipeDetailedView_recyclerView_reviews);
        recyclerviewAdapter_reviews = new DetailedViewRecyclerViewAdapter_Reviews(arraylist_reviews);
        recyclerview_reviews.setAdapter(recyclerviewAdapter_reviews);
        recyclerview_reviews.setLayoutManager(new LinearLayoutManager(this));
    }

    // Ingredients
    public ArrayList<Ingredient> getArraylist_ingredients() {
        return arraylist_ingredients;
    }

    public DetailedViewRecyclerViewAdapter_Ingredients getRecyclerviewAdapter_ingredients() {
        return recyclerviewAdapter_ingredients;
    }

    // Reviews
    public ArrayList<Review> getArraylist_reviews() {
        return arraylist_reviews;
    }

    public DetailedViewRecyclerViewAdapter_Reviews getRecyclerviewAdapter_reviews() {
        return recyclerviewAdapter_reviews;
    }

    // EditTexts
    public EditText getEditTexts(int value) {
        EditText edittext = null;

        switch (value) {
            case 0:
                edittext = edittext_country;
            break;
            case 1:
                edittext = edittext_religion;
            break;
        }

        return edittext;
    }
}
