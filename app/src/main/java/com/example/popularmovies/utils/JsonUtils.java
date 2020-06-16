package com.example.popularmovies.utils;

import android.app.Presentation;

import com.example.popularmovies.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String NO_DATA_AVAILABLE = "No Data Available";
    public static final String POSTER_PATH = "poster_path";
    public static final String RELEASE_DATE = "release_date";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE = "vote_average";

    private static List<Movie> movies = new ArrayList<>();

    public static List<Movie> parseMovieJson(String json) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");

        for(int i=0;i<results.length();i++) {
            JSONObject movie = results.getJSONObject(i);
            String poster_path = movie.optString(POSTER_PATH);
            String release_date = movie.optString(RELEASE_DATE, NO_DATA_AVAILABLE);
            String original_title = movie.optString(ORIGINAL_TITLE, NO_DATA_AVAILABLE);
            String overview = movie.optString(OVERVIEW, NO_DATA_AVAILABLE);
            Integer vote_average = movie.optInt(VOTE_AVERAGE, 0);


            movies.add(new Movie(poster_path, original_title, overview, vote_average, release_date));

        }

        return movies;

    }
}
