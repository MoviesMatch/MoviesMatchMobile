package com.example.moviesmatch.validation;

import android.view.View;
import android.widget.Button;

import pl.droidsonroids.gif.GifImageView;

public class Loading {
    public void loadingVisible(GifImageView gifImageView, View... views){
        gifImageView.setVisibility(View.VISIBLE);
        for (View view : views){
            view.setEnabled(false);
        }
    }

    public void loadingGone(GifImageView gifImageView, View... views){
        gifImageView.setVisibility(View.GONE);
        for (View view : views){
            view.setEnabled(true);
        }
    }
}
