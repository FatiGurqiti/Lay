package com.example.fibuv2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fibuv2.api.MovieAPITest;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Search extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TextView result = (TextView) findViewById(R.id.resultText);



        try {
            MovieAPITest.get("https://imdb8.p.rapidapi.com/auto-complete?q=","return of the king");
            result.setText("There are "+ MovieAPITest.totalResult +" results");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int limit = MovieAPITest.movieYear.size();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Scroll_Relative) ;
        for(int i=0;i<limit;i++)
        {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(1400,500));
            Picasso.get().load(MovieAPITest.movieAll[i][4].trim()).transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(image);
            layout.addView(image);
            setMargins(image,25,i*600,25,1);

            ImageView filter = new ImageView(this);
            filter.setLayoutParams(new ViewGroup.LayoutParams(1400,500));
            Picasso.get().load(R.drawable.black_filter_resource).transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(filter);
            filter.bringToFront();
            layout.addView(filter);
            setMargins(filter,25,i*600,25,1);

            ImageView star = new ImageView(this);
            star.setLayoutParams(new ViewGroup.LayoutParams(100,100));
            Picasso.get().load(R.drawable.star).fit().into(star);
            star.bringToFront();
            layout.addView(star);
            setMargins(star,600,i*600,25,1);

            TextView rate = new TextView(this);
            rate.setText("?/10");
            rate.setTextColor(Color.WHITE);
            rate.setPadding(0,28,0,0);
            rate.setTextSize(22);
            rate.bringToFront();
            layout.addView(rate);
            setMargins(rate,530,i*600,25,1);


            TextView title = new TextView(this);
            title.setText(MovieAPITest.movieAll[i][1] + "\t(" + MovieAPITest.movieAll[i][2] + ")" );
            title.setTextColor(Color.WHITE);
            title.setPadding(25,250,25,0);
            title.setTextSize(22);
            title.bringToFront();
            layout.addView(title);
            setMargins(title,25,i*600,25,1);


            TextView director = new TextView(this);
            director.setText("Director");
            director.setTextColor(Color.WHITE);
            director.setPadding(25,370,50,0);
            director.setTextSize(18);
            director.bringToFront();
            layout.addView(director);
            setMargins(director,25,i*600,25,1);

            TextView cast = new TextView(this);
            cast.setText(MovieAPITest.movieAll[i][3]);
            cast.setTextColor(Color.WHITE);
            cast.setPadding(25,430,50,0);
            cast.setTextSize(16);
            cast.bringToFront();
            layout.addView(cast);
            setMargins(cast,25,i*600,25,1);


        }


    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();



        }
    }

}