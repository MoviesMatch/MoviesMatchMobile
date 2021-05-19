package com.example.moviesmatch.requests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.moviesmatch.interfaces.IImageRequestCallback;

import java.io.InputStream;

public class ImageRequest extends AsyncTask<String, Void, Bitmap> {
    Bitmap bitmap;
    IImageRequestCallback callback;
    static int index = 0;
    public ImageRequest(Bitmap bitmap, IImageRequestCallback callback) {
        this.bitmap = bitmap;
        this.callback = callback;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bitmap = result;
        System.out.println("DONE " + index);
        callback.onSuccess(result, index);
        index++;
    }
}
