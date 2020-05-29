package com.nl.recipeapp.review;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nl.recipeapp.CharacterCountListener;
import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;
import com.nl.recipeapp.model.Review;

import java.util.ArrayList;

public class Add extends AppCompatActivity {
    private String recipe_id;
    private EditText edittext_title, edittext_description;
    private Spinner spinner_rating;
    private AddConnector addConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        addConnector = new AddConnector(getApplicationContext()); // Instantiate the AddConnector for this class, to pass a new review to the database

        getData();
        initializeInputFields();
        initializeButtons();
    }

    private void getData() {
        recipe_id = getIntent().getStringExtra("RECIPE_ID");
    }

    private void initializeInputFields() {
        edittext_title = findViewById(R.id.addReview_edittext_title);

        TextView textview_charactercount = findViewById(R.id.addReview_textview_characterCount);
        edittext_description = findViewById(R.id.addReview_edittext_description);
        edittext_description.addTextChangedListener(new CharacterCountListener(textview_charactercount, edittext_description));

        // Create the ArrayList with the ratings and the Spinner + its adapter
        ArrayList<String> arraylist_ratings = new ArrayList<>();
        arraylist_ratings.add("1");
        arraylist_ratings.add("2");
        arraylist_ratings.add("3");
        arraylist_ratings.add("4");
        arraylist_ratings.add("5");

        spinner_rating = findViewById(R.id.addReview_spinner_rating);
        ArrayAdapter<String> arrayAdapter_ratings = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arraylist_ratings);
        spinner_rating.setAdapter(arrayAdapter_ratings);
    }

    private void initializeButtons() {
        Button button_addReview = findViewById(R.id.addReview_button_addReview);
        button_addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if all the fields are filled in
                if (edittext_title.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Vul een titel in", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edittext_description.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Vul een omschrijving in", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create the review object and pass it to the addConnector class
                Review review = new Review("", edittext_title.getText().toString(), edittext_description.getText().toString(), spinner_rating.getSelectedItem().toString(), "", "", ((MainActivity)getApplicationContext()).getCurrentUser().getUsername());
                boolean success = addConnector.addReview(review);

                if (success) {
                    Toast.makeText(getBaseContext(), "Uw review is geplaatst", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Uw review kon niet geplaatst worden", Toast.LENGTH_SHORT).show();
                }

                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
