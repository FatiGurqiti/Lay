package com.example.fibuv2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fibuv2.api.DetailsAPI;
import com.example.fibuv2.api.GetMoreLikeThisAPI;
import com.example.fibuv2.api.RateAPI;
import com.example.fibuv2.api.SearchAPI;
import com.example.fibuv2.database.DatabaseHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDetails extends AppCompatActivity {

    private DatabaseHandler db = new DatabaseHandler(MovieDetails.this);
    private FirebaseFirestore Firedb = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference docRef = Firedb.collection("MovieLists").document(user.getUid());


    private static int hours;
    private static int minutes;
    private String SuggestionImg;
    private String SuggestionTitle;
    private String SuggestionID;

    private TextView SuggestionTitleText;
    private TextView rateText;
    private ImageView rateStar;
    private ProgressBar progressBar;
    private ImageView blackFilter;
    private ImageView moreLikeThisPicture;
    private ImageView MoreLikeThisFilter;
    private TextView Products;


    private String movieID;
    private String moviePhoto;
    private String movieTitle;
    private String movieYear;
    private String Runingtime;
    private String Type;
    private String Rate;
    private String FistText;
    private String SecondText;

    private List<Map<String, ArrayList>> mapid;
    private List<Map<String, ArrayList>> mapimg;
    private List<Map<String, ArrayList>> maptitle;
    private List<Map<String, ArrayList>> maptype;
    private List<Map<String, ArrayList>> mapyear;
    private List<Map<String, ArrayList>> mapfirstText;
    private List<Map<String, ArrayList>> mapsecondText;
    private List<Map<String, ArrayList>> mapduration;

    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> img = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> year = new ArrayList<>();
    private ArrayList<String> duration = new ArrayList<>();
    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<String> firstText = new ArrayList<>();
    private ArrayList<String> secondText = new ArrayList<>();

    private ImageView save;
    private ImageView saved;

    private boolean isSaved;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details2);

        //Get's data from last page
        Bundle extras = getIntent().getExtras();

        movieID = extras.getString("MovieID");
        moviePhoto = extras.getString("MoviePhoto");
        isSaved = extras.getBoolean("IsSaved");
        boolean isSuggestionPage = extras.getBoolean("IsSuggestedMovie");

        Log.d("isSuggestion", String.valueOf(isSuggestionPage));
        Log.d("MovieID", movieID);

            DetailsAPI.getDetails(movieID);


        if (isSaved) { //if movie is already saved get data from FireBase
            movieTitle = extras.getString("MovieTitle");
            movieYear = extras.getString("MovieYear");
            Runingtime = extras.getString("MovieDuration");
            Type = extras.getString("MovieType");
            FistText = extras.getString("MovieFirstText");
            SecondText = extras.getString("MovieSecondText");


        } else { //If movie isn't saved get data from API

            MainLoggedIn.lowerQuota();

            movieTitle = isNull(DetailsAPI.name);
            movieYear = isNull(DetailsAPI.year);
            Runingtime = isNull(DetailsAPI.runningTimeInMinutes);
            Type = isNull(DetailsAPI.genresList.get(0).toString());
            for (int j = 1; j < DetailsAPI.genresList.size(); j++) {
                Type = Type + ", " + DetailsAPI.genresList.get(j).toString();
            }


            if (DetailsAPI.plotOutlineList.size() > 0) {
                FistText = isNull(DetailsAPI.plotOutlineList.get(0));
                SecondText = isNull(DetailsAPI.plotOutlineList.get(1));

                DetailsAPI.plotOutlineList.clear(); //Clear source data after using it

            } else {
                FistText = "";
                SecondText = "";
            }
            if (!db.getIsLiteMode() || !isSuggestionPage || !isSaved) {
                //Lite mode is off
                //This isn't a suggestion page
                //And This isn't a page that is saved by user

                //I don't want the user to jump from one suggestion to another

                Log.d("IsThisSuggestionPage", "Nope");


                    //Load Rate Data
                    RateAPI.rate(movieID);
                    //Load Suggestion Data
                    GetMoreLikeThisAPI.getmorelikethiss(movieID);
                    SearchAPI.autoCompleteAPI(GetMoreLikeThisAPI.morelikethis);


                Log.d("APIStatus", "MovieID :" + movieID);
                Log.d("APIStatus", "Movie url :" + SearchAPI.movieImageUrl);
                Log.d("APIStatus", "Movie suggestion " + GetMoreLikeThisAPI.morelikethis);

                //Get data
                SuggestionImg = SearchAPI.movieImageUrl.get(0);
                SuggestionTitle = SearchAPI.movieTitle.get(0);
                SuggestionID = GetMoreLikeThisAPI.morelikethis;


            }
        }


        ImageView detailsThumbnail = findViewById(R.id.imageThumbnailinDetails);
        moreLikeThisPicture = findViewById(R.id.morelikethisimage1);
        MoreLikeThisFilter = findViewById(R.id.morelikethisfilter);
        progressBar = findViewById(R.id.progressbarindetail);
        blackFilter = findViewById(R.id.detailBlackFilter);

        save = findViewById(R.id.save);
        saved = findViewById(R.id.saved);
        SuggestionTitleText = findViewById(R.id.morelikethisTitle);
        rateText = findViewById(R.id.rate);
        rateStar = findViewById(R.id.star);
        ImageView rateBg = findViewById(R.id.RateBackground);


        Products = findViewById(R.id.products);
        TextView readMore = findViewById(R.id.readMore);
        TextView firstText = findViewById(R.id.firstText);
        TextView secondText = findViewById(R.id.secondText);
        TextView title = findViewById(R.id.title);
        TextView year = findViewById(R.id.year);
        TextView time = findViewById(R.id.time);
        TextView type = findViewById(R.id.type);

        title.setText(movieTitle);
        year.setText(movieYear);
        rateText.setText(RateAPI.contentRate);
        minutesToHours(Integer.parseInt(Runingtime));
        time.setText(hours + "h " + minutes + "m");
        type.setText(Type);
        firstText.setText(FistText);
        secondText.setText(SecondText);
        secondText.setVisibility(View.INVISIBLE);

        rateText.bringToFront();

        if (SearchAPI.movieImageUrl.isEmpty()) //Don't show suggestion if it's unavailable
        {
            Products.setVisibility(View.GONE);
            moreLikeThisPicture.setVisibility(View.GONE);
            MoreLikeThisFilter.setVisibility(View.GONE);
            SuggestionTitleText.setVisibility(View.GONE);
        }

        if (isSaved || db.getIsLiteMode()) //Don't show rate if this is a saved page or in Lite Mode
        {
            rateText.setVisibility(View.GONE);
            rateStar.setVisibility(View.GONE);
            rateBg.setVisibility(View.GONE);
        }


        intianalizeOldData();


        save.setOnClickListener(v -> {

            saveMovie();
            SetMovieSaved();
            Toast toast = Toast.makeText(getApplicationContext(),
                    movieTitle + " is added to your list",
                    Toast.LENGTH_SHORT);
            toast.show();

            finish();
            startActivity(getIntent());
        });
        saved.setOnClickListener(v -> {
            SetMovieNotSaved();  //Set's icon unsaved
            unSaveMovie();      //Unsaves movie
            Toast toast = Toast.makeText(getApplicationContext(),
                    movieTitle + " is removed from your list",
                    Toast.LENGTH_SHORT);
            toast.show();


        });

        readMore.setOnClickListener(v -> {
            secondText.setVisibility(View.VISIBLE);
            readMore.setVisibility(View.INVISIBLE);
        });

        Picasso.get().load(moviePhoto).transform(new RoundedTransformation(25, 0)).fit().centerCrop(700).into(detailsThumbnail);

        // Don't show suggestions if lite mode is on
        // Or this is a suggestion page
        Log.d("isSuggestion2", String.valueOf(isSuggestionPage));


        if (!db.getIsLiteMode()) {
            // Lite mode is off
            Picasso.get().load(SuggestionImg).transform(new RoundedTransformation(50, 0)).fit().centerCrop(300).into(moreLikeThisPicture);
            Picasso.get().load(R.drawable.black_filer_resource).transform(new RoundedTransformation(55, 0)).fit().centerCrop(700).into(MoreLikeThisFilter);
            SuggestionTitleText.setText(SuggestionTitle);

            moreLikeThisPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    blackFilter.setVisibility(View.VISIBLE);
                    openMovieDetail(SuggestionID, SuggestionImg);
                }
            });
        } else {
            //Lite Mode is On

            Products.setVisibility(View.GONE);
            moreLikeThisPicture.setVisibility(View.GONE);
            MoreLikeThisFilter.setVisibility(View.GONE);
            SuggestionTitleText.setVisibility(View.GONE);

            rateText.setVisibility(View.GONE);
            rateStar.setVisibility(View.GONE);
        }

        if (isSuggestionPage || isSaved) {
            Products.setVisibility(View.GONE);
            moreLikeThisPicture.setVisibility(View.GONE);
            SuggestionTitleText.setVisibility(View.GONE);
            MoreLikeThisFilter.setVisibility(View.GONE);
        }

        detailsThumbnail.setOnClickListener(v -> {
            Intent intent = new Intent(MovieDetails.this, ThumbnailFullScreen.class);
            intent.putExtra("moviePhoto", moviePhoto);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ActivityStatus", "Resume");

        progressBar.setVisibility(View.INVISIBLE);
        blackFilter.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        movieID = extras.getString("MovieID");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ActivityStatus", "Destroy");
        movieID = null;

    }

    private static void minutesToHours(int time) {
        int hour = 0;

        while (time > 59) {
            time = time - 60;
            hour++;
        }
        hours = hour;
        minutes = time;
    }


    private void intianalizeOldData() {
        String TAG = "TAG";

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    Log.d(TAG, "DocumentSnapshot data: " + document.get("id"));

                    mapid = (List<Map<String, ArrayList>>) document.get("id");
                    mapimg = (List<Map<String, ArrayList>>) document.get("img");
                    maptitle = (List<Map<String, ArrayList>>) document.get("title");
                    maptype = (List<Map<String, ArrayList>>) document.get("type");
                    mapyear = (List<Map<String, ArrayList>>) document.get("year");
                    mapfirstText = (List<Map<String, ArrayList>>) document.get("firstText");
                    mapsecondText = (List<Map<String, ArrayList>>) document.get("secondText");
                    mapduration = (List<Map<String, ArrayList>>) document.get("duration");

                    isMovieSaved(mapid.size());
                    Log.d("IDSIZE", "" + mapid.size());

                } else {
                    Log.d(TAG, "No such document");
                    SetMovieNotSaved();
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    private void saveMovie() {

        // add new array list to the old one
        if (mapid != null) // User has previously saved data
        {
            for (int i = 0; i < mapid.size(); i++) {

                Log.d("mapsize", String.valueOf(mapid.size()));
                if (!String.valueOf(mapid.get(i)).equals(movieID)) {

                    id.add(String.valueOf(mapid.get(i)));
                    img.add(String.valueOf(mapimg.get(i)));
                    title.add(String.valueOf(maptitle.get(i)));
                    year.add(String.valueOf(mapyear.get(i)));
                    duration.add(String.valueOf(mapduration.get(i)));
                    type.add(String.valueOf(maptype.get(i)));
                    firstText.add(String.valueOf(mapfirstText.get(i)));
                    secondText.add(String.valueOf(mapsecondText.get(i)));
                }
            }


            //adding this data to top of existing data
            id.add(movieID);
            img.add(moviePhoto);
            title.add(movieTitle);
            year.add(movieYear);
            duration.add(Runingtime);
            type.add(Type);
            firstText.add(FistText);
            secondText.add(SecondText);

            Log.d("arrayidstat", String.valueOf(id));

            //Removes duplicated data
            for (int i = 0; i < id.size(); i++) {
                if (i + 1 < id.size()) {
                    if (id.get(i).equals(id.get(i + 1))) {
                        id.remove(i + 1);
                        img.remove(i + 1);
                        title.remove(i + 1);
                        year.remove(i + 1);
                        duration.remove(i + 1);
                        type.remove(i + 1);
                        firstText.remove(i + 1);
                        secondText.remove(i + 1);
                    }
                }
            }


            //add the arraylist to FireStore
            Map<String, List<String>> favouriteMovie = new HashMap<>();
            favouriteMovie.put("id", id);
            favouriteMovie.put("img", img);
            favouriteMovie.put("title", title);
            favouriteMovie.put("year", year);
            favouriteMovie.put("duration", duration);
            favouriteMovie.put("type", type);
            favouriteMovie.put("firstText", firstText);
            favouriteMovie.put("secondText", secondText);

            Firedb.collection("MovieLists")
                    .document(user.getUid())
                    .set(favouriteMovie);


        } else { // User does not have previously saved data
            SetMovieSaved();

            id.add(movieID);
            img.add(moviePhoto);
            title.add(movieTitle);
            year.add(movieYear);
            duration.add(Runingtime);
            type.add(Type);
            firstText.add(FistText);
            secondText.add(SecondText);

            //add the arraylist to FireStore

            Map<String, List<String>> favouriteMovie = new HashMap<>();
            favouriteMovie.put("id", id);
            favouriteMovie.put("img", img);
            favouriteMovie.put("title", title);
            favouriteMovie.put("year", year);
            favouriteMovie.put("duration", duration);
            favouriteMovie.put("type", type);
            favouriteMovie.put("firstText", firstText);
            favouriteMovie.put("secondText", secondText);

            Firedb.collection("MovieLists").document(user.getUid())
                    .set(favouriteMovie);


        }
    }

    private void unSaveMovie() {

        // addd new array list to the old one

        if (mapid != null) // User has previously saved data
        {
            for (int i = 0; i < mapid.size(); i++) {

                if (!String.valueOf(mapid.get(i)).equals(movieID)) {   // to prevent saving the same data

                    id.add(String.valueOf(mapid.get(i)));
                    img.add(String.valueOf(mapimg.get(i)));
                    title.add(String.valueOf(maptitle.get(i)));
                    year.add(String.valueOf(mapyear.get(i)));
                    duration.add(String.valueOf(mapduration.get(i)));
                    type.add(String.valueOf(maptype.get(i)));
                    firstText.add(String.valueOf(mapfirstText.get(i)));
                    secondText.add(String.valueOf(mapsecondText.get(i)));
                }
            }

            Log.d("ArrayList", id.toString());
            Log.d("idStatus", String.valueOf(id));

            Map<String, Object> data = new HashMap<>();

            data.put("id", id);
            data.put("img", img);
            data.put("title", title);
            data.put("year", year);
            data.put("duration", duration);
            data.put("type", type);
            data.put("firstText", firstText);
            data.put("secondText", secondText);

            Firedb.collection("MovieLists")
                    .document(user.getUid())
                    .set(data, SetOptions.merge());


        }


    }

    private void isMovieSaved(int size) {


        SetMovieNotSaved();

        for (int i = 0; i < size; i++) {

            if (String.valueOf(mapid.get(i)).equals(movieID)) {
                Log.d("ismoviesaved", "movie is saved");
                SetMovieSaved();
            }

        }


    }

    private void SetMovieSaved() {
        saved.setVisibility(View.VISIBLE);
        save.setVisibility(View.GONE);
    }

    private void SetMovieNotSaved() {
        save.setVisibility(View.VISIBLE);
        saved.setVisibility(View.GONE);
    }

    private void openMovieDetail(String MovieID, String MoviePhoto) {
        Intent intent = new Intent(MovieDetails.this, MovieDetails.class);
        intent.putExtra("MovieID", MovieID);
        intent.putExtra("MoviePhoto", MoviePhoto);
        intent.putExtra("IsSaved", false);
        intent.putExtra("IsSuggestedMovie", true); //For not showing more suggestion in next page
        startActivity(intent);
    }

    private String isNull(String text) {
        if (TextUtils.isEmpty(text)) return "";

        else return text;

    }


}
