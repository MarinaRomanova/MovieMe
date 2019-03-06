package com.example.android.movieme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ArrayAdapter that takes and element of Films Array
 * as parameter and binds it with corresponding grid_item.xml
 */

public class GridViewAdapter extends ArrayAdapter<Films> {

    public  GridViewAdapter(Context context, ArrayList<Films> filmsArrayList) {
        super(context, 0, filmsArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Checks if the existing view is being reused, otherwise inflate the view
        View gridItemView = convertView;
        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }

        // Get the {@link Films} object located at the position in the list
        Films currentFilm = getItem(position);

        // Find the TextView in the grid_item.xml with the ID and set film's title from the list
        TextView filmTextView = gridItemView.findViewById(R.id.film_title_tv_grid_view);
        filmTextView.setText(currentFilm.getFilmTitle());

        // Find the TextView in the grid_item.xml with the ID and set film's duration from the list
        TextView durationTextView = gridItemView.findViewById(R.id.film_duration_tv_grid_view);
        durationTextView.setText(currentFilm.getReleaseDate());

        // Get the image resource ID from the current object and set the cover image
        ImageView coverImageView = gridItemView.findViewById(R.id.cover_image_grid_view);
        coverImageView.setImageBitmap(currentFilm.getPosterPath());//TO DO!!! picasso
        //coverImageView.setImageResource(currentFilm.getPosterPath());

        return gridItemView;
    }


}
