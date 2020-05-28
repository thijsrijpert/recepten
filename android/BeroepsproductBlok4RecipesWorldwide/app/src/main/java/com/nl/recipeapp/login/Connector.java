package com.nl.recipeapp.login;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.nl.recipeapp.R;

public class Connector {
    private Context context;
    private boolean succesfullyAddedIngredient;
    private EditText edit_login, edit_password;


    public Connector(Context context, View view) {
        this.context = context;
        edit_login = view.findViewById(R.id.edit_login);
        edit_password = view.findViewById(R.id.edit_password);
    }
}
