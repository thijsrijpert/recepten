package com.nl.beroepsproductblok4_recipesworldwide.administrator.tijdvakken;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nl.beroepsproductblok4_recipesworldwide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTimeOfDay extends Fragment {
    private View view;
    private EditText edittext_tijdvakName;
    private AddTimeOfDay_WebserverConnector addTimeOfDay_webserverConnector;

    public AddTimeOfDay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_time_of_day, container, false);

        // Initialize the Class variables
        edittext_tijdvakName = view.findViewById(R.id.addTijdvak_edittext_tijdvakName);

        // Create the connector that will pass requests towards the database
        addTimeOfDay_webserverConnector = new AddTimeOfDay_WebserverConnector(this.getContext(), view);

        // Launch the initialization methods
        initializeButtons();

        return view;
    }

    private void initializeButtons() {
        Button button_applyTijdvak = view.findViewById(R.id.addTijdvak_btn_applyTijdvak);
        button_applyTijdvak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, check if all the fields are filled in. If not, display a Toast accordingly
                if (edittext_tijdvakName.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een tijdvak naam invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean value = addTimeOfDay_webserverConnector.addTimeOfDay(edittext_tijdvakName.getText().toString());

                if (value) {
                    edittext_tijdvakName.setText("");
                }
            }
        });
    }
}
