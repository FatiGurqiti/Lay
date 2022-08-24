package com.fdev.lay.data.remote.api;

import com.fdev.lay.ui.base.MainActivity;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class SearchAPI {

    public static ArrayList<String> splittedJson = new ArrayList<String>();
    public static int total;
    public static List<String> movieID = new ArrayList<>();
    public static List<String> movieImageUrl = new ArrayList<>();
    public static List<String> movieTitle = new ArrayList<>();
    public static List<String> movieType = new ArrayList<>();
    public static List<String> movieQ = new ArrayList<>();


    public static void autoCompleteAPI(String querry){
        String url = "https://imdb8.p.rapidapi.com/auto-complete?q=";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+querry)
                .get()
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .addHeader("x-rapidapi-key", MainActivity.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {

            String result = response.body().string();
            fixjson(result);

            for (int i = 0; i < total; i++) {
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new StringReader(splittedJson.get(i)));
                reader.setLenient(true);  // to get the null results as well
                Movie movie = gson.fromJson(reader, Movie.class);

                movieID.add(movie.getId());
                movieImageUrl.add(movie.getImageUrl());
                movieTitle.add(movie.getL());
                movieType.add(movie.getS());
                movieQ.add(movie.getQ());
            }
        } catch (Exception e) {
            total = 0;
        }

    }

    private static void fixjson(String json) {
        String[] sJson = new String[json.length()];
        int count = 0;
        StringBuilder newjson = new StringBuilder(json);
        for (int i = 0; i < json.length(); i++) {
            if (json.charAt(i) == '{' && json.charAt(i + 1) == '\"' && json.charAt(i + 2) == 'i' && json.charAt(i + 3) == '\"') {
                count++;

                if (newjson.charAt(i + 19) == ',') {
                    newjson.setCharAt(i + 18, '}');
                } else
                    newjson.setCharAt(i + 17, '}');
            }
            if (json.charAt(i) == 'w' && json.charAt(i + 1) == 'i' && json.charAt(i + 2) == 'd' && json.charAt(i + 3) == 't' && json.charAt(i + 4) == 'h') {


                if (newjson.charAt(i + 10) == '}')
                    newjson.setCharAt(i + 10, '0');
                else
                    newjson.setCharAt(i + 11, '0');
                json = String.valueOf(newjson);

            }
            total = count;
        }


        sJson = json.split("\"i\"", count + 1);

        for (String a : sJson)
            splittedJson.add(a);

        splittedJson.remove(0);
        for (int i = 0; i < splittedJson.size(); i++) {
            String replacementString = "";
            splittedJson.set(i, "{\"i\"" + splittedJson.get(i));
            replacementString = splittedJson.get(i);
            replacementString = replacementString.replaceAll(",\"v\":\\[", "},");
            splittedJson.set(i, replacementString);

        }
    }


    private class Movie {
        private String imageUrl;
        private String l;
        private String type;
        private String id;
        private String s;
        private String q;

        public Movie() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getL() {
            return l;
        }

        public void setL(String l) {
            this.l = l;
        }

        public String getType() {
            return type;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public void setType(String type) {
            this.type = type;
        }


        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }


        public String toString() {
            return "Movie [ \nimageUrl: " + imageUrl + " \nid: " + id + "  \ntitle: " + l + "  \ntype: " + s + "  \nq: " + q + "   \n]";
        }

    }

}