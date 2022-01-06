package com.example.fibuv2.api;


import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoreLikeThisAPITest {
    public static List<String> suggestion = new ArrayList<>();



    public static void get(String query) throws IOException {

            query = query.replaceAll("/title/", "title");
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
                            result = result.replaceAll(" ", "");
                            result = result.replaceAll("\\[", "");
                            result = result.replaceAll("\\]", "");
                            result = result.replaceAll(",", "");

                            //System.out.println(result);


                            if (result.contains("title")) {
                                count++;
                                result = result.replaceAll("contentType", "");
                                // System.out.print(result);
                                suggestion.add(result);
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

    }


}
