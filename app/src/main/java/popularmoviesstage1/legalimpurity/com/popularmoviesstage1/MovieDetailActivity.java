package popularmoviesstage1.legalimpurity.com.popularmoviesstage1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.NetworkUtils;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.objects.MovieObject;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_OBJECT_KEY = "0a46c76c98b80b4ed6befbe3760b28b1";

    private MovieObject mo;
    private ImageView movie_poster;
    private CollapsingToolbarLayout toolbar_layout;
    private TextView textViewReleaseDateValue, textViewVoteAverageValue, textViewPlotSynopsisValue;

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
        setFab(this);
    }

    private void setView(Activity act)
    {
        movie_poster = (ImageView)act.findViewById(R.id.movie_poster);
        toolbar_layout = (CollapsingToolbarLayout)act.findViewById(R.id.toolbar_layout);
        textViewReleaseDateValue = (TextView) act.findViewById(R.id.textViewReleaseDateValue);
        textViewVoteAverageValue = (TextView) act.findViewById(R.id.textViewVoteAverageValue);
        textViewPlotSynopsisValue = (TextView) act.findViewById(R.id.textViewPlotSynopsisValue);

        Picasso.with(act).load(NetworkUtils.MOVIES_IMAGE_URL+mo.getMoviePosterImageThumbnailUrl()).into(movie_poster);

        toolbar_layout.setTitle(mo.getOrignalTitle());

        textViewReleaseDateValue.setText(mo.getReleaseDate());
        textViewVoteAverageValue.setText(mo.getUserRating());
        textViewPlotSynopsisValue.setText(mo.getPlotSynopsis());
    }

    private void setFab(final Activity act)
    {
        FloatingActionButton fab = (FloatingActionButton) act.findViewById(R.id.fab);

        String sharer_content = "";

        sharer_content = sharer_content + act.getResources().getString(R.string.movie_title) + " : \n";
        sharer_content = sharer_content + mo.getOrignalTitle() + "\n\n";

        sharer_content = sharer_content + act.getResources().getString(R.string.release_date) + " : \n";
        sharer_content = sharer_content + mo.getReleaseDate() + "\n\n";

        sharer_content = sharer_content + act.getResources().getString(R.string.vote_average) + " : \n";
        sharer_content = sharer_content + mo.getUserRating() + "\n\n";

        sharer_content = sharer_content + act.getResources().getString(R.string.plot_synopsis) + " : \n";
        sharer_content = sharer_content + mo.getPlotSynopsis() + "\n\n";

        sharer_content = sharer_content + act.getResources().getString(R.string.shared_from_themoviedb);

        final String finalSharer_content = sharer_content;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, act.getResources().getString(R.string.shared_subject));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, finalSharer_content);
                act.startActivity(Intent.createChooser(intent, act.getResources().getString(R.string.app_name)));
            }
        });
    }

}
