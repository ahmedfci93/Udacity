package com.example.ahmed.movieapp.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
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
public class MainMovie extends Fragment {

    private static int place=0;
    public ArrayList<objMovie> mList=new ArrayList<objMovie>();
    public static mainAdapter mAdapter=null;
    objMovie selMovie;
    public int curUrl=1;
    public static int  sortType;
    String url="http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=661aa4a6cc694b9b7c1145ab4312b589";
    public ArrayList<String> imgsUrl=new ArrayList<String>();
    public  GridView gridView;
    public MainMovie() {
    }
    @Override
    public void onStart() {
        super.onStart();
        showMovies start=new showMovies();
        start.setURL();
    }

    public interface Callback {
        void onItemSelected(objMovie movie, int index);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.refresh_main, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            showMovies start=new showMovies();
            start.setURL();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_main, container, false);
        gridView=(GridView)rootView.findViewById(R.id.mainGridView);
        mAdapter=new mainAdapter(MainMovie.this.getActivity(),R.layout.img_movie_item,imgsUrl);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),mList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                selMovie=mList.get(position);
                ((Callback) getActivity()).onItemSelected(selMovie, position);
            }
        });
        return rootView;
    }

    /*public static void forceSelection ( int index)
    {
        // gridView select item at index
        place=index;
    }*/

    public  class showMovies {
        public  void setURL()
        {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            sortType = Integer.parseInt(sharedPref.getString(getString(R.string.pre_sort_key), getString(R.string.pre_sort_defualt)));
            if(sortType==3&&curUrl!=sortType)//favourite
            {
                Favourites movFavourites = new Favourites();
                //mList.clear();
                mList=movFavourites.getMovie(getActivity());
                if(mList!=null)
                {
                    imgsUrl.clear();
                    for (int i = 0; i < mList.size(); i++)
                        imgsUrl.add(mList.get(i).getPosterPath());
                    mAdapter.notifyDataSetChanged();
                }else
                {
                    mAdapter.clear();
                    Toast.makeText(getActivity(),"Favourite is Empty",Toast.LENGTH_SHORT).show();
                }

            }else {
                if (sortType == 1 && curUrl != sortType)//popularity
                {
                    url = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=661aa4a6cc694b9b7c1145ab4312b589";
                    curUrl = sortType;
                } else if (sortType == 2 && curUrl != sortType)//top rated
                {
                    url = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=661aa4a6cc694b9b7c1145ab4312b589";
                    curUrl = sortType;
                }
                imgsUrl.clear();
                if(mList!=null)mList.clear();
                getMovies();
            }
        }
        public void getMovies() {
            if(mList==null)mList=new ArrayList<objMovie>();
            RequestQueue queue = Volley.newRequestQueue(MainMovie.this.getActivity());
            JsonObjectRequest objJsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray res = response.getJSONArray("results");
                                final java.lang.String imgPath = "https://image.tmdb.org/t/p/w500";
                                for (int i = 0; i < res.length(); i++) {
                                    objMovie movie = new objMovie();
                                    JSONObject objJson = res.getJSONObject(i);
                                    movie.setId(objJson.getString("id"));
                                    movie.setTitle(objJson.getString("title"));
                                    movie.setOverview(objJson.getString("overview"));
                                    movie.setRelease(objJson.getString("release_date"));
                                    imgsUrl.add(imgPath + objJson.getString("poster_path"));
                                    movie.setPosterPath(imgPath + objJson.getString("poster_path"));
                                    movie.setBackdrop_path(imgPath + objJson.getString("backdrop_path"));
                                    movie.setPopularity(objJson.getString("popularity"));
                                    movie.setVote_avg(objJson.getString("vote_average"));
                                    movie.setVote_count(objJson.getString("vote_count"));
                                    mList.add(movie);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    , new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.v("ahmed-er", "ERROR");
                }
            });
            queue.add(objJsonRequest);
        }
    }
}
