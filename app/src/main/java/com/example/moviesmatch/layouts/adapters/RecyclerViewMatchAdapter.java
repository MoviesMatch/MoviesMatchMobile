package com.example.moviesmatch.layouts.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmatch.R;
import com.example.moviesmatch.interfaces.IImageRequestCallback;
import com.example.moviesmatch.interfaces.IOnClickMatchListener;
import com.example.moviesmatch.layouts.fragments.MovieInfosFragment;
import com.example.moviesmatch.models.Movie;
import com.example.moviesmatch.requests.ImageRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewMatchAdapter extends RecyclerView.Adapter<RecyclerViewMatchAdapter.ViewHolder>{

    private ArrayList<Movie> mMovies = new ArrayList<>();
    private Context mContext;
    private IOnClickMatchListener iOnClickMatchListener;

    public RecyclerViewMatchAdapter(ArrayList<Movie> urls, Context context, IOnClickMatchListener iOnClickMatchListener) {
        this.mMovies = urls;
        this.mContext = context;
        this.iOnClickMatchListener = iOnClickMatchListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie_match, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(mMovies.get(position).getPosterURL()).into(holder.poster);
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(position);

            }
        });
    }

    private void loadImage(int position) {
        new ImageRequest(mMovies.get(position).getImagePoster(), new IImageRequestCallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                mMovies.get(position).setImagePoster(bitmap);
                iOnClickMatchListener.matchFragmentFromListMatch(mMovies.get(position));
            }
        }).execute(mMovies.get(position).getPosterURL());
    }

    @Override
    public int getItemCount() {
        return mMovies.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView poster ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.imageViewMiniPoster);


        }
    }
}
