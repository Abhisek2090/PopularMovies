package com.example.popularmovies.viewolder;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.adapter.TrailersAdapter;
import com.example.popularmovies.model.Trailer;


public class TrailerListViewHolder extends RecyclerView.ViewHolder {

    private TextView trailerTextView;

    public TrailerListViewHolder(@NonNull View itemView, final TrailersAdapter.Callback callback) {
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
        trailerTextView = itemView.findViewById(R.id.trailerTV);
    }

    public void bindData(final Trailer trailer) {
       String trailer_name = trailer.getName();
       trailerTextView.setText(trailer_name);

    }
}
