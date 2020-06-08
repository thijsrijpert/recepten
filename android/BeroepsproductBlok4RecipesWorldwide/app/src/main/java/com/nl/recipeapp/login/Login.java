package com.nl.recipeapp.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;
import com.nl.recipeapp.SharedPreferencesManager;
import com.nl.recipeapp.model.User;
import com.nl.recipeapp.register.Register;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
    private FragmentTransaction transaction;

    View view;

    private LinearLayout login_linearLayout_textviews;
    private EditText edit_login, edit_password;
    private Button login_btn_signIn, button_logout;
    private com.nl.recipeapp.login.Connector connector_login;
        // Loads the register fragment when clicked on register textfield
    private Fragment fragment_register;

    private User currentUser;

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

        // Connector
        connector_login = new Connector(this.getContext(), view,this);

        // Initialize the Class variables
        edit_login = view.findViewById(R.id.edit_login);
        edit_password = view.findViewById(R.id.edit_password);

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

        login_btn_signIn = view.findViewById(R.id.login_btn_signIn);
        login_btn_signIn.setOnClickListener(new View.OnClickListener() {
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
                    connector_login.login();
                    button_logout.setEnabled(true);
                    login_btn_signIn.setEnabled(false);
                }
            }
        });

        button_logout = view.findViewById(R.id.login_btn_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setCurrentUser(null);
                SharedPreferencesManager.getInstance(getActivity()).removePref("USER");
                ((MainActivity)getActivity()).getMain_textview_loggedInUser().setText("U bent niet ingelogd");
                button_logout.setEnabled(false);
                login_btn_signIn.setEnabled(true);
            }
        });

        return view;
    }

    public void onStart() {
        super.onStart();

        currentUser = SharedPreferencesManager.getInstance(this.getActivity()).getPref();

        if (currentUser != null) {
            button_logout.setEnabled(true);
            login_btn_signIn.setEnabled(false);
        } else {
            button_logout.setEnabled(false);
            login_btn_signIn.setEnabled(true);
        }
    }
}
