package com.nl.recipeapp.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    View view;
    private Fragment fragment_register;
    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        fragment_register = new com.nl.recipeapp.login.Register();

        TextView t2 = (TextView) view.findViewById(R.id.login_textView_registratie);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Inflate the layout for this fragment
        return view;

    }


    //    return inflater.inflate(R.layout.fragment_register, container, false);

}
