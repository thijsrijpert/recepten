package com.nl.recipeapp.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nl.recipeapp.R;
import com.nl.recipeapp.register.Register;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
    private FragmentTransaction transaction;

    View view;

    private Fragment fragment_register;
    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        // Create the framelayout to be able to set it either visible or invisible
        final FrameLayout frameLayout = view.findViewById(R.id.login_framelayout);
        frameLayout.setVisibility(View.INVISIBLE);

        // Place the register fragment in the frame layout
        fragment_register = new Register();
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_framelayout, fragment_register);
        transaction.commit();

        // Create the textview which toggles the display of the framelayout
        TextView t2 = view.findViewById(R.id.login_textView_registratie);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frameLayout.getVisibility() == View.INVISIBLE) {
                    frameLayout.setVisibility(View.VISIBLE);
                } else {
                    frameLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        return view;

    }
}
