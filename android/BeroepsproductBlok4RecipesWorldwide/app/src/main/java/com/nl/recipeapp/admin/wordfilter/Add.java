package com.nl.recipeapp.admin.wordfilter;

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
    private EditText edittext_word;
    private com.nl.recipeapp.admin.wordfilter.AddConnector addConnector;

    public Add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_word, container, false);

        // Initialize the Class variables
        edittext_word = view.findViewById(R.id.addWord_edittext_wordName);

        // Create the connector that will pass requests towards the database
        addConnector = new AddConnector(this.getContext());
        addConnector.setAddWord(this);

        // Launch the initialization methods
        initializeButtons();

        return view;
    }

    private void initializeButtons() {
        Button button_applyWord = view.findViewById(R.id.addWord_btn_applyWord);
        button_applyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, check if all the fields are filled in. If not, display a Toast accordingly
                if (edittext_word.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een woord invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                addConnector.addWord(edittext_word.getText().toString());
            }
        });
    }

    public EditText getEdittext_word() {
        return edittext_word;
    }
}
