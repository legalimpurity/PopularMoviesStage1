package popularmoviesstage1.legalimpurity.com.popularmoviesstage1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.net.URL;
import java.util.ArrayList;

import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.MoviesJsonUtils;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.NetworkUtils;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.adapters.MovieListAdapter;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.listeners.MovieClickListener;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.objects.MovieObject;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movie_list_recycler_view;
    private MovieListAdapter mAdapter;
    private ArrayList<MovieObject> movies_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews(this);
        setAdapter(this);
        loadMoviesData(this,"popular");
    }

    private void findViews(Activity act)
    {
        movie_list_recycler_view = (RecyclerView) act.findViewById(R.id.movie_list_recycler_view);
    }

    private void setAdapter(final Activity act)
    {
        RecyclerView.LayoutManager moviesLayoutManager = new GridLayoutManager(act,2);
        movie_list_recycler_view.setLayoutManager(moviesLayoutManager);

        movie_list_recycler_view.setHasFixedSize(true);

//        movies_list = dummyData();
        movies_list = new ArrayList<MovieObject>();
        mAdapter = new MovieListAdapter(act,movies_list, new MovieClickListener() {
            @Override
            public void onMovieCLick(MovieObject movieItem) {
                Intent movieDetailActivityClickIntent = new Intent(act,MovieDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putParcelable(MovieDetailActivity.MOVIE_OBJECT_KEY,movieItem);
                movieDetailActivityClickIntent.putExtras(extras);
                act.startActivity(movieDetailActivityClickIntent);
            }
        });

        movie_list_recycler_view.setAdapter(mAdapter);

    }

    private ArrayList<MovieObject> dummyData()
    {
        ArrayList<MovieObject> dummyData = new ArrayList<MovieObject>();
        dummyData.add(new MovieObject("Title","http://i.imgur.com/DvpvklR.png","plot synopsis","userRating",0l));
        dummyData.add(new MovieObject("Title","http://i.imgur.com/DvpvklR.png","plot synopsis","userRating",0l));
        dummyData.add(new MovieObject("Title","http://i.imgur.com/DvpvklR.png","plot synopsis","userRating",0l));
        dummyData.add(new MovieObject("Title","http://i.imgur.com/DvpvklR.png","plot synopsis","userRating",0l));
        dummyData.add(new MovieObject("Title","http://i.imgur.com/DvpvklR.png","plot synopsis","userRating",0l));
        return dummyData;
    }

    private void loadMoviesData(Activity act, String sort_by) {
        new FetchMoviesTask().execute(act,sort_by);
    }

    private class FetchMoviesTask extends AsyncTask<Object, Void, ArrayList<MovieObject>> {

        private Activity act;
        private ArrayList<MovieObject> output;
        @Override
        protected ArrayList<MovieObject> doInBackground(Object... params) {

            if (params.length == 0) {
                return null;
            }

            String sortByParam =(String) params[1];
            act = (Activity) params[0];
            URL sortByRequestUrl = NetworkUtils.buildSortByUrl(sortByParam);

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(sortByRequestUrl);
                output = MoviesJsonUtils.getMovieObjectsFromJson(act,jsonMoviesResponse);
                return output;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieObject> moviesData) {
            mAdapter.setMoviesData(moviesData);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.change_sort_order) {
            CharSequence colors[] = new CharSequence[] {getString(R.string.most_popular), getString(R.string.highest_rated)};

            final Activity act = this;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.pick_an_order);
            builder.setItems(colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==0)
                    {
                        loadMoviesData(act,"popular");
                    }
                    else
                    {
                        loadMoviesData(act,"top_rated");
                    }
                }
            });
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}