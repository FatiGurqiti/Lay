package com.example.fibuv2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fibuv2.MainLoggedIn;
import com.example.fibuv2.MovieDetails;
import com.example.fibuv2.R;
import com.example.fibuv2.RoundedTransformation;
import com.example.fibuv2.Search;
import com.example.fibuv2.api.SearchAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> img = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> year = new ArrayList<>();
    private ArrayList<String> duration = new ArrayList<>();
    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<String> firstText = new ArrayList<>();
    private ArrayList<String> secondText = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Typeface face = getResources().getFont(R.font.plusjakartatextregular);   // Font-Family
        Typeface boldface = getResources().getFont(R.font.plusjakartatexbold);  // Font-Family

        TextView WelcomeText = (TextView) root.findViewById(R.id.welcomeText);
        WelcomeText.setText(" \t Welcome "+ MainLoggedIn.getUsername() + ", here's your favourite list");

        String TAG = "HomeDataStatus";
        FirebaseFirestore Firedb = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = Firedb.collection("MovieLists").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        id = (ArrayList<String>) document.get("id");
                        img = (ArrayList<String>) document.get("img");
                        title = (ArrayList<String>) document.get("title");
                        type = (ArrayList<String>) document.get("type");
                        year = (ArrayList<String>) document.get("year");
                        firstText = (ArrayList<String>) document.get("firstText");
                        secondText = (ArrayList<String>) document.get("secondText");
                        duration = (ArrayList<String>) document.get("duration");

                        Log.d("HomeDataStatus2", String.valueOf(id.size()));

                        int limit = id.size();
                        if (limit > 0) {

                            // There are results

                            RelativeLayout layout = (RelativeLayout) root.findViewById(R.id.Scroll_Relative);
                            for (int i = 0; i < limit; i++) {
                                int sizeheight = (int) (getScreenHeight(getContext()) * 0.5);
                                int sizewidth = (int) (getScreenWidth(getContext()));


                                ImageView image = new ImageView(getContext());
                                image.setLayoutParams(new ViewGroup.LayoutParams(1400, (int) ((int) (sizeheight) * .6)));
                                Picasso.get().load(img.get(i).trim())
                                        .transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(image);
                                layout.addView(image);
                                Search.setMargins(image, 25, (int) (i * (sizeheight) * .75 ), 25, 1);


                                int finalI = i;
                                image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        openMovieDetail(
                                                id.get(finalI),
                                                img.get(finalI),
                                                title.get(finalI),
                                                type.get(finalI),
                                                year.get(finalI),
                                                firstText.get(finalI),
                                                secondText.get(finalI),
                                                duration.get(finalI));
                                    }
                                });

                                ImageView filter = new ImageView(getContext());
                                filter.setLayoutParams(new ViewGroup.LayoutParams(1400, (int) ((int) (sizeheight) * .6)));
                                Picasso.get().load(R.drawable.black_filer_resource).transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(filter);
                                filter.bringToFront();
                                layout.addView(filter);
                                Search.setMargins(filter, 25, (int) (i * (sizeheight) * .75), 25, 1);


                                TextView titleText = new TextView(getContext());
                                titleText.setText(title.get(i));
                                titleText.setTypeface(boldface);
                                titleText.setTextColor(Color.WHITE);
                                titleText.setPadding(25, 250, 25, 0);
                                titleText.setTextSize(22);
                                titleText.bringToFront();
                                layout.addView(titleText);
                                Search.setMargins(titleText, 25, (int) (i * (sizeheight) * .75), 25, 1);
                                titleText.setPadding(25, 200, 50, 0);


                            }

                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        return root;
    }

    private void openMovieDetail(String MovieID, String MoviePhoto, String MovieTitle, String MovieType, String MovieYear, String MovieFirstText, String MovieSecondText, String MovieDuration) {

        Intent intent = new Intent(getContext(), MovieDetails.class);
        intent.putExtra("MovieID", MovieID);
        intent.putExtra("MoviePhoto", MoviePhoto);
        intent.putExtra("MovieTitle", MovieTitle);
        intent.putExtra("MovieType", MovieType);
        intent.putExtra("MovieYear", MovieYear);
        intent.putExtra("MovieFirstText", MovieFirstText);
        intent.putExtra("MovieSecondText", MovieSecondText);
        intent.putExtra("MovieDuration", MovieDuration);
        intent.putExtra("IsSaved",true);
        startActivity(intent);
    }

    public int getScreenHeight(Context context) {
        Display display = ((AppCompatActivity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        return height;
    }

    public int getScreenWidth(Context context) {
        Display display = ((AppCompatActivity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        return width;
    }
}