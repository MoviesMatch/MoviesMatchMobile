package com.example.moviesmatch.validation;

import android.view.View;
import android.widget.Button;

import pl.droidsonroids.gif.GifImageView;

public class Loading {
    public void loadingVisible(GifImageView gifImageView, Button... buttons){
        gifImageView.setVisibility(View.VISIBLE);
        for (Button button : buttons){
            button.setEnabled(false);
        }
    }

    public void loadingGone(GifImageView gifImageView, Button... buttons){
        gifImageView.setVisibility(View.GONE);
        for (Button button : buttons){
            button.setEnabled(true);
        }
    }
}
