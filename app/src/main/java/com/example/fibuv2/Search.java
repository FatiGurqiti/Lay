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

import com.squareup.picasso.Picasso;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Scroll_Relative) ;
        for(int i=10;i>0;i--)
        {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(1100,1100));
            image.setId(i);
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").fit().into(image);
            layout.addView(image);
            setMargins(image,i*1300,100,1,1);
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