package com.nl.beroepsproductblok4_recipesworldwide.home;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nl.beroepsproductblok4_recipesworldwide.MainActivity;
import com.nl.beroepsproductblok4_recipesworldwide.R;
import com.nl.beroepsproductblok4_recipesworldwide.administrator.Administrator;
import com.nl.beroepsproductblok4_recipesworldwide.search.Search;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    private View view;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Button button_administrator = view.findViewById(R.id.home_button_administrator);
        button_administrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Administrator.class);
                startActivity(intent);
            }
        });

        Button button_search = view.findViewById(R.id.home_button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Search.class);
                startActivity(intent);
            }
        });

        return view;


    }

}
