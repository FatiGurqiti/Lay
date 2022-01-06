package com.example.fibuv2.api;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieAPITest {
    public static List<String> movieID = new ArrayList<>();
    public static List<String> movieTitle = new ArrayList<>();
    public static List<String> movieYear = new ArrayList<>();
    public static List<String> movieCast = new ArrayList<>();
    public static List<String> movieImageUrl = new ArrayList<>();
    public static int totalResult;


    public static void get(String query) throws IOException {

        String returnString;

            //String query = response.body().string();



            query = query.replaceAll("\"", " ");
            query = query.replaceAll("l :", "title:");
            query = query.replaceAll("s :", "cast:");
            query = query.replaceAll("y :", "year:");
            query = query.replaceAll("imageUrtitle:", "imageUrl:");
            query = query.replaceAll("width :", "width:");
            query = query.replaceAll("id : ", "idOfTheMovie:");


            //System.out.println(query);   //prints the whole body
            text(query);
    }


    private static void text(String text) {
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

                            if (result.contains("idOfTheMovie")) {
                                count++;
                                result = result.replaceAll("idOfTheMovie" + ":", "");
                                // System.out.print(result);
                                movieID.add(result);
                            }

                            else if (result.contains("title")) {
                                count++;
                                result = result.replaceAll("title" + ":", "");
                                // System.out.print(result);
                                movieTitle.add(result);
                            }

                            else if (result.contains("year")) {
                                count++;
                                result = result.replaceAll("year" + ":", "");
                                // System.out.print(result);
                                movieYear.add(result);
                            }

                            else if (result.contains("cast")) {
                                count++;
                                result = result.replaceAll("cast" + ":", "");
                                // System.out.print(result);
                                movieCast.add(result);
                            }

                            else if (result.contains("imageUrl")) {
                                count++;
                                result = result.replaceAll("imageUrl" + ":", "");
                                // System.out.print(result);
                                movieImageUrl.add(result);
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

        }

        totalResult = count;
    }


}
