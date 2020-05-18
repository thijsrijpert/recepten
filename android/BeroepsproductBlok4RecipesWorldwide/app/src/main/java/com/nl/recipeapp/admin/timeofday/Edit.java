package com.nl.recipeapp.admin.timeofday;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nl.recipeapp.R;
import com.nl.recipeapp.model.TimeOfDay;
import com.nl.recipeapp.recipe.AddConnector;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit extends Fragment {
    private View view;
    private AddConnector addConnector_recipe;
    private com.nl.recipeapp.admin.timeofday.AddConnector addConnector_timeofday;

    private EditText edittext_name;

    // Variables for the Spinner
    private Spinner spinner_timeofday;
    private ArrayAdapter<TimeOfDay> arrayAdapter_timeofday;
    private ArrayList<TimeOfDay> arraylist_timeofday;

    public Edit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_time_of_day, container, false);

        // Create the ArrayList to store the TimeOfDay objects
        arraylist_timeofday = new ArrayList<>();

        addConnector_recipe = new AddConnector(this.getContext()); // Create the AddConnector from the recipe package, used to get all the TimeOfDay objects stored in the database
        addConnector_recipe.setEditTimeOfDay(this);

        addConnector_timeofday = new com.nl.recipeapp.admin.timeofday.AddConnector(this.getContext()); // Create the AddConnector from the admin.timeofday package, so a TimeOfDay object may be changed

        // Call the initialization methods
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

    private void initializeSpinner() {
        spinner_timeofday = view.findViewById(R.id.editTijdvak_spinner_tijdvakList);

        // Fill the ArrayLists which contain the String objects used in the dropdown lists
        initializeArrayLists();

        arrayAdapter_timeofday = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_timeofday);
        spinner_timeofday.setAdapter(arrayAdapter_timeofday);
    }

    private void initializeButton() {
        edittext_name = view.findViewById(R.id.editTijdvak_edittext_tijdvakName);

        Button button_saveChanges = view.findViewById(R.id.editTijdvak_btn_saveChanges);
        button_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, check if all the fields are filled in. If not, display a Toast accordingly
                if (edittext_name.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een tijdvak naam invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean value = addConnector_timeofday.editTimeOfDay(spinner_timeofday.getSelectedItem().toString(), edittext_name.getText().toString());

                if (value) {
                    Toast.makeText(getContext(), "Tijdvak '" + spinner_timeofday.getSelectedItem().toString() + "' succesvol gewijzigd naar '" + edittext_name.getText().toString() + "'", Toast.LENGTH_SHORT).show();
                    edittext_name.setText("");
                }
            }
        });
    }

    /**
     * Initializes the ArrayLists, used in the Spinners. This method is called once in the onCreate() and again every time the onStart() is called to refresh its contents
     * Every time this method is called, the ArrayLists with names are cleared first to prevent the list from filling with the wrong (or duplicate) items
     */
    private void initializeArrayLists() {
        addConnector_recipe.getTimeOfDay("EditTimeOfDay");
    }

    public ArrayList<TimeOfDay> getArraylist_timeofday() {
        return arraylist_timeofday;
    }

    public ArrayAdapter<TimeOfDay> getArrayAdapter_timeofday() {
        return arrayAdapter_timeofday;
    }
}
