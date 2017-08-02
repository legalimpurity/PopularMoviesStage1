package popularmoviesstage1.legalimpurity.com.popularmoviesstage1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;

import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.NetworkUtils;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.adapters.MovieListAdapter;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.objects.MovieObject;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movie_list_recycler_view;
    private MovieListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView(this);
        loadMoviesData("popularity.desc");
    }

    private void setView(Activity act)
    {
        movie_list_recycler_view = (RecyclerView) act.findViewById(R.id.movie_list_recycler_view);

        RecyclerView.LayoutManager moviesLayoutManager = new GridLayoutManager(act,2);
        movie_list_recycler_view.setLayoutManager(moviesLayoutManager);

        movie_list_recycler_view.setHasFixedSize(true);

        ArrayList<MovieObject> movies_list = dummyData();
        mAdapter = new MovieListAdapter(act,movies_list);

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

    private void loadMoviesData(String sort_by) {
        new FetchMoviesTask().execute(sort_by);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String sortByParam = params[0];
            URL sortByRequestUrl = NetworkUtils.buildSortByUrl(sortByParam);

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(sortByRequestUrl);
                Log.d("as",jsonMoviesResponse);

                return null;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
        }
    }
}