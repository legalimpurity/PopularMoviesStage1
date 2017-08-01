package popularmoviesstage1.legalimpurity.com.popularmoviesstage1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movie_list_recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView(this);
    }

    private void setView(Activity act)
    {
        movie_list_recycler_view = (RecyclerView) act.findViewById(R.id.movie_list_recycler_view);
    }
}
