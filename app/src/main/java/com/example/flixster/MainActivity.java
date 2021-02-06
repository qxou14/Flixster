package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity"; //this is use for log messages

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        //create adatater
        MovieAdapter movieAdapter= new MovieAdapter(this,movies);

        //set adatper to the recyler view
        rvMovies.setAdapter(movieAdapter);

        //layout manager
        rvMovies.setLayoutManager(new LinearLayoutManager(this));



        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "Success");
                JSONObject jsonObject = json.jsonObject;
                try {
                    //we get the data with the key result
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Result: "+results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    //when data changes
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG,"movies: "+ movies.size());
                } catch (JSONException e) {
                    Log.e(TAG,"exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "Failure");

            }
        });
    }
}