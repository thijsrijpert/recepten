package com.nl.recipeapp.religion;

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
    private EditText edittext_religieName;
    private AddConnector addConnector;

    public Add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_religion, container, false);

        // Initialize the Class variables
        edittext_religieName = view.findViewById(R.id.addReligion_edittext_religionName);

        // Create the connector that will pass requests towards the database
        addConnector = new AddConnector(this.getContext(), view);

        // Launch the initialization methods
        initializeButtons();

        return view;
    }

    private void initializeButtons() {
        Button button_applyReligie = view.findViewById(R.id.addReligion_btn_applyReligion);
        button_applyReligie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, check if all the fields are filled in. If not, display a Toast accordingly
//                ArrayList<String> arraylist_religie = addReligie_webserverConnector.getAllReligie();
                if (edittext_religieName.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een religie naam invullen", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Check for duplicates
//                    for (int c = 0; c < arraylist_religie.size(); c++) {
//                        if (arraylist_religie.get(c).equals(edittext_religieName.getText().toString())) {
//                            Toast.makeText(getActivity(), "Het ingevulde religie naam bestaat al", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
                }

                boolean value = addConnector.addReligion(edittext_religieName.getText().toString());

                if (value) {
                    edittext_religieName.setText("");
                }
            }
        });
    }
}
