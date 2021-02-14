package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    int movieId;
    String posterPath;
    String title;
    String overview;
    String backdropPath;
    double rating;

    //for parcel library
    public Movie()
    {}

    public Movie(JSONObject jsonobject) throws JSONException {
        backdropPath = jsonobject.getString("backdrop_path");
        posterPath = jsonobject.getString("poster_path");
        title = jsonobject.getString("title");
        overview = jsonobject.getString("overview");
        rating = jsonobject.getDouble("vote_average");
        movieId = jsonobject.getInt("id");
    }
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length();i++ )
        {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath()
    {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating(){return rating;}

    public int getMovieId() {return movieId;}
}
