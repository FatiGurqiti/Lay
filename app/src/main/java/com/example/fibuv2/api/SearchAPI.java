package com.example.fibuv2.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SearchAPI {

    public static ArrayList<String> splittedJson = new ArrayList<String>();
    public static int total;
    public static List<String> movieID = new ArrayList<>();
    public static List<String> movieImageUrl = new ArrayList<>();
    public static List<String> movieTitle = new ArrayList<>();
    public static List<String> movieType = new ArrayList<>();
    public static List<String> movieQ = new ArrayList<>();

    public static void main(String[] args) {

        autoCompleteAPI("q");


    }

    public static void autoCompleteAPI(String query) {

        try {

            String jsonString = "{\"d\":[{\"i\":{\"height\":2936,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_.jpg\",\"width\":1978},\"id\":\"tt0120737\",\"l\":\"The Lord of the Rings: The Fellowship of the Ring\",\"q\":\"feature\",\"rank\":158,\"s\":\"Elijah Wood, Ian McKellen\",\"v\":[{\"i\":{\"height\":1080,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BMzQ2YjI2YzgtZmNmMy00ZjUyLWE0NGItY2Y3NjUzZWM4NmM3XkEyXkFqcGdeQWxiaWFtb250._V1_.jpg\",\"width\":1920},\"id\":\"vi684573465\",\"l\":\"Official Trailer\",\"s\":\"1:54\"},{\"i\":{\"height\":1080,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNzkzYzNmOTYtMTE1Yi00Yzg0LWE4OTgtY2QxOGZkNWQ2ODdiXkEyXkFqcGdeQWxpenpp._V1_.jpg\",\"width\":1920},\"id\":\"vi2108539673\",\"l\":\"Does Andy Serkis Know How Many Times He's Played Gollum?\",\"s\":\"3:01\"},{\"i\":{\"height\":480,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BODk1MzkwNTA4N15BMl5BanBnXkFtZTgwOTU1ODY3MjI@._V1_.jpg\",\"width\":640},\"id\":\"vi2073101337\",\"l\":\"The Lord of the Rings Trilogy on Blu-ray\",\"s\":\"2:02\"}],\"vt\":10,\"y\":2001},{\"i\":{\"height\":1185,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNzA5ZDNlZWMtM2NhNS00NDJjLTk4NDItYTRmY2EwMWZlMTY3XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg\",\"width\":800},\"id\":\"tt0167260\",\"l\":\"The Lord of the Rings: The Return of the King\",\"q\":\"feature\",\"rank\":322,\"s\":\"Elijah Wood, Viggo Mortensen\",\"v\":[{\"i\":{\"height\":1080,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNDRmNjQxOWItNTY3MS00M2ExLWJiYWYtNGQ2OTI5MWRkYWFlXkEyXkFqcGdeQWxiaWFtb250._V1_.jpg\",\"width\":1920},\"id\":\"vi718127897\",\"l\":\"Official Trailer\",\"s\":\"1:41\"},{\"i\":{\"height\":1080,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNzkzYzNmOTYtMTE1Yi00Yzg0LWE4OTgtY2QxOGZkNWQ2ODdiXkEyXkFqcGdeQWxpenpp._V1_.jpg\",\"width\":1920},\"id\":\"vi2108539673\",\"l\":\"Does Andy Serkis Know How Many Times He's Played Gollum?\",\"s\":\"3:01\"},{\"i\":{\"height\":480,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BODk1MzkwNTA4N15BMl5BanBnXkFtZTgwOTU1ODY3MjI@._V1_.jpg\",\"width\":640},\"id\":\"vi2073101337\",\"l\":\"The Lord of the Rings Trilogy on Blu-ray\",\"s\":\"2:02\"}],\"vt\":11,\"y\":2003},{\"i\":{\"height\":1500,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BZGMxZTdjZmYtMmE2Ni00ZTdkLWI5NTgtNjlmMjBiNzU2MmI5XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg\",\"width\":964},\"id\":\"tt0167261\",\"l\":\"The Lord of the Rings: The Two Towers\",\"q\":\"feature\",\"rank\":403,\"s\":\"Elijah Wood, Ian McKellen\",\"y\":2002},{\"i\":{\"height\":994,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BMjM0ZDM1YzQtMjlmYy00YThlLTk2MmItNTM3NWRiZmUwYWViXkEyXkFqcGdeQXVyNzg5MzIyOA@@._V1_.jpg\",\"width\":994},\"id\":\"tt7631058\",\"l\":\"The Lord of the Rings\",\"q\":\"TV series\",\"rank\":415,\"s\":\"Benjamin Walker, Peter Mullan\",\"y\":2022,\"yr\":\"2022-\"},{\"i\":{\"height\":1500,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BOGMyNWJhZmYtNGQxYi00Y2ZjLWJmNjktNTgzZWJjOTg4YjM3L2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_.jpg\",\"width\":979},\"id\":\"tt0077869\",\"l\":\"The Lord of the Rings\",\"q\":\"feature\",\"rank\":5647,\"s\":\"Christopher Guard, William Squire\",\"y\":1978},{\"id\":\"nm1648230\",\"l\":\"Hank Kaplan\",\"rank\":448608,\"s\":\"Self, Ring of Fire: The Emile Griffith Story (2005)\"},{\"i\":{\"height\":503,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNWU1NjZiODktMjc4Ni00Yjg4LWEzMzUtNTJiMzM0ZDQ5NWYzXkEyXkFqcGdeQXVyMTMzNTYwMjAz._V1_.jpg\",\"width\":755},\"id\":\"tt14824600\",\"l\":\"The Lord of the Rings: The War of the Rohirrim\",\"q\":\"feature\",\"rank\":26262},{\"i\":{\"height\":455,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNDEzNDJhNzctMjNhZC00MThiLWFjN2EtZGJlZTM5Yjg1MGY0XkEyXkFqcGdeQXVyODE1OTI0Mjg@._V1_.jpg\",\"width\":808},\"id\":\"tt12789698\",\"l\":\"The Lord of the Rings: Gollum\",\"q\":\"video game\",\"rank\":28979,\"s\":\"Action, Adventure, Drama\",\"y\":2022}],\"q\":\"the lord of the ring\",\"v\":1}";

            fixjson(jsonString);       //The data result has more than one respose, this splits them all to a list in order to use them all

            for (int i = 0; i < total; i++) {
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new StringReader(splittedJson.get(i)));
                reader.setLenient(true);                                                       // to get the null results
                Movie movie = gson.fromJson(reader, Movie.class);

                movieID.add(movie.getId());
                movieImageUrl.add(movie.getImageUrl());
                movieTitle.add(movie.getL());
                movieType.add(movie.getS());
                movieQ.add(movie.getQ());
            }


        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    private static void fixjson(String json) {

        // This part locates how many results there are
        // and splits them all to different arrays
        // so that I can use them all

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

        for (String a : sJson) {
            splittedJson.add(a);
        }
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
            return "Movie [ \nimageUrl: " + imageUrl + " \nid: " + id + "  \ntitle: " + l + "  \ntype: " + s +"  \nq: " + q + "   \n]";
        }

    }

}