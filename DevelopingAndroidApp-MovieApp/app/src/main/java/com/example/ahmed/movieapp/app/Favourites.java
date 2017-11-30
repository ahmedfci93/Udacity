package com.example.ahmed.movieapp.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Belal on 28-Sep-16.
 */
public class Favourites {


    public static final String pref_name = "movFavourites";
    public static final String favourite = "movFavorite";
    public Favourites()
    {
        super();
    }

    public void saveMovie(Context context, ArrayList<objMovie> favorites)
    {
        SharedPreferences setting;
        SharedPreferences.Editor editor;
        setting = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        editor = setting.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(favourite, jsonFavorites);
        editor.commit();
    }
    public void removeMovie(Context context, objMovie movie)
    {
        ArrayList<objMovie> movFavourite = getMovie(context);
        if (movFavourite != null)
        {
            Log.v("remove",Integer.toString(movFavourite.size()));
            //movFavourite.remove(movie);
            int Location=0;
            for (int index = 0; index < movFavourite.size(); index++) {
                if(movFavourite.get(index).getId().equalsIgnoreCase(movie.getId()))
                {
                    Location=index;
                    break;
                }
            }
            movFavourite.remove(Location);
            Log.v("remove",Integer.toString(movFavourite.size()));
            saveMovie(context, movFavourite);
        }
    }
    public void addMovie(Context context, objMovie movie)
    {
        ArrayList<objMovie> favorites = getMovie(context);
        if (favorites == null)
            favorites = new ArrayList<objMovie>();
        if(!(favorites.contains(movie))) favorites.add(movie);
        saveMovie(context, favorites);
    }
    public ArrayList<objMovie> getMovie(Context context)
    {
        SharedPreferences setting;
        List favourites;
        setting = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        if (setting.contains(favourite))
        {
            String jsonFavorites = setting.getString(favourite, null);
            Gson gson = new Gson();
            objMovie[] movFavourite = gson.fromJson(jsonFavorites, objMovie[].class);
            favourites=Arrays.asList(movFavourite);
            favourites=new ArrayList(favourites);
        }else return null;
        return (ArrayList) favourites;
    }
}
