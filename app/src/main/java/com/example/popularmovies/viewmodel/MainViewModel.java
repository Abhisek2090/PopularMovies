package com.example.popularmovies.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovies.database.Movie;
import com.example.popularmovies.database.MovieDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;
    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase database = MovieDatabase.getInstance(getApplication());
        Log.d(TAG, "Actively retrieveing task from Database");
        movies = database.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
