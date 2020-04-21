package com.nl.beroepsproductblok4_recipesworldwide.administrator.country;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nl.beroepsproductblok4_recipesworldwide.R;
import com.nl.beroepsproductblok4_recipesworldwide.model.Country;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCountry extends Fragment {
    private View view;
    private EditText edittext_countryName, edittext_countryCode, edittext_countryDescription;
    private TextView textview_countryDescription;
    private AddCountry_WebserverConnector addCountry_webserverConnector;

    public AddCountry() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_country, container, false);

        // Initialize the Class variables
        edittext_countryName = view.findViewById(R.id.addCountry_edittext_countryName);
        edittext_countryCode = view.findViewById(R.id.addCountry_edittext_countryCode);
        edittext_countryDescription = view.findViewById(R.id.addCountry_edittext_description);
        textview_countryDescription = view.findViewById(R.id.addCountry_textview_countryDescriptionCharacterCount);

        // Create the connector that will pass requests towards the database
        addCountry_webserverConnector = new AddCountry_WebserverConnector(this.getContext(), view);

        // Launch the initialization methods
        initializeInputFields();
        initializeButtons();

        return view;
    }

    /**
     * Initializes the EditText elements on this Fragment
     */
    private void initializeInputFields() {
        // Create the listener on recipeDescription's EditText, so the character counter changes when the user is typing
        edittext_countryDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textview_countryDescription.setText(edittext_countryDescription.getText().length() + " / 65.535", null);

                if (edittext_countryDescription.getText().length() > 65535) {
                    textview_countryDescription.setTextColor(Color.RED);
                } else {
                    textview_countryDescription.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Initializes the buttons on the Add Country section within the Administrator section.
     */
    private void initializeButtons() {
        Button button_applyCountry = view.findViewById(R.id.addCountry_btn_applyCountry);
        button_applyCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, get the ArrayList of all Countries
                ArrayList<Country> arraylist_countries = addCountry_webserverConnector.getCountries();

                // Check if the countrycode is entered. If so, does it already exist?
                if (edittext_countryCode.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "U moet een landcode invullen", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Check for duplicates
                    for (int c = 0; c < arraylist_countries.size(); c++) {
                        if (arraylist_countries.get(c).getCode().equals(edittext_countryCode.getText().toString())) {
                            Toast.makeText(getActivity(), "De ingevulde landcode bestaat al", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                // Check if the name is entered. If so, does it already exist?
                if (edittext_countryName.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een land naam invullen", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Check for duplicates
                    for (int c = 0; c < arraylist_countries.size(); c++) {
                        if (arraylist_countries.get(c).getName().equals(edittext_countryName.getText().toString())) {
                            Toast.makeText(getActivity(), "Het ingevulde land naam bestaat al", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                boolean value = addCountry_webserverConnector.addCountry(edittext_countryCode.getText().toString(), edittext_countryName.getText().toString(), edittext_countryDescription.getText().toString());

                if (value) {
                    edittext_countryName.setText("");
                }
            }
        });
    }
}
