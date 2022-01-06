package com.example.fibuv2.api;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetRateAPITest {
    public static List<String> contentType = new ArrayList<>();
    public static List<String> movieRate = new ArrayList<>();


    public static void get(String query) throws IOException {

        String returnString;

            query = query.replaceAll("titleType : ", "contentType:");
            query = query.replaceAll("rating :", "rating:");


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


                            if (result.contains("contentType")) {
                                count++;
                                result = result.replaceAll("contentType" + ":", "");
                                // System.out.print(result);
                                contentType.add(result);
                            }else if (result.contains("rating")) {
                                count++;
                                result = result.replaceAll("rating" + ":", "");
                                // System.out.print(result);
                                movieRate.add(result);
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
