package popularmoviesstage1.legalimpurity.com.popularmoviesstage1;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.NetworkUtils;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.objects.MovieObject;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_OBJECT_KEY = "0a46c76c98b80b4ed6befbe3760b28b1";

    private MovieObject mo;
    private ImageView movie_poster;
    private CollapsingToolbarLayout toolbar_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent() != null && getIntent().getExtras() != null) {
            mo = (MovieObject) getIntent().getExtras().getParcelable(MOVIE_OBJECT_KEY);
        }
        setView(this);
    }

    private void setView(Activity act)
    {
        movie_poster = (ImageView)act.findViewById(R.id.movie_poster);
        Picasso.with(act).load(NetworkUtils.MOVIES_IMAGE_URL+mo.getMoviePosterImageThumbnailUrl()).into(movie_poster);

        toolbar_layout = (CollapsingToolbarLayout)act.findViewById(R.id.toolbar_layout);
        toolbar_layout.setTitle(mo.getOrignalTitle());
    }

}
