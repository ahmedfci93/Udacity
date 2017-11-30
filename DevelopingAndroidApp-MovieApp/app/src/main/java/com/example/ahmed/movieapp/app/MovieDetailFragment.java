package com.example.ahmed.movieapp.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    public ArrayList<String> favList=new ArrayList<String>();
    public static final String TAG = MovieDetailFragment.class.getSimpleName();
    static final String DETAIL_MOVIE = "DETAIL_MOVIE";
    public static objMovie movie=new objMovie();
    Favourites savFavourites;
    ImageButton imgButton;
    public MovieDetailFragment() {
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        imgButton = (ImageButton) rootview.findViewById(R.id.favMovie);
        Button traButton = (Button) rootview.findViewById(R.id.trailerButton);
        Button revButton = (Button) rootview.findViewById(R.id.reviewButton);
        Bundle arguments = getArguments();
        if (arguments != null) {
            movie = (objMovie) arguments.getSerializable(MovieDetailFragment.DETAIL_MOVIE);
        }
        if (movie.getRelease()!=null) {
            rootview = new DetailAdapter(getActivity(), rootview, movie).start();
            savFavourites = new Favourites();
            Log.v("dddddddddd", "ddddddd");
            traButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MovieDetailFragment.this.getActivity(), TrailerActivity.class);
                    startActivity(intent);
                }
            });
            revButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MovieDetailFragment.this.getActivity(), ReviewActivity.class);
                    startActivity(intent);
                }
            });
            if (movie.getId() != null) {
                Log.v("llllllll","kkkkkkkkkkkk");
                imgButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (imgButton.getTag().toString() == "on") {

                            Toast.makeText(getActivity(), "Remove from favourites", Toast.LENGTH_SHORT).show();
                            imgButton.setImageResource(R.drawable.off);
                            savFavourites.removeMovie(getActivity(), movie);
                            imgButton.setTag("off");
                        } else {
                            Toast.makeText(getActivity(), "Add to favourites", Toast.LENGTH_SHORT).show();
                            imgButton.setImageResource(R.drawable.on);
                            savFavourites.addMovie(getActivity(), movie);
                            imgButton.setTag("on");
                        }
                    }

                });
            }
        }
        return rootview;
    }

}
