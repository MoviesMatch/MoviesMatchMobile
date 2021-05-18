package com.example.moviesmatch.requests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

public class ImageRequest extends AsyncTask<String, Void, Drawable> {
    protected Drawable doInBackground(String... urls) {
        String url = urls[0];
        Drawable drawable = null;
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            drawable = Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }
}
