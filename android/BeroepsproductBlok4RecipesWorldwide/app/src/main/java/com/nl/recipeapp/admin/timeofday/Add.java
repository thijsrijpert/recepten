package com.nl.recipeapp.admin.timeofday;


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
    private EditText edittext_name;
    private AddConnector addConnector;

    public Add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_time_of_day, container, false);

        // Initialize the Class variables
        edittext_name = view.findViewById(R.id.addTimeOfDay_edittext_timeOfDayName);

        // Create the connector that will pass requests towards the database
        addConnector = new AddConnector(this.getContext());
        addConnector.setAddTimeOfDay(this);

        // Launch the initialization methods
        initializeButtons();

        return view;
    }

    private void initializeButtons() {
        Button button_applyTijdvak = view.findViewById(R.id.addTimeOfDay_btn_applyTimeOfDay);
        button_applyTijdvak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, check if all the fields are filled in. If not, display a Toast accordingly
                if (edittext_name.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een tijdvak naam invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                addConnector.addTimeOfDay(edittext_name.getText().toString());
            }
        });
    }

    public EditText getEdittext_name() {
        return edittext_name;
    }
}
