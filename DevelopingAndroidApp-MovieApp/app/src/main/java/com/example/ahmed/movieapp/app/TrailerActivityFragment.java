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
import android.widget.ListView;
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
public class TrailerActivityFragment extends Fragment {

    public ArrayList<objTrailer> trailers=new ArrayList<objTrailer>();
    String Url="http://api.themoviedb.org/3/movie/";
    String trailerURl="/videos?api_key=661aa4a6cc694b9b7c1145ab4312b589";
    trailerAdapter tAdapter=null;
    String MovieId;
    String url;
    public TrailerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_trailer, container, false);
        Intent intent = getActivity().getIntent();
        MovieId=MovieDetailFragment.movie.getId();
        if (MovieId != null) {

            ListView trailerList = (ListView) rootView.findViewById(R.id.TrailerList);
            getTrailer();
            tAdapter = new trailerAdapter(TrailerActivityFragment.this.getActivity(), R.layout.trailer_movie, trailers);
            trailerList.setAdapter(tAdapter);
            trailerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String link = trailers.get(position).getKey();
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(java.lang.String.valueOf(link))));
                }
            });
        }
        return rootView;
    }
    public void getTrailer() {
        url="";
        trailers.clear();
        url = Url + MovieId + trailerURl;
        if (MovieId != null) {
            RequestQueue queue = Volley.newRequestQueue(TrailerActivityFragment.this.getActivity());

            JsonObjectRequest objJsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray res = response.getJSONArray("results");
                                for (int i = 0; i < res.length(); i++) {
                                    objTrailer trailer = new objTrailer();
                                    JSONObject objJson = res.getJSONObject(i);
                                    String path = "https://www.youtube.com/watch?v=" + objJson.getString("key");
                                    trailer.setKey(path);
                                    trailer.setName("Trailer " + Integer.toString(i + 1));
                                    trailers.add(trailer);
                                    Log.v("key", trailers.get(i).getKey());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(trailers.size()==0) Toast.makeText(getActivity(), "Trailer is Empty", Toast.LENGTH_SHORT).show();
                            else Toast.makeText(getActivity(),Integer.toString(trailers.size())+" Trailers", Toast.LENGTH_SHORT).show();
                            tAdapter.notifyDataSetChanged();

                            Log.v("size", Integer.toString(trailers.size()));
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
