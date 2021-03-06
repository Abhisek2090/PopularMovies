package com.example.popularmovies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.database.Movie;
import com.example.popularmovies.viewolder.MoviesListViewHolder;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    // --Commented out by Inspection (16/6/20 5:35 PM):private static final String TAG = MoviesAdapter.class.getSimpleName();
    private List<Movie> movieList;
    private Callback callback;

    public MoviesAdapter(){}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_movie, parent, false);
        return new MoviesListViewHolder(view, callback);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((MoviesListViewHolder)viewHolder).bindData(getItemByPosition(position));

    }

    public void setMovies(List<Movie> movies) {
        movieList = movies;
        notifyDataSetChanged();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getItemCount() {
        return movieList!= null ? movieList.size():0;
    }

    public Movie getItemByPosition(int position) {
     return movieList.get(position);
    }

    @Override
    public void onClick(View v) {

    }

    public interface Callback {
        void onListItemClick(int position);
    }


}
