package com.example.fibuv2.api;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailsAPI {

    public static String name;
    public static String runningTimeInMinutes;
    public static String genresList;
    public static String year;
    public static List<String> plotOutlineList = new ArrayList<>();
    public static int totalResult;

    public static void main(String[] args) {

        getDetails("{\"id\":\"/title/tt0167261/\",\"title\":{\"@type\":\"imdb.api.title.title\",\"id\":\"/title/tt0167261/\",\"image\":{\"height\":1500,\"id\":\"/title/tt0167261/images/rm306845440\",\"url\":\"https://m.media-amazon.com/images/M/MV5BZGMxZTdjZmYtMmE2Ni00ZTdkLWI5NTgtNjlmMjBiNzU2MmI5XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg\",\"width\":964},\"runningTimeInMinutes\":179,\"title\":\"The Lord of the Rings: The Two Towers\",\"titleType\":\"movie\",\"year\":2002},\"certificates\":{\"US\":[{\"attributes\":[\"Preview Short\"],\"certificate\":\"PG\",\"certificateNumber\":38927,\"ratingsBody\":\"MPAA\",\"country\":\"US\"}]},\"ratings\":{\"canRate\":true,\"rating\":8.7,\"ratingCount\":1571278,\"topRank\":14},\"genres\":[\"Action\",\"Adventure\",\"Drama\",\"Fantasy\"],\"releaseDate\":\"2002-12-18\",\"plotOutline\":{\"id\":\"/title/tt0167261/plot/po0952965\",\"text\":\"While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.\"},\"plotSummary\":{\"author\":\"Jwelch5742\",\"id\":\"/title/tt0167261/plot/ps2971820\",\"text\":\"The continuing quest of Frodo and the Fellowship to destroy the One Ring. Frodo and Sam discover they are being followed by the mysterious Gollum. Aragorn, the Elf archer Legolas, and Gimli the Dwarf encounter the besieged Rohan kingdom, whose once great King Theoden has fallen under Saruman's deadly spell.\"}}");


    }

    public static void getDetails(String jsonString) {

        try {

            Gson gson = new Gson();

            MovieDetails movieDetails;
            movieDetails = gson.fromJson(jsonString, MovieDetails.class);

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

