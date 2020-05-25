package com.nl.recipeapp.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nl.recipeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    View view;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        TextView t2 = (TextView) view.findViewById(R.id.login_textView_registratie);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        // Inflate the layout for this fragment
        return view;



    }
}
