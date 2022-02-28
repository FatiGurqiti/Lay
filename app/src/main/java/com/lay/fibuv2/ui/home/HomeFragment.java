package com.lay.fibuv2.ui.home;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lay.fibuv2.MainLoggedIn;
import com.lay.fibuv2.MovieDetails;
import com.lay.fibuv2.R;
import com.lay.fibuv2.RoundedTransformation;
import com.lay.fibuv2.Search;
import com.lay.fibuv2.database.DatabaseHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
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

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> localarrayList = new ArrayList<>();

    private CardView pop;
    private CardView popafter;
    private ImageView blackbg;
    private ImageButton likeButton;
    private ImageButton dislikeButton;
    private ProgressBar progressBar;

    private ImageView CuteRobot;
    private TextView NoFavouriteText;
    private TextView FirstReference;

    private String currentid;
    private String currentname;
    private String currentimg;

    private static String username;

    private static boolean ShouldShowSeenContent;
    private boolean showCurrentContent;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        username = MainLoggedIn.getUsername();

        getSeenMovies();
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Typeface face = getResources().getFont(R.font.plusjakartatextregular);   // Font-Family
        Typeface boldface = getResources().getFont(R.font.plusjakartatexbold);  // Font-Family

        pop = root.findViewById(R.id.ratepop);
        popafter = root.findViewById(R.id.afterRate);
        blackbg = root.findViewById(R.id.homeBlackFilter);
        progressBar = root.findViewById(R.id.progressbarinMyList);
        ImageButton likeButton = root.findViewById(R.id.likeButton);
        ImageButton dislikeButton = root.findViewById(R.id.dislikeButton);
        CuteRobot = root.findViewById(R.id.cuteRobot);
        NoFavouriteText = root.findViewById(R.id.noFavouriteText);
        FirstReference = root.findViewById(R.id.firstReference);
        TextView WelcomeText = root.findViewById(R.id.welcomeText);


        if (username == null){
            WelcomeText.setText(" \t Welcome, here's your favourite list");}
        else
            WelcomeText.setText(" \t Hi " + username + ", here's your favourite list");

        //Get status of showing content that are already seen
        DatabaseHandler sqldb = new DatabaseHandler(getContext());
        if (sqldb.getShowSeenContents()) ShouldShowSeenContent = true;
        else ShouldShowSeenContent = false;





        String TAG = "HomeDataStatus";
        FirebaseFirestore Firedb = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = Firedb.collection("MovieLists").document(user.getUid());


        docRef.get().addOnCompleteListener(task -> {
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
                        Log.d("GetSeenMoviesFirebaseStatusOnStart", String.valueOf(localarrayList));

                        RelativeLayout layout = root.findViewById(R.id.Scroll_Relative);
                        int j = -1;
                        for (int i = 0; i < limit; i++) {

                            int sizeheight = (int) (getScreenHeight(getContext()) * 0.5);
                            int sizewidth = (getScreenWidth(getContext()));
                            int finalI = i;


                            //Don't show contents that are already seen if the mode is off
                            if (!ShouldShowSeenContent && isSeen(id.get(finalI))) {
                                showCurrentContent = false;
                            } else showCurrentContent = true; //Show Seen Content is on, So show everything

                            if (showCurrentContent) {
                                j++;
                                ImageView image = new ImageView(getContext());
                                image.setLayoutParams(new ViewGroup.LayoutParams(1400, (int) ((int) (sizeheight) * .6)));
                                Picasso.get().load(img.get(i).trim())
                                        .transform(new RoundedTransformation(50, 0))
                                        .fit()
                                        .centerCrop(700)
                                        .into(image);
                                image.setTranslationZ(0);
                                layout.addView(image);
                                Search.setMargins(image, 25, (int) (j * (sizeheight) * .75), 25, 1);


                                ImageView filter = new ImageView(getContext());
                                filter.setLayoutParams(new ViewGroup.LayoutParams(1400, (int) ((int) (sizeheight) * .6)));
                                Picasso.get().load(R.drawable.black_filer_resource).transform(new RoundedTransformation(50, 0)).fit().centerCrop(700).into(filter);
                                filter.setTranslationZ(0);
                                layout.addView(filter);
                                Search.setMargins(filter, 25, (int) (j * (sizeheight) * .75), 25, 1);


                                TextView titleText = new TextView(getContext());
                                titleText.setText(title.get(i));
                                titleText.setTypeface(boldface);
                                titleText.setTextColor(Color.WHITE);
                                titleText.setPadding(25, 250, 25, 0);
                                titleText.setTextSize(22);
                                titleText.setHeight(300);
                                titleText.bringToFront();
                                titleText.setTranslationZ(2);
                                titleText.setElevation(2);
                                layout.addView(titleText);
                                Search.setMargins(titleText, 25, (int) (j * (sizeheight) * .75), 25, 1);
                                titleText.setPadding(25, (int) (sizewidth * .2), 50, 0);

                                titleText.setOnClickListener(v -> {
                                    blackbg.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.VISIBLE);

                                    openMovieDetail(
                                            id.get(finalI),
                                            img.get(finalI),
                                            title.get(finalI),
                                            type.get(finalI),
                                            year.get(finalI),
                                            firstText.get(finalI),
                                            secondText.get(finalI),
                                            duration.get(finalI));
                                });


                                ImageView seenicon = new ImageView(getContext());
                                if (isSeen(id.get(finalI))) {
                                    Picasso.get().load(R.drawable.check).into(seenicon);
                                    seenicon.setEnabled(false);
                                } else {
                                    Picasso.get().load(R.drawable.seen).into(seenicon);
                                }
                                seenicon.bringToFront();
                                layout.addView(seenicon);
                                Search.setMargins(seenicon, 25, (int) (j * (sizeheight) * .75), 25, 1);
                                seenicon.setPadding((int) ((sizewidth) * .3), (int) (sizewidth * .5), 50, 0);


                                TextView seenText = new TextView(getContext());
                                if (isSeen(id.get(finalI))) {
                                    seenText.setText("Seen");
                                    seenText.setEnabled(false);
                                } else {
                                    seenText.setText("Set as seen");
                                }

                                seenText.setTypeface(face);
                                seenText.setTextColor(Color.WHITE);
                                seenText.setPadding(25, 250, 25, 0);
                                seenText.setTextSize(16);
                                seenText.setHeight(500);
                                seenText.bringToFront();
                                layout.addView(seenText);
                                Search.setMargins(seenText, (int) ((sizewidth) * .4), (int) (j * (sizeheight) * .75), 25, 1);
                                seenText.setPadding(25, (int) (sizewidth * .5), 50, 0);


                                seenicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        setmovieSeen();
                                        currentid = id.get(finalI);
                                        currentname = title.get(finalI);
                                        currentimg = img.get(finalI);

                                        Picasso.get().load(R.drawable.check).into(seenicon);
                                        seenicon.setEnabled(false);
                                        seenText.setText("Seen");
                                        seenText.setEnabled(false);

                                    }
                                });

                                seenText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        setmovieSeen();

                                        currentid = id.get(finalI);
                                        currentname = title.get(finalI);
                                        currentimg = img.get(finalI);

                                        Picasso.get().load(R.drawable.check).into(seenicon);
                                        seenicon.setEnabled(false);
                                        seenText.setText("Seen");
                                        seenText.setEnabled(false);
                                    }
                                });


                                likeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        Log.d("CurrentIDStatus", currentid);
                                        like(currentid, currentname, currentimg, true);
                                        addSeenMovies(currentid);

                                    }
                                });

                                dislikeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        like(currentid, currentname, currentimg, false);
                                        addSeenMovies(currentid);

                                    }

                                });

                            }

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
        });


        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        blackbg.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
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

                        if (like) docRef.update("rate", rate + 1);
                        else {
                            if (rate > 1) docRef.update("rate", rate - 1);
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
        intent.putExtra("isSuggestionPage", false);
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

    private void getSeenMovies() {

        String TAG = "GetSeenMoviesFirebaseStatus";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference docRef = db.collection("SeenMovies").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        localarrayList = (ArrayList<String>) document.get("id");
                        Log.d(TAG, "SeenMoviesInLoop: " + localarrayList);


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void addSeenMovies(String id) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String TAG = "SeenMoviesFirebaseStatus";
        DocumentReference docRef = db.collection("SeenMovies").document(user.getUid());

        //Get previous data
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        arrayList = (ArrayList<String>) document.get("id");

                    } else {
                        Log.d(TAG, "No such document");

                        arrayList.add(id);
                        Log.d(TAG, "ArrayInFirstSave" + arrayList);

                        Map<String, ArrayList> seenMovie = new HashMap<>();
                        seenMovie.put("id", arrayList);

                        db.collection("SeenMovies")
                                .document(user.getUid())
                                .set(seenMovie);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

                Log.d(TAG, "ListOut" + arrayList);
                if (arrayList.size() > 0) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        Log.d(TAG, "ListInLoop " + arrayList.get(i));
                        Log.d(TAG, "IDInLoop " + id);
                        if (!arrayList.get(i).equals(id)) {
                            arrayList.add(id);
                        } else {

                        }
                        //Removes duplicated data
                        if (i + 1 < arrayList.size()) {
                            if (arrayList.get(i).equals(arrayList.get(i + 1))) {
                                arrayList.remove(i + 1);
                            }
                        }
                    }

                    Log.d(TAG, "ListOut2" + arrayList);
                    Map<String, ArrayList> data = new HashMap<>();
                    data.put("id", arrayList);

                    db.collection("SeenMovies").document(user.getUid())
                            .set(data, SetOptions.merge());

                }


            }
        });


    }

    private boolean isSeen(String id) {
        boolean result = false;
        for (int i = 0; i < localarrayList.size(); i++) {
            if (id.equals(localarrayList.get(i))) {
                result = true;
            }
        }
        return result;
    }


    public static String getUsername(){return username;}
    public static boolean getSeenContentStatus(){return ShouldShowSeenContent;}
}
