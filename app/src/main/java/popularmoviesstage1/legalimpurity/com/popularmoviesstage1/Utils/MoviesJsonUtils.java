package popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.objects.MovieObject;


public class MoviesJsonUtils {

    public static ArrayList<MovieObject> getMovieObjectsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        final String MOVIE_LIST = "results";

        final String TITLE_ATTRIBUTE = "title";
        final String POSTER_URL = "poster_path";
        final String PLOT_SYNOPSIS = "overview";
        final String USER_RATING = "vote_average";
        final String RELEASE_DATE = "release_date";

        ArrayList<MovieObject> parsedMoviesData;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        JSONArray moviesArray = movieJson.getJSONArray(MOVIE_LIST);

        parsedMoviesData = new ArrayList<>();

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieJSONObj = moviesArray.getJSONObject(i);
            parsedMoviesData.add(new MovieObject(movieJSONObj.getString(TITLE_ATTRIBUTE),
                    movieJSONObj.getString(POSTER_URL),
                    movieJSONObj.getString(PLOT_SYNOPSIS),
                    movieJSONObj.getString(USER_RATING),
                    movieJSONObj.getString(RELEASE_DATE)
                    ));
        }

        return parsedMoviesData;
    }

}
