package com.example.fibuv2.api;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class RateAPI {

    public static ArrayList<String> splittedJson = new ArrayList<String>();
    public static List<String> contentType = new ArrayList<>();
    public static List<String> contentRate = new ArrayList<>();
    public static List<String> contentYear = new ArrayList<>();
    public static int totalResult;

    public static void main(String[] args) {

        rate("q");

        System.out.println(contentType.get(0));
        System.out.println(contentRate.get(0));
        System.out.println(contentYear.get(0));


    }

    public static void rate(String query) {

        try {


            String jsonString = "{\"@type\":\"imdb.api.title.ratings\",\"id\":\"/title/tt0167261/\",\"title\":\"The Lord of the Rings: The Two Towers\",\"titleType\":\"movie\",\"year\":2002,\"bottomRank\":9277,\"canRate\":true,\"rating\":8.7,\"ratingCount\":1571156,\"ratingsHistograms\":{\"Aged 45+\":{\"aggregateRating\":8.5,\"demographic\":\"Aged 45+\",\"histogram\":{\"1\":4422,\"2\":1350,\"3\":1365,\"4\":1704,\"5\":3433,\"6\":6494,\"7\":15719,\"8\":32364,\"9\":43077,\"10\":51596},\"totalRatings\":161524},\"Aged 18-29\":{\"aggregateRating\":8.9,\"demographic\":\"Aged 18-29\",\"histogram\":{\"1\":1799,\"2\":414,\"3\":554,\"4\":920,\"5\":2085,\"6\":5138,\"7\":17915,\"8\":48725,\"9\":71271,\"10\":93761},\"totalRatings\":242582},\"US users\":{\"aggregateRating\":8.7,\"demographic\":\"US users\",\"histogram\":{\"1\":6611,\"2\":1359,\"3\":1395,\"4\":1937,\"5\":3909,\"6\":7277,\"7\":19059,\"8\":42168,\"9\":58444,\"10\":80706},\"totalRatings\":222865},\"Females Aged 18-29\":{\"aggregateRating\":8.8,\"demographic\":\"Females Aged 18-29\",\"histogram\":{\"1\":492,\"2\":121,\"3\":160,\"4\":273,\"5\":627,\"6\":1373,\"7\":4123,\"8\":9535,\"9\":11921,\"10\":17936},\"totalRatings\":46561},\"Males Aged under 18\":{\"aggregateRating\":8.7,\"demographic\":\"Males Aged under 18\",\"histogram\":{\"1\":6,\"2\":3,\"3\":1,\"4\":5,\"5\":6,\"6\":21,\"7\":31,\"8\":111,\"9\":143,\"10\":155},\"totalRatings\":482},\"Males Aged 45+\":{\"aggregateRating\":8.5,\"demographic\":\"Males Aged 45+\",\"histogram\":{\"1\":3272,\"2\":1081,\"3\":1113,\"4\":1398,\"5\":2772,\"6\":5365,\"7\":13067,\"8\":27095,\"9\":35969,\"10\":40610},\"totalRatings\":131742},\"Aged under 18\":{\"aggregateRating\":8.7,\"demographic\":\"Aged under 18\",\"histogram\":{\"1\":12,\"2\":3,\"3\":1,\"4\":5,\"5\":10,\"6\":31,\"7\":50,\"8\":153,\"9\":194,\"10\":210},\"totalRatings\":669},\"Females Aged under 18\":{\"aggregateRating\":8.3,\"demographic\":\"Females Aged under 18\",\"histogram\":{\"1\":5,\"2\":0,\"3\":0,\"4\":0,\"5\":2,\"6\":7,\"7\":10,\"8\":31,\"9\":26,\"10\":25},\"totalRatings\":106},\"IMDb Users\":{\"aggregateRating\":8.7,\"demographic\":\"IMDb Users\",\"histogram\":{\"1\":22741,\"2\":6105,\"3\":7172,\"4\":10175,\"5\":22045,\"6\":47296,\"7\":137201,\"8\":327441,\"9\":442861,\"10\":548119},\"totalRatings\":1571156},\"Non-US users\":{\"aggregateRating\":8.7,\"demographic\":\"Non-US users\",\"histogram\":{\"1\":8343,\"2\":2316,\"3\":2756,\"4\":3989,\"5\":8845,\"6\":19830,\"7\":56361,\"8\":131099,\"9\":176267,\"10\":206584},\"totalRatings\":616390},\"Males Aged 18-29\":{\"aggregateRating\":8.9,\"demographic\":\"Males Aged 18-29\",\"histogram\":{\"1\":1283,\"2\":279,\"3\":376,\"4\":626,\"5\":1396,\"6\":3618,\"7\":13262,\"8\":37795,\"9\":57421,\"10\":73481},\"totalRatings\":189537},\"Males\":{\"aggregateRating\":8.7,\"demographic\":\"Males\",\"histogram\":{\"1\":13545,\"2\":3453,\"3\":3941,\"4\":5692,\"5\":12263,\"6\":26891,\"7\":78696,\"8\":190912,\"9\":266751,\"10\":312918},\"totalRatings\":915062},\"Males Aged 30-44\":{\"aggregateRating\":8.7,\"demographic\":\"Males Aged 30-44\",\"histogram\":{\"1\":8440,\"2\":1947,\"3\":2234,\"4\":3360,\"5\":7369,\"6\":16264,\"7\":46781,\"8\":110481,\"9\":151492,\"10\":174593},\"totalRatings\":522961},\"Females Aged 45+\":{\"aggregateRating\":8.6,\"demographic\":\"Females Aged 45+\",\"histogram\":{\"1\":1093,\"2\":243,\"3\":222,\"4\":266,\"5\":598,\"6\":995,\"7\":2346,\"8\":4562,\"9\":6291,\"10\":9993},\"totalRatings\":26609},\"Females\":{\"aggregateRating\":8.7,\"demographic\":\"Females\",\"histogram\":{\"1\":3689,\"2\":920,\"3\":1138,\"4\":1514,\"5\":3396,\"6\":7019,\"7\":18681,\"8\":39664,\"9\":49383,\"10\":74604},\"totalRatings\":200008},\"Top 1000 voters\":{\"aggregateRating\":8.5,\"demographic\":\"Top 1000 voters\",\"histogram\":{\"1\":26,\"2\":9,\"3\":11,\"4\":19,\"5\":25,\"6\":45,\"7\":106,\"8\":147,\"9\":228,\"10\":286},\"totalRatings\":902},\"IMDb Staff\":{\"aggregateRating\":8.1,\"demographic\":\"IMDb Staff\",\"histogram\":{\"1\":0,\"2\":1,\"3\":1,\"4\":1,\"5\":3,\"6\":2,\"7\":12,\"8\":18,\"9\":20,\"10\":11},\"totalRatings\":69},\"Females Aged 30-44\":{\"aggregateRating\":8.6,\"demographic\":\"Females Aged 30-44\",\"histogram\":{\"1\":1999,\"2\":517,\"3\":685,\"4\":904,\"5\":1977,\"6\":4184,\"7\":10897,\"8\":22464,\"9\":27475,\"10\":41437},\"totalRatings\":112539},\"Aged 30-44\":{\"aggregateRating\":8.7,\"demographic\":\"Aged 30-44\",\"histogram\":{\"1\":10531,\"2\":2491,\"3\":2967,\"4\":4335,\"5\":9473,\"6\":20784,\"7\":58620,\"8\":135036,\"9\":181612,\"10\":219171},\"totalRatings\":645020}},\"topRank\":14}";
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(jsonString));
            reader.setLenient(true);                                                       // to get the null results
            MovieRate movierate = gson.fromJson(reader, MovieRate.class);

            contentType.add(movierate.getTitleType());
            contentRate.add(movierate.getRating());
            contentYear.add(movierate.getYear());


        } catch (Exception e) {
            System.out.println(e.toString());
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