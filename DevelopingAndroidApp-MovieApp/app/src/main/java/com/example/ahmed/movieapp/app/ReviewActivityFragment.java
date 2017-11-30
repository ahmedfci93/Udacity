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
public class ReviewActivityFragment extends Fragment {


    public ArrayList<objReview> reviews=new ArrayList<objReview>();
    String MovieId;
    String url;
    String Url="http://api.themoviedb.org/3/movie/";
    String reviewURL="/reviews?api_key=661aa4a6cc694b9b7c1145ab4312b589";
    reviewAdapter rAdapter=null;
    public ReviewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_review, container, false);
        Intent intent = getActivity().getIntent();
        MovieId=MovieDetailFragment.movie.getId();
        if (MovieId != null) {

            ListView reviewList = (ListView) rootView.findViewById(R.id.ReviewList);
            getReview();
            rAdapter = new reviewAdapter(ReviewActivityFragment.this.getActivity(), R.layout.review_movie, reviews);
            reviewList.setAdapter(rAdapter);
        }
        return rootView;
    }
    public void getReview() {
        reviews.clear();
        url = " ";
        url = Url + MovieId + reviewURL;
        if (MovieId != null) {
            Log.v("Url", url);
            RequestQueue queue = Volley.newRequestQueue(ReviewActivityFragment.this.getActivity());
            JsonObjectRequest objJsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray res = response.getJSONArray("results");
                                Log.v("reviews", Integer.toString(res.length()));
                                for (int i = 0; i < res.length(); i++) {
                                    objReview review = new objReview();
                                    JSONObject objJson = res.getJSONObject(i);
                                    review.setAuthor(objJson.getString("author") + " :");
                                    review.setContent(objJson.getString("content"));
                                    reviews.add(review);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(reviews.isEmpty())Toast.makeText(getActivity(), "Review is Empty", Toast.LENGTH_SHORT).show();
                            else Toast.makeText(getActivity(),Integer.toString(reviews.size())+" Reviews", Toast.LENGTH_SHORT).show();
                            rAdapter.notifyDataSetChanged();
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
