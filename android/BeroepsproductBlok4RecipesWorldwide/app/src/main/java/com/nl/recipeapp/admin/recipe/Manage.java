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
import android.widget.TextView;
import android.widget.Toast;

import com.nl.recipeapp.CharacterCountListener;
import com.nl.recipeapp.GeneralMethods;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Country;
import com.nl.recipeapp.model.Ingredient;
import com.nl.recipeapp.model.Mealtype;
import com.nl.recipeapp.model.Recipe;
import com.nl.recipeapp.model.Religion;
import com.nl.recipeapp.model.TimeOfDay;
import com.nl.recipeapp.recipe.AddConnector;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Manage extends Fragment {
    // Class variables for general use
    private View view;
    private com.nl.recipeapp.admin.recipe.Connector connectorRecipes;
    private com.nl.recipeapp.admin.ingredients.Connector connectorIngredients;
    private AddConnector addConnectorRecipe;

    private GeneralMethods generalMethods;

    // Class variables used in both A and B
    private ArrayList<Recipe> arraylist_unapprovedRecipes, arraylist_approvedRecipes;
    private ArrayList<Country> arraylist_countries;
    private ArrayList<Religion> arraylist_religions;
    private ArrayList<Mealtype> arraylist_mealtypes;
    private ArrayList<TimeOfDay> arraylist_timeofday;

    // Class variables (A means these variables are for approving or denying a recipe)
    private EditText edittext_A_name, edittext_A_description, edittext_A_username;
    private TextView textview_A_descriptionCharCount;
    private Spinner spinner_A_unapprovedRecipes, spinner_A_mealtype, spinner_A_religion, spinner_A_country, spinner_A_timeOfDay;
    private Button button_A_deny, button_A_approve;
    private RecyclerView recyclerview_A_ingredients;
    private ManageRecyclerViewAdapterA recyclerview_A_ingredients_adapter;
    private ArrayList<String> arraylist_unapprovedRecipeNames;
    private ArrayList<Ingredient> arraylist_ingredientsBoundToRecipe_A, arraylist_unapprovedIngredients;
    private ArrayAdapter<String> arrayadapter_unapprovedRecipes;

    private ArrayAdapter<Religion> arrayadapter_A_religion;
    private ArrayAdapter<Country> arrayadapter_A_country;
    private ArrayAdapter<Mealtype> arrayadapter_A_mealtype;
    private ArrayAdapter<TimeOfDay> arrayadapter_A_timeofday;

    // Class variables (B means these variables are for managing a recipe)
    private EditText edittext_B_name, edittext_B_description, edittext_B_username;
    private TextView textview_B_descriptionCharCount;
    private Spinner spinner_B_approvedRecipes, spinner_B_mealtype, spinner_B_religion, spinner_B_country, spinner_B_timeOfDay, spinner_B_isApproved;
    private Button button_B_saveChanges, button_B_delete;
    private RecyclerView recyclerview_B_ingredients;
    private ManageRecyclerViewAdapterB recyclerview_B_ingredients_adapter;
    private ArrayList<Ingredient> arraylist_ingredientsBoundToRecipe_B;
    private ArrayList<String> arraylist_approvedRecipeNames, arraylist_isApproved;
    private ArrayAdapter<String> arrayadapter_approvedRecipes, arrayadapter_B_isApproved;

    private ArrayAdapter<Religion> arrayadapter_B_religion;
    private ArrayAdapter<Country> arrayadapter_B_country;
    private ArrayAdapter<Mealtype> arrayadapter_B_mealtype;
    private ArrayAdapter<TimeOfDay> arrayadapter_B_timeofday;

    public Manage() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_manage_admin_recipes, container, false);

        connectorRecipes = new Connector(this.getContext()); // Create the webserver connector for transferring queries and getting data from the database
        connectorIngredients = new com.nl.recipeapp.admin.ingredients.Connector(this.getContext()); // Create the webserver connector for getting Ingredients. (Mostly used for checking purposes)
        addConnectorRecipe = new AddConnector(this.getContext()); // Create the webserver connector for getting data to fill the Spinners on this page
        generalMethods = new GeneralMethods(this.getContext()); // Create the GeneralMethods class, used for figuring out corresponding id's and names.

        // Create the ArrayLists used in both parts A and B
        arraylist_unapprovedRecipeNames = new ArrayList<>();
        arraylist_unapprovedRecipes = new ArrayList<>();
        arraylist_approvedRecipeNames = new ArrayList<>();
        arraylist_approvedRecipes = new ArrayList<>();
        arraylist_countries = new ArrayList<>();
        arraylist_religions = new ArrayList<>();
        arraylist_mealtypes = new ArrayList<>();
        arraylist_timeofday = new ArrayList<>();

        arraylist_isApproved = new ArrayList<>();
        arraylist_isApproved.add("Ja");
        arraylist_isApproved.add("Nee");

        addConnectorRecipe.setManageRecipe(this);
        connectorRecipes.setManageRecipe(this);
        connectorIngredients.setManageRecipe(this);

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

        return view;
    }

    /**
     * Gets called when the user resumes on this screen after initializing
     */
    public void onStart() {
        super.onStart();
        initializeArrayLists();  // Initializes the Spinner ArrayLists, which are used in the ArrayAdapters
        updateViewContent_A();
        updateViewContent_B();
    }

    ////// INITIALIZATION METHODS FOR PART A: APPROVING OR DENYING RECIPES //////
    /**
     * Initialize the EditTexts for viewing information in EditTexts (like a name, description etc.)
     */
    private void initializeViewContent_A_EditText() {
        // Initialize the EditTexts
        edittext_A_name = view.findViewById(R.id.manageRecipes_A_edittext_recipeName);
        edittext_A_username = view.findViewById(R.id.manageRecipes_A_edittext_addedByUser);

        textview_A_descriptionCharCount = view.findViewById(R.id.manageRecipes_A_textview_receptomschrijvingCharacterCount);
        edittext_A_description = view.findViewById(R.id.manageRecipes_A_edittext_description);
        edittext_A_description.addTextChangedListener(new CharacterCountListener(textview_A_descriptionCharCount, edittext_A_description));
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
                        Country country = null;
                        for (int i = 0; i < arraylist_countries.size(); i++) {
                            if (arraylist_unapprovedRecipes.get(c).getCountryCode().equals(arraylist_countries.get(i).getCountryCode())) {
                                country = arraylist_countries.get(i);
                            }
                        }
                        int positionCountry = arrayadapter_A_country.getPosition(country);
                        spinner_A_country.setSelection(positionCountry);

                        Religion religion = null;
                        for (int i = 0; i < arraylist_religions.size(); i++) {
                            if (arraylist_unapprovedRecipes.get(c).getReligionId().equals(arraylist_religions.get(i).getId())) {
                                religion = arraylist_religions.get(i);
                            }
                        }
                        int positionReligion = arrayadapter_A_religion.getPosition(religion);
                        spinner_A_religion.setSelection(positionReligion);

                        Mealtype mealtype = null;
                        for (int i = 0; i < arraylist_mealtypes.size(); i++) {
                            if (arraylist_unapprovedRecipes.get(c).getMealtypeName().equals(arraylist_mealtypes.get(i).getName())) {
                                mealtype = arraylist_mealtypes.get(i);
                            }
                        }
                        int positionMealtype = arrayadapter_A_mealtype.getPosition(mealtype);
                        spinner_A_mealtype.setSelection(positionMealtype);

                        TimeOfDay timeofday = null;
                        for (int i = 0; i < arraylist_timeofday.size(); i++) {
                            if (arraylist_unapprovedRecipes.get(c).getTimeOfDay().equals(arraylist_timeofday.get(i).getName())) {
                                timeofday = arraylist_timeofday.get(i);
                            }
                        }
                        int positionTimeOfDay = arrayadapter_A_timeofday.getPosition(timeofday);
                        spinner_A_timeOfDay.setSelection(positionTimeOfDay);

                        // Initialize the items on the RecyclerView and notify its adapter that the data has been changed
                        arraylist_ingredientsBoundToRecipe_A = connectorRecipes.getIngredientsBoundToRecipe(arraylist_unapprovedRecipes.get(c).getId());
                        recyclerview_A_ingredients_adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the Spinner Adapters
        arrayadapter_unapprovedRecipes = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_unapprovedRecipeNames);
        spinner_A_unapprovedRecipes.setAdapter(arrayadapter_unapprovedRecipes);

        arrayadapter_A_mealtype = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_mealtypes);
        spinner_A_mealtype.setAdapter(arrayadapter_A_mealtype);

        arrayadapter_A_religion = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_religions);
        spinner_A_religion.setAdapter(arrayadapter_A_religion);

        arrayadapter_A_country = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_countries);
        spinner_A_country.setAdapter(arrayadapter_A_country);

        arrayadapter_A_timeofday = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_timeofday);
        spinner_A_timeOfDay.setAdapter(arrayadapter_A_timeofday);
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
                connectorIngredients.getUnapprovedIngredients("ManageRecipe"); // Make sure to fill this variable with the number of unapproved ingredients.
                if (arraylist_unapprovedIngredients.size() > 0) {
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

                            boolean succeeded = connectorRecipes.approveRecipe(recipe);

                            if (succeeded) {
                                Toast.makeText(view.getContext(), recipe.getName() + " is goedgekeurd", Toast.LENGTH_SHORT).show();
                                initializeArrayLists();
                                updateViewContent_A();
                                updateViewContent_B();
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
                }
            }
        });

        button_A_deny = view.findViewById(R.id.manageRecipes_A_button_denyRecipe);
        button_A_deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_A_unapprovedRecipes.getCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myDialog);
                    builder.setMessage("Weet u zeker dat u dit recept wilt afkeuren?");
                    builder.setTitle("Recept afkeuren");
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Recipe recipe = null;
                            for (int c = 0; c < arraylist_unapprovedRecipes.size(); c++) {
                                if (spinner_A_unapprovedRecipes.getSelectedItem().toString().equals(arraylist_unapprovedRecipes.get(c).getName())) {
                                    recipe = arraylist_unapprovedRecipes.get(c);
                                }
                            }

                            boolean succeeded = connectorRecipes.denyRecipe(recipe);

                            if (succeeded) {
                                Toast.makeText(view.getContext(), recipe.getName() + " is afgekeurd", Toast.LENGTH_SHORT).show();
                                initializeArrayLists();
                                updateViewContent_A();
                                updateViewContent_B();
                            } else {
                                Toast.makeText(view.getContext(), recipe.getName() + " kon niet worden afgekeurd", Toast.LENGTH_SHORT).show();
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
                }
            }
        });
    }

    /**
     * Updates part A after approving or denying a recipe
     */
    private void updateViewContent_A() {
        arrayadapter_unapprovedRecipes.notifyDataSetChanged();

        if (spinner_A_unapprovedRecipes.getCount() > 0) {
            spinner_A_unapprovedRecipes.setSelection(0);
        } else {
            edittext_A_name.setText(null);
            edittext_A_username.setText(null);
            edittext_A_description.setText(null);
        }
    }



    ////// INITIALIZATION METHODS FOR PART B: MANAGING RECIPES //////
    /**
     * Initialize the EditText input fields for changing text related to a recipe
     */
    private void initializeViewContent_B_EditText() {
        // Initialize the EditTexts
        edittext_B_name = view.findViewById(R.id.manageRecipes_B_edittext_recipeName);
        edittext_B_username = view.findViewById(R.id.manageRecipes_B_edittext_addedByUser);

        textview_B_descriptionCharCount = view.findViewById(R.id.manageRecipes_B_textview_receptomschrijvingCharacterCount);
        edittext_B_description = view.findViewById(R.id.manageRecipes_B_edittext_description);
        edittext_B_description.addTextChangedListener(new CharacterCountListener(textview_B_descriptionCharCount, edittext_B_description));
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
        // Initialize the Spinners
        spinner_B_approvedRecipes = view.findViewById(R.id.manageRecipes_B_spinner_approvedRecipes);
        spinner_B_mealtype = view.findViewById(R.id.manageRecipes_B_spinner_recipeMealtype);
        spinner_B_religion = view.findViewById(R.id.manageRecipes_B_spinner_recipeReligion);
        spinner_B_country = view.findViewById(R.id.manageRecipes_B_spinner_recipeCountry);
        spinner_B_timeOfDay = view.findViewById(R.id.manageRecipes_B_spinner_recipeMealDaypart);
        spinner_B_isApproved = view.findViewById(R.id.manageRecipes_B_spinner_recipeIsApproved);

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
                        Country country = null;
                        for (int i = 0; i < arraylist_countries.size(); i++) {
                            if (arraylist_unapprovedRecipes.get(c).getCountryCode().equals(arraylist_countries.get(i).getCountryCode())) {
                                country = arraylist_countries.get(i);
                            }
                        }
                        int positionCountry = arrayadapter_B_country.getPosition(country);
                        spinner_B_country.setSelection(positionCountry);

                        Religion religion = null;
                        for (int i = 0; i < arraylist_religions.size(); i++) {
                            if (arraylist_unapprovedRecipes.get(c).getReligionId().equals(arraylist_religions.get(i).getId())) {
                                religion = arraylist_religions.get(i);
                            }
                        }
                        int positionReligion = arrayadapter_B_religion.getPosition(religion);
                        spinner_B_religion.setSelection(positionReligion);

                        Mealtype mealtype = null;
                        for (int i = 0; i < arraylist_mealtypes.size(); i++) {
                            if (arraylist_unapprovedRecipes.get(c).getMealtypeName().equals(arraylist_mealtypes.get(i).getName())) {
                                mealtype = arraylist_mealtypes.get(i);
                            }
                        }
                        int positionMealtype = arrayadapter_B_mealtype.getPosition(mealtype);
                        spinner_B_mealtype.setSelection(positionMealtype);

                        TimeOfDay timeofday = null;
                        for (int i = 0; i < arraylist_timeofday.size(); i++) {
                            if (arraylist_unapprovedRecipes.get(c).getTimeOfDay().equals(arraylist_timeofday.get(i).getName())) {
                                timeofday = arraylist_timeofday.get(i);
                            }
                        }
                        int positionTimeOfDay = arrayadapter_B_timeofday.getPosition(timeofday);
                        spinner_B_timeOfDay.setSelection(positionTimeOfDay);

                        // Initialize the items on the RecyclerView and notify its adapter that the data has been changed
                        arraylist_ingredientsBoundToRecipe_B = connectorRecipes.getIngredientsBoundToRecipe(arraylist_approvedRecipes.get(c).getId());
                        recyclerview_A_ingredients_adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the Spinner Adapters
        arrayadapter_approvedRecipes = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_approvedRecipeNames);
        spinner_B_approvedRecipes.setAdapter(arrayadapter_approvedRecipes);

        arrayadapter_B_mealtype = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_mealtypes);
        spinner_B_mealtype.setAdapter(arrayadapter_B_mealtype);

        arrayadapter_B_religion = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_religions);
        spinner_B_religion.setAdapter(arrayadapter_B_religion);

        arrayadapter_B_country = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_countries);
        spinner_B_country.setAdapter(arrayadapter_B_country);

        arrayadapter_B_timeofday = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_timeofday);
        spinner_B_timeOfDay.setAdapter(arrayadapter_B_timeofday);

        arrayadapter_B_isApproved = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_isApproved);
        spinner_B_isApproved.setAdapter(arrayadapter_B_isApproved);
    }

    /**
     * Initialize the buttons for saving changes made to approved recipes
     */
    private void initializeViewContent_B_Buttons() {
        // Initialize the Buttons
        button_B_saveChanges = view.findViewById(R.id.manageRecipes_B_button_applyRecipeChanges);
        button_B_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_B_approvedRecipes.getCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myDialog);
                    builder.setMessage("Weet u zeker dat u dit recept wilt wijzigen?");
                    builder.setTitle("Recept wijzigen");
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Recipe recipe = null;
                            for (int c = 0; c < arraylist_approvedRecipes.size(); c++) {
                                if (spinner_B_approvedRecipes.getSelectedItem().toString().equals(arraylist_approvedRecipes.get(c).getName())) {
                                    String approved = "1";
                                    if (spinner_B_isApproved.getSelectedItem().toString().equals("Ja")) {
                                        approved = "1";
                                    } else {
                                        approved = "0";
                                    }

                                    recipe = arraylist_approvedRecipes.get(c);
                                    recipe.setMealtypeName(spinner_B_mealtype.getSelectedItem().toString());
                                    recipe.setName(edittext_B_name.getText().toString());
                                    recipe.setCountryCode(generalMethods.getCountryCodeFromName(spinner_B_country.getSelectedItem().toString()));
                                    recipe.setReligionId(generalMethods.getReligionIdFromName(spinner_B_religion.getSelectedItem().toString()));
                                    recipe.setTimeOfDay(spinner_B_timeOfDay.getSelectedItem().toString());
                                    recipe.setDescription(edittext_B_description.getText().toString());
                                    recipe.setApproved(approved);
                                    break;
                                }
                            }

                            boolean succeeded = connectorRecipes.updateRecipe(recipe);

                            if (succeeded) {
                                Toast.makeText(view.getContext(), recipe.getName() + " is geüpdatet", Toast.LENGTH_SHORT).show();
                                initializeArrayLists();
                                updateViewContent_B();
                            } else {
                                Toast.makeText(view.getContext(), recipe.getName() + " kon niet worden geüpdatet", Toast.LENGTH_SHORT).show();
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
                }
            }
        });

        button_B_delete = view.findViewById(R.id.manageRecipes_B_button_deleteRecipe);
        button_B_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_B_approvedRecipes.getCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myDialog);
                    builder.setMessage("Weet u zeker dat u dit recept wilt verwijderen?");
                    builder.setTitle("Recept verwijderen");
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Recipe recipe = null;
                            for (int c = 0; c < arraylist_approvedRecipes.size(); c++) {
                                if (spinner_B_approvedRecipes.getSelectedItem().toString().equals(arraylist_approvedRecipes.get(c).getName())) {
                                    recipe = arraylist_approvedRecipes.get(c);
                                }
                            }

                            boolean succeeded = connectorRecipes.deleteRecipe(recipe);

                            if (succeeded) {
                                Toast.makeText(view.getContext(), recipe.getName() + " is verwijderd", Toast.LENGTH_SHORT).show();
                                initializeArrayLists();
                                updateViewContent_B();
                            } else {
                                Toast.makeText(view.getContext(), recipe.getName() + " kon niet worden verwijderd", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(view.getContext(), "Er zijn geen recepten om te verwijderen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Updates part B after approving or denying a recipe
     */
    private void updateViewContent_B() {
        arrayadapter_approvedRecipes.notifyDataSetChanged();

        if (spinner_B_approvedRecipes.getCount() > 0) {
            spinner_B_approvedRecipes.setSelection(0);
        } else {
            edittext_B_name.setText(null);
            edittext_B_username.setText(null);
            edittext_B_description.setText(null);
        }
    }



    ////// DEFAULT METHODS, USED IN BOTH PARTS A AND B //////
    /**
     * Initializes the ArrayLists, used in the Buttons in parts A and B. This method is called once in the onCreate() and again every time the onStart() is called to refresh its contents
     */
    private void initializeArrayLists() {
        // Unapproved and Approved Recipes
        connectorRecipes.getUnapprovedRecipes("ManageRecipe");
        connectorRecipes.getApprovedRecipes("ManageRecipe");

        addConnectorRecipe.getTimeOfDay("ManageRecipe");
        addConnectorRecipe.getMealTypes("ManageRecipe");
        addConnectorRecipe.getCountries("ManageRecipe");
        addConnectorRecipe.getReligions("ManageRecipe");
    }

    // Religion
    public ArrayAdapter<Religion> getArrayAdapter_A_religions() {
        return arrayadapter_A_religion;
    }

    public ArrayAdapter<Religion> getArrayAdapter_B_religions() {
        return arrayadapter_B_religion;
    }

    public ArrayList<Religion> getArrayList_religions() {
        return arraylist_religions;
    }

    // Country
    public ArrayAdapter<Country> getArrayAdapter_A_countries() {
        return arrayadapter_A_country;
    }

    public ArrayAdapter<Country> getArrayAdapter_B_countries() {
        return arrayadapter_B_country;
    }

    public ArrayList<Country> getArrayList_countries() {
        return arraylist_countries;
    }

    // Mealtype
    public ArrayAdapter<Mealtype> getArrayAdapter_A_mealtypes() {
        return arrayadapter_A_mealtype;
    }

    public ArrayAdapter<Mealtype> getArrayAdapter_B_mealtypes() {
        return arrayadapter_B_mealtype;
    }

    public ArrayList<Mealtype> getArrayList_mealtypes() {
        return arraylist_mealtypes;
    }

    // TimeOfDay
    public ArrayAdapter<TimeOfDay> getArrayAdapter_A_timeofday() {
        return arrayadapter_A_timeofday;
    }

    public ArrayAdapter<TimeOfDay> getArrayAdapter_B_timeofday() {
        return arrayadapter_B_timeofday;
    }

    public ArrayList<TimeOfDay> getArrayList_timeofday() {
        return arraylist_timeofday;
    }

    // Unapproved Recipes
    public ArrayList<Recipe> getArraylist_unapprovedRecipes() {
        return arraylist_unapprovedRecipes;
    }

    public ArrayAdapter<String> getArrayAdapter_unapprovedRecipes() {
        return arrayadapter_unapprovedRecipes;
    }

    public ArrayList<String> getArraylist_unapprovedRecipeNames() {
        return arraylist_unapprovedRecipeNames;
    }

    // Approved Recipes
    public ArrayList<Recipe> getArraylist_approvedRecipes() {
        return arraylist_approvedRecipes;
    }

    public ArrayAdapter<String> getArrayAdapter_approvedRecipes() {
        return arrayadapter_approvedRecipes;
    }

    public ArrayList<String> getArraylist_approvedRecipeNames() {
        return arraylist_approvedRecipeNames;
    }

    // Unapproved Ingredients
    public ArrayList<Ingredient> getArraylist_unapprovedIngredients() {
        return arraylist_unapprovedIngredients;
    }
}
