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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fibuv2.MovieDetails;
import com.example.fibuv2.R;
import com.example.fibuv2.Search;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar pg;
    private HashMap<String,String> movie = new HashMap<String,String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);



        ImageButton coolsearchBtn = root.findViewById(R.id.coolsearchbtn);
        EditText searchBar = root.findViewById(R.id.search_bar);

        CardView blue = root.findViewById(R.id.blueCard);
        CardView yellow = root.findViewById(R.id.yellowCard);
        CardView green = root.findViewById(R.id.greenCard);
        CardView red = root.findViewById(R.id.redCard);

        TextView blueText = root.findViewById(R.id.blueCardText);
        TextView yellowText = root.findViewById(R.id.yellowCardText);
        TextView greenText = root.findViewById(R.id.greenCardText);
        TextView redText = root.findViewById(R.id.redCardText);




        ProgressBar pg = root.findViewById(R.id.progressBarInMainDashBorad);
        pg.setVisibility(View.INVISIBLE);




        coolsearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pg.setVisibility(View.VISIBLE);

                String searchbarText=searchBar.getText().toString();



                if(!searchbarText.isEmpty()){

                    Intent intent = new Intent(getActivity(), Search.class);

                    Log.d("SearchContent",searchbarText);
                    intent.putExtra("searchContent",searchbarText);
                    startActivity(intent);
                }

                pg.setVisibility(View.INVISIBLE);
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