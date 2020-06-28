package com.example.popularmovies.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.R;
import com.example.popularmovies.adapter.ReviewsAdapter;
import com.example.popularmovies.adapter.TrailersAdapter;
import com.example.popularmovies.database.Movie;
import com.example.popularmovies.database.MovieDatabase;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;
import com.example.popularmovies.utils.AppExecutors;
import com.example.popularmovies.utils.Constants;
import com.example.popularmovies.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 5000;
    public static final int CONNECTION_TIMEOUT = 5000;

    private TextView titleTextView, releaseDateTextView, voteTextView, synopsisTextView;
    private ImageView posterImageView;
    private Button favButton;
    private TrailersAdapter trailersAdapter;
    private RecyclerView trailersRecyclerView;
    private ReviewsAdapter reviewsAdapter;
    private RecyclerView reviewRecyclerView;
    private MovieDatabase movieDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.movie_details);
        }

        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this);

        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this);



        trailersRecyclerView = findViewById(R.id.trailersRecyclerView);
        trailersRecyclerView.setLayoutManager(trailerLayoutManager);

        reviewRecyclerView = findViewById(R.id.reviewsRecyclerView);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);


        Bundle bundle = getIntent().getBundleExtra(Constants.MOVIE_DETAILS);
        Movie movie = null;
        if (bundle != null) {
            movie = (Movie) bundle.getSerializable(Constants.EXTRAS);
        }
        initViews();
        populateMovieDetails(movie);

        movieDatabase = MovieDatabase.getInstance(getApplicationContext());


    }

    private void populateMovieDetails(final Movie movie) {

        if(movie != null) {
            titleTextView.setText(movie.getOriginal_title());
            releaseDateTextView.setText(movie.getRelease_date());
            voteTextView.setText(String.valueOf(movie.getVote_average()));
            synopsisTextView.setText(movie.getOverview());

            String thumbnail_path = movie.getImage();
            String image_url = Constants.BACKDROP_URL + thumbnail_path;

            Picasso.get().
                    load(image_url)
                    .into(posterImageView);

            getTrailers(movie.getId());
            getReviews(movie.getId());

            favButton = findViewById(R.id.addToFavButton);
            if(movie.isFav()) {
                favButton.setText(R.string.favourite);
                favButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }

            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!movie.isFav()) {

                        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                movie.setFav(true);
                                movieDatabase.movieDao().insertMovie(movie);
                            }
                        });

                        favButton.setText(R.string.favourite);
                        favButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    else {
                        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                movie.setFav(false);
                                movieDatabase.movieDao().removeFromFav(movie);
                            }
                        });

                        favButton.setText(R.string.add_to_favourites);
                        favButton.setBackgroundColor(0);

                    }

                }
            });
        }

        else {
            finish();
            Toast.makeText(this, R.string.ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews() {
        posterImageView = findViewById(R.id.posterImageView);
        titleTextView = findViewById(R.id.movieNameTV);
        releaseDateTextView = findViewById(R.id.releaseDateTextView);
        voteTextView = findViewById(R.id.voteAverageTextView);
        synopsisTextView = findViewById(R.id.synopsisTextView);

    }

    class GetTrailersAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                if(result != null) {

                    List<Trailer> trailers = JsonUtils.getMovieTrailers(result);
                    trailersAdapter = new TrailersAdapter(trailers);
                    trailersRecyclerView.setAdapter(trailersAdapter);
                    trailersAdapter.notifyDataSetChanged();

                    trailersAdapter.setCallback(new TrailersAdapter.Callback() {
                        @Override
                        public void onListItemClick(int position) {
                           String trailer_url_key =  trailersAdapter.getItemByPosition(position).getKey();
                           String video_link = "http://www.youtube.com/watch?v="+trailer_url_key;
                           playTrailer(video_link);
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    
    void playTrailer(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
        startActivity(intent);
    }

    void getTrailers(Integer movieId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendPath("videos")
                .appendQueryParameter("api_key", Constants.TMDB_API_KEY);

        String URL = builder.toString();
        new GetTrailersAsyncTask().execute(URL);
    }

    void getReviews(Integer movieId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendPath("reviews")
                .appendQueryParameter("api_key", Constants.TMDB_API_KEY);

        String URL = builder.toString();
        new GetReviewsAsyncTask().execute(URL);
    }

    class GetReviewsAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                if(result != null) {

                    List<Review> reviews = JsonUtils.getMovieReviews(result);
                    reviewsAdapter = new ReviewsAdapter(reviews);
                    reviewRecyclerView.setAdapter(reviewsAdapter);
                    reviewsAdapter.notifyDataSetChanged();


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}