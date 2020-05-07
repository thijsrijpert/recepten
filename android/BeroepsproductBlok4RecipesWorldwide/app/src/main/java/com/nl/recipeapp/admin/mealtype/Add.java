package com.nl.recipeapp.admin.mealtype;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nl.recipeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Add extends Fragment {
    private View view;
    private EditText edittext_mealtypeName;
    private AddConnector addConnector;

    public Add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_mealtype, container, false);

        // Initialize the Class variables
        edittext_mealtypeName = view.findViewById(R.id.addMealtype_edittext_mealtypeName);

        // Create the connector that will pass requests towards the database
        addConnector = new AddConnector(this.getContext(), view);

        // Launch the initialization methods
        initializeButtons();

        return view;
    }

    private void initializeButtons() {
        Button button_applyMealtype = view.findViewById(R.id.addMealtype_btn_applyMealtype);
        button_applyMealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, check if all the fields are filled in. If not, display a Toast accordingly
                if (edittext_mealtypeName.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een maaltijdsoort naam invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean value = addConnector.addMealtype(edittext_mealtypeName.getText().toString());

                if (value) {
                    edittext_mealtypeName.setText("");
                }
            }
        });
    }
}
