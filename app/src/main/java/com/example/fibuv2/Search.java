package com.example.fibuv2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.fibuv2.api.RateAPI;
import com.example.fibuv2.api.SearchAPI;
import com.squareup.picasso.Picasso;

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


        SearchAPI.autoCompleteAPI("q");
        RateAPI.rate("q");
        result.setText("There are "+ String.valueOf(SearchAPI.total) +" results");


        int limit = 8;


        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Scroll_Relative) ;
        for(int i=0;i<limit;i++)
        {
            int sizeheight = (int) (getScreenHeight(Search.this)* 0.5);
            int sizewidth = (int) (getScreenWidth(Search.this));


            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(1400,(int) (sizeheight)));
            Picasso.get().load(SearchAPI.movieImageUrl.get(i).trim())
                    .transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(image);
            layout.addView(image);
            setMargins(image,25, (int) (i*(sizeheight) * 1.2),25,1);


            int finalI = i;
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMovieDetail(SearchAPI.movieID.get(finalI), SearchAPI.movieTitle.get(finalI),RateAPI.contentYear.get(finalI),SearchAPI.movieImageUrl.get(finalI).trim());
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
            rate.setText(RateAPI.contentRate.get(0));
            rate.setTypeface(face);
            rate.setTextColor(Color.WHITE);
            rate.setTextSize(30);
            rate.bringToFront();
            layout.addView(rate);
            setMargins(rate, (int) (sizewidth * 0.65),(int) (i*sizeheight * 1.2) + (int) (sizeheight * .03),25,1);
            Log.d("screensize", String.valueOf(sizeheight * .03));
            rate.setPadding((int) ((int) (sizewidth * 0.1)*0.3),0,0,0);


            TextView title = new TextView(this);
            title.setText(SearchAPI.movieTitle.get(i) + "\t(" + RateAPI.contentYear.get(0) + ")" );
            title.setTypeface(boldface);
            title.setTextColor(Color.WHITE);
            title.setPadding(25,250,25,0);
            title.setTextSize(22);
            title.bringToFront();
            layout.addView(title);
            setMargins(title,25,(int) (i*(sizeheight) * 1.2),25,1);
            title.setPadding(25, (int) (sizeheight *.5),50,0);


            TextView type = new TextView(this);
            type.setText(RateAPI.contentType.get(0));
            type.setTextColor(Color.WHITE);
            type.setTypeface(face);
            type.setPadding(25,370,50,0);
            type.setTextSize(18);
            type.setText(RateAPI.contentType.get(0));
            type.bringToFront();
            layout.addView(type);
            setMargins(type,25, (int) ((int) (i*(sizeheight) * 1.2)+(sizeheight * 0.25)),25,1);
            type.setPadding(25, (int) (sizeheight *.5),50,0);

            TextView cast = new TextView(this);
            cast.setText(SearchAPI.movieType.get(i));
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

    private void openMovieDetail(String movieID,String movieTitle,String movieYear,String moviePhoto){
        Intent intent = new Intent(Search.this, MovieDetails.class);
        intent.putExtra("movieID",movieID);
        intent.putExtra("movieTitle",movieTitle);
        intent.putExtra("movieYear",movieYear);
        intent.putExtra("moviePhoto",moviePhoto);
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