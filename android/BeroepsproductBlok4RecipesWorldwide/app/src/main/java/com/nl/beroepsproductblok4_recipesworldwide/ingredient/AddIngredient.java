package com.nl.beroepsproductblok4_recipesworldwide.ingredient;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nl.beroepsproductblok4_recipesworldwide.MainActivity;
import com.nl.beroepsproductblok4_recipesworldwide.R;
import com.nl.beroepsproductblok4_recipesworldwide.model.Ingredient;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIngredient extends Fragment {
    private View view;
    private EditText edittext_ingredientName, edittext_ingredientDescription;

    // Variables for the database connection
    AddIngredient_WebserverConnector addIngredient_webserverConnector;

    public AddIngredient() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_ingredient, container, false);

        // Initialize the Class variables
        edittext_ingredientName = view.findViewById(R.id.addIngredient_edittext_ingredientName);
        edittext_ingredientDescription = view.findViewById(R.id.addIngredient_edittext_ingredientDescription);

        // Create the connector that will pass requests towards the database
        addIngredient_webserverConnector = new AddIngredient_WebserverConnector(this.getContext(), view);

        // Launch the initialization methods
        initializeButtons();

        return view;
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
//                    ArrayList<Ingredient> ingredients = addIngredient_webserverConnector.getAllIngredients();
                    if (edittext_ingredientName.getText().toString().equals("")) {
                        // Check if a name is entered
                        Toast.makeText(getActivity(), "U moet een ingrediënt naam invullen", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        // Check for duplicates
//                        for (int c = 0; c < ingredients.size(); c++) {
//                            if (ingredients.get(c).getName().equals(edittext_ingredientName.getText().toString())) {
//                                Toast.makeText(getActivity(), "Het ingevulde ingrediënt naam bestaat al", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        }
                    }

                    // Second, check if the description field isn't more than 65.535 characters. If it is, display a Toast
                    if (edittext_ingredientDescription.getText().length() > 65535) {
                        Toast.makeText(getActivity(), "Uw ingrediënt omschrijving mag niet meer dan 65.535 karakters bevatten", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Ingredient ingredient = new Ingredient(edittext_ingredientName.getText().toString(), edittext_ingredientDescription.getText().toString(), false);
                    boolean value = addIngredient_webserverConnector.addIngredient(ingredient);

                    if (value) {
                        edittext_ingredientName.setText("");
                        edittext_ingredientDescription.setText("");
                    }
                }
            }
        });
    }
}
