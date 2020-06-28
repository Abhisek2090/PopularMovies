package com.example.popularmovies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.popularmovies.R;
import com.example.popularmovies.adapter.MoviesAdapter;
import com.example.popularmovies.database.Movie;
import com.example.popularmovies.utils.Constants;
import com.example.popularmovies.utils.JsonUtils;
import com.example.popularmovies.viewmodel.MainViewModel;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 5000;
    public static final int CONNECTION_TIMEOUT = 5000;
    private RecyclerView moviesRecyclerView;
    private MoviesAdapter moviesAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);



        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);
        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesAdapter = new MoviesAdapter();
        moviesRecyclerView.setAdapter(moviesAdapter);

        moviesAdapter.setCallback(new MoviesAdapter.Callback() {
            @Override
            public void onListItemClick(int position) {
                startDetailActivity(moviesAdapter.getItemByPosition(position));
            }
        });

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{getString(R.string.POPULAR), getString(R.string.HIGHEST_RATED),getString(R.string.FAVOURITES)};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        if(selected.equalsIgnoreCase(getString(R.string.HIGHEST_RATED))) {
           selected = "top_rated";
        }
        else if(selected.equalsIgnoreCase(getString(R.string.POPULAR))) {
            selected = "popular";
        }
        else {
            retrieveTasks();
            return;
        }
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(selected)
                .appendQueryParameter("api_key", Constants.TMDB_API_KEY);

        String URL = builder.toString();
        new MyAsyncTask().execute(URL);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class MyAsyncTask extends AsyncTask<String, Void, String> {

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

                    List<Movie> movies = JsonUtils.parseMovieJson(result);
                    moviesAdapter.setMovies(movies);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void startDetailActivity(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.EXTRAS, movie);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.MOVIE_DETAILS, bundle);
        startActivity(intent);
    }

    private void retrieveTasks() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "updating list from viewmodel");
                moviesAdapter.setMovies(movies);
            }
        });
    }
}