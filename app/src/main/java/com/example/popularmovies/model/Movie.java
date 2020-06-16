package com.example.popularmovies.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private String image;
    private String original_title;
    private String overview;
    private Integer vote_average;
    private String release_date;

    public Movie(String image, String original_title, String overview, Integer vote_average, String release_date) {
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

}
