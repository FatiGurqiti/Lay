package com.example.fibuv2.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieAPI {

    public static List<String> movie = new ArrayList<>();
    public static int totalResult;

    public static String get(String url,String querry,String field, int index) throws IOException {
        String returnString;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+querry)
                .get()
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "ba1a7a4182mshd780c0eb859bfbcp16a349jsn307fcc77a0f2")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String query = response.body().string();

            query = query.replaceAll(",", ",\n");
            query = query.replaceAll("\"", " ");
            query = query.replaceAll("l :", "title:");
            query = query.replaceAll("s :", "cast:");
            query = query.replaceAll("y :", "year:");
            query = query.replaceAll("imageUrtitle:", "imageUrl:");
            query = query.replaceAll("width :", "width:");
            query = query.replaceAll("id : ", "idOfTheMovie:");

            // System.out.println(query);   //prints the whole body
            text(query, field); // prints the spesific field
            // Do something with movie
            returnString = movie.get(index);
            movie.clear();



        }
        catch (Exception e)
        {
            returnString= e.toString();
        }
        return returnString;
    }


    private static void text(String text, String field) {
        char[] arr = text.toCharArray();
        int count = 0;
        String result;
        List<String> myList = new ArrayList<>();
        int j;
        for (int i = 0; i < text.length(); i++) {
            if (arr[i] == ',') {
                j = i+1;
                while (true) {
                    myList.add(String.valueOf(arr[j]));
                    j++;
                    try {
                        if (arr[j] == ',') {

                            result = myList.toString();
                            result = result.replaceAll(",,", "");
                            result = result.replaceAll(" ", "");
                            result = result.replaceAll("\\[", "");
                            result = result.replaceAll("\\]", "");
                            result = result.replaceAll("\\}", "");
                            result = result.replaceAll("\\{", "");
                            result = result.replaceAll(",,", " ");
                            result = result.replaceAll(",", "");

                            //System.out.println(result);

                            if (result.contains(field)) {
                                count++;
                                result = result.replaceAll(field + ":", "");
                                // System.out.print(result);
                                movie.add(result);

                            }

                            myList.clear();
                            break;
                        }
                    } catch (Exception e) {
                        j = 0;
                    }


                }
            }

            j = i;

            if(count >= 10){
                break;
            }

        }

        totalResult = count;
    }

}
