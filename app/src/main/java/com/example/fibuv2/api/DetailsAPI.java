package com.example.fibuv2.api;

import android.util.Log;

import com.example.fibuv2.MainActivity;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsAPI {

    public static String name;
    public static String runningTimeInMinutes;
    public static String genresList;
    public static String year;
    public static List<String> plotOutlineList = new ArrayList<>();


    public static void getDetails(String jsonString) {

            String url = "https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=";

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url+jsonString)
                    .get()
                    .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", MainActivity.getToken())
                    .build();


        try (Response response = client.newCall(request).execute()) {

            String result = response.body().string();

            Gson gson = new Gson();

            MovieDetails movieDetails;
            movieDetails = gson.fromJson(result, MovieDetails.class);



            plotOutlineList.add(movieDetails.getPlotOutline().text);
            plotOutlineList.add(movieDetails.getPlotSummary().text);
            runningTimeInMinutes = movieDetails.getTitle().runningTimeInMinutes;
            year = movieDetails.getTitle().year;
            name = movieDetails.getTitle().title;

            JsonParser parser = new JsonParser();
            JsonElement rootNode = parser.parse(jsonString);

            if (rootNode.isJsonObject()) {
                JsonObject details = rootNode.getAsJsonObject();
                JsonArray genres = details.getAsJsonArray("genres");

                String stringGengres = genres.toString();
                stringGengres = stringGengres.replaceAll("\\[", "");
                stringGengres = stringGengres.replaceAll("\\]", "");
                stringGengres = stringGengres.replaceAll("\"", "");
                stringGengres = stringGengres.replaceAll(",", ", ");

                genresList = stringGengres;

            }
            else
            {
                Title title;
                title = gson.fromJson(result, Title.class);
                genresList = title.titleType;
            }


        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    class Title {
        public String runningTimeInMinutes;
        public String titleType;
        public String year;
        public String title;
    }

    class PlotOutline {
        public String id;
        public String text;
    }

    class PlotSummary {
        public String author;
        public String id;
        public String text;
    }

    private class MovieDetails {


        private PlotOutline plotOutline;
        private PlotSummary plotSummary;
        private Title title;

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }


        public MovieDetails() {
        }

        public PlotOutline getPlotOutline() {
            return plotOutline;
        }

        public void setPlotOutline(PlotOutline plotOutline) {
            this.plotOutline = plotOutline;
        }

        public PlotSummary getPlotSummary() {
            return plotSummary;
        }

        public void setPlotSummary(PlotSummary plotSummary) {
            this.plotSummary = plotSummary;
        }


    }
}

