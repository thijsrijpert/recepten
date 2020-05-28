package com.nl.recipeapp;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class CharacterCountListener implements TextWatcher {
    private TextView textview_charactercount;
    private EditText edittext_textfield;

    public CharacterCountListener(TextView textview_charactercount, EditText edittext_textfield) {
        this.textview_charactercount = textview_charactercount;
        this.edittext_textfield = edittext_textfield;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textview_charactercount.setText(edittext_textfield.getText().length() + " / 65535", null);

        if (edittext_textfield.getText().length() > 65535) {
            textview_charactercount.setTextColor(Color.RED);
        } else {
            textview_charactercount.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
