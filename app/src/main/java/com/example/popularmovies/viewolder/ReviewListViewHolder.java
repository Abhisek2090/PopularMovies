package com.example.popularmovies.viewolder;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.adapter.ReviewsAdapter;
import com.example.popularmovies.model.Review;


public class ReviewListViewHolder extends RecyclerView.ViewHolder {

    private TextView reviewTextView;

    public ReviewListViewHolder(@NonNull View itemView, final ReviewsAdapter.Callback callback) {
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
        reviewTextView = itemView.findViewById(R.id.reviewTV);
    }

    public void bindData(final Review review) {
       String reviewContent = review.getContent();
       reviewTextView.setText(reviewContent);

    }
}
