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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCountry extends Fragment {
    private View view;
    private EditText edittext_countryName, edittext_countryDescription;
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
                textview_countryDescription.setText(edittext_countryDescription.getText().length() + " / 255", null);

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
                // First, get the ArrayList of all Country names (this is to check if the entered name already exists
                ArrayList<String> arraylist_countryNames = addCountry_webserverConnector.getAllCountryNames();

                if (edittext_countryName.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een land naam invullen", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Check for duplicates
                    for (int c = 0; c < arraylist_countryNames.size(); c++) {
                        if (arraylist_countryNames.get(c).equals(edittext_countryName.getText().toString())) {
                            Toast.makeText(getActivity(), "Het ingevulde land naam bestaat al", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                boolean value = addCountry_webserverConnector.addCountry(edittext_countryName.getText().toString());

                if (value) {
                    edittext_countryName.setText("");
                }
            }
        });
    }
}
