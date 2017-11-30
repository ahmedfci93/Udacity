package com.example.ahmed.movieapp.app;

import java.io.Serializable;

/**
 * Created by Belal on 28-Sep-16.
 */
public class objMovie implements Serializable{
        public String id;
        public String title;
        public String posterPath;
        public String overview;
        public String release;
        public String backdrop_path;
        public String popularity;
        public String vote_count;
        public String vote_avg;


        public void objMovie(String pPath, String oView, String Release, String Id, String Title, String bdtitle, String Pop, String vCount, String vAvg) {
            this.overview = oView;
            this.release = Release;
            this.id = Id;
            this.title = Title;
            this.backdrop_path = bdtitle;
            this.popularity = Pop;
            this.vote_count = vCount;
            this.vote_avg = vAvg;
            this.posterPath = pPath;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease() {
            return release;
        }

        public void setRelease(String release) {
            this.release = release;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public String getPopularity() {
            return popularity;
        }

        public void setPopularity(String popularity) {
            this.popularity = popularity;
        }

        public String getVote_count() {
            return vote_count;
        }

        public void setVote_count(String vote_count) {
            this.vote_count = vote_count;
        }

        public String getVote_avg() {
            return vote_avg;
        }

        public void setVote_avg(String vote_avg) {
            this.vote_avg = vote_avg;
        }
}
