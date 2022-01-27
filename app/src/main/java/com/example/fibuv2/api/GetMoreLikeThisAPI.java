package com.example.fibuv2.api;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMoreLikeThisAPI {


    public static List<String> morelikethis = new ArrayList<>();


    public static void getmorelikethiss(String jsonString) {


        String url = "https://imdb8.p.rapidapi.com/title/get-more-like-this?tconst=";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + jsonString)
                .get()
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "74f75c29e8mshddc4a4f5ad1f5e3p158145jsn38393a1db087")
                .build();


        try (Response response = client.newCall(request).execute()) {

            String querry = response.body().string();

            querry = querry.replaceAll("\\[", "");
            querry = querry.replaceAll("\\]", "");
            querry = querry.replaceAll("\"", "");
            querry = querry.replaceAll("title", "");
            querry = querry.replaceAll("/", "");
            querry = querry.replaceAll(",", "");


            char arr[] = new char[9];


            for (int i = 0; i < 9; i++) {
                arr[i] = querry.charAt(i);
            }
            morelikethis.add(String.valueOf(arr));

            for (int i = 9; i < 18; i++) {
                arr[i - 9] = querry.charAt(i);
            }
            morelikethis.add(String.valueOf(arr));

            for (int i = 18; i < 27; i++) {
                arr[i - 18] = querry.charAt(i);
            }
            morelikethis.add(String.valueOf(arr));


        }
        catch (Exception e)
        {
            Log.d("GetMoreLikeThisStatus",e.toString());
        }

    }
}


