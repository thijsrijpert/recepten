package com.nl.recipeapp.admin.wordfilter;

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
import com.nl.recipeapp.model.Word;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit extends Fragment {
    private View view;
    private EditText edittext_word;
    private AddConnector addConnector;

    private Spinner spinner_words;
    private ArrayList<Word> arraylist_words;
    private ArrayAdapter<Word> arrayadapter_words;

    public Edit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_word, container, false);

        edittext_word = view.findViewById(R.id.editWordfilter_edittext_WordfilterName);
        addConnector = new AddConnector(this.getContext());
        addConnector.setEditWord(this);

        arraylist_words = new ArrayList<>();

        initializeSpinner();
        initializeButtons();

        return view;
    }

    private void initializeSpinner() {
        spinner_words = view.findViewById(R.id.editWordfilter_spinner_WordfilterList);

        initializeArrayLists();

        arrayadapter_words = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_words);
        spinner_words.setAdapter(arrayadapter_words);
    }

    private void initializeButtons() {
        Button button_saveChanges = view.findViewById(R.id.editWordfilter_btn_saveChanges);
        button_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if a new word is filled in
                if (edittext_word.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "Vul een woord in", Toast.LENGTH_SHORT).show();
                }

                // Otherwise, move on to editing the word
                addConnector.editWord(spinner_words.getSelectedItem().toString(), edittext_word.getText().toString());
            }
        });
    }

    public void initializeArrayLists() {
        addConnector.getWordfilter("editWord");
    }

    public EditText getEdittext_word() {
        return edittext_word;
    }

    public ArrayList<Word> getArrayList_words() {
        return arraylist_words;
    }

    public ArrayAdapter<Word> getArrayAdapter_words() {
        return arrayadapter_words;
    }
}

