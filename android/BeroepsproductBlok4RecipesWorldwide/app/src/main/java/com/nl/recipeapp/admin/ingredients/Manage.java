package com.nl.recipeapp.admin.ingredients;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nl.recipeapp.CharacterCountListener;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Ingredient;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Manage extends Fragment {
    // Class variables for general use
    private View view;
    private Connector connectorIngredients;

    // Class variables used in both A and B
    private ArrayList<Ingredient> arraylist_A_unapprovedIngredients, arraylist_B_approvedIngredients;

    // Class variables (A means these variables are for approving or denying an Ingredient)
    private EditText edittext_A_username, edittext_A_name, edittext_A_description;
    private TextView textview_A_descriptionCharCount;
    private Spinner spinner_A_unapprovedIngredients;
    private Button button_A_approve, button_A_deny;
    private ArrayList<String> arraylist_A_unapprovedIngredientNames;
    private ArrayAdapter<String> arrayadapter_A_unapprovedIngredients;

    // Class variables (B means these variables are for managing an Ingredient)
    private EditText edittext_B_username, edittext_B_name, edittext_B_description;
    private TextView textview_B_descriptionCharCount;
    private Spinner spinner_B_approvedIngredients;
    private Button button_B_saveChanges;
    private ArrayList<String> arraylist_B_approvedIngredientNames;
    private ArrayAdapter<String> arrayadapter_B_approvedIngredients;

    public Manage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_manage_admin_ingredients, container, false);

        connectorIngredients = new Connector(this.getContext()); // Create the webserver connector for transferring queries and getting data from the database
        connectorIngredients.setManageIngredients(this);

        // Create the ArrayLists used in both parts A and B
        arraylist_A_unapprovedIngredientNames = new ArrayList<>();
        arraylist_A_unapprovedIngredients = new ArrayList<>();
        arraylist_B_approvedIngredientNames = new ArrayList<>();
        arraylist_B_approvedIngredients = new ArrayList<>();

        // Start the initialization methods for A: Approving or denying ingredients
        initializeViewContent_A_EditText();
        initializeViewContent_A_Spinners();
        initializeViewContent_A_Buttons();

        // Start the initialization methods for B: Managing ingredients
        initializeViewContent_B_EditText();
        initializeViewContent_B_Spinners();
        initializeViewContent_B_Buttons();

        return view;
    }

    /**
     * Gets called when the user resumes on this screen after initializing
     */
    public void onStart() {
        super.onStart();
        initializeArrayLists();  // Initializes the Spinner ArrayLists, which are used in the ArrayAdapters
        updateViewContent_A();
        updateViewContent_B();
    }

    ////// INITIALIZATION METHODS FOR PART A: APPROVING OR DENYING INGREDIENTS //////
    /**
     * Initialize the EditTexts for viewing information in EditTexts (like a name, description etc.)
     */
    private void initializeViewContent_A_EditText() {
        edittext_A_name = view.findViewById(R.id.manageIngredients_A_edittext_ingredientName);
        edittext_A_username = view.findViewById(R.id.manageIngredients_A_edittext_addedByUser);

        textview_A_descriptionCharCount = view.findViewById(R.id.manageIngredients_A_textview_ingredientDescriptionCharacterCount);
        edittext_A_description = view.findViewById(R.id.manageIngredients_A_edittext_description);
        edittext_A_description.addTextChangedListener(new CharacterCountListener(textview_A_descriptionCharCount, edittext_A_description));
    }

    /**
     * Initialize the Spinners for showing the unapproved ingredients
     */
    private void initializeViewContent_A_Spinners() {
        // Initialize the Spinners
        spinner_A_unapprovedIngredients = view.findViewById(R.id.manageIngredients_A_spinner_unapprovedIngredients);

        // Initialize the Spinner's listeners
        spinner_A_unapprovedIngredients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int c = 0; c < arraylist_A_unapprovedIngredients.size(); c++) {
                    if (spinner_A_unapprovedIngredients.getSelectedItem().toString().equals(arraylist_A_unapprovedIngredients.get(c).getName())) {
                        // Initialize the EditTexts
                        edittext_A_name.setText(arraylist_A_unapprovedIngredients.get(c).getName());
                        edittext_A_username.setText(arraylist_A_unapprovedIngredients.get(c).getUsername());
                        edittext_A_description.setText(arraylist_A_unapprovedIngredients.get(c).getDescription());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the Spinner Adapters
        arrayadapter_A_unapprovedIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_A_unapprovedIngredientNames);
        spinner_A_unapprovedIngredients.setAdapter(arrayadapter_A_unapprovedIngredients);
    }

    /**
     * Initialize the Buttons for approving or denying ingredients
     */
    private void initializeViewContent_A_Buttons() {
        button_A_approve = view.findViewById(R.id.manageIngredients_A_button_approveIngredient);
        button_A_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_A_unapprovedIngredients.getCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myDialog);
                    builder.setMessage("Weet u zeker dat u dit ingrediënt wilt goedkeuren?");
                    builder.setTitle("Ingrediënt goedkeuren");
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Ingredient ingredient = null;
                            for (int c = 0; c < arraylist_A_unapprovedIngredients.size(); c++) {
                                if (spinner_A_unapprovedIngredients.getSelectedItem().toString().equals(arraylist_A_unapprovedIngredients.get(c).getName())) {
                                    ingredient = arraylist_A_unapprovedIngredients.get(c);
                                }
                            }

                            connectorIngredients.approveIngredient(ingredient);

//                            if (succeeded) {
//                                Toast.makeText(view.getContext(), ingredient.getName() + " is goedgekeurd", Toast.LENGTH_SHORT).show();
//                                initializeArrayLists();
//                                updateViewContent_A();
//                                updateViewContent_B();
//                            } else {
//                                Toast.makeText(view.getContext(), ingredient.getName() + " kon niet worden goedgekeurd", Toast.LENGTH_SHORT).show();
//                            }
                        }
                    });
                    builder.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(view.getContext(), "Er zijn geen ingrediënten om te beheren", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_A_deny = view.findViewById(R.id.manageIngredients_A_button_denyIngredient);
        button_A_deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_A_unapprovedIngredients.getCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myDialog);
                    builder.setMessage("Weet u zeker dat u dit ingrediënt wilt afkeuren?");
                    builder.setTitle("Ingrediënt afkeuren");
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Ingredient ingredient = null;
                            for (int c = 0; c < arraylist_A_unapprovedIngredients.size(); c++) {
                                if (spinner_A_unapprovedIngredients.getSelectedItem().toString().equals(arraylist_A_unapprovedIngredients.get(c).getName())) {
                                    ingredient = arraylist_A_unapprovedIngredients.get(c);
                                }
                            }

                            connectorIngredients.denyIngredient(ingredient);

//                            if (succeeded) {
//                                Toast.makeText(view.getContext(), ingredient.getName() + " is afgekeurd", Toast.LENGTH_SHORT).show();
//                                initializeArrayLists();
//                                updateViewContent_B();
//                            } else {
//                                Toast.makeText(view.getContext(), ingredient.getName() + " kon niet worden afgekeurd", Toast.LENGTH_SHORT).show();
//                            }
                        }
                    });
                    builder.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(view.getContext(), "Er zijn geen ingrediënten om te beheren", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Updates part A after approving or denying an ingredient
     */
    public void updateViewContent_A() {
        arrayadapter_A_unapprovedIngredients.notifyDataSetChanged();

        if (spinner_A_unapprovedIngredients.getCount() > 0) {
            spinner_A_unapprovedIngredients.setSelection(0);
        } else {
            edittext_A_name.setText(null);
            edittext_A_username.setText(null);
            edittext_A_description.setText(null);
        }
    }



    ////// INITIALIZATION METHODS FOR PART B: MANAGING INGREDIENTS //////
    /**
     * Initialize the EditText input fields for changing text related to a recipe
     */
    private void initializeViewContent_B_EditText() {
        edittext_B_name = view.findViewById(R.id.manageIngredients_B_edittext_ingredientName);
        edittext_B_username = view.findViewById(R.id.manageIngredients_B_edittext_addedByUser);

        textview_B_descriptionCharCount = view.findViewById(R.id.manageIngredients_B_textview_ingredientDescriptionCharacterCount);
        edittext_B_description = view.findViewById(R.id.manageIngredients_B_edittext_description);
        edittext_B_description.addTextChangedListener(new CharacterCountListener(textview_B_descriptionCharCount, edittext_B_description));
    }

    /**
     * Initialize the Spinners for showing the approved ingredients
     */
    private void initializeViewContent_B_Spinners() {
        // Initialize the Spinners
        spinner_B_approvedIngredients = view.findViewById(R.id.manageIngredients_B_spinner_approvedIngredients);

        // Initialize the Spinner's listeners
        spinner_B_approvedIngredients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int c = 0; c < arraylist_B_approvedIngredients.size(); c++) {
                    if (spinner_B_approvedIngredients.getSelectedItem().toString().equals(arraylist_B_approvedIngredients.get(c).getName())) {
                        // Initialize the EditTexts
                        edittext_B_name.setText(arraylist_B_approvedIngredients.get(c).getName());
                        edittext_B_username.setText(arraylist_B_approvedIngredients.get(c).getUsername());
                        edittext_B_description.setText(arraylist_B_approvedIngredients.get(c).getDescription());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the Spinner Adapters
        arrayadapter_B_approvedIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_B_approvedIngredientNames);
        spinner_B_approvedIngredients.setAdapter(arrayadapter_B_approvedIngredients);
    }

    /**
     * Initialize the buttons for saving changes made to approved ingredients
     */
    private void initializeViewContent_B_Buttons() {
        button_B_saveChanges = view.findViewById(R.id.manageIngredients_B_button_approveIngredientChanges);
        button_B_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_B_approvedIngredients.getCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myDialog);
                    builder.setMessage("Weet u zeker dat u dit ingrediënt wilt wijzigen?");
                    builder.setTitle("Ingrediënt wijzigen");
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Ingredient ingredient = null;
                            for (int c = 0; c < arraylist_B_approvedIngredients.size(); c++) {
                                if (spinner_B_approvedIngredients.getSelectedItem().toString().equals(arraylist_B_approvedIngredients.get(c).getName())) {
                                    ingredient = arraylist_B_approvedIngredients.get(c);
                                }
                            }

                            connectorIngredients.updateIngredient(spinner_B_approvedIngredients.getSelectedItem().toString(), edittext_B_name.getText().toString(), edittext_B_description.getText().toString());
                        }
                    });
                    builder.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(view.getContext(), "Er zijn geen ingrediënten om te wijzigen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Updates part B after approving or denying an ingredient
     */
    public void updateViewContent_B() {
        arrayadapter_B_approvedIngredients.notifyDataSetChanged();

        if (spinner_B_approvedIngredients.getCount() > 0) {
            spinner_B_approvedIngredients.setSelection(0);
        } else {
            edittext_B_name.setText(null);
            edittext_B_username.setText(null);
            edittext_B_description.setText(null);
        }
    }



    ////// DEFAULT METHODS, USED IN BOTH PARTS A AND B //////
    /**
     * Initializes the ArrayLists, used in the Buttons in parts A and B. This method is called once in the onCreate() and again every time the onStart() is called to refresh its contents
     */
    public void initializeArrayLists() {
        // Unapproved and Approved Ingredients
        connectorIngredients.getUnapprovedIngredients("ManageIngredient");
        connectorIngredients.getApprovedIngredients("ManageIngredient");
    }

    public EditText getEdittexts(int value) {
        EditText edittext = null;

        switch (value) {
            case 0:
                edittext = edittext_A_name;
            break;
            case 1:
                edittext = edittext_A_description;
            break;
            case 2:
                edittext = edittext_B_name;
                break;
            case 3:
                edittext = edittext_B_description;
                break;
        }

        return edittext;
    }

    // Unapproved Ingredients
    public ArrayList<Ingredient> getArraylist_unapprovedIngredients() {
        return arraylist_A_unapprovedIngredients;
    }

    public ArrayAdapter<String> getArrayAdapter_unapprovedIngredients() {
        return arrayadapter_A_unapprovedIngredients;
    }

    public ArrayList<String> getArraylist_unapprovedIngredientNames() {
        return arraylist_A_unapprovedIngredientNames;
    }

    // Approved Ingredients
    public ArrayList<Ingredient> getArraylist_approvedIngredients() {
        return arraylist_B_approvedIngredients;
    }

    public ArrayAdapter<String> getArrayAdapter_approvedIngredients() {
        return arrayadapter_B_approvedIngredients;
    }

    public ArrayList<String> getArraylist_approvedIngredientNames() {
        return arraylist_B_approvedIngredientNames;
    }
}
