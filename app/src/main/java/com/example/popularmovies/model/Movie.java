package com.example.popularmovies.model;

public class Movie {
    private String image;
    private String original_title;
    private String overview;
    private Integer vote_average;
    private String release_date;

    /**
     * No args constructor for use in serialization
     */
    public Movie() {

    }

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

    public void setImage(String image) {
        this.image = image;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getVote_average() {
        return vote_average;
    }

    public void setVote_average(Integer vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
