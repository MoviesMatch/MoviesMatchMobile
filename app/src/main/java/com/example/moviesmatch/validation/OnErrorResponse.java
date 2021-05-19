package com.example.moviesmatch.validation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IPutActivity;

import java.util.List;

public class OnErrorResponse {

    public void onGetErrorResponse(int errorCode, AppCompatActivity activity){
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        for (Fragment f : fragments) {
            if (f != null && f instanceof IGetActivity)
                ((IGetActivity) f).onGetErrorResponse(errorCode);
        }
    }

    public void onPostErrorResponse(int errorCode, AppCompatActivity activity){
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        for (Fragment f : fragments) {
            if (f != null && f instanceof IPostActivity)
                ((IPostActivity) f).onPostErrorResponse(errorCode);
        }
    }

    public void onPutErrorResponse(int errorCode, AppCompatActivity activity){
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        for (Fragment f : fragments) {
            if (f != null && f instanceof IPutActivity)
                ((IPutActivity) f).onPutErrorResponse(errorCode);
        }
    }
}
