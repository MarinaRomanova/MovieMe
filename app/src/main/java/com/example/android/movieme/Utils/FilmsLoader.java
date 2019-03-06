package com.example.android.movieme.Utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.movieme.BuildConfig;
import com.example.android.movieme.Films;
import com.example.android.movieme.GridViewAdapter;
import com.example.android.movieme.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FilmsLoader extends android.support.v4.content.AsyncTaskLoader<List<Films>> {
    //Tag for the log messages
    private static final String LOG_TAG = FilmsLoader.class.getSimpleName();
    //constants used for forming request url
    final  String BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
    final String API = "api_key";
    final String SORT_BY = "sort_by";
    final String PRIMARY_RELEASE_YEAR = "primary_release_year";
    final  String LANGUAGE = "language";
    final String GENRE_IDS = "genre_ids";
    final String IMAGE = "http://image.tmdb.org/t/p/w185//";

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

    GridViewAdapter mGridViewAdapter;

    public FilmsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    //returns a list of films objects
    @Override
    public List<Films> loadInBackground()  {
        String jsonResponse = null;
        try {
            HttpHandler handler = new HttpHandler();
            jsonResponse = handler.fetchData(buildURL());
            Log.v(LOG_TAG, "Response from url: " + jsonResponse + "built URL: " + buildURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractFromJson(jsonResponse);
    }

    //creates a request url depending on chosen settings
    private URL buildURL() {
        String sortBy = sharedPreferences.getString(getContext().getString(R.string.settings_sort_by_key), getContext().getString(R.string.settings_sort_by_default));
        Log.v(LOG_TAG, "Sort By " + sortBy);
        URL url = null;
        try {
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    // Append query parameter and its value.
                    .appendQueryParameter(API, BuildConfig.API_KEY)
                    .appendQueryParameter(SORT_BY, sortBy)
                    .build();
            url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "Built URI " + builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //Return a list of films objects that has been built up from parsing the given JSON response.
    @Nullable
    private static List<Films> extractFromJson(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding films to
        List<Films> filmsList = new ArrayList<>();
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            //JSONObject response = baseJsonResponse.getJSONObject("response");
            //JSONArray jsonResultsArray = response.getJSONArray("results");
            JSONArray jsonResultsArray = baseJsonResponse.getJSONArray("results");

            for (int i = 0; i < jsonResultsArray.length(); i++) {
                // Get a single film at position i within the list of films
                JSONObject results = jsonResultsArray.getJSONObject(i);

                // Extract from "results" JSONArray the value for the keys: webPublicationDate, webTitle, webUrl
                String title = results.optString("title");
                Double vote_average = results.optDouble("vote_average");
                String date = results.optString("release_date");
                String dateFormat;
                if (date == null) {
                    dateFormat = " ";
                } else {
                    dateFormat = date.substring(8, 10);
                    dateFormat += "." + date.substring(5, 7);
                    dateFormat += "." + date.substring(0, 4);
                }
                String overview = results.optString("overview");

                String imageUrl = "http://image.tmdb.org/t/p/w500/" + results.optString("poster_path");

                InputStream inputStream = new java.net.URL(imageUrl).openStream();
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);

                // Create a news object with the information from JSON response and add it to the list.
                Films film = new Films(title, dateFormat, imageBitmap, overview, vote_average);
                film.setImage(imageUrl);
                filmsList.add(film);
                Log.v(LOG_TAG, "Film #1" + film.getFilmTitle() + film.getOverview() + film.getReleaseDate());
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filmsList;
    }

}
