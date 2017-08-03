package popularmoviesstage1.legalimpurity.com.popularmoviesstage1.tasks.callbacks;

import java.util.ArrayList;

import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.objects.MovieObject;

/**
 * Created by rajatkhanna on 03/08/17.
 */

public interface MovieApiRespondedBack {
    public void onApiResponded(ArrayList<MovieObject> response);
}
