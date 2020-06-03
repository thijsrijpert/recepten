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

import java.util.ArrayList;

public class AddConnector {
    private Context context;
    private EditText edittext_recipeName;
    private boolean succesfullyAddedRecipe;
    private View view;

    private Add addRecipe; // recipe.add
    private Manage manageRecipe; // admin.recipe.manage
    private com.nl.recipeapp.user.recipe.Manage manageUserRecipe; // user.recipe.Manage;
    private Search searchRecipe; // search.search
    private GeneralMethods generalMethods; // generalmethods
    private com.nl.recipeapp.admin.timeofday.Edit editTimeOfDay; //admin.timeofday.edit
    private com.nl.recipeapp.admin.country.Edit editCountry; // admin.country.edit
    private com.nl.recipeapp.admin.mealtype.Edit editMealtype; // admin.mealtype.edit
    private DetailedView detailedViewRecipe; // recipe.detailedview

    // The local ArrayLists, which are used to save the data received from the database to later merge it with the existing ArrayLists in the classes
    private ArrayList<Religion> arraylist_religions;
    private ArrayList<Country> arraylist_countries;
    private ArrayList<Mealtype> arraylist_mealtypes;
    private ArrayList<TimeOfDay> arraylist_timeofday;
    private ArrayList<Ingredient> arraylist_ingredientsForSpecificUser;
    private ArrayList<Ingredient> arraylist_ingredientsForSpecificRecipe;
    private ArrayList<Review> arraylist_reviews;

    private ArrayList<String> arraylist_ingredientNames; // For local use only in the getIngredientsForSpecificRecipe() method

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
        arraylist_ingredientsForSpecificUser = new ArrayList<>();
        arraylist_ingredientsForSpecificRecipe = new ArrayList<>();
        arraylist_reviews = new ArrayList<>();

        arraylist_ingredientNames = new ArrayList<>();
    }

    public void initializeEditTexts() {
        edittext_recipeName = view.findViewById(R.id.addRecipe_edittext_recipeName);
    }

    /**
     * Adds a recipe to the database
     */
    public boolean addRecipe(Recipe recipe) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Recipe.php?name="+ recipe.getName() + "&description=" + recipe.getDescription() + "&isApproved=0&countrycode=" + recipe.getCountryCode() + "&username=" + recipe.getUsername() + "&mealtype_name=" + recipe.getMealtypeName() + "&religion_id=" + recipe.getReligionId() + "&time_of_day=" + recipe.getTimeOfDay() + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Recept '" + edittext_recipeName.getText() + "' succesvol aangemeld. Een administrator zal het beoordelen.", Toast.LENGTH_SHORT).show();
//              System.out.println(response);
                succesfullyAddedRecipe = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "recipeapp.recipe.AddConnector: Het recept '" + edittext_recipeName.getText() + "' kon niet worden aangemeld.", Toast.LENGTH_SHORT).show();
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
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Mealtype.php", new JSONArray(), new Response.Listener<JSONArray>() {
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
                            searchRecipe.getArrayList_mealtypes().add(new Mealtype("Selecteer een maaltijdsoort"));
                            searchRecipe.getArrayList_mealtypes().addAll(arraylist_mealtypes);
                            searchRecipe.getArrayAdapter_mealtypes().notifyDataSetChanged();
                            break;
                        case "EditMealtype":
                            editMealtype.getArraylist_mealtypes().clear();
                            editMealtype.getArraylist_mealtypes().addAll(arraylist_mealtypes);
                            editMealtype.getArrayadapter_mealtypes().clear();
                            editMealtype.getArrayadapter_mealtypes().addAll(arraylist_mealtypes);
                            editMealtype.getArrayadapter_mealtypes().notifyDataSetChanged();
                            break;
                        case "ManageUserRecipe":
                            manageUserRecipe.getArrayList_mealtypes().clear();
                            manageUserRecipe.getArrayList_mealtypes().addAll(arraylist_mealtypes);
                            manageUserRecipe.getArrayAdapter_mealtypes().notifyDataSetChanged();
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
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Country.php?order=name", new JSONArray(), new Response.Listener<JSONArray>() {
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
                            searchRecipe.getArrayList_countries().add(new Country("000", "Selecteer een land", ""));
                            searchRecipe.getArrayList_countries().addAll(arraylist_countries);
                            searchRecipe.getArrayAdapter_countries().notifyDataSetChanged();
                            break;
                        case "GeneralMethods":
                            generalMethods.getArrayList_Countries().clear();
                            generalMethods.getArrayList_Countries().addAll(arraylist_countries);
                            generalMethods.getDetailedView().setInputFieldContents();
                            break;
                        case "EditCountry":
                            editCountry.getArraylist_countries().clear();
                            editCountry.getArraylist_countries().addAll(arraylist_countries);
                            editCountry.getArrayadapter_countries().clear();
                            editCountry.getArrayadapter_countries().addAll(arraylist_countries);
                            editCountry.getArrayadapter_countries().notifyDataSetChanged();
                            break;
                        case "ManageUserRecipe":
                            manageUserRecipe.getArrayList_countries().clear();
                            manageUserRecipe.getArrayList_countries().addAll(arraylist_countries);
                            manageUserRecipe.getArrayAdapter_countries().notifyDataSetChanged();
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

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/religion.php?order=name", new JSONArray(), new Response.Listener<JSONArray>() {
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
                            searchRecipe.getArrayList_religions().add(new Religion("0", "Selecteer een religie"));
                            searchRecipe.getArrayList_religions().addAll(arraylist_religions);
                            searchRecipe.getArrayAdapter_religions().notifyDataSetChanged();
                            break;
                        case "GeneralMethods":
                            generalMethods.getArrayList_Religions().clear();
                            generalMethods.getArrayList_Religions().addAll(arraylist_religions);
                        break;
                        case "ManageUserRecipe":
                            manageUserRecipe.getArrayList_religions().clear();
                            manageUserRecipe.getArrayList_religions().addAll(arraylist_religions);
                            manageUserRecipe.getArrayAdapter_religions().notifyDataSetChanged();
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
                            searchRecipe.getArrayList_timeofday().add(new TimeOfDay("Selecteer een tijdvak"));
                            searchRecipe.getArrayList_timeofday().addAll(arraylist_timeofday);
                            searchRecipe.getArrayAdapter_timeofday().notifyDataSetChanged();
                            break;
                        case "EditTimeOfDay":
                            editTimeOfDay.getArraylist_timeofday().clear();
                            editTimeOfDay.getArraylist_timeofday().addAll(arraylist_timeofday);
                            editTimeOfDay.getArrayAdapter_timeofday().clear();
                            editTimeOfDay.getArrayAdapter_timeofday().addAll(arraylist_timeofday);
                            editTimeOfDay.getArrayAdapter_timeofday().notifyDataSetChanged();
                            break;
                        case "ManageUserRecipe":
                            manageUserRecipe.getArrayList_timeofday().clear();
                            manageUserRecipe.getArrayList_timeofday().addAll(arraylist_timeofday);
                            manageUserRecipe.getArrayAdapter_timeofday().notifyDataSetChanged();
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

                arraylist_ingredientsForSpecificUser.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Ingredient ingredient = gson.fromJson(object.toString(), Ingredient.class);
                        arraylist_ingredientsForSpecificUser.add(ingredient);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Ingredients to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "AddRecipe":
                            addRecipe.getArrayList_ingredients().clear();
                            addRecipe.getArrayList_ingredients().addAll(arraylist_ingredientsForSpecificUser);
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
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?where=is_approved-eq-1", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_ingredientsForSpecificUser.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Ingredient ingredient = gson.fromJson(object.toString(), Ingredient.class);
                        arraylist_ingredientsForSpecificUser.add(ingredient);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Ingredients to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "AddRecipe":
                            addRecipe.getArrayList_ingredients().clear();
                            addRecipe.getArrayList_ingredients().addAll(arraylist_ingredientsForSpecificUser);
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
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?where=is_approved-eq-1&where=username-eq-" + username + "", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_ingredientsForSpecificUser.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Ingredient ingredient = gson.fromJson(object.toString(), Ingredient.class);
                        arraylist_ingredientsForSpecificUser.add(ingredient);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Ingredients to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "AddRecipe":
                            addRecipe.getArrayList_ingredients().clear();
                            addRecipe.getArrayList_ingredients().addAll(arraylist_ingredientsForSpecificUser);
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
     */
    public void getIngredientsForSpecificRecipe(String recipeId, final String calledFrom) {
        // Selecteer alle ingredient namen die bij het recipeId horen uit de recipe_ingredient tabel en sla deze hier lokaal op
        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/recipe_ingredient.php?select=name&where=recipe_id-eq-" + recipeId + "", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_ingredientNames.clear(); // Clear the local ArrayList, so no duplicates will be added

                // Add the ingredient names from request1 to the arraylist_ingredientNames list.

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Ingrediëntnamen voor specifiek recept ID konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request1);

        // Gebruik de opgehaalde ingredient namen om uit de ingredient tabel de bijbehorende ingredientgegevens op te halen.
        // Request a string response from the provided URL.
        arraylist_ingredientsForSpecificRecipe.clear(); // Clear the local ArrayList, so no duplicates will be added

        for (int c = 0; c < arraylist_ingredientNames.size(); c++) {
            JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?where=name-eq-" + arraylist_ingredientNames.get(c) + "", new JSONArray(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Gson gson = new Gson();

                    try {
                        for (int c = 0; c < response.length(); c++) {
                            JSONObject object = response.getJSONObject(c);
                            Ingredient ingredient = gson.fromJson(object.toString(), Ingredient.class);
                            arraylist_ingredientsForSpecificRecipe.add(ingredient);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("AddConnector (recipe): Ingrediënten voor specifiek recept konden niet worden opgehaald uit de database.");
                }
            });

            // Get the queue and give a request
            RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request2);
        }

        // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
        // Add the Ingredients to the necessary ArrayLists
        // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
        switch (calledFrom) {
            case "RecipeDetailedView":
                detailedViewRecipe.getArraylist_ingredients().clear();
                detailedViewRecipe.getArraylist_ingredients().addAll(arraylist_ingredientsForSpecificRecipe);
                detailedViewRecipe.getRecyclerviewAdapter_ingredients().notifyDataSetChanged();
                break;
        }
    }

    /**
     * Gets the Reviews belonging to a specific Recipe
     */
    public void getReviewsForSpecificRecipe(String recipeId, final String calledFrom) {
        // Request a JsonArray response from the provided URL.

        // tablename.php --> selecteert alles uit de tabel
        // tablename.php?select=kolomnaam --> selecteert alles uit een specifieke kolom (kolomnaam)
        // tablename.php?select=kolomnaam&where=kolomnaam-eq-waardenaam --> selecteert alles uit een specifieke kolom, waar de waarde uit de kolom gelijk is aan de waardenaam

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/review.php?where=recipe_id-eq-" + recipeId, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                System.out.println("Response: " + response.toString());
                Gson gson = new Gson();

                arraylist_reviews.clear();

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Review review = gson.fromJson(object.toString(), Review.class);
                        arraylist_reviews.add(review);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Reviews to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "RecipeDetailedView":
                            detailedViewRecipe.getArraylist_reviews().clear();
                            detailedViewRecipe.getArraylist_reviews().addAll(arraylist_reviews);
                            detailedViewRecipe.getRecyclerviewAdapter_reviews().notifyDataSetChanged();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("AddConnector (recipe): Reviews konden niet worden opgehaald uit de database.");
//                System.out.println("Error: " + error.networkResponse.headers.toString());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
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

    public void setDetailedViewRecipe(DetailedView detailedView) {
        detailedViewRecipe = detailedView;
    }

    public void setManageUserRecipe(com.nl.recipeapp.user.recipe.Manage manageUserRecipe) {
        this.manageUserRecipe = manageUserRecipe;
    }
}
