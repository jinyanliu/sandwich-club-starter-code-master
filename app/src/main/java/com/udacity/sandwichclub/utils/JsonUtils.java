package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameJSONObject = jsonObject.getJSONObject("name");
            String mainName = nameJSONObject.getString("mainName");
            JSONArray alsoKnownAsNameJSONArray = nameJSONObject.getJSONArray("alsoKnownAs");

            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsNameJSONArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsNameJSONArray.getString(i));
            }

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");
            JSONArray ingredientsJSONArray = jsonObject.getJSONArray("ingredients");

            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJSONArray.length(); i++) {
                ingredients.add(ingredientsJSONArray.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Failed to pass Sandwich JSON.");
            return null;
        }
    }
}
