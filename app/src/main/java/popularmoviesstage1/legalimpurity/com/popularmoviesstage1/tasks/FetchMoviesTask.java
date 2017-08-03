package popularmoviesstage1.legalimpurity.com.popularmoviesstage1.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.MoviesJsonUtils;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils.NetworkUtils;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.objects.MovieObject;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.tasks.callbacks.MovieApiRespondedBack;

/**
 * Created by rajatkhanna on 03/08/17.
 */

public class FetchMoviesTask extends AsyncTask<Object, Void, ArrayList<MovieObject>> {

    private Activity act;
    private ArrayList<MovieObject> output;
    private MovieApiRespondedBack responder;

    @Override
    protected ArrayList<MovieObject> doInBackground(Object... params) {

        if (params.length == 0) {
            return null;
        }

        act = (Activity) params[0];
        String sortByParam =(String) params[1];
        responder = (MovieApiRespondedBack) params[2];

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
        responder.onApiResponded(moviesData);
    }
}
