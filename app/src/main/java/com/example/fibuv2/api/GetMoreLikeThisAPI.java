package com.example.fibuv2.api;

import java.util.ArrayList;
import java.util.List;

public class GetMoreLikeThisAPI {


    public static List<String> morelikethis = new ArrayList<>();

    public static void main(String[] args) {

        getmorelikethiss("[\"/title/tt0167261/\",\"/title/tt0120737/\",\"/title/tt0468569/\",\"/title/tt0110912/\",\"/title/tt1375666/\",\"/title/tt0109830/\",\"/title/tt0080684/\",\"/title/tt0108052/\",\"/title/tt0137523/\",\"/title/tt0111161/\",\"/title/tt0068646/\",\"/title/tt0071562/\",\"/title/tt0076759/\",\"/title/tt0903624/\",\"/title/tt0172495/\"]");

    }

    public static void getmorelikethiss(String querry) {

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


}


