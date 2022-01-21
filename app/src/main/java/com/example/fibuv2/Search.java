package com.example.fibuv2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.example.fibuv2.api.SearchAPI;
import com.example.fibuv2.database.DatabaseHandler;
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

        ImageView notfoundIMAGE = findViewById(R.id.notFoundImage);
        TextView notfoundText = findViewById(R.id.notFoundText);

        progressBar.setVisibility(View.INVISIBLE);

        coolSearchButtonInSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchBarInSearch.getText().toString();
                if (!search.isEmpty()) {
                    searchContent = search;
                    Log.d("SearchContent", searchContent);
                }
            }
        });

        searchContent = "something to change later";
        Log.d("SearchContent", searchContent);


        SearchAPI.autoCompleteAPI("q");

        int limit = SearchAPI.total;

        DatabaseHandler db = new DatabaseHandler(Search.this);
        if (db.getIsLiteMode())  // if lite mode is on
        {
            if (limit > 3) limit = 3;
        }

        //limit =0;
        if (limit > 0) {

            // There are results

            notfoundIMAGE.setVisibility(View.INVISIBLE);
            notfoundText.setVisibility(View.INVISIBLE);

            result.setText("There are " + String.valueOf(SearchAPI.total) + " results");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Scroll_Relative);
        for (int i = 0; i < limit; i++) {
            int sizeheight = (int) (getScreenHeight(Search.this) * 0.5);
            int sizewidth = (int) (getScreenWidth(Search.this));


            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(1400, (int) (sizeheight)));
            Picasso.get().load(SearchAPI.movieImageUrl.get(i).trim())
                    .transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(image);
            layout.addView(image);
            setMargins(image, 25, (int) (i * (sizeheight) * 1.2), 25, 1);


            int finalI = i;
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMovieDetail(SearchAPI.movieID.get(finalI), SearchAPI.movieTitle.get(finalI), null, SearchAPI.movieImageUrl.get(finalI).trim());
                }
            });

            ImageView filter = new ImageView(this);
            filter.setLayoutParams(new ViewGroup.LayoutParams(1400, (int) (sizeheight)));
            Picasso.get().load(R.drawable.black_filer_resource).transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(filter);
            filter.bringToFront();
            layout.addView(filter);
            setMargins(filter, 25, (int) (i * (sizeheight) * 1.2), 25, 1);


            TextView title = new TextView(this);
            title.setText(SearchAPI.movieTitle.get(i));
            title.setTypeface(boldface);
            title.setTextColor(Color.WHITE);
            title.setPadding(25, 250, 25, 0);
            title.setTextSize(22);
            title.bringToFront();
            layout.addView(title);
            setMargins(title, 25, (int) (i * (sizeheight) * 1.2), 25, 1);
            title.setPadding(25, (int) (sizeheight * .5), 50, 0);


            TextView type = new TextView(this);
            type.setText(SearchAPI.movieQ.get(i));
            type.setTextColor(Color.WHITE);
            type.setTypeface(face);
            type.setPadding(25, 370, 50, 0);
            type.setTextSize(18);
            type.bringToFront();
            layout.addView(type);
            setMargins(type, 25, (int) ((int) (i * (sizeheight) * 1.2) + (sizeheight * 0.25)), 25, 1);
            type.setPadding(25, (int) (sizeheight * .5), 50, 0);

            TextView cast = new TextView(this);
            cast.setText(SearchAPI.movieType.get(i));
            cast.setTypeface(face);
            cast.setTextColor(Color.WHITE);
            cast.setPadding(25, 430, 50, 0);
            cast.setTextSize(16);
            cast.bringToFront();
            layout.addView(cast);
            setMargins(cast, 25, (int) ((int) (i * (sizeheight) * 1.2) + (sizeheight * 0.35)), 25, 1);
            cast.setPadding(25, (int) (sizeheight * .5), 50, 0);


        }

    }
        else{
            // There are no results
            notfoundIMAGE.setVisibility(View.VISIBLE);
            notfoundText.setVisibility(View.VISIBLE);
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

    public void setMargins (View v, int l, int t, int r, int b) {
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

    public  int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        return width;
    }

}