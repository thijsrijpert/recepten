package com.nl.recipeapp.admin.timeofday;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nl.recipeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit extends Fragment {

    public Edit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_time_of_day, container, false);
    }
}
