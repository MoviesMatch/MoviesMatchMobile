package com.example.moviesmatch;

public class Genre {
    boolean checked;
    String ItemString;

    public Genre(String t, boolean b){
        ItemString = t;
        checked = b;
    }

    public boolean isChecked(){
        return checked;
    }
}
