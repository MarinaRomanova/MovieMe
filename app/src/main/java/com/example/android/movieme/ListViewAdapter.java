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
 * as parameter and binds it with corresponding list_item.xml
 */

public class ListViewAdapter extends ArrayAdapter<Films> {

    public ListViewAdapter(Context context, ArrayList<Films> filmsArrayList) {
        super(context, 0, filmsArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Films currentFilm = getItem(position);
        // Find the TextView in the list_item.xml with the ID and set film's title from the list
        TextView filmTextView = listItemView.findViewById(R.id.film_title_tv_list_view);
        filmTextView.setText(currentFilm.getFilmTitle());

        // Find the TextView in the list_item.xml with the ID and set film's duration from the list
        TextView durationTextView = listItemView.findViewById(R.id.film_duration_tv_list_view);
        durationTextView.setText(currentFilm.getReleaseDate());

        // Get the image resource ID and set the cover image
        ImageView coverImageView = listItemView.findViewById(R.id.thumbnail_list_view);
        coverImageView.setImageResource(R.drawable.placeholder);//TO DO picasso
        //coverImageView.setImageResource(currentFilm.getPosterPath());

        return listItemView;
    }
    }
