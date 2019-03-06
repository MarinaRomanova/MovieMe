package com.example.android.movieme.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;

public class ImageLoader extends android.support.v4.content.AsyncTaskLoader<Bitmap> {

    private String url;
    public ImageLoader(Context context, String url) {
        super(context);
        this.url=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Bitmap loadInBackground() {
        InputStream inputStream = null;
        try {
            inputStream = new java.net.URL(url).openStream();
        } catch (IOException e) {
            System.out.println(e);
        }
        return BitmapFactory.decodeStream(inputStream);
    }
}
