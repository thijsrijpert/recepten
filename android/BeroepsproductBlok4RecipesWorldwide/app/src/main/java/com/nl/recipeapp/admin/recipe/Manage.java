package com.nl.recipeapp.admin.recipe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nl.recipeapp.GeneralMethods;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Country;
import com.nl.recipeapp.model.Ingredient;
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
    private GeneralMethods generalMethods;

    // Class variables used in both A and B
    private ArrayList<Recipe> arraylist_unapprovedRecipes, arraylist_approvedRecipes;
    private ArrayList<Country> arraylist_countries;
    private ArrayList<Religion> arraylist_religions;
    private ArrayList<String> arraylist_mealtypeNames, arraylist_timeOfDayNames, arraylist_countryNames, arraylist_religionNames;

    // Class variables (A means these variables are for approving or denying a recipe)
    private EditText edittext_A_name, edittext_A_description, edittext_A_username;
    private Spinner spinner_A_unapprovedRecipes, spinner_A_mealtype, spinner_A_religion, spinner_A_country, spinner_A_timeOfDay;
    private Button button_A_deny, button_A_approve;
    private RecyclerView recyclerview_A_ingredients;
    private ManageRecyclerViewAdapterA recyclerview_A_ingredients_adapter;
    private ArrayList<String> arraylist_unapprovedRecipeNames;
    private ArrayList<Ingredient> arraylist_ingredientsBoundToRecipe_A;
    private ArrayAdapter<String> arrayadapter_A_country, arrayadapter_A_mealtype, arrayadapter_A_religion, arrayadapter_A_timeOfDay;

    // Class variables (B means these variables are for managing a recipe)
    private EditText edittext_B_name, edittext_B_description, edittext_B_username;
    private Spinner spinner_B_approvedRecipes, spinner_B_mealtype, spinner_B_religion, spinner_B_country, spinner_B_timeOfDay;
    private Button button_B_saveChanges;
    private RecyclerView recyclerview_B_ingredients;
    private ManageRecyclerViewAdapterB recyclerview_B_ingredients_adapter;
    private ArrayList<Ingredient> arraylist_ingredientsBoundToRecipe_B;
    private ArrayList<String> arraylist_approvedRecipeNames;
    private ArrayAdapter<String> arrayadapter_B_country, arrayadapter_B_mealtype, arrayadapter_B_religion, arrayadapter_B_timeOfDay;

    public Manage() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_manage_recipes, container, false);

        connector = new Connector(this.getContext()); // Create the webserver connector for transferring queries and getting data from the database
        addConnector = new AddConnector(this.getContext()); // Create the webserver connector for getting data to fill the Spinners on this page
        generalMethods = new GeneralMethods(this.getContext()); // Create the GeneralMethods class, used for figuring out corresponding id's and names.

        // Create the ArrayLists used in both parts A and B
        arraylist_unapprovedRecipeNames = new ArrayList<>();
        arraylist_approvedRecipeNames = new ArrayList<>();
        arraylist_countryNames = new ArrayList<>();
        arraylist_religionNames = new ArrayList<>();
        arraylist_mealtypeNames = new ArrayList<>();
        arraylist_timeOfDayNames = new ArrayList<>();

        // Start the initialization methods for A: Approving or denying recipes
        arraylist_ingredientsBoundToRecipe_A = new ArrayList<>();
        initializeViewContent_A_EditText();
        initializeViewContent_A_RecyclerView();
        initializeViewContent_A_Spinners();
        initializeViewContent_A_Buttons();

        // Start the initialization methods for B: Managing recipes
        arraylist_ingredientsBoundToRecipe_B = new ArrayList<>();
        initializeViewContent_B_EditText();
        initializeViewContent_B_RecyclerView();
        initializeViewContent_B_Spinners();
        initializeViewContent_B_Buttons();

        // Initializes the Spinner ArrayLists, which are used in the ArrayAdapters
        initializeArrayLists();

        return view;
    }

    /**
     * Gets called when the user resumes on this screen after initializing
     */
    public void onStart() {
        super.onStart();
        initializeArrayLists();  // Initializes the Spinner ArrayLists, which are used in the ArrayAdapters
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
        recyclerview_A_ingredients_adapter = new ManageRecyclerViewAdapterA(arraylist_ingredientsBoundToRecipe_A);
        recyclerview_A_ingredients.setAdapter(recyclerview_A_ingredients_adapter);
        recyclerview_A_ingredients.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    /**
     * Initialize the Spinners for showing the data related to the selected recipe. This data can be changed before approval.
     */
    private void initializeViewContent_A_Spinners() {
        // Create an ArrayList<Recipe> which contains all the unapproved recipes
        arraylist_unapprovedRecipes = connector.getUnapprovedRecipes();

        // Initialize the Spinners
        spinner_A_unapprovedRecipes = view.findViewById(R.id.manageRecipes_A_spinner_unapprovedRecipes);
        spinner_A_mealtype = view.findViewById(R.id.manageRecipes_A_spinner_mealtype);
        spinner_A_religion = view.findViewById(R.id.manageRecipes_A_spinner_recipeReligion);
        spinner_A_country = view.findViewById(R.id.manageRecipes_A_spinner_recipeCountry);
        spinner_A_timeOfDay = view.findViewById(R.id.manageRecipes_A_spinner_recipeMealDaypart);

        // Initialize the Spinners' listeners
        spinner_A_unapprovedRecipes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int c = 0; c < arraylist_unapprovedRecipes.size(); c++) {
                    if (spinner_A_unapprovedRecipes.getSelectedItem().toString().equals(arraylist_unapprovedRecipes.get(c).getName())) {
                        // Initialize the EditTexts
                        edittext_A_name.setText(arraylist_unapprovedRecipes.get(c).getName());
                        edittext_A_username.setText(arraylist_unapprovedRecipes.get(c).getUsername());
                        edittext_A_description.setText(arraylist_unapprovedRecipes.get(c).getDescription());

                        // Initialize the Spinners
                        String countryName = generalMethods.getCountryNameFromCode(arraylist_unapprovedRecipes.get(c).getCountryCode());
                        int positionCountry = arrayadapter_A_country.getPosition(countryName);
                        spinner_A_country.setSelection(positionCountry);

                        String religionName = generalMethods.getReligionNameFromId(arraylist_unapprovedRecipes.get(c).getReligionId());
                        int positionReligion = arrayadapter_A_religion.getPosition(religionName);
                        spinner_A_religion.setSelection(positionReligion);

                        int positionMealtype = arrayadapter_A_mealtype.getPosition(arraylist_unapprovedRecipes.get(c).getMealtypeName());
                        spinner_A_mealtype.setSelection(positionMealtype);

                        int positionTimeOfDay = arrayadapter_A_timeOfDay.getPosition(arraylist_unapprovedRecipes.get(c).getTimeOfDay());
                        spinner_A_timeOfDay.setSelection(positionTimeOfDay);

                        // Initialize the items on the RecyclerView and notify its adapter that the data has been changed
                        arraylist_ingredientsBoundToRecipe_A = connector.getIngredientsBoundToRecipe(arraylist_unapprovedRecipes.get(c).getId());
                        recyclerview_A_ingredients_adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the Spinner Adapters
        ArrayAdapter<String> arrayadapter_unapprovedRecipes = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_unapprovedRecipeNames);
        spinner_A_unapprovedRecipes.setAdapter(arrayadapter_unapprovedRecipes);

        arrayadapter_A_mealtype = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_mealtypeNames);
        spinner_A_mealtype.setAdapter(arrayadapter_A_mealtype);

        arrayadapter_A_religion = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_religionNames);
        spinner_A_religion.setAdapter(arrayadapter_A_religion);

        arrayadapter_A_country = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_countryNames);
        spinner_A_country.setAdapter(arrayadapter_A_country);

        arrayadapter_A_timeOfDay = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_timeOfDayNames);
        spinner_A_timeOfDay.setAdapter(arrayadapter_A_timeOfDay);
    }

    /**
     * Initialize the Buttons for approving or denying recipes
     */
    private void initializeViewContent_A_Buttons() {
        // Initialize the Buttons
        button_A_approve = view.findViewById(R.id.manageRecipes_A_button_approveRecipe);
        button_A_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, check if there are ingredients that have to be approved. If there are these have to be either approved or denied. If this is not done, there could be a
                // foreign key constraint error: a recipe could be added that has an ingredient that isn't in the database yet (not approved, anyway).

                int unapprovedIngredientsCount = 0; // Make sure to fill this variable with the number of unapproved ingredients. Get these from the database?
                if (unapprovedIngredientsCount > 0) {
                    Toast.makeText(view.getContext(), "Er zijn nog ingrediënten die beheerd moeten worden", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Second: check if there are any recipes in the Spinner. If there are, proceed displaying the alert dialog
                if (spinner_A_unapprovedRecipes.getCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myDialog);
                    builder.setMessage("Weet u zeker dat u dit recept wilt goedkeuren?");
                    builder.setTitle("Recept goedkeuren");
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Recipe recipe = null;
                            for (int c = 0; c < arraylist_unapprovedRecipes.size(); c++) {
                                if (spinner_A_unapprovedRecipes.getSelectedItem().toString().equals(arraylist_unapprovedRecipes.get(c).getName())) {
                                    recipe = arraylist_unapprovedRecipes.get(c);
                                }
                            }

                            boolean succeeded = connector.approveRecipe(recipe);

                            if (succeeded) {
                                Toast.makeText(view.getContext(), recipe.getName() + " is goedgekeurd", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(view.getContext(), recipe.getName() + " kon niet worden goedgekeurd", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(view.getContext(), "Er zijn geen recepten om te beheren", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

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
        recyclerview_B_ingredients_adapter = new ManageRecyclerViewAdapterB(arraylist_ingredientsBoundToRecipe_B);
        recyclerview_B_ingredients.setAdapter(recyclerview_B_ingredients_adapter);
        recyclerview_B_ingredients.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    /**
     * Initialize the Spinners for changing data related to a recipe
     */
    private void initializeViewContent_B_Spinners() {
        // Create an ArrayList<Recipe> which contains all the approved recipes
        arraylist_approvedRecipes = connector.getApprovedRecipes();

        // Initialize the Spinners
        spinner_B_approvedRecipes = view.findViewById(R.id.manageRecipes_B_spinner_approvedRecipes);
        spinner_B_mealtype = view.findViewById(R.id.manageRecipes_B_spinner_mealtype);
        spinner_B_religion = view.findViewById(R.id.manageRecipes_B_spinner_recipeReligion);
        spinner_B_country = view.findViewById(R.id.manageRecipes_B_spinner_recipeCountry);
        spinner_B_timeOfDay = view.findViewById(R.id.manageRecipes_B_spinner_recipeMealDaypart);

        // Initialize the Spinners' listeners
        spinner_B_approvedRecipes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int c = 0; c < arraylist_approvedRecipes.size(); c++) {
                    if (spinner_B_approvedRecipes.getSelectedItem().toString().equals(arraylist_approvedRecipes.get(c).getName())) {
                        // Initialize the EditTexts
                        edittext_B_name.setText(arraylist_approvedRecipes.get(c).getName());
                        edittext_B_username.setText(arraylist_approvedRecipes.get(c).getUsername());
                        edittext_B_description.setText(arraylist_approvedRecipes.get(c).getDescription());

                        // Initialize the Spinners
                        String countryName = generalMethods.getCountryNameFromCode(arraylist_approvedRecipes.get(c).getCountryCode());
                        int positionCountry = arrayadapter_B_country.getPosition(countryName);
                        spinner_B_country.setSelection(positionCountry);

                        String religionName = generalMethods.getReligionNameFromId(arraylist_approvedRecipes.get(c).getReligionId());
                        int positionReligion = arrayadapter_B_religion.getPosition(religionName);
                        spinner_B_religion.setSelection(positionReligion);

                        int positionMealtype = arrayadapter_B_mealtype.getPosition(arraylist_approvedRecipes.get(c).getMealtypeName());
                        spinner_B_mealtype.setSelection(positionMealtype);

                        int positionTimeOfDay = arrayadapter_B_timeOfDay.getPosition(arraylist_approvedRecipes.get(c).getTimeOfDay());
                        spinner_B_timeOfDay.setSelection(positionTimeOfDay);

                        // Initialize the items on the RecyclerView and notify its adapter that the data has been changed
                        arraylist_ingredientsBoundToRecipe_B = connector.getIngredientsBoundToRecipe(arraylist_approvedRecipes.get(c).getId());
                        recyclerview_A_ingredients_adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the Spinner Adapters
        ArrayAdapter<String> arrayadapter_approvedRecipes = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_approvedRecipeNames);
        spinner_B_approvedRecipes.setAdapter(arrayadapter_approvedRecipes);

        arrayadapter_B_mealtype = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_mealtypeNames);
        spinner_B_mealtype.setAdapter(arrayadapter_B_mealtype);

        arrayadapter_B_religion = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_religionNames);
        spinner_B_religion.setAdapter(arrayadapter_B_religion);

        arrayadapter_B_country = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_countryNames);
        spinner_B_country.setAdapter(arrayadapter_B_country);

        arrayadapter_B_timeOfDay = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_timeOfDayNames);
        spinner_B_timeOfDay.setAdapter(arrayadapter_B_timeOfDay);
    }

    /**
     * Initialize the buttons for saving changes made to approved recipes
     */
    private void initializeViewContent_B_Buttons() {
        // Initialize the Buttons
        button_B_saveChanges = view.findViewById(R.id.manageRecipes_B_button_applyRecipeChanges);
    }



    ////// DEFAULT METHODS, USED IN BOTH PARTS A AND B //////
    /**
     * Initializes the ArrayLists, used in the Spinners in parts A and B. This method is called once in the onCreate() and again every time the onStart() is called to refresh its contents
     */
    private void initializeArrayLists() {
        arraylist_mealtypeNames = addConnector.getMealTypes();
        arraylist_timeOfDayNames = addConnector.getDayparts();

        for (int c = 0; c < arraylist_unapprovedRecipes.size(); c++) {
            arraylist_unapprovedRecipeNames.add(arraylist_unapprovedRecipes.get(c).getName());
        }

        for (int c = 0; c < arraylist_approvedRecipes.size(); c++) {
            arraylist_approvedRecipeNames.add(arraylist_approvedRecipes.get(c).getName());
        }

        arraylist_countries = addConnector.getCountries();
        for (int c = 0; c < arraylist_countries.size(); c++) {
            arraylist_countryNames.add(arraylist_countries.get(c).getName());
        }

        arraylist_religions = addConnector.getReligions();
        for (int c = 0; c < arraylist_religions.size(); c++) {
            arraylist_religionNames.add(arraylist_religions.get(c).getName());
        }
    }
}
