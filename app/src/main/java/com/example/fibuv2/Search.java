package com.example.fibuv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fibuv2.api.MovieAPITest;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        try {
            MovieAPITest.get("https://imdb8.p.rapidapi.com/auto-complete?q=","return of the king");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int limit = MovieAPITest.movieYear.size();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Scroll_Relative) ;
        for(int i=0;i<limit;i++)
        {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(1100,1100));
            image.setId(i);
            Picasso.get().load(MovieAPITest.movieAll[i][4].trim()).resize(1100,1100).centerCrop(0).into(image);
            Log.d("url", MovieAPITest.movieAll[i][4]);
            layout.addView(image);
            setMargins(image,i*1300,200,1,1);
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