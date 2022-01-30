package com.example.fibuv2.ui.dashboard;

import android.content.Intent;
import android.os.Build;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fibuv2.CreateAccount;
import com.example.fibuv2.MainLoggedIn;
import com.example.fibuv2.MovieDetails;
import com.example.fibuv2.R;
import com.example.fibuv2.Search;
import com.example.fibuv2.api.SearchAPI;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private static ArrayList<String> id = new ArrayList<>();
    private static ArrayList<String> img = new ArrayList<>();
    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> rate = new ArrayList<>();

    private ProgressBar pg;
    private ImageView blackfilter;
    private boolean canSearch;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        MainLoggedIn.quotaQuery();

        if(MainLoggedIn.getQuota() > 0 ) canSearch = true;
        else canSearch = false;

        Log.d("QuotaInPreSearch", String.valueOf(MainLoggedIn.getQuota()));
        Log.d("QuotaInPreSearch", String.valueOf(canSearch));

        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


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

        TextView blueRate = root.findViewById(R.id.blueCardRateText);
        TextView yellowRate = root.findViewById(R.id.yellowCardRateText);
        TextView greenRate = root.findViewById(R.id.greenCardRateText);
        TextView redRate = root.findViewById(R.id.redCardRateText);

        pg = root.findViewById(R.id.progressBarInSearch);
        blackfilter = root.findViewById(R.id.homeBlackFilterInSearch);


        Log.d("rateMovie", "IdListinLoop" + MainLoggedIn.getTopRatedId());
        Log.d("rateMovie", "ImgListinLoop" + MainLoggedIn.getTopRatedImg());
        Log.d("rateMovie", "NameListinLoop" + MainLoggedIn.getTopRatedName());
        Log.d("rateMovie", "RateListinLoop" + MainLoggedIn.getTopRatedRate());


        id = MainLoggedIn.getTopRatedId();
        img = MainLoggedIn.getTopRatedImg();
        name = MainLoggedIn.getTopRatedName();
        rate = MainLoggedIn.getTopRatedRate();


        blueText.setText(name.get(0));
        yellowText.setText(name.get(1));
        greenText.setText(name.get(2));
        redText.setText(name.get(3));

        blueRate.setText(rate.get(0));
        yellowRate.setText(rate.get(1));
        greenRate.setText(rate.get(2));
        redRate.setText(rate.get(3));

            blue.setOnClickListener(v -> {
                if(canClick()) {
                    activateLoad();
                    openMovieDetail(id.get(0), img.get(0));
                }
            });

            yellow.setOnClickListener(v -> {
                if(canClick()) {
                    activateLoad();
                    openMovieDetail(id.get(1), img.get(1));
                }
            });

            green.setOnClickListener(v -> {
                if(canClick()) {
                    activateLoad();
                    openMovieDetail(id.get(2), img.get(2));
                }
            });

            red.setOnClickListener(v -> {
                if(canClick()) {
                    activateLoad();
                    openMovieDetail(id.get(3), img.get(3));
                }
            });

            coolsearchBtn.setOnClickListener(v -> {
                if(canClick()) {
                    activateLoad();
                    String searchbarText = searchBar.getText().toString();
                    if (!searchbarText.isEmpty()) {

                        Intent intent = new Intent(getActivity(), Search.class);
                        Log.d("SearchContent", searchbarText);
                        intent.putExtra("coolsearchBtn", searchbarText);
                        startActivity(intent);
                    }
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

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            MainLoggedIn.quotaQuery();

            if(MainLoggedIn.getQuota() > 0 ) canSearch = true;
            else canSearch = false;
        }
        blackfilter.setVisibility(View.INVISIBLE);
        pg.setVisibility(View.INVISIBLE);

    }


    boolean canClick(){
        if(canSearch)
        {return true;}
        else {
            Toast.makeText(getContext(), "Sorry, you are out of your daily quota. Try again tomorrow",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
     }

    private void activateLoad() {
        pg.setVisibility(View.VISIBLE);
        blackfilter.setVisibility(View.VISIBLE);
    }

    private void openMovieDetail(String MovieID, String MoviePhoto) {

        Intent intent = new Intent(getContext(), MovieDetails.class);
        intent.putExtra("MovieID", MovieID);
        intent.putExtra("MoviePhoto", MoviePhoto);
        intent.putExtra("IsSaved", false);
        startActivity(intent);
    }


}