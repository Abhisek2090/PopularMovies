package com.example.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> loadAllMovies();

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Movie> getMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMovies(List<Movie> movies);

    @Delete
    void removeFromFav(Movie movie);


}
