package com.example.popularmovies.viewolder;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.adapter.MoviesAdapter;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;


public class MoviesListViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;

    public MoviesListViewHolder(@NonNull View itemView, final MoviesAdapter.Callback callback) {
        super(itemView);
        Context context = itemView.getContext();
        initViews(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onListItemClick(getAdapterPosition());
                }
            }
        });
    }

    private void initViews(View itemView) {
        imageView = itemView.findViewById(R.id.thumbnailIv);

    }

    public void bindData(final Movie movie) {
        String thumbnail_path = movie.getImage();
        String image_url = Constants.IMAGE_URL + thumbnail_path;

        Picasso.get().
                load(image_url)
               .into(imageView);


    }
}
