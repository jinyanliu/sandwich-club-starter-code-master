package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final  String EXTRA_POSITION   = "extra_position";
    private static final int    DEFAULT_POSITION = -1;

    Sandwich sandwich = new Sandwich();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        populatePlaceOfOrigin();
        populateDescription();
        populateAlsoKnownAs();
        populateIngredients();
    }

    private void populatePlaceOfOrigin() {
        TextView placeOfOrigin = findViewById(R.id.place_of_origin_tv);
        String placeOfOriginText = sandwich.getPlaceOfOrigin();
        placeOfOriginText = handleEmptyString(placeOfOriginText);
        placeOfOrigin.setText(placeOfOriginText);
    }

    private void populateDescription() {
        TextView description = findViewById(R.id.description_tv);
        String descriptionText = sandwich.getDescription();
        descriptionText = handleEmptyString(descriptionText);
        description.setText(descriptionText);
    }

    private void populateAlsoKnownAs() {
        TextView alsoKnownAs = findViewById(R.id.also_known_as_tv);
        String alsoKnownAsString = getString(sandwich.getAlsoKnownAs());
        alsoKnownAsString = handleEmptyString(alsoKnownAsString);
        alsoKnownAs.setText(alsoKnownAsString);
    }

    private void populateIngredients() {
        TextView ingredients = findViewById(R.id.ingredients_tv);
        String ingredientsString = getString(sandwich.getIngredients());
        ingredientsString = handleEmptyString(ingredientsString);
        ingredients.setText(ingredientsString);
    }

    @NonNull
    private String handleEmptyString(String textToShow) {
        if (textToShow.isEmpty()) {
            textToShow = "Unknown";
        }
        return textToShow;
    }

    @NonNull
    private String getString(final List<String> stringList) {
        StringBuilder alsoKnownAsStringBuilder = new StringBuilder();
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                alsoKnownAsStringBuilder.append(stringList.get(i));
            } else {
                alsoKnownAsStringBuilder.append(", ").append(stringList.get(i));
            }
        }
        return alsoKnownAsStringBuilder.toString();
    }
}
