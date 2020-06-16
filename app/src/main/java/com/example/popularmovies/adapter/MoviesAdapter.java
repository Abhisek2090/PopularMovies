package com.example.popularmovies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.viewolder.MoviesListViewHolder;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private List<Movie> data;


    public MoviesAdapter(List<Movie> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_movie, parent, false);
        return new MoviesListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((MoviesListViewHolder)viewHolder).bindData(getItemByPosition(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Movie getItemByPosition(int position) {
     return data.get(position);
    }

    @Override
    public void onClick(View v) {

    }


}
