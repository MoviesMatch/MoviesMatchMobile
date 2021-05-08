package com.example.moviesmatch.layouts.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.moviesmatch.models.Genre;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

public class GenreCheckboxAdapter {
    private Context context;
    private int nbrCheckedGenres;
    private JSONArray selectedGenres;
    private ArrayList<Genre> listGenres;
    private final int MAX_AMOUNT_OF_GENRES = 5;

    public GenreCheckboxAdapter(Context context, ArrayList<Genre> listGenres){
        this.context = context;
        nbrCheckedGenres = 0;
        this.listGenres = listGenres;
    }

    public boolean isFiveChecked() {
        int numberChecked = 0;
        for (int i = 0; i < listGenres.size(); i++) {
            if (listGenres.get(i).isChecked()) {
                numberChecked++;
            }

            if (numberChecked == 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create a checkbox for each genre in the listview and adds it to the linearlayout
     * @param linearLayout
     */
    public void setCheckboxesGenres(LinearLayout linearLayout) {
        Collections.sort(listGenres);
        for (Genre genre : listGenres) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setTag(genre);
            checkBox.setPadding(0, 40, 0, 40);
            checkBox.setText(genre.getItemString());
            checkBox.setChecked(genre.isChecked());
            linearLayout.addView(checkBox);
            checkBoxOnClick(checkBox);
        }
    }

    /**
     * The conditions to not select more than 5 genres
     * @param checkBox
     */
    private void checkBoxOnClick(CheckBox checkBox) {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nbrCheckedGenres = 0;
                for (Genre genre : listGenres) {
                    if (genre.isChecked()) {
                        nbrCheckedGenres++;
                    }
                }
                if (nbrCheckedGenres < MAX_AMOUNT_OF_GENRES) {
                    ((Genre) checkBox.getTag()).setChecked(true);
                } else {
                    ((Genre) checkBox.getTag()).setChecked(false);
                    checkBox.setChecked(false);
                }

                if (!checkBox.isChecked()){
                    ((Genre) checkBox.getTag()).setChecked(false);
                }
            }
        });
    }

    public JSONArray getSelectedGenres(){
        selectedGenres = new JSONArray();
        for (Genre g : listGenres){
            if (g.isChecked()){
                selectedGenres.put(g.getId());
            }
        }
        return selectedGenres;
    }
}
