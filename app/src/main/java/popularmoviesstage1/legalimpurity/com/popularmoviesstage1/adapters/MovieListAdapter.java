package popularmoviesstage1.legalimpurity.com.popularmoviesstage1.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.R;
import popularmoviesstage1.legalimpurity.com.popularmoviesstage1.objects.MovieObject;

/**
 * Created by rajatkhanna on 01/08/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemHolder>{

    private ArrayList<MovieObject> movieObjs;
    private Activity act;

    public MovieListAdapter(Activity act, ArrayList<MovieObject> movieObjs)
    {
        this.movieObjs = movieObjs;
        this.act = act;
    }

    @Override
    public MovieItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(act).inflate(R.layout.movie_list_item, parent,false);
        MovieItemHolder movieItemHolderObject = new MovieItemHolder(layoutView);
        return movieItemHolderObject;
    }

    @Override
    public void onBindViewHolder(MovieItemHolder holder, int position) {
        MovieObject mo = movieObjs.get(position);
        holder.bind(mo);
    }

    @Override
    public int getItemCount() {
        return movieObjs.size();
    }

    public class MovieItemHolder extends RecyclerView.ViewHolder{

        public ImageView posterImage;

        public MovieItemHolder(View itemView) {
            super(itemView);
            posterImage = (ImageView) itemView.findViewById(R.id.movie_poster);
        }

        void bind(MovieObject mo)
        {
            Picasso.with(act).load(mo.getMoviePosterImageThumbnailUrl()).into(posterImage);
        }
    }
}
