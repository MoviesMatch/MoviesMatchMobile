package com.example.moviesmatch.interfaces;

import android.graphics.Bitmap;

public interface IImageRequestCallback {
    void onSuccess(Bitmap bitmap, int index);
}
