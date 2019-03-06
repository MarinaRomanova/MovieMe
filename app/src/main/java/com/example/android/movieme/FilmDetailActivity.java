package com.example.android.movieme;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movieme.Utils.ImageLoader;

public class FilmDetailActivity extends FragmentActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Bitmap> {
    private static final String TAG = FilmDetailActivity.class.getSimpleName();
    private static final int IMAGE_LOADER_ID = 1;

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

        //getSupportLoaderManager().initLoader(IMAGE_LOADER_ID, null, this);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(IMAGE_LOADER_ID, null, null);
        }

    }

    @Override
    public android.support.v4.content.AsyncTaskLoader<Bitmap> onCreateLoader(int id, @Nullable Bundle args) {

        return new ImageLoader(this, imageUrl);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bitmap> loader, Bitmap image) {
        ImageView coverImage = (ImageView) findViewById(R.id.image_detail);
        coverImage.setImageBitmap(image);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Bitmap> loader) {

    }
}
