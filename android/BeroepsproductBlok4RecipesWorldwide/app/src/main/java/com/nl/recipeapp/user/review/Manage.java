package com.nl.recipeapp.user.review;

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

import com.nl.recipeapp.CharacterCountListener;
import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Review;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Manage extends Fragment {
    private View view;
    private Connector connector_userReview;

    private EditText edittext_title, edittext_description;
    private Spinner spinner_reviews, spinner_ratings;
    private ArrayAdapter<String> arrayadapter_reviewNames;

    private ArrayList<Review> arraylist_reviews;
    private ArrayList<String> arraylist_ratings, arraylist_reviewNames;

    public Manage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_manage_user_reviews, container, false);

        // Initialize the Connectors, used to communicate data back and forth to and from the database
        connector_userReview = new Connector(this.getContext());

        // Initialize the ArrayLists, which will be filled with items from the Connector(s)
        arraylist_reviews = new ArrayList<>();
        arraylist_reviewNames = new ArrayList<>();

        arraylist_ratings = new ArrayList<>();
        arraylist_ratings.add("1");
        arraylist_ratings.add("2");
        arraylist_ratings.add("3");
        arraylist_ratings.add("4");
        arraylist_ratings.add("5");

        // Set the class in the Connectors, so the ArrayLists from this class can be instantly filled with data from the Connector
        connector_userReview.setManageUserReview(this);

        // Start the initialization methods
        initializeEditTexts();
        initializeRecyclerView();
        initializeSpinners();
        initializeButtons();

        return view;
    }

    /**
     * Gets called when the user resumes on this screen after initializing
     */
    public void onStart() {
        super.onStart();
        initializeArrayLists();  // Initializes the Spinner ArrayLists, which are used in the ArrayAdapters
    }

    private void initializeEditTexts() {
        edittext_title = view.findViewById(R.id.manageUserReviews_edittext_title);

        TextView textview_charactercount = view.findViewById(R.id.manageUserReviews_textview_characterCount);
        edittext_description = view.findViewById(R.id.manageUserReviews_edittext_description);
        edittext_description.addTextChangedListener(new CharacterCountListener(textview_charactercount, edittext_description));
    }

    private void initializeRecyclerView() {

    }

    private void initializeSpinners() {
        // Initialize the Spinner objects
        spinner_reviews = view.findViewById(R.id.manageUserReviews_spinner_reviews);
        spinner_ratings = view.findViewById(R.id.manageUserReviews_spinner_ratings);

        // Initialize the spinner_reviews listener, so all input fields get filled when selecting an item from the Spinner
        spinner_reviews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int c = 0; c < arraylist_reviews.size(); c++) {
                    if (spinner_reviews.getSelectedItem().toString().equals(arraylist_reviews.get(c).getTitle())) {
                        // Initialize the EditTexts
                        edittext_title.setText(arraylist_reviews.get(c).getTitle());
                        edittext_description.setText(arraylist_reviews.get(c).getDescription());

                        // Initialize Spinners
                        int spinner_position = Integer.parseInt(arraylist_reviews.get(c).getRating()) - 1;
                        spinner_ratings.setSelection(spinner_position);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the adapters belonging to the Spinners
        ArrayAdapter<String> arrayAdapter_ratings = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_ratings);
        spinner_ratings.setAdapter(arrayAdapter_ratings);

        arrayadapter_reviewNames = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_reviewNames);
        spinner_reviews.setAdapter(arrayadapter_reviewNames);
    }

    private void initializeButtons() {
        Button button_saveChanges = view.findViewById(R.id.manageUserReviews_button_saveChanges);
        button_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button button_delete = view.findViewById(R.id.manageUserReviews_button_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initializeArrayLists() {
        arraylist_reviewNames.clear();
//        connector_userReview.getReviewsForSpecificUser(((MainActivity)getActivity()).getCurrentUser().getUsername(), "ManageUserReview");
        for (int c = 0; c < arraylist_reviews.size(); c++) {
            arraylist_reviewNames.add(arraylist_reviews.get(c).getTitle());
        }
        arrayadapter_reviewNames.notifyDataSetChanged();
    }

    // Getters for the ArrayLists and Adapters
    public ArrayList<Review> getArrayList_reviews() {
        return arraylist_reviews;
    }
}
