package com.lay.fibuv2;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lay.fibuv2.R;
import com.lay.fibuv2.api.SearchAPI;
import com.lay.fibuv2.database.DatabaseHandler;
import com.squareup.picasso.Picasso;

public class Search extends AppCompatActivity {

    private static String searchContent;
    private TextView SecondTextReference;

    private ImageView blackfilter;
    private  ProgressBar progressBar;

    private boolean canSearch;



    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        MainLoggedIn.lowerQuota();
        MainLoggedIn.quotaQuery();

        if(MainLoggedIn.getQuota() > 0 ) canSearch = true;
        else canSearch = false;

        Log.d("QuotaInPreSearch", String.valueOf(MainLoggedIn.getQuota()));
        Log.d("QuotaInPreSearch", String.valueOf(canSearch));

        Typeface face = getResources().getFont(R.font.plusjakartatextregular);   // Font-Family
        Typeface boldface = getResources().getFont(R.font.plusjakartatexbold);   // Font-Family

        Bundle extra = getIntent().getExtras();

        TextView result = findViewById(R.id.resultText);
        progressBar = findViewById(R.id.progressBarInSearch2);
        blackfilter = findViewById(R.id.BlackFilterInSearch2);

        ImageView notfoundIMAGE = findViewById(R.id.notFoundImage);
        TextView notfoundText = findViewById(R.id.notFoundText);
        SecondTextReference = findViewById(R.id.secondTextReference);

        progressBar.setVisibility(View.INVISIBLE);

        searchContent = extra.getString("coolsearchBtn");

        SearchAPI.autoCompleteAPI(searchContent);

        Log.d("Search Value",searchContent);
        int limit = SearchAPI.total;
        Log.d("Limitin", String.valueOf(limit));



        DatabaseHandler db = new DatabaseHandler(Search.this);
        if (db.getIsLiteMode())  // if lite mode is on
        {
            if (limit > 3) limit = 3;
        }

        if (limit > 0) {

            // There are results

            notfoundIMAGE.setVisibility(View.INVISIBLE);
            notfoundText.setVisibility(View.INVISIBLE);


        RelativeLayout layout =  findViewById(R.id.Scroll_Relative);
        int j = -1;
        for (int i = 0; i < limit; i++) {
            int sizeheight = (int) (getScreenHeight(Search.this) * 0.5);

            if (SearchAPI.movieQ.get(i) != null) { //Prevents loading useless contents like: trailer, review, etc..
                j++;


            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(1400, (int) (sizeheight)));
            image.setOutlineAmbientShadowColor(Color.parseColor("#FFFFFF"));
            image.setOutlineSpotShadowColor(Color.parseColor("#FFFFFF"));
            Picasso.get().load(SearchAPI.movieImageUrl.get(i).trim())
                    .transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(image);
            layout.addView(image);
            setMargins(image, 25, (int) (j * (sizeheight) * 1.2), 25, 1);


            int finalI = i;
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(canClick()){ //Don't open if out of quota
                    progressBar.setVisibility(View.VISIBLE);
                    blackfilter.setVisibility(View.VISIBLE);
                    openMovieDetail(SearchAPI.movieID.get(finalI), SearchAPI.movieImageUrl.get(finalI).trim());
                    }
                }
            });


            ImageView filter = new ImageView(this);
            filter.setLayoutParams(new ViewGroup.LayoutParams(1400, (int) (sizeheight)));
            Picasso.get().load(R.drawable.black_filer_resource).transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(filter);
            filter.bringToFront();
            layout.addView(filter);
            setMargins(filter, 25, (int) (j * (sizeheight) * 1.2), 25, 50);


            TextView title = new TextView(this);
            title.setText(SearchAPI.movieTitle.get(i));
            title.setTypeface(boldface);
            title.setTextColor(Color.WHITE);
            title.setPadding(25, 250, 25, 0);
            title.setTextSize(22);
            title.bringToFront();
            layout.addView(title);
            setMargins(title, 25, (int) (j * (sizeheight) * 1.2), 25, 1);
            title.setPadding(25, (int) (sizeheight * .5), 50, 0);


            TextView type = new TextView(this);
            type.setText(SearchAPI.movieQ.get(i));
            type.setTextColor(Color.WHITE);
            type.setTypeface(face);
            type.setPadding(25, 370, 50, 0);
            type.setTextSize(18);
            type.bringToFront();
            layout.addView(type);
            setMargins(type, 25, (int) ((int) (j * (sizeheight) * 1.2) + (sizeheight * 0.25)), 25, 1);
            type.setPadding(25, (int) (sizeheight * .5), 50, 0);

            TextView cast = new TextView(this);
            cast.setText(SearchAPI.movieType.get(i));
            cast.setTypeface(face);
            cast.setTextColor(Color.WHITE);
            cast.setPadding(25, 430, 50, 0);
            cast.setTextSize(16);
            cast.bringToFront();
            layout.addView(cast);
            setMargins(cast, 25, (int) ((int) (j * (sizeheight) * 1.2) + (sizeheight * 0.35)), 25, 1);
            cast.setPadding(25, (int) (sizeheight * .5), 50, 0);

        }

        }

        int totalResult = j+1;
            result.setText("There are " + totalResult  + " results");

    }
        else{
            // There are no results
            notfoundIMAGE.setVisibility(View.VISIBLE);
            notfoundText.setVisibility(View.VISIBLE);
            SecondTextReference.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            MainLoggedIn.quotaQuery();
        }
        if(MainLoggedIn.getQuota() > 0 ) canSearch = true;
        else canSearch = false;

        progressBar.setVisibility(View.INVISIBLE);
        blackfilter.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SearchAPI.total = 0;
        SearchAPI.movieImageUrl.clear();
        SearchAPI.movieQ.clear();
        SearchAPI.movieTitle.clear();
        SearchAPI.movieID.clear();
        SearchAPI.movieType.clear();
        SearchAPI.splittedJson.clear();
    }

    private boolean canClick() {
        if(canSearch)
        {return true;}
        else {
            Toast.makeText(Search.this, "Sorry, you are out of your daily quota.\nPlease try again tomorrow.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void openMovieDetail(String MovieID, String MoviePhoto){
        Intent intent = new Intent(Search.this, MovieDetails.class);
        intent.putExtra("MovieID",MovieID);
        intent.putExtra("MoviePhoto",MoviePhoto);
        intent.putExtra("IsSaved",false);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();


        }
    }

    public int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        return height;
    }


public static String getSearchContent(){return searchContent;}
}