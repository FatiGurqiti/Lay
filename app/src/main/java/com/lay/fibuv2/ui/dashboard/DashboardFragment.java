package com.lay.fibuv2.ui.dashboard;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lay.fibuv2.MainLoggedIn;
import com.lay.fibuv2.Search.SearchViewModel;
import com.lay.fibuv2.api.SearchAPI;
import com.lay.fibuv2.movieDetails.MovieDetails;
import com.lay.fibuv2.R;
import com.lay.fibuv2.Search.Search;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private static ArrayList<String> id = new ArrayList<>();
    private static ArrayList<String> img = new ArrayList<>();
    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> rate = new ArrayList<>();
    private static String searchbarText;

    private ProgressBar pg;
    private ImageView blackfilter;
    private static int quota;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        quota = MainLoggedIn.getQuota();
        MainLoggedIn.quotaQuery();

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

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

        id = MainLoggedIn.getTopRatedId();
        img = MainLoggedIn.getTopRatedImg();
        name = MainLoggedIn.getTopRatedName();
        rate = MainLoggedIn.getTopRatedRate();

        if (name.size() > 0) {
            blueText.setText(name.get(0));
            yellowText.setText(name.get(1));
            greenText.setText(name.get(2));
            redText.setText(name.get(3));

            blueRate.setText(rate.get(0));
            yellowRate.setText(rate.get(1));
            greenRate.setText(rate.get(2));
            redRate.setText(rate.get(3));
        }

        blue.setOnClickListener(v -> {
            activateLoad();
            openMovieDetail(id.get(0), img.get(0));
        });

        yellow.setOnClickListener(v -> {
            activateLoad();
            openMovieDetail(id.get(1), img.get(1));

        });

        green.setOnClickListener(v -> {
            activateLoad();
            openMovieDetail(id.get(2), img.get(2));

        });

        red.setOnClickListener(v -> {
            activateLoad();
            openMovieDetail(id.get(3), img.get(3));

        });

        coolsearchBtn.setOnClickListener(v -> {
                searchbarText = searchBar.getText().toString();
                if (!searchbarText.isEmpty()) {
                    activateLoad();
                    SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
                    searchViewModel.prepareResults(searchbarText);
                    Intent intent = new Intent(getActivity(), Search.class);
                    intent.putExtra("coolsearchBtn", searchbarText);
                    startActivity(intent);

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
        blackfilter.setVisibility(View.INVISIBLE);
        pg.setVisibility(View.INVISIBLE);

        SearchAPI.movieType.clear();
        SearchAPI.movieTitle.clear();
        SearchAPI.movieID.clear();
        SearchAPI.movieQ.clear();
        SearchAPI.movieType.clear();
        SearchAPI.movieImageUrl.clear();
        SearchAPI.splittedJson.clear();
    }

    private void activateLoad() {
        pg.setVisibility(View.VISIBLE);
        blackfilter.setVisibility(View.VISIBLE);
    }

    private void openMovieDetail(String MovieID, String MoviePhoto) {
        Intent intent = new Intent(getContext(), MovieDetails.class);
        intent.putExtra("MovieID", MovieID);
        intent.putExtra("MoviePhoto", MoviePhoto);
        startActivity(intent);
    }

    public static int getQuota() {
        return quota;
    }

    public static ArrayList<String> getid() {
        return id;
    }

    public static ArrayList<String> getimg() {
        return img;
    }

    public static ArrayList<String> getname() {
        return name;
    }

    public static ArrayList<String> getrate() {
        return rate;
    }

    public static String getSearchbarText() {
        return searchbarText;
    }

}