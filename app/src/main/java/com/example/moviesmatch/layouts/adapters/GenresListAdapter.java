package com.example.moviesmatch.layouts.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.moviesmatch.models.Genre;
import com.example.moviesmatch.R;

import java.util.List;

public class GenresListAdapter extends BaseAdapter {


    private Context context;
    private List<Genre> list;
    private int numberChecked = 0;  // Le nombre d'item checker

    public GenresListAdapter(Context context, List<Genre> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isChecked(int position) {
        return list.get(position).isChecked();
    }

// Classe static qu'on appelle dans la methode getView
// cette classe nous permet d'acceder au checkBox et textView
    static class GenresListViewHolder {
        CheckBox checkBox;
        TextView text;
    }

//Methode de base du chaque custom adapter pour customiser la listView
// et mettre les conditons voulu dessus

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        GenresListViewHolder viewHolder = new GenresListViewHolder();

        if (rowView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_genre, null);

            viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.rowCheckBox);
            viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (GenresListViewHolder) rowView.getTag();
        }

        viewHolder.checkBox.setChecked(list.get(position).isChecked());

        final String itemStr = list.get(position).getItemString();
        viewHolder.text.setText(itemStr);
        viewHolder.checkBox.setTag(position);

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newState = !list.get(position).isChecked();
                list.get(position).checked = newState;

            }
        });

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton checkboxView, boolean isChecked) {
                if (isChecked) {
                    numberChecked++;
                } else if (!isChecked) {
                    numberChecked--;
                }
                if (numberChecked >= 6) {
                    checkboxView.setChecked(false);
                    numberChecked--;
                }
            }
        });

        viewHolder.checkBox.setChecked(isChecked(position));

        return rowView;
    }

}
