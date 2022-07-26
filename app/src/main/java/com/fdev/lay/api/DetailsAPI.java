package com.fdev.lay.api;

import com.fdev.lay.MainActivity;
import com.google.gson.*;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsAPI {

    private static String name;
    private static String runningTimeInMinutes;
    private static ArrayList genresList;
    private static String year;
    private static List<String> plotOutlineList = new ArrayList<>();


    public static void getDetails(String jsonString) {

        String url = "https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + jsonString)
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
            genresList = movieDetails.getGenres();

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

        public ArrayList getGenres() {
            return genres;
        }

        public void setGenres(ArrayList genres) {
            this.genres = genres;
        }

        private ArrayList genres;

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

    public String getName() {
        if (name != null) return name;
        else return "";
    }

    public String getRunningTimeInMinutes() {
        if (runningTimeInMinutes != null) return runningTimeInMinutes;
        else return "0";
    }

    public String getYear() {
        if (year != null) return year;
        else return "";
    }

    public ArrayList getGenresList() {
        return genresList;
    }

    public List<String> getPlotOutlineList() {
        return plotOutlineList;
    }

    public void clearGenresList() {
        genresList.clear();
    }

    public void clearPlotOutlineList() {
        plotOutlineList.clear();
    }
}

