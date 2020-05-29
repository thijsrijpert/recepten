package com.nl.recipeapp.admin.country;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.nl.recipeapp.recipe.AddConnector;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit extends Fragment {
    private View view;
    private AddConnector addConnector_recipe;
    private com.nl.recipeapp.admin.country.AddConnector addConnector_country;
    private GeneralMethods generalMethods;

    private EditText edittext_code, edittext_name, edittext_description;

    // Variables for the Spinner
    private Spinner spinner_countries;
    private ArrayAdapter<Country> arrayadapter_countries;
    private ArrayList<Country> arraylist_countries;

    public Edit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_edit_country, container, false);

        // Create the ArrayList to store the Country objects
        arraylist_countries = new ArrayList<>();

        // Create the neseccary connectors
        addConnector_recipe = new AddConnector(this.getContext());
        addConnector_recipe.setEditCountry(this);

        addConnector_country = new com.nl.recipeapp.admin.country.AddConnector(this.getContext());
        addConnector_country.setEditCountry(this);

        generalMethods = new GeneralMethods(this.getContext());

        // Call the initialization methods
        initializeEditTexts();
        initializeSpinner();
        initializeButton();

        return view;
    }

    /**
     * Gets called when the user resumes on this screen after initializing
     */
    public void onStart() {
        super.onStart();
        initializeArrayLists(); // Initializes the Spinner ArrayLists, which are used in the ArrayAdapters
    }

    private void initializeEditTexts() {
        TextView textview_description = view.findViewById(R.id.editCountry_textview_countryDescriptionCharacterCount);
        edittext_code = view.findViewById(R.id.editCountry_edittext_countryCode);
        edittext_name = view.findViewById(R.id.editCountry_edittext_countryName);
        edittext_description = view.findViewById(R.id.editCountry_edittext_description);
        edittext_description.addTextChangedListener(new CharacterCountListener(textview_description, edittext_description));
    }

    private void initializeSpinner() {
        spinner_countries = view.findViewById(R.id.editCountry_spinner_approvedCountries);
        initializeArrayLists();

        arrayadapter_countries = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_countries);
        spinner_countries.setAdapter(arrayadapter_countries);
    }

    private void initializeButton() {
        Button button_saveChanges = view.findViewById(R.id.editCountry_btn_saveChanges);
        button_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, check if all the fields are filled in. If not, display a Toast accordingly
                if (edittext_code.getText().toString().equals("")) {
                    // Check if a countrycode is entered
                    Toast.makeText(getActivity(), "U moet een landcode invullen", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edittext_name.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een land naam invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                addConnector_country.editCountry(generalMethods.getCountryCodeFromName(spinner_countries.getSelectedItem().toString()), spinner_countries.getSelectedItem().toString(), edittext_code.getText().toString(), edittext_name.getText().toString(), edittext_description.getText().toString());
            }
        });
    }

    public void initializeArrayLists() {
        addConnector_recipe.getCountries("EditCountry");
    }

    public ArrayList<Country> getArraylist_countries() {
        return arraylist_countries;
    }

    public ArrayAdapter<Country> getArrayadapter_countries() {
        return arrayadapter_countries;
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
