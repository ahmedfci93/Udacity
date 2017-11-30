package com.example.ahmed.movieapp.app;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Belal on 20-Sep-16.
 */
public class DetailAdapter {
    View rootView;
    private objMovie movie=new objMovie();
    public Favourites favourites=new Favourites();
    public ArrayList<objMovie> favMovies=new ArrayList<objMovie>();
    Context mContext;

    public DetailAdapter(Context context,View rootView,objMovie dMovie)
    {
        this.rootView=rootView;
        this.movie=dMovie;
        this.mContext=context;
    }

    public boolean checkFav(objMovie favMovie)
    {
        int i = 0;
        favMovies =favourites.getMovie(mContext);
        if(favMovies!=null)
        {
            for(objMovie movie: favMovies)
            {
                if(movie.getId().equalsIgnoreCase(favMovie.getId()))
                {
                    Log.v("yarb","satrk");
                    return true;
                }
            }
        }
        return false;
    }


    public View start()
    {
        Holder holder=new Holder();
        holder.title = (TextView) rootView.findViewById(R.id.title_movie);
        holder.overview = (TextView) rootView.findViewById(R.id.overView);
        holder.release = (TextView) rootView.findViewById(R.id.release_movie);
        holder.rate = (TextView) rootView.findViewById(R.id.rate_movie);
        holder.poster = (ImageView) rootView.findViewById(R.id.poster_movie);
        holder.favourite = (ImageButton) rootView.findViewById(R.id.favMovie);
        if(movie.getRelease()!=null) {
            holder.title.setText(movie.getTitle());
            holder.release.setText(movie.getRelease());
            holder.rate.setText(movie.getVote_avg() + "/10");
            holder.overview.setText(movie.getOverview());
            Picasso.with(rootView.getContext()).load(movie.getBackdrop_path()).resize(2000, 1500).into(holder.poster);
        }else
        {
            holder.rate.setVisibility(View.INVISIBLE);
            holder.title.setVisibility(View.INVISIBLE);
            holder.release.setVisibility(View.INVISIBLE);
            holder.overview.setVisibility(View.INVISIBLE);
            holder.poster.setVisibility(View.INVISIBLE);
        }
        if (checkFav(movie) == true) {
            holder.favourite.setImageResource(R.drawable.on);
            holder.favourite.setTag("on");
        } else {
            holder.favourite.setImageResource(R.drawable.off);
            holder.favourite.setTag("off");
        }
        return rootView;
    }
    public class Holder
    {
        TextView title;
        ImageView poster;
        TextView release;
        TextView rate;
        TextView overview;
        ImageButton favourite;
    }
}
