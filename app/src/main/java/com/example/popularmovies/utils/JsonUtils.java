package com.example.popularmovies.utils;

import com.example.popularmovies.database.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;

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
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String KEY = "key";
    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";

    private static final List<Movie> movies = new ArrayList<>();
    private static final List<Trailer> trailers = new ArrayList<>();
    private static final List<Review> reviews = new ArrayList<>();

    public static List<Movie> parseMovieJson(String json) throws JSONException {
        movies.clear();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");

        for(int i=0;i<results.length();i++) {
            JSONObject movie = results.getJSONObject(i);
            String poster_path = movie.optString(POSTER_PATH);
            Integer id = movie.getInt(ID);
            String release_date = movie.optString(RELEASE_DATE, NO_DATA_AVAILABLE);
            String original_title = movie.optString(ORIGINAL_TITLE, NO_DATA_AVAILABLE);
            String overview = movie.optString(OVERVIEW, NO_DATA_AVAILABLE);
            Integer vote_average = movie.optInt(VOTE_AVERAGE, 0);


            movies.add(new Movie(id, poster_path, original_title, overview, vote_average, release_date));

        }

        return movies;

    }

    public static List<Trailer> getMovieTrailers(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");
        for(int i=0;i<results.length();i++) {
                JSONObject trailer = results.getJSONObject(i);
                String name = trailer.optString(NAME);
                String key = trailer.getString(KEY);

                trailers.add(new Trailer(key, name));
        }

            return trailers;
    }

    public static List<Review> getMovieReviews(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");
        for(int i=0;i<results.length();i++) {
            JSONObject trailer = results.getJSONObject(i);
            String author = trailer.optString(AUTHOR);
            String content = trailer.getString(CONTENT);

            reviews.add(new Review(author, content));
        }

        return reviews;
    }
}
