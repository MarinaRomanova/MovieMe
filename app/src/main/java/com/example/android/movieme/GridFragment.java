package com.example.android.movieme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.android.movieme.Utils.FilmsLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment displayed as the first tab, containing an ArrayList of
 * Films displayed as a grid with the help of GridViewAdapter
 * Corresponding layout file is grid_tab.xml
 */

public class GridFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Films>> {
    //Constant value for the earthquake loader ID.
    private static final int FILM_LOADER_ID = 0;
    GridViewAdapter gridViewAdapter;
    View rootView;
    SharedPreferences sharedPreferences;
    //TextView that is displayed when the list is empty
    private TextView anEmptyStateTextView;

    public GridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.grid_tab, container, false);
        anEmptyStateTextView = rootView.findViewById(R.id.empty_view);
        gridViewAdapter = new GridViewAdapter(getActivity(), new ArrayList<Films>());
        GridView gridView = rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Films currentFilm = gridViewAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Intent intent = new Intent(getContext(), FilmDetailActivity.class);
                intent.putExtra("title", currentFilm.getFilmTitle());
                intent.putExtra("overview", currentFilm.getOverview());
                intent.putExtra("date", currentFilm.getReleaseDate());
                intent.putExtra("rate", currentFilm.getVote());
                intent.putExtra("position", position);
                intent.putExtra("image", currentFilm.getImage());
                startActivity(intent);
            }
        });

        // The code below checks of there is an internet connection
        //If connected, a loading starts with AsyncTaskLoader
        //Otherwise a TextView is displayed  to notify the user that there is no connection

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(FILM_LOADER_ID, null, this);
        } else {
            View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            //anEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
            anEmptyStateTextView.setText(R.string.no_internet_connection);
        }
       setHasOptionsMenu(true);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return rootView;
    }

    @Override
    public android.support.v4.content.Loader<List<Films>> onCreateLoader(int id, Bundle args) {
        Log.v("onCreateLoader : ", String.valueOf(new FilmsLoader(getContext())));
        return new FilmsLoader(getContext()); //returns a list of films after performing fetchData method in background
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<Films>> loader, List<Films> films) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        // Clear the adapter of previous news data
        gridViewAdapter.clear();

        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (films != null && !films.isEmpty()) {
            gridViewAdapter.addAll(films);
        } else {
            // Set empty state text to display "No films to display."
            anEmptyStateTextView.setText(R.string.no_films);
        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<List<Films>> loader) {
        // Loader reset, so we can clear out our existing data.
        gridViewAdapter.clear();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                //settingsIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
