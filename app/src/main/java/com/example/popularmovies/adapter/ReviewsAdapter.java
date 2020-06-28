package com.example.popularmovies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.viewolder.ReviewListViewHolder;

import java.util.List;


public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    // --Commented out by Inspection (16/6/20 5:35 PM):private static final String TAG = MoviesAdapter.class.getSimpleName();
    private final List<Review> data;
    private Callback callback;


    public ReviewsAdapter(List<Review> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_review, parent, false);
        return new ReviewListViewHolder(view, callback);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((ReviewListViewHolder)viewHolder).bindData(getItemByPosition(position));

    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Review getItemByPosition(int position) {
     return data.get(position);
    }

    @Override
    public void onClick(View v) {

    }

    public interface Callback {
        void onListItemClick(int position);
    }


}
