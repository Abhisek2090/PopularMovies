package activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView, releaseDateTextView, voteTextView, synopsisTextView;
    private ImageView posterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }


        Bundle bundle = getIntent().getBundleExtra(Constants.MOVIE_DETAILS);
        Movie movie = null;
        if (bundle != null) {
            movie = (Movie) bundle.getSerializable(Constants.EXTRAS);
        }
        initViews();
        populateNewsDetails(movie);
    }

    private void populateNewsDetails(Movie movie) {

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
        titleTextView = findViewById(R.id.titleTextView);
        releaseDateTextView = findViewById(R.id.releaseDateTextView);
        voteTextView = findViewById(R.id.voteAverageTextView);
        synopsisTextView = findViewById(R.id.synopsisTextView);
    }
}