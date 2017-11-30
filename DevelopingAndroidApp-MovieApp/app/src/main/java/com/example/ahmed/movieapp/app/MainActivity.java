package com.example.ahmed.movieapp.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainMovie.Callback {
    public static boolean mTwoPane;
    private int currentSelectedMovie = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieDetailFragment(),
                                MovieDetailFragment.TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
        /*if(savedInstanceState!=null){
            currentSelectedMovie = savedInstanceState.getInt("currentSelectedItem");
            //get fragment object supportFragmentManager find fragment by id
            //Call force selection
            getSupportFragmentManager().findFragmentById(currentSelectedMovie);
            MainMovie.forceSelection(currentSelectedMovie);
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemSelected(objMovie movie, int index)
    {
        currentSelectedMovie = index;
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(MovieDetailFragment.DETAIL_MOVIE, movie);
            findViewById(R.id.movie_detail_container).setVisibility(View.VISIBLE);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, MovieDetailFragment.TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, MoiveDetail.class)
                    .putExtra(MovieDetailFragment.DETAIL_MOVIE, movie);
            startActivity(intent);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("currentSelectedIndex", currentSelectedMovie);
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
