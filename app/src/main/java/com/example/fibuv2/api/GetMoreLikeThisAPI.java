package com.example.fibuv2.api;

import android.util.Log;

import com.example.fibuv2.MainActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMoreLikeThisAPI {


    public static String morelikethis;


    public static void getmorelikethiss(String jsonString) {


        String url = "https://imdb8.p.rapidapi.com/title/get-more-like-this?tconst=";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + jsonString)
                .get()
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .addHeader("x-rapidapi-key", MainActivity.getToken())
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
            morelikethis = (String.valueOf(arr));


        }
        catch (Exception e)
        {
            Log.d("GetMoreLikeThisStatus",e.toString());
        }

    }
}


