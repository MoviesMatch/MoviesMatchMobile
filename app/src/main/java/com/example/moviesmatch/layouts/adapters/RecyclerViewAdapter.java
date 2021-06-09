package com.example.moviesmatch.layouts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmatch.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mGenres = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> genres, Context context) {
        this.mGenres = genres;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_genre_movie_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.genre.setText(mGenres.get(position));
    }

    @Override
    public int getItemCount() {
        return mGenres.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView genre ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            genre = itemView.findViewById(R.id.textViewNameGenre);

        }
    }
}
