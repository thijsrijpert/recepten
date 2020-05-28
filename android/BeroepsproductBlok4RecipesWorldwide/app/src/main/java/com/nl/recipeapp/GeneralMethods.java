package com.nl.recipeapp;

import android.content.Context;

import com.nl.recipeapp.model.Country;
import com.nl.recipeapp.model.Religion;
import com.nl.recipeapp.recipe.AddConnector;

import java.util.ArrayList;

public class GeneralMethods {
    private AddConnector connector;
    private ArrayList<Religion> religions;
    private ArrayList<Country> countries;

    public GeneralMethods(Context context) {
        connector = new AddConnector(context);
        connector.setGeneralMethods(this);
        connector.getReligions("GeneralMethods");
        connector.getCountries("GeneralMethods");
    }

    /**
     * Gets the Religion name that corresponds with the given ID
     * @param religionId The given Religion ID
     * @return The corresponding Religion name
     */
    public String getReligionNameFromId(String religionId) {
        String name = null;

        // Check whether the religionId is similar to 'Selecteer een religie', which indicates the user hasn't made a choice
        if (religionId.equals("Selecteer een religie")) {
            return "null";
        }

        // If the religionId is not similar to the default line, extract the religion id
        for (int c = 0; c < religions.size(); c++) {
            if (religionId.equals(religions.get(c).getId())) {
                name = religions.get(c).getName();
            }
        }

        return name;
    }

    /**
     * Gets the Religion ID that corresponds with the given name
     * @param religionName The given Religion name
     * @return The corresponding Religion ID
     */
    public String getReligionIdFromName(String religionName) {
        String id = null;

        // Check whether the religionName is similar to 'Selecteer een religie', which indicates the user hasn't made a choice
        if (religionName.equals("Selecteer een religie")) {
            return "null";
        }

        // If the religionName is not similar to the default line, extract the religion id
        for (int c = 0; c < religions.size(); c++) {
            if (religionName.equals(religions.get(c).getName())) {
                id = religions.get(c).getId();
            }
        }

        return id;
    }

    /**
     * Gets the Country name that corresponds with the given code
     * @param countrycode The given countrycode
     * @return The corresponding Country name
     */
    public String getCountryNameFromCode(String countrycode) {
        String name = null;

        // Check whether the countryName is similar to 'Selecteer een land', which indicates the user hasn't made a choice
        if (countrycode.equals("Selecteer een land")) {
            return "null";
        }

        // If the countrycode is not similar to the default line, extract the countrycode
        for (int c = 0; c < countries.size(); c++) {
            if (countrycode.equals(countries.get(c).getCountryCode())) {
                name = countries.get(c).getName();
            }
        }

        return name;
    }

    /**
     * Gets the Countrycode that corresponds with the given name
     * @param countryName The given country name
     * @return The corresponding Countrycode
     */
    public String getCountryCodeFromName(String countryName) {
        String code = null;

        // Check whether the countryName is similar to 'Selecteer een land', which indicates the user hasn't made a choice
        if (countryName.equals("Selecteer een land")) {
            return "null";
        }

        // If the countrycode is not similar to the default line, extract the countrycode
        for (int c = 0; c < countries.size(); c++) {
            if (countryName.equals(countries.get(c).getName())) {
                code = countries.get(c).getCountryCode();
            }
        }

        return code;
    }

    public ArrayList<Religion> getArrayList_Religions() {
        return religions;
    }

    public ArrayList<Country> getArrayList_Countries() {
        return countries;
    }
}
