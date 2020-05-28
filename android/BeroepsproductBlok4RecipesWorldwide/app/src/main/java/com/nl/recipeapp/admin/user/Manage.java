package com.nl.recipeapp.admin.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nl.recipeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Manage extends Fragment {
    private View view;

    public Manage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_manage_admin_users, container, false);



        return view;
    }
}
