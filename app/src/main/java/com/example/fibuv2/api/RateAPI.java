package com.example.fibuv2.api;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RateAPI {

    public static String contentType;
    public static String contentRate;


    public static void rate(String jsonString) {

        String url = "https://imdb8.p.rapidapi.com/title/get-ratings?tconst=";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+jsonString)
                .get()
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "ba1a7a4182mshd780c0eb859bfbcp16a349jsn307fcc77a0f2")
                .build();

        try (Response response = client.newCall(request).execute()) {

            String result = response.body().string();


            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(result));
            reader.setLenient(true);                                                       // to get the null results
            MovieRate movierate = gson.fromJson(reader, MovieRate.class);

            contentType=movierate.getTitleType();
            contentRate=movierate.getRating();



        } catch (Exception e) {

            contentType=null;
            contentRate=null;
        }

    }


    private class MovieRate {
        public String getTitleType() {
            return titleType;
        }

        public void setTitleType(String titleType) {
            this.titleType = titleType;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        private String titleType;
        private String year;
        private String rating;


        public MovieRate() {
        }


        public String toString() {
            return "Movie [ \ntitleType: " + titleType + " \nyear: " + year + "  \nrating: " + rating + "\n]";
        }

    }

}