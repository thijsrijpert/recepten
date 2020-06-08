package com.nl.recipeapp.religion;

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
import com.nl.recipeapp.model.Religion;
import com.nl.recipeapp.model.Word;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit extends Fragment {
    private View view;
    private EditText edittext_religion;
    private com.nl.recipeapp.religion.AddConnector addConnector;

    private Spinner spinner_religion;
    private ArrayList<Religion> arraylist_religion;
    private ArrayAdapter<Religion> arrayadapter_religion;

    public Edit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_religion, container, false);

        edittext_religion = view.findViewById(R.id.editReligion_edittext_religionName);
        addConnector = new AddConnector(this.getContext(),view);
        addConnector.setEdittext_religionName(this);

        arraylist_religion = new ArrayList<>();

        initializeSpinner();
        initializeButtons();

        return view;
    }

    private void initializeSpinner() {
        spinner_religion = view.findViewById(R.id.editReligion_spinner_approvedReligion);

        initializeArrayLists();

        arrayadapter_religion = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_religion);
        spinner_religion.setAdapter(arrayadapter_religion);
    }

    private void initializeButtons() {
        Button button_saveChanges = view.findViewById(R.id.editReligion_btn_saveChanges);
        button_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if a new word is filled in
                if (edittext_religion.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "Vul een woord in", Toast.LENGTH_SHORT).show();
                }

                // Otherwise, move on to editing the word
                addConnector.editReligion(spinner_religion.getSelectedItem().toString(), edittext_religion.getText().toString());
            }
        });
    }

    public void initializeArrayLists() {
        addConnector.getAllReligion();
    }

    public EditText getEdittext_religion() {
        return edittext_religion;
    }

    public ArrayList<Religion> getArraylist_religion() {
        return arraylist_religion;
    }

    public ArrayAdapter<Religion> getArrayadapter_religion() {
        return arrayadapter_religion;
    }
}
//public class Edit extends Fragment {
//
//    public Edit() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R
//                .layout.fragment_edit_religion, container, false);
//    }
//}
