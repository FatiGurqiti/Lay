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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fibuv2.MainLoggedIn;
import com.example.fibuv2.MovieDetails;
import com.example.fibuv2.R;
import com.example.fibuv2.RoundedTransformation;
import com.example.fibuv2.Search;
import com.example.fibuv2.api.SearchAPI;
import com.example.fibuv2.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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

    private CardView pop;
    private CardView popafter;
    private ImageView blackbg;
    private ImageButton likeButton;
    private ImageButton dislikeButton;

    private ImageView CuteRobot;
    private TextView NoFavouriteText;
    private TextView FirstReference;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Typeface face = getResources().getFont(R.font.plusjakartatextregular);   // Font-Family
        Typeface boldface = getResources().getFont(R.font.plusjakartatexbold);  // Font-Family

        pop = (CardView) root.findViewById(R.id.ratepop);
        popafter = (CardView) root.findViewById(R.id.afterRate);
        blackbg = (ImageView) root.findViewById(R.id.homeBlackFilter);
        ImageButton likeButton = (ImageButton) root.findViewById(R.id.likeButton);
        ImageButton dislikeButton = (ImageButton) root.findViewById(R.id.dislikeButton);
        CuteRobot = (ImageView) root.findViewById(R.id.cuteRobot);
        NoFavouriteText = (TextView) root.findViewById(R.id.noFavouriteText);
        FirstReference = (TextView) root.findViewById(R.id.firstReference);

        TextView WelcomeText = (TextView) root.findViewById(R.id.welcomeText);
        WelcomeText.setText(" \t Welcome " + MainLoggedIn.getUsername() + ", here's your favourite list");

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
                                Search.setMargins(image, 25, (int) (i * (sizeheight) * .75), 25, 1);


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
                                titleText.setPadding(25, (int) (sizewidth * .2), 50, 0);

                                ImageView seenicon = new ImageView(getContext());
                                Picasso.get().load(R.drawable.seen).into(seenicon);
                                seenicon.bringToFront();
                                layout.addView(seenicon);
                                Search.setMargins(seenicon, 25, (int) (i * (sizeheight) * .75), 25, 1);
                                seenicon.setPadding((int) ((sizewidth) * .3), (int) (sizewidth * .5), 50, 0);


                                TextView seenText = new TextView(getContext());
                                seenText.setText("Set as seen");
                                seenText.setTypeface(face);
                                seenText.setTextColor(Color.WHITE);
                                seenText.setPadding(25, 250, 25, 0);
                                seenText.setTextSize(16);
                                seenText.bringToFront();
                                layout.addView(seenText);
                                Search.setMargins(seenText, (int) ((sizewidth) * .4), (int) (i * (sizeheight) * .75), 25, 1);
                                seenText.setPadding(25, (int) (sizewidth * .5), 50, 0);

                                seenicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        setmovieSeen();
                                    }
                                });

                                seenText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        setmovieSeen();
                                    }
                                });

                                int finalI1 = i;
                                likeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        like(id.get(finalI1), title.get(finalI1), img.get(finalI1), true);
                                    }
                                });

                                dislikeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        like(id.get(finalI1), title.get(finalI1), img.get(finalI1), false);
                                    }
                                });

                            }

                        }

                    } else {
                        Log.d(TAG, "No such document");
                        CuteRobot.setVisibility(View.VISIBLE);
                        NoFavouriteText.setVisibility(View.VISIBLE);
                        FirstReference.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        return root;
    }


    private void like(String id, String name, String img, Boolean like) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference movieRate = db.collection("MovieRate");


        String TAG = "rateMovie";
        DocumentReference docRef = db.collection("MovieRate").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        int rate = document.getLong("rate").intValue();

                        if (like) docRef.update("rate", rate+1);
                        else {
                            if (rate > 1) docRef.update("rate", rate-1);
                        }

                    } else {
                        Log.d(TAG, "No such document");

                        Map<String, String> MovieRate = new HashMap<>();
                        MovieRate.put("id", id);
                        MovieRate.put("name", name);
                        MovieRate.put("img", img);
                        if (like) MovieRate.put("rate", "1");

                        db.collection("MovieRate").document(id)
                                .set(MovieRate);

                        docRef.update("rate", 1);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        afterRate();
    }


    private void setmovieSeen() {
        pop.setVisibility(View.VISIBLE);
        blackbg.setVisibility(View.VISIBLE);
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
        intent.putExtra("IsSaved", true);
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

    private void afterRate() {

        popafter.setVisibility(View.VISIBLE);
        pop.setVisibility(View.INVISIBLE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               popafter.setVisibility(View.INVISIBLE);
                               blackbg.setVisibility(View.INVISIBLE);
                           }
                       }
                , 1000
        );
    }

}
