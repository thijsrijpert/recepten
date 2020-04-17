package com.nl.beroepsproductblok4_recipesworldwide.religie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nl.beroepsproductblok4_recipesworldwide.R;
import com.nl.beroepsproductblok4_recipesworldwide.administrator.tijdvakken.AddTijdvak_WebserverConnector;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddReligie extends Fragment {
    private View view;
    private EditText edittext_religieName;
    private AddTijdvak_WebserverConnector addTijdvak_webserverConnector;

    public AddReligie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_tijdvak, container, false);

        // Initialize the Class variables
        edittext_religieName = view.findViewById(R.id.addTijdvak_edittext_tijdvakName);

        // Create the connector that will pass requests towards the database
        addTijdvak_webserverConnector = new AddTijdvak_WebserverConnector(this.getContext(), view);

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
                ArrayList<String> arraylist_tijdvakken = addTijdvak_webserverConnector.getAllTijdvakken();
                if (edittext_religieName.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een tijdvak naam invullen", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Check for duplicates
                    for (int c = 0; c < arraylist_tijdvakken.size(); c++) {
                        if (arraylist_tijdvakken.get(c).equals(edittext_religieName.getText().toString())) {
                            Toast.makeText(getActivity(), "Het ingevulde tijdvak naam bestaat al", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                boolean value = addTijdvak_webserverConnector.addTijdvak(edittext_religieName.getText().toString());

                if (value) {
                    edittext_religieName.setText("");
                }
            }
        });
    }
}
