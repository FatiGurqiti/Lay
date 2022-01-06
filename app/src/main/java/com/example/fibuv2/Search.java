package com.example.fibuv2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fibuv2.api.GetRateAPITest;
import com.example.fibuv2.api.MoreLikeThisAPITest;
import com.example.fibuv2.api.MovieAPITest;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Search extends AppCompatActivity {

    private String searchContent;


    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Typeface face = getResources().getFont(R.font.davidlibre_regular);   // Font-Family
        Typeface boldface = getResources().getFont(R.font.davidlibre_bold);   // Font-Family

        Bundle extra = getIntent().getExtras();

        TextView result = (TextView) findViewById(R.id.resultText);
        ImageButton coolSearchButtonInSearch = (ImageButton) findViewById(R.id.coolsearchbtnInSearch);
        EditText searchBarInSearch = findViewById(R.id.search_barInSearch);
        ProgressBar progressBar = findViewById(R.id.progressBarInSearch);

        progressBar.setVisibility(View.INVISIBLE);

        coolSearchButtonInSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchBarInSearch.getText().toString();
                if(!search.isEmpty()){
                    searchContent = search;
                Log.d("SearchContent",searchContent);}
            }
        });

        //searchContent = (String) extra.get("searchContent");
        searchContent = "something to be cahnged later";
        Log.d("SearchContent",searchContent);


        try {
            MovieAPITest.get("{\"d\":[{\"i\":{\"height\":1185,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNzA5ZDNlZWMtM2NhNS00NDJjLTk4NDItYTRmY2EwMWZlMTY3XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg\",\"width\":800},\"id\":\"tt0167260\",\"l\":\"The Lord of the Rings: The Return of the King\",\"q\":\"feature\",\"rank\":600,\"s\":\"Elijah Wood, Viggo Mortensen\",\"v\":[{\"i\":{\"height\":480,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BODk1MzkwNTA4N15BMl5BanBnXkFtZTgwOTU1ODY3MjI@._V1_.jpg\",\"width\":640},\"id\":\"vi2073101337\",\"l\":\"The Lord of the Rings Trilogy on Blu-ray\",\"s\":\"2:02\"},{\"i\":{\"height\":1080,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNzkzYzNmOTYtMTE1Yi00Yzg0LWE4OTgtY2QxOGZkNWQ2ODdiXkEyXkFqcGdeQWxpenpp._V1_.jpg\",\"width\":1920},\"id\":\"vi2108539673\",\"l\":\"Does Andy Serkis Know How Many Times He's Played Gollum?\",\"s\":\"3:01\"},{\"i\":{\"height\":1080,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BZjRmMmNmNDEtNTBmYi00NDU4LWIzYmMtNTJjZTFiMGFmZmM0XkEyXkFqcGdeQW1hZGV0aXNj._V1_.jpg\",\"width\":1920},\"id\":\"vi1923201561\",\"l\":\"A Guide to the Films of Peter Jackson\",\"s\":\"1:33\"}],\"vt\":10,\"y\":2003},{\"i\":{\"height\":475,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BMTcxNzUxNjExNV5BMl5BanBnXkFtZTcwNTcwMjgxMQ@@._V1_.jpg\",\"width\":327},\"id\":\"tt0079802\",\"l\":\"The Return of the King\",\"q\":\"TV movie\",\"rank\":19727,\"s\":\"Orson Bean, John Huston\",\"y\":1980},{\"i\":{\"height\":481,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNDBmMzg3MWItNGNlNi00YzA3LWIzNDQtNmViZGViMWI5ZDBmXkEyXkFqcGdeQXVyMjMxMTE2MTQ@._V1_.jpg\",\"width\":338},\"id\":\"tt11502758\",\"l\":\"Return of the King Huang Feihong\",\"q\":\"feature\",\"rank\":352105,\"s\":\"Yin Danni, Chen Lin\",\"y\":2017},{\"i\":{\"height\":400,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BMTIwNDk5ODQtMWZlNi00MTM5LTgwYWUtZmUxMTlhYTZlN2U2XkEyXkFqcGdeQXVyMTk3OTg1OA@@._V1_.jpg\",\"width\":600},\"id\":\"tt2125599\",\"l\":\"Return of the King\",\"q\":\"TV movie\",\"rank\":352718,\"s\":\"Brad Stapleton, Jeff Weinkam\",\"y\":2006},{\"i\":{\"height\":897,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BYzY0ZjJlNmMtMGU3NC00Yjk3LTk0N2ItMDNlMDVhZjA4OTFmXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg\",\"width\":580},\"id\":\"tt0089907\",\"l\":\"The Return of the Living Dead\",\"q\":\"feature\",\"rank\":5132,\"s\":\"Clu Gulager, James Karen\",\"y\":1985},{\"i\":{\"height\":1950,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BNTUyNTNjNzYtMDVhYi00YTEwLWJiMGYtNTcxNTFhZjU4NDFiXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg\",\"width\":1300},\"id\":\"tt0107953\",\"l\":\"Return of the Living Dead III\",\"q\":\"feature\",\"rank\":13287,\"s\":\"Kent McCord, James T. Callahan\",\"y\":1993},{\"i\":{\"height\":2969,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BZGNlMDgzMWYtNDkzOC00ODExLWEzNzYtZTA0NDI0YmIzOWM2XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg\",\"width\":1993},\"id\":\"tt0095990\",\"l\":\"Return of the Living Dead II\",\"q\":\"feature\",\"rank\":14818,\"s\":\"James Karen, Thom Mathews\",\"y\":1988},{\"i\":{\"height\":895,\"imageUrl\":\"https://m.media-amazon.com/images/M/MV5BOTExZmViMGYtNTBiMy00NmJlLThkNmEtOWFiMWVjMmZmOGUxXkEyXkFqcGdeQXVyMTQ2MjQyNDc@._V1_.jpg\",\"width\":600},\"id\":\"tt0095989\",\"l\":\"Return of the Killer Tomatoes!\",\"q\":\"feature\",\"rank\":20201,\"s\":\"Anthony Starke, George Clooney\",\"y\":1988}],\"q\":\"return of the king\",\"v\":1}\n");
            GetRateAPITest.get("{ @type : imdb.api.title.ratings , idOfTheMovie:/title/tt0167261/ , title : The Lord of the Rings: The Two Towers , titleType : movie , year :2002, bottomRank :9261, canRate :true, rating :8.7, ratingCount :1567551, ratingsHistogramcast:{ Females Aged under 18 :{ aggregateRating :8.3, demographic : Females Aged under 18 , histogram :{ 1 :5, 2 :0, 3 :0, 4 :0, 5 :2, 6 :7, 7 :11, 8 :30, 9 :25, 10 :27}, totalRatingcast:107}, IMDb Staff :{ aggregateRating :8.1, demographic : IMDb Staff , histogram :{ 1 :0, 2 :1, 3 :1, 4 :1, 5 :3, 6 :2, 7 :12, 8 :18, 9 :21, 10 :11}, totalRatingcast:70}, US usercast:{ aggregateRating :8.7, demographic : US users , histogram :{ 1 :6607, 2 :1357, 3 :1394, 4 :1937, 5 :3905, 6 :7275, 7 :19043, 8 :42127, 9 :58374, 10 :80615}, totalRatingcast:222634}, Top 1000 votercast:{ aggregateRating :8.5, demographic : Top 1000 voters , histogram :{ 1 :26, 2 :9, 3 :11, 4 :19, 5 :25, 6 :45, 7 :106, 8 :146, 9 :228, 10 :287}, totalRatingcast:902}, Males Aged 30-44 :{ aggregateRating :8.7, demographic : Males Aged 30-44 , histogram :{ 1 :8465, 2 :1981, 3 :2242, 4 :3325, 5 :7383, 6 :16179, 7 :46087, 8 :108012, 9 :147581, 10 :169928}, totalRatingcast:511183}, Males Aged 18-29 :{ aggregateRating :8.9, demographic : Males Aged 18-29 , histogram :{ 1 :1406, 2 :303, 3 :414, 4 :694, 5 :1511, 6 :3935, 7 :14532, 8 :41333, 9 :62672, 10 :79392}, totalRatingcast:206192}, IMDb Usercast:{ aggregateRating :8.7, demographic : IMDb Users , histogram :{ 1 :22710, 2 :6098, 3 :7172, 4 :10159, 5 :22017, 6 :47223, 7 :136939, 8 :326700, 9 :441826, 10 :546707}, totalRatingcast:1567551}, Femalecast:{ aggregateRating :8.7, demographic : Females , histogram :{ 1 :3687, 2 :919, 3 :1137, 4 :1513, 5 :3396, 6 :7009, 7 :18669, 8 :39603, 9 :49344, 10 :74507}, totalRatingcast:199784}, Non-US usercast:{ aggregateRating :8.7, demographic : Non-US users , histogram :{ 1 :8342, 2 :2317, 3 :2759, 4 :3981, 5 :8841, 6 :19811, 7 :56335, 8 :130955, 9 :176113, 10 :206394}, totalRatingcast:615848}, Females Aged 45+ :{ aggregateRating :8.6, demographic : Females Aged 45+ , histogram :{ 1 :1071, 2 :235, 3 :214, 4 :253, 5 :557, 6 :938, 7 :2227, 8 :4377, 9 :6011, 10 :9634}, totalRatingcast:25517}, Aged 45+ :{ aggregateRating :8.5, demographic : Aged 45+ , histogram :{ 1 :4246, 2 :1282, 3 :1313, 4 :1649, 5 :3256, 6 :6185, 7 :14977, 8 :30941, 9 :41203, 10 :49677}, totalRatingcast:154729}, Males Aged 45+ :{ aggregateRating :8.5, demographic : Males Aged 45+ , histogram :{ 1 :3119, 2 :1022, 3 :1070, 4 :1356, 5 :2637, 6 :5114, 7 :12455, 8 :25866, 9 :34389, 10 :39067}, totalRatingcast:126095}, Females Aged 18-29 :{ aggregateRating :8.8, demographic : Females Aged 18-29 , histogram :{ 1 :543, 2 :139, 3 :178, 4 :299, 5 :687, 6 :1528, 7 :4608, 8 :10558, 9 :13178, 10 :19633}, totalRatingcast:51351}, Males Aged under 18 :{ aggregateRating :8.7, demographic : Males Aged under 18 , histogram :{ 1 :6, 2 :3, 3 :1, 4 :5, 5 :6, 6 :22, 7 :31, 8 :112, 9 :139, 10 :153}, totalRatingcast:478}, Malecast:{ aggregateRating :8.7, demographic : Males , histogram :{ 1 :13539, 2 :3452, 3 :3944, 4 :5682, 5 :12258, 6 :26869, 7 :78645, 8 :190725, 9 :266449, 10 :312566}, totalRatingcast:914129}, Aged under 18 :{ aggregateRating :8.6, demographic : Aged under 18 , histogram :{ 1 :12, 2 :3, 3 :1, 4 :5, 5 :10, 6 :32, 7 :51, 8 :153, 9 :190, 10 :208}, totalRatingcast:665}, Females Aged 30-44 :{ aggregateRating :8.6, demographic : Females Aged 30-44 , histogram :{ 1 :1969, 2 :506, 3 :674, 4 :890, 5 :1958, 6 :4078, 7 :10523, 8 :21577, 9 :26467, 10 :40012}, totalRatingcast:108654}, Aged 30-44 :{ aggregateRating :8.7, demographic : Aged 30-44 , histogram :{ 1 :10527, 2 :2515, 3 :2965, 4 :4285, 5 :9469, 6 :20596, 7 :57553, 8 :131678, 9 :176688, 10 :213059}, totalRatingcast:629335}, Aged 18-29 :{ aggregateRating :8.9, demographic : Aged 18-29 , histogram :{ 1 :1973, 2 :456, 3 :610, 4 :1014, 5 :2260, 6 :5609, 7 :19670, 8 :53286, 9 :77780, 10 :101375}, totalRatingcast:264033}}, topRank :15}\\n");
            result.setText("There are "+ MovieAPITest.totalResult +" results");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int limit = MovieAPITest.movieYear.size();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Scroll_Relative) ;
        for(int i=0;i<limit;i++)
        {
            int sizeheight = (int) (getScreenHeight(Search.this)* 0.5);
            int sizewidth = (int) (getScreenWidth(Search.this));


            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(1400,(int) (sizeheight)));
            Picasso.get().load(MovieAPITest.movieImageUrl.get(i).trim())
                    .transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(image);
            layout.addView(image);
            setMargins(image,25, (int) (i*(sizeheight) * 1.2),25,1);


            int finalI = i;
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMovieDetail(MovieAPITest.movieID.get(finalI),MovieAPITest.movieTitle.get(finalI),MovieAPITest.movieYear.get(finalI),MovieAPITest.movieCast.get(finalI),MovieAPITest.movieImageUrl.get(finalI).trim(),"Director");
                }
            });

            ImageView filter = new ImageView(this);
            filter.setLayoutParams(new ViewGroup.LayoutParams(1400, (int) (sizeheight)));
            Picasso.get().load(R.drawable.black_filer_resource).transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(filter);
            filter.bringToFront();
            layout.addView(filter);
            setMargins(filter,25,(int) (i*(sizeheight) * 1.2),25,1);

            ImageView star = new ImageView(this);
            star.setLayoutParams(new ViewGroup.LayoutParams((int) (sizewidth * .10), (int) (sizeheight *.10)));
            Picasso.get().load(R.drawable.star).fit().into(star);
            star.bringToFront();
            layout.addView(star);
            setMargins(star, (int) (sizewidth * 0.8), (int) (i*sizeheight * 1.2) ,1,1);


            TextView rate = new TextView(this);
            rate.setText(GetRateAPITest.movieRate.get(0));
            rate.setTypeface(face);
            rate.setTextColor(Color.WHITE);
            rate.setTextSize(30);
            rate.bringToFront();
            layout.addView(rate);
            setMargins(rate, (int) (sizewidth * 0.65),(int) (i*sizeheight * 1.2) + (int) (sizeheight * .03),25,1);
            Log.d("screensize", String.valueOf(sizeheight * .03));
            rate.setPadding((int) ((int) (sizewidth * 0.1)*0.3),0,0,0);


            TextView title = new TextView(this);
            title.setText(MovieAPITest.movieTitle.get(i) + "\t(" + MovieAPITest.movieYear.get(i) + ")" );
            title.setTypeface(boldface);
            title.setTextColor(Color.WHITE);
            title.setPadding(25,250,25,0);
            title.setTextSize(22);
            title.bringToFront();
            layout.addView(title);
            setMargins(title,25,(int) (i*(sizeheight) * 1.2),25,1);
            title.setPadding(25, (int) (sizeheight *.5),50,0);


            TextView director = new TextView(this);
            director.setText(GetRateAPITest.contentType.get(0));
            director.setTextColor(Color.WHITE);
            director.setTypeface(face);
            director.setPadding(25,370,50,0);
            director.setTextSize(18);
            director.setText("Something");
            director.bringToFront();
            layout.addView(director);
            setMargins(director,25, (int) ((int) (i*(sizeheight) * 1.2)+(sizeheight * 0.25)),25,1);
            director.setPadding(25, (int) (sizeheight *.5),50,0);

            TextView cast = new TextView(this);
            cast.setText(MovieAPITest.movieCast.get(i));
            cast.setTypeface(face);
            cast.setTextColor(Color.WHITE);
            cast.setPadding(25,430,50,0);
            cast.setTextSize(16);
            cast.bringToFront();
            layout.addView(cast);
            setMargins(cast,25, (int) ((int) (i*(sizeheight) * 1.2)+(sizeheight * 0.35)),25,1);
            cast.setPadding(25, (int) (sizeheight *.5),50,0);


        }


    }

    private static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();


        }
    }

    private void openMovieDetail(String movieID,String movieTitle,String movieYear,String movieCast,String moviePhoto,String movieDirector){
        Intent intent = new Intent(Search.this, MovieDetails.class);
        intent.putExtra("movieID",movieID);
        intent.putExtra("movieTitle",movieTitle);
        intent.putExtra("movieYear",movieYear);
        intent.putExtra("movieCast",movieCast);
        intent.putExtra("moviePhoto",moviePhoto);
        intent.putExtra("movieDirector",movieDirector);
        startActivity(intent);
    }

    public int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        return height;
    }

    public int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        return width;
    }

}