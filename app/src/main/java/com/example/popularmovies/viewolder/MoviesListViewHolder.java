package com.example.popularmovies.viewolder;


import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.adapter.MoviesAdapter;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;


public class MoviesListViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private Context context;

    public MoviesListViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        initViews(itemView);
    }

    private void initViews(View itemView) {

        imageView = (ImageView) itemView.findViewById(R.id.thumbnailIv);

    }

    public void bindData(final Movie movie) {
        String thumbnail_path = movie.getImage();
        String image_url = Constants.IMAGE_URL + thumbnail_path;

        Picasso.get().load(image_url).into(imageView);


    }
}
