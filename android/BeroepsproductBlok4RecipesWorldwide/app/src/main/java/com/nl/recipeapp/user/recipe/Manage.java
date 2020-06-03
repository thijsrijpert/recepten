package com.nl.recipeapp.user.recipe;

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
import com.nl.recipeapp.admin.recipe.Connector;
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
    private View view;
    private AddConnector connector_recipe;
    private Connector connector_recipeAdmin;
    private com.nl.recipeapp.user.recipe.Connector connector_recipeUser;
    private GeneralMethods generalMethods;

    private Spinner spinner_recipes, spinner_mealtypes, spinner_countries, spinner_religions, spinner_timeofday;
    private EditText edittext_name, edittext_description;
    private RecyclerView recyclerview_ingredients;
    private ManageRecyclerViewAdapter recyclerviewAdapter;
    private Button button_delete, button_saveChanges;

    private ArrayList<Recipe> arraylist_recipes;
    private ArrayList<String> arraylist_recipeNames;
    private ArrayList<Country> arraylist_countries;
    private ArrayList<Religion> arraylist_religions;
    private ArrayList<Mealtype> arraylist_mealtypes;
    private ArrayList<TimeOfDay> arraylist_timeofday;
    private ArrayList<Ingredient> arraylist_ingredientsBoundToRecipe;

    private ArrayAdapter<Religion> arrayadapter_religion;
    private ArrayAdapter<Country> arrayadapter_country;
    private ArrayAdapter<Mealtype> arrayadapter_mealtype;
    private ArrayAdapter<TimeOfDay> arrayadapter_timeofday;
    private ArrayAdapter<String> arrayadapter_recipeNames;

    public Manage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_manage_user_recipes, container, false);

        connector_recipe = new AddConnector(this.getContext()); // Create the connector to fill the Spinner ArrayLists
        connector_recipeAdmin = new Connector(this.getContext());
        connector_recipeUser = new com.nl.recipeapp.user.recipe.Connector(this.getContext());
        generalMethods = new GeneralMethods(this.getContext()); // Create the GeneralMethods class, used for figuring out corresponding id's and names

        arraylist_recipes = new ArrayList<>();
        arraylist_recipeNames = new ArrayList<>();
        arraylist_countries = new ArrayList<>();
        arraylist_religions = new ArrayList<>();
        arraylist_mealtypes = new ArrayList<>();
        arraylist_timeofday = new ArrayList<>();
        arraylist_ingredientsBoundToRecipe = new ArrayList<>();

        connector_recipe.setManageUserRecipe(this);
        connector_recipeUser.setManageUserRecipe(this);

        initializeEditTexts();
        initializeRecyclerView();
        initializeSpinners();
        initializeButtons();

        return view;
    }

    /**
     * Gets called when the user resumes on this screen after initializing
     */
    public void onStart() {
        super.onStart();
        initializeArrayLists();  // Initializes the Spinner ArrayLists, which are used in the ArrayAdapters
    }

    private void initializeEditTexts() {
        edittext_name = view.findViewById(R.id.manageUserRecipes_edittext_recipeName);

        TextView textview_charactercount = view.findViewById(R.id.manageUserRecipes_textview_receptomschrijvingCharacterCount);
        edittext_description = view.findViewById(R.id.manageUserRecipes_edittext_description);
        edittext_description.addTextChangedListener(new CharacterCountListener(textview_charactercount, edittext_description));
    }

    private void initializeRecyclerView() {
        recyclerview_ingredients = view.findViewById(R.id.manageUserRecipes_recyclerView);
        recyclerviewAdapter = new ManageRecyclerViewAdapter(arraylist_ingredientsBoundToRecipe);
        recyclerview_ingredients.setAdapter(recyclerviewAdapter);
        recyclerview_ingredients.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void initializeSpinners() {
        spinner_countries = view.findViewById(R.id.manageUserRecipes_spinner_recipeCountry);
        spinner_mealtypes = view.findViewById(R.id.manageUserRecipes_spinner_recipeMealtype);
        spinner_religions = view.findViewById(R.id.manageUserRecipes_spinner_recipeReligion);
        spinner_timeofday = view.findViewById(R.id.manageUserRecipes_spinner_recipeMealDaypart);
        spinner_recipes = view.findViewById(R.id.manageUserRecipes_spinner_approvedRecipes);

        spinner_recipes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int c = 0; c < arraylist_recipes.size(); c++) {
                    if (spinner_recipes.getSelectedItem().toString().equals(arraylist_recipes.get(c).getName())) {
                        // Initialize the EditTexts
                        edittext_name.setText(arraylist_recipes.get(c).getName());
                        edittext_description.setText(arraylist_recipes.get(c).getDescription());

                        // Initialize the Spinners
                        Country country = null;
                        for (int i = 0; i < arraylist_countries.size(); i++) {
                            if (arraylist_recipes.get(c).getCountryCode().equals(arraylist_countries.get(i).getCountrycode())) {
                                country = arraylist_countries.get(i);
                            }
                        }
                        int positionCountry = arrayadapter_country.getPosition(country);
                        spinner_countries.setSelection(positionCountry);

                        Religion religion = null;
                        for (int i = 0; i < arraylist_religions.size(); i++) {
                            if (arraylist_recipes.get(c).getReligionId().equals(arraylist_religions.get(i).getId())) {
                                religion = arraylist_religions.get(i);
                            }
                        }
                        int positionReligion = arrayadapter_religion.getPosition(religion);
                        spinner_religions.setSelection(positionReligion);

                        Mealtype mealtype = null;
                        for (int i = 0; i < arraylist_mealtypes.size(); i++) {
                            if (arraylist_recipes.get(c).getMealtypeName().equals(arraylist_mealtypes.get(i).getName())) {
                                mealtype = arraylist_mealtypes.get(i);
                            }
                        }
                        int positionMealtype = arrayadapter_mealtype.getPosition(mealtype);
                        spinner_mealtypes.setSelection(positionMealtype);

                        TimeOfDay timeofday = null;
                        for (int i = 0; i < arraylist_timeofday.size(); i++) {
                            if (arraylist_recipes.get(c).getTimeOfDay().equals(arraylist_timeofday.get(i).getName())) {
                                timeofday = arraylist_timeofday.get(i);
                            }
                        }
                        int positionTimeOfDay = arrayadapter_timeofday.getPosition(timeofday);
                        spinner_timeofday.setSelection(positionTimeOfDay);

                        // Initialize the items on the RecyclerView and notify its adapter that the data has been changed
                        arraylist_ingredientsBoundToRecipe = connector_recipeAdmin.getIngredientsBoundToRecipe(arraylist_recipes.get(c).getId());
                        recyclerviewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrayadapter_recipeNames = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_recipeNames);
        spinner_recipes.setAdapter(arrayadapter_recipeNames);

        arrayadapter_mealtype = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_mealtypes);
        spinner_mealtypes.setAdapter(arrayadapter_mealtype);

        arrayadapter_religion = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_religions);
        spinner_religions.setAdapter(arrayadapter_religion);

        arrayadapter_country = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_countries);
        spinner_countries.setAdapter(arrayadapter_country);

        arrayadapter_timeofday = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_timeofday);
        spinner_timeofday.setAdapter(arrayadapter_timeofday);
    }

    private void initializeButtons() {
        button_delete = view.findViewById(R.id.manageUserRecipes_button_deleteRecipe);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_recipes.getCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myDialog);
                    builder.setMessage("Weet u zeker dat u dit recept wilt verwijderen?");
                    builder.setTitle("Recept verwijderen");
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Recipe recipe = null;
                            for (int c = 0; c < arraylist_recipes.size(); c++) {
                                if (spinner_recipes.getSelectedItem().toString().equals(arraylist_recipes.get(c).getName())) {
                                    recipe = arraylist_recipes.get(c);
                                }
                            }

                            connector_recipeAdmin.deleteRecipe(recipe);
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

        button_saveChanges = view.findViewById(R.id.manageUserRecipes_button_applyRecipeChanges);
        button_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_recipes.getCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myDialog);
                    builder.setMessage("Weet u zeker dat u dit recept wilt wijzigen?");
                    builder.setTitle("Recept wijzigen");
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Recipe recipe = null;
                            for (int c = 0; c < arraylist_recipes.size(); c++) {
                                if (spinner_recipes.getSelectedItem().toString().equals(arraylist_recipes.get(c).getName())) {
                                    recipe = arraylist_recipes.get(c);
                                    recipe.setMealtypeName(spinner_mealtypes.getSelectedItem().toString());
                                    recipe.setName(edittext_name.getText().toString());
                                    recipe.setCountryCode(generalMethods.getCountryCodeFromName(spinner_countries.getSelectedItem().toString()));
                                    recipe.setReligionId(generalMethods.getReligionIdFromName(spinner_religions.getSelectedItem().toString()));
                                    recipe.setTimeOfDay(spinner_timeofday.getSelectedItem().toString());
                                    recipe.setDescription(edittext_description.getText().toString());
                                    break;
                                }
                            }

                            connector_recipeAdmin.updateRecipe(recipe);
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

    private void initializeArrayLists() {
        arraylist_recipeNames.clear();

//        connector_recipeUser.getRecipesForSpecificUser(((MainActivity)getActivity()).getCurrentUser().getUsername());
        for (int c = 0; c < arraylist_recipes.size(); c++) {
            arraylist_recipeNames.add(arraylist_recipes.get(c).getName());
        }
        arrayadapter_recipeNames.notifyDataSetChanged();

        connector_recipe.getTimeOfDay("ManageUserRecipe");
        connector_recipe.getMealTypes("ManageUserRecipe");
        connector_recipe.getCountries("ManageUserRecipe");
        connector_recipe.getReligions("ManageUserRecipe");
    }

    // Religion
    public ArrayAdapter<Religion> getArrayAdapter_religions() {
        return arrayadapter_religion;
    }

    public ArrayList<Religion> getArrayList_religions() {
        return arraylist_religions;
    }

    // Country
    public ArrayAdapter<Country> getArrayAdapter_countries() {
        return arrayadapter_country;
    }

    public ArrayList<Country> getArrayList_countries() {
        return arraylist_countries;
    }

    // Mealtype
    public ArrayAdapter<Mealtype> getArrayAdapter_mealtypes() {
        return arrayadapter_mealtype;
    }

    public ArrayList<Mealtype> getArrayList_mealtypes() {
        return arraylist_mealtypes;
    }

    // TimeOfDay
    public ArrayAdapter<TimeOfDay> getArrayAdapter_timeofday() {
        return arrayadapter_timeofday;
    }

    public ArrayList<TimeOfDay> getArrayList_timeofday() {
        return arraylist_timeofday;
    }
}
