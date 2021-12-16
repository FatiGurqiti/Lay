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
    public static String[][] movieAll = new String[10][5];
    public static int totalResult;

    public static void main(String[] args) {

        try {
            get("https://imdb8.p.rapidapi.com/auto-complete?q=","return of the king");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void get(String url,String querry) throws IOException {
        String returnString;

            //String query = response.body().string();

            String query = "{\"d\":[{\"i\":{\"height\":1185,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNzA5ZDNlZWMtM2NhNS00NDJjLTk4NDItYTRmY2EwMWZlMTY3XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg\",\"width\":800},\"id\":\"tt0167260\",\"l\":\"The Lord of the Rings: The Return of the King\",\"q\":\"feature\",\"rank\":600,\"s\":\"Elijah Wood, Viggo Mortensen\",\"v\":[{\"i\":{\"height\":480,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BODk1MzkwNTA4N15BMl5BanBnXkFtZTgwOTU1ODY3MjI@._V1_.jpg\",\"width\":640},\"id\":\"vi2073101337\",\"l\":\"The Lord of the Rings Trilogy on Blu-ray\",\"s\":\"2:02\"},{\"i\":{\"height\":1080,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNzkzYzNmOTYtMTE1Yi00Yzg0LWE4OTgtY2QxOGZkNWQ2ODdiXkEyXkFqcGdeQWxpenpp._V1_.jpg\",\"width\":1920},\"id\":\"vi2108539673\",\"l\":\"Does Andy Serkis Know How Many Times He's Played Gollum?\",\"s\":\"3:01\"},{\"i\":{\"height\":1080,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BZjRmMmNmNDEtNTBmYi00NDU4LWIzYmMtNTJjZTFiMGFmZmM0XkEyXkFqcGdeQW1hZGV0aXNj._V1_.jpg\",\"width\":1920},\"id\":\"vi1923201561\",\"l\":\"A Guide to the Films of Peter Jackson\",\"s\":\"1:33\"}],\"vt\":10,\"y\":2003},{\"i\":{\"height\":475,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BMTcxNzUxNjExNV5BMl5BanBnXkFtZTcwNTcwMjgxMQ@@._V1_.jpg\",\"width\":327},\"id\":\"tt0079802\",\"l\":\"The Return of the King\",\"q\":\"TV movie\",\"rank\":19727,\"s\":\"Orson Bean, John Huston\",\"y\":1980},{\"i\":{\"height\":481,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNDBmMzg3MWItNGNlNi00YzA3LWIzNDQtNmViZGViMWI5ZDBmXkEyXkFqcGdeQXVyMjMxMTE2MTQ@._V1_.jpg\",\"width\":338},\"id\":\"tt11502758\",\"l\":\"Return of the King Huang Feihong\",\"q\":\"feature\",\"rank\":352105,\"s\":\"Yin Danni, Chen Lin\",\"y\":2017},{\"i\":{\"height\":400,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BMTIwNDk5ODQtMWZlNi00MTM5LTgwYWUtZmUxMTlhYTZlN2U2XkEyXkFqcGdeQXVyMTk3OTg1OA@@._V1_.jpg\",\"width\":600},\"id\":\"tt2125599\",\"l\":\"Return of the King\",\"q\":\"TV movie\",\"rank\":352718,\"s\":\"Brad Stapleton, Jeff Weinkam\",\"y\":2006},{\"i\":{\"height\":897,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BYzY0ZjJlNmMtMGU3NC00Yjk3LTk0N2ItMDNlMDVhZjA4OTFmXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg\",\"width\":580},\"id\":\"tt0089907\",\"l\":\"The Return of the Living Dead\",\"q\":\"feature\",\"rank\":5132,\"s\":\"Clu Gulager, James Karen\",\"y\":1985},{\"i\":{\"height\":1950,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNTUyNTNjNzYtMDVhYi00YTEwLWJiMGYtNTcxNTFhZjU4NDFiXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg\",\"width\":1300},\"id\":\"tt0107953\",\"l\":\"Return of the Living Dead III\",\"q\":\"feature\",\"rank\":13287,\"s\":\"Kent McCord, James T. Callahan\",\"y\":1993},{\"i\":{\"height\":2969,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BZGNlMDgzMWYtNDkzOC00ODExLWEzNzYtZTA0NDI0YmIzOWM2XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg\",\"width\":1993},\"id\":\"tt0095990\",\"l\":\"Return of the Living Dead II\",\"q\":\"feature\",\"rank\":14818,\"s\":\"James Karen, Thom Mathews\",\"y\":1988},{\"i\":{\"height\":895,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BOTExZmViMGYtNTBiMy00NmJlLThkNmEtOWFiMWVjMmZmOGUxXkEyXkFqcGdeQXVyMTQ2MjQyNDc@._V1_.jpg\",\"width\":600},\"id\":\"tt0095989\",\"l\":\"Return of the Killer Tomatoes!\",\"q\":\"feature\",\"rank\":20201,\"s\":\"Anthony Starke, George Clooney\",\"y\":1988}],\"q\":\"return of the king\",\"v\":1}\n";


            query = query.replaceAll("\"", " ");
            query = query.replaceAll("l :", "title:");
            query = query.replaceAll("s :", "cast:");
            query = query.replaceAll("y :", "year:");
            query = query.replaceAll("imageUrtitle:", "imageUrl:");
            query = query.replaceAll("width :", "width:");
            query = query.replaceAll("id : ", "idOfTheMovie:");

            //System.out.println(query);   //prints the whole body
            text(query); // prints the spesific field

            for (int i =0;i< movieYear.size();i++){
                movieAll[i][0]= movieID.get(i);
                movieAll[i][1]= movieTitle.get(i);
                movieAll[i][2]= movieYear.get(i);
                movieAll[i][3]= movieCast.get(i);
                movieAll[i][4]= movieImageUrl.get(i);

            }



    }


    private static void text(String text) {
        char[] arr = text.toCharArray();
        int count = 0;
        String result;
        List<String> myList = new ArrayList<>();
        int j;
        for (int i = 0; i < text.length(); i++) {
            if (arr[i] == ',') {
                j = i;
                while (true) {
                    myList.add(String.valueOf(arr[j]));
                    j++;
                    try {
                        if (arr[j + 1] == ',') {

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
