package com.example.android.movieme;

import android.graphics.Bitmap;

/**
 * Creates a single object Film containing as attributes
 * title, duration and cover image. Corresponding xml files
 * grid_item.xml and list_item.xml, which are then displayed in
 * GridFragment and ListFragment as a Grid or as a List of Films
 * with the help of ListViewAdapter and GridViewAdapter
 */

public class Films {
    private String _filmTitle;
    private Bitmap _posterPath;
    private String _releaseDate;
    private String _overview;
    private double _vote;
    private String _image;

    public Films(String filmTitle, String releaseDate, Bitmap posterPath, String overview, double vote) {
        _filmTitle = filmTitle;
        _releaseDate = releaseDate;
        _posterPath = posterPath;
        _overview = overview;
        _vote = vote;
    }

    public String getFilmTitle() { return  _filmTitle;}
    public String getReleaseDate() { return _releaseDate;}
    public Bitmap getPosterPath() { return _posterPath;}
    public String getOverview() { return _overview;}
    public double getVote() {return _vote;}

    public void setImage(String image){
        _image = image;
    }

    public String getImage(){
        return _image;
    }


}
