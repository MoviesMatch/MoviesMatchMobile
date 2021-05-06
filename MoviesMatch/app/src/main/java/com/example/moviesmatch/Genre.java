package com.example.moviesmatch;

import java.util.Comparator;

public class Genre implements Comparator<Genre>, Comparable<Genre> {
    int id;
    String ItemString;
    boolean checked;

    public Genre(int id, String ItemString, boolean checked) {
        this.id = id;
        this.ItemString = ItemString;
        this.checked = checked;
    }

    public int getId() {
        return this.id;
    }

    public String getItemString() {
        return this.ItemString;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int compare(Genre o1, Genre o2) {
        return o1.ItemString.compareTo(o2.ItemString);
    }

    @Override
    public int compareTo(Genre o) {
        return this.ItemString.compareTo(o.getItemString());
    }
}
