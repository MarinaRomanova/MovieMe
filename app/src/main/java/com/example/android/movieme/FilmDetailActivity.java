package com.example.android.movieme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FilmDetailActivity extends FragmentActivity {
    private static final String TAG = FilmDetailActivity.class.getSimpleName();

    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String overview = intent.getStringExtra("overview");
        imageUrl = intent.getStringExtra("image");
        double rate = intent.getExtras().getDouble("rate");
        String date = intent.getStringExtra("date");
        int position = intent.getExtras().getInt("position");

        TextView titleTextView = (TextView) findViewById(R.id.title_detail);
        titleTextView.setText(title);

        TextView overviewTextView = (TextView) findViewById(R.id.overview_detail);
        overviewTextView.setText(overview);

        TextView dateTextView = (TextView) findViewById(R.id.release_date_detail);
        dateTextView.setText(date);

        TextView rateTextView = (TextView) findViewById(R.id.rate_detail);
        rateTextView.setText(rate+"/10");

        ImageView coverImage = (ImageView) findViewById(R.id.image_detail);
        Picasso.with(this).load(imageUrl).into(coverImage);
    }

}
