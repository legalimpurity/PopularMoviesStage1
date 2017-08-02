package popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String MOVIE_DATABASE_ROOT_URL = "https://api.themoviedb.org/3/";
    public static final String MOVIES_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

    private static final String POPULAR_MOVIES_URL = MOVIE_DATABASE_ROOT_URL + "discover/movie";

    // Get your own from www.themoviedb.org
//    final static String API_KEY_VALUE = "ENTER_YOUR_API_KEY_HERE";
    private final static String API_KEY_VALUE = "";


    private final static String API_KEY = "api_key";
    private final static String SORT_BY = "sort_by";

    public static URL buildSortByUrl(String sortBy) {
        Uri builtUri = Uri.parse(POPULAR_MOVIES_URL).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .appendQueryParameter(SORT_BY, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}