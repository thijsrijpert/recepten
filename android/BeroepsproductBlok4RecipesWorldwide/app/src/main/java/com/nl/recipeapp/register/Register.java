package com.nl.recipeapp.register;

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
public class Register extends Fragment {

    View view;

    private EditText edit_login, edit_password;

    public Register() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        edit_login = view.findViewById(R.id.edit_login);
        edit_password = view.findViewById(R.id.edit_password);

        Button login_btn_register = view.findViewById(R.id.login_btn_register);
        login_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, check if edit_login is filled in. If not, display a Toast accordingly
                if (edit_login.getText().toString().equals("")) {
                    // Check if a name is entered
                    Toast.makeText(getActivity(), "U moet een gebruikersnaam invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Second, check if the password field in. If not, display a Toast accordingly
                if (edit_password.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Uw moet uw wachtwoord invullen", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    edit_login.setText("");
                    edit_password.setText("");

                }
            }
        });

        return view;

    }
}
