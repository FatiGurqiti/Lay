package com.example.fibuv2.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fibuv2.MovieDetails;
import com.example.fibuv2.R;
import com.example.fibuv2.api.MovieAPI;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView movietitle;
    private TextView movieyear;
    private TextView moviedirector;
    private TextView moviecast;
    private ProgressBar pg;
    private HashMap<String,String> movie = new HashMap<String,String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);

        ImageView notfoundIMAGE = root.findViewById(R.id.notFoundImage);
        TextView notfoundText = root.findViewById(R.id.notFoundText);

        ImageButton coolsearchBtn = root.findViewById(R.id.coolsearchbtn);
        EditText searchBar = root.findViewById(R.id.search_bar);
        pg = root.findViewById(R.id.progressBar2);

        CardView blue = root.findViewById(R.id.blueCard);
        CardView yellow = root.findViewById(R.id.yellowCard);
        CardView green = root.findViewById(R.id.greenCard);
        CardView red = root.findViewById(R.id.redCard);
        CardView moviecard = root.findViewById(R.id.movieCard);

        TextView blueText = root.findViewById(R.id.blueCardText);
        TextView yellowText = root.findViewById(R.id.yellowCardText);
        TextView greenText = root.findViewById(R.id.greenCardText);
        TextView redText = root.findViewById(R.id.redCardText);

        TextView resultQuantity = root.findViewById(R.id.resultquntity);


        movietitle = root.findViewById(R.id.movieTitle);
        moviedirector = root.findViewById(R.id.movieDirector);
        moviecast = root.findViewById(R.id.movieCast);

        notfoundIMAGE.setVisibility(View.INVISIBLE);
        notfoundText.setVisibility(View.INVISIBLE);
        pg.setVisibility(View.INVISIBLE);
        resultQuantity.setVisibility(View.INVISIBLE);

        moviecard.setVisibility(View.GONE);

        ImageView thumbnail = root.findViewById(R.id.movieThumbnail);





        coolsearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pg.setVisibility(View.VISIBLE);
                String searchbarText=searchBar.getText().toString();
                if(!searchbarText.isEmpty()){


                    blue.setVisibility(View.INVISIBLE);
                    yellow.setVisibility(View.INVISIBLE);
                    green.setVisibility(View.INVISIBLE);
                    red.setVisibility(View.INVISIBLE);


                        String details = null;
                        try {
                            movie.put("imageUrl",MovieAPI.get("https://imdb8.p.rapidapi.com/auto-complete?q=",searchbarText,"imageUrl",0));
                            movie.put("idOfTheMovie",MovieAPI.get("https://imdb8.p.rapidapi.com/auto-complete?q=",searchbarText,"idOfTheMovie",0));
                            movie.put("title",MovieAPI.get("https://imdb8.p.rapidapi.com/auto-complete?q=",searchbarText,"title",0));
                            movie.put("year",MovieAPI.get("https://imdb8.p.rapidapi.com/auto-complete?q=",searchbarText,"year",0));
                            movie.put("cast",MovieAPI.get("https://imdb8.p.rapidapi.com/auto-complete?q=",searchbarText,"cast",0));
                            movie.put("height",MovieAPI.get("https://imdb8.p.rapidapi.com/auto-complete?q=",searchbarText,"height",0));
                            movie.put("width",MovieAPI.get("https://imdb8.p.rapidapi.com/auto-complete?q=",searchbarText,"width",0));


                            details = movie.get("title") + "\n" +movie.get("year") + "\n" + movie.get("cast");
                                    Log.d("Search Result: ", details);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("Api status:", e.toString());
                            notfoundIMAGE.setVisibility(View.VISIBLE);
                            notfoundText.setVisibility(View.VISIBLE);
                        }
                        if(!details.equals("Error"))
                        {
                            //Movie exits
                            resultQuantity.setText("There are " + String.valueOf(MovieAPI.totalResult) + " results");
                            resultQuantity.setVisibility(View.VISIBLE);

                            Picasso.get().load(movie.get("imageUrl").trim()).into(thumbnail);
                            movietitle.setText(movie.get("title") + "\n" + movie.get("year"));
                            moviedirector.setText("working on the director");
                            moviecast.setText(movie.get("cast"));

                            moviecard.setVisibility(View.VISIBLE);
//                            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,500);
//                            moviecard.setLayoutParams(parms);

                        }
                        else{
                            //Movie doesn't exist
                            notfoundIMAGE.setVisibility(View.VISIBLE);
                            notfoundText.setVisibility(View.VISIBLE);
                       }


                    pg.setVisibility(View.GONE);
                }


            }
        });




        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovieDetail("The Lord of the Rings");
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovieDetail("Avengers");
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovieDetail("Fight Club");
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovieDetail("Dune");
            }
        });

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        return root;
    }

    private void openMovieDetail(String movieName){
        Intent intent = new Intent(getActivity(), MovieDetails.class);
        intent.putExtra("movieName",movieName);
        startActivity(intent);
    }


}