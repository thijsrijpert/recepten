package com.nl.recipeapp.admin.country;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nl.recipeapp.CharacterCountListener;
import com.nl.recipeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Add extends Fragment {
    private View view;
    private EditText edittext_name, edittext_code, edittext_description;
    private TextView textview_countryDescription;
    private AddConnector addCountry_webserverConnector;

    public Add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_country, container, false);

        // Initialize the Class variables
        edittext_name = view.findViewById(R.id.addCountry_edittext_countryName);
        edittext_code = view.findViewById(R.id.addCountry_edittext_countryCode);
        edittext_description = view.findViewById(R.id.addCountry_edittext_description);
        textview_countryDescription = view.findViewById(R.id.addCountry_textview_countryDescriptionCharacterCount);

        // Create the connector that will pass requests towards the database
        addCountry_webserverConnector = new AddConnector(this.getContext());
        addCountry_webserverConnector.setAddCountry(this);

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
        edittext_description.addTextChangedListener(new CharacterCountListener(textview_countryDescription, edittext_description));
    }

    /**
     * Initializes the buttons on the Add Country section within the Administrator section.
     */
    private void initializeButtons() {
        Button button_applyCountry = view.findViewById(R.id.addCountry_btn_applyCountry);
        button_applyCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the countrycode is entered. If so, does it already exist?
                if (edittext_code.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "U moet een landcode invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the name is entered. If so, does it already exist?
                if (edittext_name.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een land naam invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                addCountry_webserverConnector.addCountry(edittext_code.getText().toString(), edittext_name.getText().toString(), edittext_description.getText().toString());
            }
        });
    }

    public EditText getEdittextFields(int value) {
        EditText edittext = null;
        switch (value) {
            case 0:
                edittext = edittext_code;
                break;
            case 1:
                edittext = edittext_name;
                break;
            case 2:
                edittext = edittext_description;
                break;
        }

        return edittext;
    }
}
