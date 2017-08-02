package popularmoviesstage1.legalimpurity.com.popularmoviesstage1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;

import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.MoviesJsonUtils;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.NetworkUtils;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.adapters.MovieListAdapter;
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
        loadMoviesData(this,"popularity.desc");
    }

    private void findViews(Activity act)
    {
        movie_list_recycler_view = (RecyclerView) act.findViewById(R.id.movie_list_recycler_view);
    }

    private void setAdapter(Activity act)
    {
        RecyclerView.LayoutManager moviesLayoutManager = new GridLayoutManager(act,2);
        movie_list_recycler_view.setLayoutManager(moviesLayoutManager);

        movie_list_recycler_view.setHasFixedSize(true);

//        movies_list = dummyData();
        movies_list = new ArrayList<MovieObject>();
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
}