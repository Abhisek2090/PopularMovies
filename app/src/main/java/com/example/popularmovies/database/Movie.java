package com.example.popularmovies.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movies")
public class Movie implements Serializable {
    private String image;
    private String original_title;
    private String overview;
    private Integer vote_average;
    private String release_date;
    private boolean fav;
    @PrimaryKey
    private int id;

    public Movie(int id, String image, String original_title, String overview, Integer vote_average, String release_date) {
        this.id = id;
        this.image = image;
        this.original_title = original_title;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public String getImage() {
        return image;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public Integer getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {return id;}

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }
}
