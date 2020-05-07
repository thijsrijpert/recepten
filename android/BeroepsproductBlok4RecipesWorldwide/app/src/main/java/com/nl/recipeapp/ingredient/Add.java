package com.nl.recipeapp.ingredient;


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

import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Ingredient;

/**
 * A simple {@link Fragment} subclass.
 */
public class Add extends Fragment {
    private View view;
    private EditText edittext_ingredientName, edittext_ingredientDescription;
    private TextView textview_ingredientDescriptionCharacterCount;

    // Variables for the database connection
    AddConnector addConnector;

    public Add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_ingredient, container, false);

        // Initialize the Class variables
        edittext_ingredientName = view.findViewById(R.id.addIngredient_edittext_ingredientName);
        edittext_ingredientDescription = view.findViewById(R.id.addIngredient_edittext_ingredientDescription);
        textview_ingredientDescriptionCharacterCount = view.findViewById(R.id.addIngredient_textview_ingredientDescriptionCharacterCount);

        // Create the connector that will pass requests towards the database
        addConnector = new AddConnector(this.getContext(), view);

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
        edittext_ingredientDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textview_ingredientDescriptionCharacterCount.setText(edittext_ingredientDescription.getText().length() + " / 65.535", null);

                if (edittext_ingredientDescription.getText().length() > 65535) {
                    textview_ingredientDescriptionCharacterCount.setTextColor(Color.RED);
                } else {
                    textview_ingredientDescriptionCharacterCount.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initializeButtons() {
        Button button_applyIngredient = view.findViewById(R.id.addIngredient_btn_applyIngredient);
        button_applyIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity)getActivity()).getCurrentUser() == null) {
                    Toast.makeText(getActivity(), "U moet ingelogd zijn om een ingrediënt toe te voegen", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // First, check if all the fields are filled in. If not, display a Toast accordingly
                    if (edittext_ingredientName.getText().toString().equals("")) {
                        // Check if a name is entered
                        Toast.makeText(getActivity(), "U moet een ingrediënt naam invullen", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Second, check if the description field isn't more than 65.535 characters. If it is, display a Toast
                    if (edittext_ingredientDescription.getText().length() > 65535) {
                        Toast.makeText(getActivity(), "Uw ingrediënt omschrijving mag niet meer dan 65.535 karakters bevatten", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Ingredient ingredient = new Ingredient(edittext_ingredientName.getText().toString(), edittext_ingredientDescription.getText().toString(), false);
                    boolean value = addConnector.addIngredient(ingredient);

                    if (value) {
                        edittext_ingredientName.setText("");
                        edittext_ingredientDescription.setText("");
                    }
                }
            }
        });
    }
}
