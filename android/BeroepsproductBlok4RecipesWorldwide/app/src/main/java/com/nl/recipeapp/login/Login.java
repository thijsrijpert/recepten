package com.nl.recipeapp.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        fragment_register = new Register();
        Register register = new Register();
        TextView t2 = (TextView) view.findViewById(R.id.login_textView_registratie);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction("Register");
            }
        });

//        Button login_btn_register = register.getLogin_btn_register();
//        login_btn_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentTransaction("Clean");
//            }
//        });


        // Inflate the layout for this fragment
        return view;

    }

    private void fragmentTransaction(String fragment) {
        transaction = getActivity().getSupportFragmentManager().beginTransaction();

        switch (fragment) {
            case "Register":
                transaction.replace(R.id.login_framelayout, fragment_register);
                break;
            case "Clean":
                transaction.replace(R.id.login_framelayout, fragment_register);
                break;
        }

        transaction.commit();
    }


    //    return inflater.inflate(R.layout.fragment_register, container, false);

}
