package com.example.fibuv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fibuv2.api.DetailsAPI;
import com.example.fibuv2.api.GetMoreLikeThisAPI;
import com.example.fibuv2.api.SearchAPI;
import com.example.fibuv2.database.DatabaseHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDetails extends AppCompatActivity {

    private static int hours;
    private static int minutes;
    private String SuggestionImg;
    private String SuggestionTitle;

    private TextView SuggestionTitleText;
    private TextView  rateText;
    private ImageView rateStar;

    private DatabaseHandler db = new DatabaseHandler(MovieDetails.this);

    private FirebaseFirestore Firedb = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference docRef = Firedb.collection("MovieLists").document(user.getUid());

    private String movieID;
    private String moviePhoto;
    private String movieTitle;
    private String movieYear;
    private String Runingtime;
    private String Type;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details2);


        //Get's data from last page
        Bundle extras = getIntent().getExtras();

        movieID = extras.getString("MovieID");
        moviePhoto = extras.getString("MoviePhoto");
        boolean isSaved = extras.getBoolean("IsSaved");
        boolean shouldUnsave = extras.getBoolean("ShouldUnSave");

        DetailsAPI.getDetails(movieID);

        if (isSaved) { //if movie is already saved get data from FireBase

            movieTitle = extras.getString("MovieTitle");
            movieYear = extras.getString("MovieYear");
            Runingtime = extras.getString("MovieDuration");
            Type = extras.getString("MovieType");
            FistText = extras.getString("MovieFirstText");
            SecondText = extras.getString("MovieSecondText");

        } else { //If movie isn't saved get data from API
            movieTitle = DetailsAPI.name;
            movieYear = DetailsAPI.year;
            Runingtime = DetailsAPI.runningTimeInMinutes;
            Type = DetailsAPI.genresList;
            FistText = DetailsAPI.plotOutlineList.get(0);
            SecondText = DetailsAPI.plotOutlineList.get(1);
        }


        if (db.getIsLiteMode() == false) setSuggestionDetails(movieID);

        ImageView detailsThumbnail = (ImageView) findViewById(R.id.imageThumbnailinDetails);
        ImageView moreLikeThisPicture1 = (ImageView) findViewById(R.id.morelikethisimage1);
        save = (ImageView) findViewById(R.id.save);
        saved = (ImageView) findViewById(R.id.saved);
        SuggestionTitleText = findViewById(R.id.morelikethisTitle);
        rateText = findViewById(R.id.rate);
        rateStar = findViewById(R.id.star);


        TextView Products = (TextView) findViewById(R.id.products);
        TextView readMore = (TextView) findViewById(R.id.readMore);
        TextView firstText = (TextView) findViewById(R.id.firstText);
        TextView secondText = (TextView) findViewById(R.id.secondText);
        TextView title = (TextView) findViewById(R.id.title);
        TextView year = (TextView) findViewById(R.id.year);
        TextView time = (TextView) findViewById(R.id.time);
        TextView type = (TextView) findViewById(R.id.type);

        title.setText(movieTitle);
        year.setText(movieYear);
        minutesToHours(Integer.parseInt(Runingtime));
        time.setText(hours + "h " + minutes + "m");
        type.setText(Type);
        firstText.setText(FistText);
        secondText.setText(SecondText);
        secondText.setVisibility(View.INVISIBLE);


        Log.d("currentID", movieID);
        intianalizeOldData();

        Log.d("Unsave", String.valueOf(shouldUnsave));
        if (shouldUnsave) {

            SetMovieNotSaved();  //Set's icon unsaved
            //  unSaveMovie();      //Unsaves movie
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveMovie();
                SetMovieSaved();
                Toast toast = Toast.makeText(getApplicationContext(),
                        movieTitle + " is added to your list",
                        Toast.LENGTH_SHORT);
                toast.show();

                finish();
                startActivity(getIntent());
            }
        });
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetMovieNotSaved();  //Set's icon unsaved
                unSaveMovie();      //Unsaves movie
                Toast toast = Toast.makeText(getApplicationContext(),
                        movieTitle + " is removed from your list",
                        Toast.LENGTH_SHORT);
                toast.show();


            }
        });

        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondText.setVisibility(View.VISIBLE);
                readMore.setVisibility(View.INVISIBLE);
            }
        });

        Picasso.get().load(moviePhoto).transform(new RoundedTransformation(25, 0)).fit().centerCrop(700).into(detailsThumbnail);

        // Don't show suggestions if lite mode is on
        if (!db.getIsLiteMode()) {
            // Lite mode is off

            Picasso.get().load(SuggestionImg).transform(new RoundedTransformation(50, 0)).fit().centerCrop(300).into(moreLikeThisPicture1);
            SuggestionTitleText.setText(SuggestionTitle);
        } else {
            //Lite Mode is On

            Products.setVisibility(View.GONE);
            moreLikeThisPicture1.setVisibility(View.GONE);
            SuggestionTitleText.setVisibility(View.GONE);

            rateText.setVisibility(View.GONE);
            rateStar.setVisibility(View.GONE);
        }

        detailsThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetails.this, ThumbnailFullScreen.class);
                intent.putExtra("moviePhoto", moviePhoto);
                startActivity(intent);
            }
        });

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

    private void setSuggestionDetails(String movieID) {
        GetMoreLikeThisAPI.getmorelikethiss(movieID);
        SearchAPI.autoCompleteAPI(GetMoreLikeThisAPI.morelikethis.get(0));
        SuggestionImg = SearchAPI.movieImageUrl.get(0);
        SuggestionTitle = SearchAPI.movieTitle.get(0);

    }

    private void intianalizeOldData() {
        String TAG = "TAG";

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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


}
