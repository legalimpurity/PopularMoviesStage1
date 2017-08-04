package popularmoviesstage1.legalimpurity.com.popularmoviesstage1.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.BuildConfig;

public final class NetworkUtils {

    private static final String MOVIE_DATABASE_ROOT_URL = "https://api.themoviedb.org/3/movie/";
    public static final String MOVIES_IMAGE_URL = "https://image.tmdb.org/t/p/w185/";

    // Get your own from www.themoviedb.org
    private final static String API_KEY_VALUE = BuildConfig.THE_MOVIE_DB_API_TOKEN;


    private final static String API_KEY = "api_key";

    public static URL buildSortByUrl(String appendingUrl) {
        Uri builtUri = Uri.parse(MOVIE_DATABASE_ROOT_URL+appendingUrl).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
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
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(10000);
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

    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}