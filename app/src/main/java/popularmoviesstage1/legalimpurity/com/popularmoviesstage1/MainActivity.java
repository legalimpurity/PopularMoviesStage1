package popularmoviesstage1.legalimpurity.com.popularmoviesstage1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.NetworkStateReceiver;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.NetworkUtils;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.adapters.MovieListAdapter;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.listeners.MovieClickListener;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.objects.MovieObject;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.tasks.FetchMoviesTask;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.tasks.callbacks.MovieApiRespondedBack;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.movie_list_recycler_view) RecyclerView movie_list_recycler_view;
    @BindView(R.id.no_internet_text_view) TextView no_internet_text_view;


    private MovieListAdapter mAdapter;
    private ArrayList<MovieObject> movies_list;

    private String selectedApi = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        findViews(this);
        setAdapter(this);
        if(NetworkUtils.isNetworkAvailable(this)) {
            selectedApi = "popular";
            // Will be called from networkAvailable Function
            //  loadMoviesData(this, selectedApi);
        }
        else
        {
            movie_list_recycler_view.setVisibility(View.GONE);
            no_internet_text_view.setVisibility(View.VISIBLE);
        }
        addNetworkStateReceiver(this);
    }

    private void addNetworkStateReceiver(final Activity act)
    {
        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(new NetworkStateReceiver.NetworkStateReceiverListener() {
            @Override
            public void networkAvailable() {
                movie_list_recycler_view.setVisibility(View.VISIBLE);
                no_internet_text_view.setText(R.string.no_internet);
                no_internet_text_view.setVisibility(View.GONE);
                loadMoviesData(act,selectedApi);
            }

            @Override
            public void networkUnavailable() {
//                movie_list_recycler_view.setVisibility(View.GONE);
//                no_internet_text_view.setText(R.string.no_internet);
//                no_internet_text_view.setVisibility(View.VISIBLE);
            }
        });
        act.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void findViews(Activity act)
    {
        movie_list_recycler_view = (RecyclerView) act.findViewById(R.id.movie_list_recycler_view);
        no_internet_text_view = (TextView) act.findViewById(R.id.no_internet_text_view);
    }

    private void setAdapter(final Activity act)
    {
        int numberOfColumns = 2;
        if(act.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            numberOfColumns = 2;
        }
        else{
            numberOfColumns = 4;
        }
        RecyclerView.LayoutManager moviesLayoutManager = new GridLayoutManager(act,numberOfColumns);
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
        dummyData.add(new MovieObject("Title","http://i.imgur.com/DvpvklR.png","plot synopsis","userRating",""));
        dummyData.add(new MovieObject("Title","http://i.imgur.com/DvpvklR.png","plot synopsis","userRating",""));
        dummyData.add(new MovieObject("Title","http://i.imgur.com/DvpvklR.png","plot synopsis","userRating",""));
        dummyData.add(new MovieObject("Title","http://i.imgur.com/DvpvklR.png","plot synopsis","userRating",""));
        dummyData.add(new MovieObject("Title","http://i.imgur.com/DvpvklR.png","plot synopsis","userRating",""));
        return dummyData;
    }

    private void loadMoviesData(Activity act, String sort_by) {

        MovieApiRespondedBack responder = new MovieApiRespondedBack() {
            @Override
            public void onApiResponded(ArrayList<MovieObject> response) {
                if(response == null)
                {
                    no_internet_text_view.setText(R.string.api_error);
                    no_internet_text_view.setVisibility(View.VISIBLE);
                }
                else
                    mAdapter.setMoviesData(response);
            }
        };
        new FetchMoviesTask().execute(act,sort_by,responder);
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
                        selectedApi = "popular";
                        loadMoviesData(act,selectedApi);
                    }
                    else
                    {
                        selectedApi = "top_rated";
                        loadMoviesData(act,selectedApi);
                    }
                }
            });
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}