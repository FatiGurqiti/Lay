package com.fdev.lay.presentation.ui.search_fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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

import com.fdev.lay.MainLoggedIn;
import com.fdev.lay.presentation.ui.Search.SearchViewModel;
import com.fdev.lay.api.SearchAPI;
import com.fdev.lay.movieDetails.MovieDetails;
import com.fdev.lay.R;
import com.fdev.lay.presentation.ui.Search.Search;
import com.fdev.lay.movieDetails.MovieDetailsViewModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchFragmentViewModel dashboardViewModel;

    private static ArrayList<String> id = new ArrayList<>();
    private static ArrayList<String> img = new ArrayList<>();
    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> rate = new ArrayList<>();
    private static String searchbarText;

    private EditText searchBar;
    private ProgressBar pg;
    private ImageView blackfilter;
    private static int quota;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_fragment, container, false);

        quota = MainLoggedIn.getQuota();
        MainLoggedIn.quotaQuery();

        dashboardViewModel = new ViewModelProvider(this).get(SearchFragmentViewModel.class);
        searchBar = root.findViewById(R.id.search_bar);

        ImageButton coolsearchBtn = root.findViewById(R.id.coolsearchbtn);

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
            openMovieDetail(id.get(0), img.get(0),name.get(0));
        });

        yellow.setOnClickListener(v -> {
            activateLoad();
            openMovieDetail(id.get(1), img.get(1),name.get(0));

        });

        green.setOnClickListener(v -> {
            activateLoad();
            openMovieDetail(id.get(2), img.get(2),name.get(0));

        });

        red.setOnClickListener(v -> {
            activateLoad();
            openMovieDetail(id.get(3), img.get(3),name.get(0));

        });

        coolsearchBtn.setOnClickListener(v -> {
            search();
        });

        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId & EditorInfo.IME_MASK_ACTION) != 0) {
                search();
                return true;
            } else
                return false;
        });

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });
        return root;
    }

    private void search(){
        searchbarText = searchBar.getText().toString();
        if (!searchbarText.isEmpty()) {
            activateLoad();
            SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
            searchViewModel.prepareResults(searchbarText);
            Intent intent = new Intent(getActivity(), Search.class);
            intent.putExtra("coolsearchBtn", searchbarText);
            startActivity(intent);
        }
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

    private void openMovieDetail(String MovieID, String MoviePhoto,String MovieTitle) {
        MovieDetailsViewModel movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.prepareDetails(MovieID, MoviePhoto,MovieTitle,requireActivity());
        Intent intent = new Intent(requireActivity(), MovieDetails.class);
        intent.putExtra("MovieID", MovieID);
        intent.putExtra("MoviePhoto", MoviePhoto);
        intent.putExtra("MovieTitle", MovieTitle);
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