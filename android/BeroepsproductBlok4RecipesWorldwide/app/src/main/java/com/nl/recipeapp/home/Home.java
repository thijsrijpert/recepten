package com.nl.recipeapp.home;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;
import com.nl.recipeapp.SharedPreferencesManager;
import com.nl.recipeapp.admin.Admin;
import com.nl.recipeapp.search.Search;
import com.nl.recipeapp.user.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    private View view;
    private com.nl.recipeapp.model.User currentUser;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        currentUser = SharedPreferencesManager.getInstance(this.getActivity()).getPref();

        Button button_administrator = view.findViewById(R.id.home_button_administrator);
        button_administrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user is logged in and check if the logged in user is an administrator
                if (currentUser == null || !currentUser.getUserRole().trim().equals("administrator")) {
                    Toast.makeText(getActivity(), "U moet ingelogd zijn als een administrator", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If all the checks are passed, send the user forth to the Administrator area
                Intent intent = new Intent(v.getContext(), Admin.class);
                intent.putExtra("USERNAME", currentUser.getUsername());
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

        Button button_user = view.findViewById(R.id.home_button_userArea);
        button_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Activeer deze code zodra alles correct werkt (Nu zijn er geen checks benodigd zodat controleren sneller kan)
                if (currentUser == null) {
                    Toast.makeText(getActivity(), "U moet ingelogd zijn om naar uw gebruikersomgeving te gaan", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(v.getContext(), User.class);
                    startActivity(intent);
                }
            }
        });

        return view;


    }

}
