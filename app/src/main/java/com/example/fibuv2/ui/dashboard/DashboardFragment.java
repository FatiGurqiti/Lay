package com.example.fibuv2.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fibuv2.Admin;
import com.example.fibuv2.MovieDetails;
import com.example.fibuv2.R;
import com.example.fibuv2.api.MovieAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean ifmoviexists = true;
    private TextView moviedetail;

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
        ProgressBar progressBar = root.findViewById(R.id.searchProgressBar);

        CardView blue = root.findViewById(R.id.blueCard);
        CardView yellow = root.findViewById(R.id.yellowCard);
        CardView green = root.findViewById(R.id.greenCard);
        CardView red = root.findViewById(R.id.redCard);

        TextView blueText = root.findViewById(R.id.blueCardText);
        TextView yellowText = root.findViewById(R.id.yellowCardText);
        TextView greenText = root.findViewById(R.id.greenCardText);
        TextView redText = root.findViewById(R.id.redCardText);


        moviedetail = root.findViewById(R.id.movieDetails);



        notfoundIMAGE.setVisibility(View.INVISIBLE);
        notfoundText.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);


        coolsearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!searchBar.getText().toString().isEmpty()){

                    progressBar.setVisibility(View.VISIBLE);


                    blue.setVisibility(View.INVISIBLE);
                    yellow.setVisibility(View.INVISIBLE);
                    green.setVisibility(View.INVISIBLE);
                    red.setVisibility(View.INVISIBLE);

                    moviedetail.setVisibility(View.VISIBLE);
                    moviedetail.setText("hello");



                        String details = null;
                        try {
                            details= (MovieAPI.get("https://imdb8.p.rapidapi.com/auto-complete?q=",searchBar.getText().toString(),"title",0)


                            );

                            Log.d("Search Result: ", details);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("Api status:", e.toString());
                        }
                        if(!details.equals("Error") || !details.isEmpty())
                        {
                            moviedetail.setText(details);
                        }
                        else{
                            //Movie does not exist
                            notfoundIMAGE.setVisibility(View.VISIBLE);
                            notfoundText.setVisibility(View.VISIBLE);
                       }


                progressBar.setVisibility(View.INVISIBLE);
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