package com.nl.recipeapp.admin.mealtype;

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
import com.nl.recipeapp.model.Mealtype;
import com.nl.recipeapp.recipe.AddConnector;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit extends Fragment {
    private View view;
    private AddConnector addConnector_recipe;
    private com.nl.recipeapp.admin.mealtype.AddConnector addConnector_mealtype;

    private EditText edittext_name;

    // Variables for the Spinner
    private Spinner spinner_mealtypes;
    private ArrayList<Mealtype> arraylist_mealtypes;
    private ArrayAdapter<Mealtype> arrayadapter_mealtypes;

    public Edit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_mealtype, container, false);

        // Create the ArrayList to store the Mealtype objects
        arraylist_mealtypes = new ArrayList<>();

        // Create the connectors to create a connection with the webserver
        addConnector_recipe = new AddConnector(this.getContext());
        addConnector_recipe.setEditMealtype(this);

        addConnector_mealtype = new com.nl.recipeapp.admin.mealtype.AddConnector(this.getContext());
        addConnector_mealtype.setEditMealtype(this);

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
        edittext_name = view.findViewById(R.id.editMealtype_edittext_mealtypeName);
    }

    private void initializeSpinner() {
        spinner_mealtypes = view.findViewById(R.id.editMealtype_spinner_approvedMealtypes);
        initializeArrayLists();

        arrayadapter_mealtypes = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_mealtypes);
        spinner_mealtypes.setAdapter(arrayadapter_mealtypes);
    }

    private void initializeButton() {
        Button button_saveChanges = view.findViewById(R.id.editMealtype_btn_saveChanges);
        button_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, check if all the fields are filled in. If not, display a Toast accordingly
                if (edittext_name.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een maaltijdsoort invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                addConnector_mealtype.editMealtype(spinner_mealtypes.getSelectedItem().toString(), edittext_name.getText().toString());
            }
        });
    }

    public void initializeArrayLists() {
        addConnector_recipe.getMealTypes("EditMealtype");
    }

    public ArrayList<Mealtype> getArraylist_mealtypes() {
        return arraylist_mealtypes;
    }

    public ArrayAdapter<Mealtype> getArrayadapter_mealtypes() {
        return arrayadapter_mealtypes;
    }

    public EditText getEdittext_name() {
        return edittext_name;
    }
}
