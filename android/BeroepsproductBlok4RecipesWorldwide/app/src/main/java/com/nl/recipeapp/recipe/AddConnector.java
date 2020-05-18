package com.nl.recipeapp.recipe;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.nl.recipeapp.GeneralMethods;
import com.nl.recipeapp.R;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.admin.recipe.Manage;
import com.nl.recipeapp.admin.timeofday.Edit;
import com.nl.recipeapp.model.Country;
import com.nl.recipeapp.model.Ingredient;
import com.nl.recipeapp.model.Mealtype;
import com.nl.recipeapp.model.Recipe;
import com.nl.recipeapp.model.Religion;
import com.nl.recipeapp.model.Review;
import com.nl.recipeapp.model.TimeOfDay;
import com.nl.recipeapp.search.Search;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddConnector {
    private Context context;
    private EditText edittext_recipeName;
    private boolean succesfullyAddedRecipe;
    private View view;

    private Add addRecipe; // recipe.add
    private Manage manageRecipe; // admin.recipe.manage
    private Search searchRecipe; // search.search
    private GeneralMethods generalMethods; // generalmethods
    private com.nl.recipeapp.admin.timeofday.Edit editTimeOfDay; //admin.timeofday.edit
    private com.nl.recipeapp.admin.country.Edit editCountry; // admin.country.edit
    private com.nl.recipeapp.admin.mealtype.Edit editMealtype; // admin.mealtype.edit

    // The local ArrayLists, which are used to save the data received from the database to later merge it with the existing ArrayLists in the classes
    private ArrayList<Religion> arraylist_religions;
    private ArrayList<Country> arraylist_countries;
    private ArrayList<Mealtype> arraylist_mealtypes;
    private ArrayList<TimeOfDay> arraylist_timeofday;
    private ArrayList<Ingredient> arraylist_ingredients;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     */
    public AddConnector(Context context) {
        this.context = context;

        arraylist_religions = new ArrayList<>();
        arraylist_countries = new ArrayList<>();
        arraylist_mealtypes = new ArrayList<>();
        arraylist_timeofday = new ArrayList<>();
    }

    public void initializeEditTexts() {
        edittext_recipeName = view.findViewById(R.id.addRecipe_edittext_recipeName);
    }

    /**
     * Adds a recipe to the database
     */
    public boolean addRecipe(Recipe recipe) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Recept '" + edittext_recipeName.getText() + "' succesvol aangemeld. Een administrator zal het beoordelen.", Toast.LENGTH_SHORT).show();
//              System.out.println(response);
                succesfullyAddedRecipe = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Het recept '" + edittext_recipeName.getText() + "' kon niet worden aangemeld.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
                succesfullyAddedRecipe = false;
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
        return succesfullyAddedRecipe;
    }

    /**
     * Gets all Mealtypes from the database
     */
    public void getMealTypes(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/mealtype.php", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_mealtypes.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Mealtype mealtype = gson.fromJson(object.toString(), Mealtype.class);
                        arraylist_mealtypes.add(mealtype);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Mealtypes to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "AddRecipe":
                            addRecipe.getArrayList_mealtypes().clear();
                            addRecipe.getArrayList_mealtypes().addAll(arraylist_mealtypes);
                            addRecipe.getArrayAdapter_mealtypes().notifyDataSetChanged();
                            break;
                        case "ManageRecipe":
                            manageRecipe.getArrayList_mealtypes().clear();
                            manageRecipe.getArrayList_mealtypes().addAll(arraylist_mealtypes);
                            manageRecipe.getArrayAdapter_A_mealtypes().notifyDataSetChanged();
                            manageRecipe.getArrayAdapter_B_mealtypes().notifyDataSetChanged();
                            break;
                        case "SearchRecipe":
                            searchRecipe.getArrayList_mealtypes().clear();
                            searchRecipe.getArrayList_mealtypes().addAll(arraylist_mealtypes);
                            searchRecipe.getArrayAdapter_mealtypes().notifyDataSetChanged();
                            break;
                        case "EditMealtype":
                            editMealtype.getArraylist_mealtypes().clear();
                            editMealtype.getArraylist_mealtypes().addAll(arraylist_mealtypes);
                            editMealtype.getArrayadapter_mealtypes().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Maaltijdsoorten konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    /**
     * Gets all Countries from the database
     */
    public void getCountries(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/country.php", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_countries.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Country country = gson.fromJson(object.toString(), Country.class);
                        arraylist_countries.add(country);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Countries to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "AddRecipe":
                            addRecipe.getArrayList_countries().clear();
                            addRecipe.getArrayList_countries().addAll(arraylist_countries);
                            addRecipe.getArrayAdapter_countries().notifyDataSetChanged();
                            break;
                        case "ManageRecipe":
                            manageRecipe.getArrayList_countries().clear();
                            manageRecipe.getArrayList_countries().addAll(arraylist_countries);
                            manageRecipe.getArrayAdapter_A_countries().notifyDataSetChanged();
                            manageRecipe.getArrayAdapter_B_countries().notifyDataSetChanged();
                            break;
                        case "SearchRecipe":
                            searchRecipe.getArrayList_countries().clear();
                            searchRecipe.getArrayList_countries().addAll(arraylist_countries);
                            searchRecipe.getArrayAdapter_countries().notifyDataSetChanged();
                            break;
                        case "GeneralMethods":
                            generalMethods.getArrayList_Countries().clear();
                            generalMethods.getArrayList_Countries().addAll(arraylist_countries);
                            break;
                        case "EditCountry":
                            editCountry.getArraylist_countries().clear();
                            editCountry.getArraylist_countries().addAll(arraylist_countries);
                            editCountry.getArrayadapter_countries().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Landen konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    /**
     * Gets all Religions from the database
     */
    public void getReligions(final String calledFrom) {
        // Request a JsonArray response from the provided URL.

        // tablename.php --> selecteert alles uit de tabel
        // tablename.php?select=kolomnaam --> selecteert alles uit een specifieke kolom (kolomnaam)
        // tablename.php?select=kolomnaam&where=kolomnaam-eq-waardenaam --> selecteert alles uit een specifieke kolom, waar de waarde uit de kolom gelijk is aan de waardenaam

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/religion.php", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_religions.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Religion religion = gson.fromJson(object.toString(), Religion.class);
                        arraylist_religions.add(religion);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Religions to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "AddRecipe":
                            addRecipe.getArrayList_religions().clear();
                            addRecipe.getArrayList_religions().addAll(arraylist_religions);
                            addRecipe.getArrayAdapter_religions().notifyDataSetChanged();
                            break;
                        case "ManageRecipe":
                            manageRecipe.getArrayList_religions().clear();
                            manageRecipe.getArrayList_religions().addAll(arraylist_religions);
                            manageRecipe.getArrayAdapter_A_religions().notifyDataSetChanged();
                            manageRecipe.getArrayAdapter_B_religions().notifyDataSetChanged();
                            break;
                        case "SearchRecipe":
                            searchRecipe.getArrayList_religions().clear();
                            searchRecipe.getArrayList_religions().addAll(arraylist_religions);
                            searchRecipe.getArrayAdapter_religions().notifyDataSetChanged();
                            break;
                        case "GeneralMethods":
                            generalMethods.getArrayList_Religions().clear();
                            generalMethods.getArrayList_Religions().addAll(arraylist_religions);
                        break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Religies konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    /**
     * Gets all TimeOfDay objects from the database
     */
    public void getTimeOfDay(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/timeofday.php", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_timeofday.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        TimeOfDay timeofday = gson.fromJson(object.toString(), TimeOfDay.class);
                        arraylist_timeofday.add(timeofday);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the TimeOfDay to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "AddRecipe":
                            addRecipe.getArrayList_timeofday().clear();
                            addRecipe.getArrayList_timeofday().addAll(arraylist_timeofday);
                            addRecipe.getArrayAdapter_timeofday().notifyDataSetChanged();
                            break;
                        case "ManageRecipe":
                            manageRecipe.getArrayList_timeofday().clear();
                            manageRecipe.getArrayList_timeofday().addAll(arraylist_timeofday);
                            manageRecipe.getArrayAdapter_A_timeofday().notifyDataSetChanged();
                            manageRecipe.getArrayAdapter_B_timeofday().notifyDataSetChanged();
                            break;
                        case "SearchRecipe":
                            searchRecipe.getArrayList_timeofday().clear();
                            searchRecipe.getArrayList_timeofday().addAll(arraylist_timeofday);
                            searchRecipe.getArrayAdapter_timeofday().notifyDataSetChanged();
                            break;
                        case "EditTimeOfDay":
                            editTimeOfDay.getArraylist_timeofday().clear();
                            editTimeOfDay.getArraylist_timeofday().addAll(arraylist_timeofday);
                            editTimeOfDay.getArrayAdapter_timeofday().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Tijdvakken konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    /**
     * Gets all Ingredients from the database
     */
    public void getAllIngredients(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_ingredients.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Ingredient ingredient = gson.fromJson(object.toString(), Ingredient.class);
                        arraylist_ingredients.add(ingredient);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Ingredients to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "AddRecipe":
                            addRecipe.getArrayList_ingredients().clear();
                            addRecipe.getArrayList_ingredients().addAll(arraylist_ingredients);
                            addRecipe.getArrayAdapter_ingredients().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Alle ingrediënten konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    /**
     * Gets only the approved Ingredients from the database
     */
    public void getApprovedIngredients(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?where=isapproved-eq-1", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_ingredients.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Ingredient ingredient = gson.fromJson(object.toString(), Ingredient.class);
                        arraylist_ingredients.add(ingredient);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Ingredients to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "AddRecipe":
                            addRecipe.getArrayList_ingredients().clear();
                            addRecipe.getArrayList_ingredients().addAll(arraylist_ingredients);
                            addRecipe.getArrayAdapter_ingredients().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Alle ingrediënten konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    /**
     * Gets all approved Ingredients + unapproved Ingredients submitted by the Current User from the database
     */
    public void getIngredientsForSpecificUser(String username, final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?where=isapproved-eq-1&where=username-eq-" + username + "", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_ingredients.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Ingredient ingredient = gson.fromJson(object.toString(), Ingredient.class);
                        arraylist_ingredients.add(ingredient);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Ingredients to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "AddRecipe":
                            addRecipe.getArrayList_ingredients().clear();
                            addRecipe.getArrayList_ingredients().addAll(arraylist_ingredients);
                            addRecipe.getArrayAdapter_ingredients().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Ingrediënten voor specifieke gebruiker konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    /**
     * Gets the Ingredients belonging to a specific Recipe
     * @return An ArrayList<Ingredient> with all Ingredients belonging to a specific Recipe
     */
    public ArrayList<Ingredient> getIngredientsForSpecificRecipe(String recipeId) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Ingrediënten voor specifiek recept konden niet worden opgehaald uit de database.");
//                Toast.makeText(context, "RecipeHTTP: Ingrediënten konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        // Fill the ArrayList with the ingredients

        return ingredients;
    }

    /**
     * Gets the Reviews belonging to a specific Recipe
     * @return An ArrayList<Review> with all Reviews belonging to a specific Recipe
     */
    public ArrayList<Review> getReviewsForSpecificRecipe(String recipeId) {
        // Request a JsonArray response from the provided URL.
        final ArrayList<Review> reviews = new ArrayList<>();

        // tablename.php --> selecteert alles uit de tabel
        // tablename.php?select=kolomnaam --> selecteert alles uit een specifieke kolom (kolomnaam)
        // tablename.php?select=kolomnaam&where=kolomnaam-eq-waardenaam --> selecteert alles uit een specifieke kolom, waar de waarde uit de kolom gelijk is aan de waardenaam

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/review.php?where=id-eq-" + recipeId, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                System.out.println("Response: " + response.toString());
                Gson gson = new Gson();

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Review review = gson.fromJson(object.toString(), Review.class);

                        reviews.add(review);

//                        System.out.println(religion.getId());
//                        System.out.println(religion.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Religies konden niet worden opgehaald uit de database.");
//                System.out.println("Error: " + error.networkResponse.headers.toString());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);

        return reviews;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setAddRecipe(Add addRecipe) {
        this.addRecipe = addRecipe;
    }

    public void setManageRecipe(Manage manageRecipe) {
        this.manageRecipe = manageRecipe;
    }

    public void setSearchRecipe(Search searchRecipe) {
        this.searchRecipe = searchRecipe;
    }

    public void setGeneralMethods(GeneralMethods generalMethods) {
        this.generalMethods = generalMethods;
    }

    public void setEditTimeOfDay(Edit editTimeOfDay) {
        this.editTimeOfDay = editTimeOfDay;
    }

    public void setEditCountry(com.nl.recipeapp.admin.country.Edit editCountry) {
        this.editCountry = editCountry;
    }

    public void setEditMealtype(com.nl.recipeapp.admin.mealtype.Edit editMealtype) {
        this.editMealtype = editMealtype;
    }
}
