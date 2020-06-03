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
import com.nl.recipeapp.admin.Admin;
import com.nl.recipeapp.search.Search;
import com.nl.recipeapp.user.User;

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
//                System.out.println(((MainActivity)getActivity()).getCurrentUser().getUsername());
//                System.out.println(((MainActivity)getActivity()).getCurrentUser().getUserRole());

                // Activeer deze code zodra alles correct werkt (Nu zijn er geen checks benodigd zodat controleren sneller kan)
//                if (((MainActivity)getActivity()).getCurrentUser() == null) {
//                    Toast.makeText(getActivity(), "U moet ingelogd zijn als een administrator", Toast.LENGTH_SHORT).show();
//                } else if (!((MainActivity)getActivity()).getCurrentUser().getUserRole().equals("administrator")) {
//                    Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(v.getContext(), Admin.class);
//                    intent.putExtra("USERNAME", ((MainActivity)getActivity()).getCurrentUser().getUsername());
//                    startActivity(intent);
//                }

                Intent intent = new Intent(v.getContext(), Admin.class);
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
                if (((MainActivity)getActivity()).getCurrentUser() == null) {
                    Toast.makeText(getActivity(), "U moet ingelogd zijn om naar uw gebruikersomgeving te gaan", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(v.getContext(), User.class);
                    intent.putExtra("USERNAME", ((MainActivity)getActivity()).getCurrentUser().getUsername());
                    startActivity(intent);
                }
            }
        });

        return view;


    }

}
