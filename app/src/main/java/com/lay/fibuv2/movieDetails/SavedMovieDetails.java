package com.lay.fibuv2.movieDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.lay.fibuv2.R;
import com.lay.fibuv2.RoundedTransformation;
import com.lay.fibuv2.ThumbnailFullScreen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedMovieDetails extends AppCompatActivity {

    private FirebaseFirestore Firedb = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference docRef = Firedb.collection("MovieLists").document(user.getUid());

    private static int hours;
    private static int minutes;
    private ProgressBar progressBar;
    private ImageView blackFilter;
    private ImageView save;
    private ImageView saved;

    private String movieID;
    private String moviePhoto;
    private String movieTitle;
    private String movieYear;
    private String runTime;
    private String movieType;
    private String fistText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_movie_details);

        MovieDetailsViewModel movieViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        Bundle extras = getIntent().getExtras();

        movieID = extras.getString("MovieID");
        moviePhoto = extras.getString("MoviePhoto");
        movieTitle = extras.getString("MovieTitle");
        movieYear = extras.getString("MovieYear");
        runTime = extras.getString("MovieDuration");
        movieType = extras.getString("MovieType");
        fistText = extras.getString("MovieFirstText");
        SecondText = extras.getString("MovieSecondText");

        ImageView detailsThumbnail = findViewById(R.id.imageThumbnailinDetails);
        progressBar = findViewById(R.id.progressbarindetail);
        blackFilter = findViewById(R.id.detailBlackFilter);
        save = findViewById(R.id.save);
        saved = findViewById(R.id.saved);

        TextView readMore = findViewById(R.id.readMore);
        TextView firstText = findViewById(R.id.firstText);
        TextView secondText = findViewById(R.id.secondText);
        TextView title = findViewById(R.id.title);
        TextView year = findViewById(R.id.year);
        TextView time = findViewById(R.id.time);
        TextView type = findViewById(R.id.type);

        title.setText(movieTitle);
        year.setText(movieYear);
        minutesToHours(Integer.parseInt(runTime));
        time.setText(hours + "h " + minutes + "m");
        type.setText(movieType);
        firstText.setText(fistText);
        secondText.setText(SecondText);

        intianalizeOldData();

        save.setOnClickListener(v -> {

            saveMovie();
            SetMovieSaved();
            Toast toast = Toast.makeText(getApplicationContext(),
                    movieTitle + " is added to your list",
                    Toast.LENGTH_SHORT);
            toast.show();
        });
        saved.setOnClickListener(v -> {
            SetMovieNotSaved();  //Set's icon unsaved
            unSaveMovie();

            Toast toast = Toast.makeText(getApplicationContext(),
                    movieTitle + " is removed from your list",
                    Toast.LENGTH_SHORT);
            toast.show();


        });
        

        Picasso.get().load(moviePhoto).transform(new RoundedTransformation(25, 0)).fit().centerCrop(700).into(detailsThumbnail);

        detailsThumbnail.setOnClickListener(v -> {
            Intent intent = new Intent(this, ThumbnailFullScreen.class);
            intent.putExtra("moviePhoto", moviePhoto);
            startActivity(intent);
        });


    }



    void saveMovie() {

        // add new array list to the old one
        if (mapid != null) // User has previously saved data
        {
            for (int i = 0; i < mapid.size(); i++) {

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
            duration.add(runTime);
            type.add(movieType);
            firstText.add(fistText);
            secondText.add(SecondText);

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
            duration.add(runTime);
            type.add(movieType);
            firstText.add(fistText);
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

        Bundle extras = getIntent().getExtras();
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    mapid = (List<Map<String, ArrayList>>) document.get("id");
                    mapimg = (List<Map<String, ArrayList>>) document.get("img");
                    maptitle = (List<Map<String, ArrayList>>) document.get("title");
                    maptype = (List<Map<String, ArrayList>>) document.get("type");
                    mapyear = (List<Map<String, ArrayList>>) document.get("year");
                    mapfirstText = (List<Map<String, ArrayList>>) document.get("firstText");
                    mapsecondText = (List<Map<String, ArrayList>>) document.get("secondText");
                    mapduration = (List<Map<String, ArrayList>>) document.get("duration");

                    int index = 0;
                    for (int i = 0; i < mapid.size(); i++) {
                        Log.d("Mapinyo",String.valueOf(mapid.get(i)));
                        if (String.valueOf(mapid.get(i)).equals(extras.getString("MovieID")))
                            index = i;
                    }

                    update("id",mapid,index);
                    update("img",mapimg,index);
                    update("title",maptitle,index);
                    update("type",maptype,index);
                    update("year",mapyear,index);
                    update("firstText",mapfirstText,index);
                    update("secondText",mapsecondText,index);
                    update("duration",mapduration,index);

                }
            }
        });
    }

    private void update(String field, List list, int index) {
        list.remove(index);
        Firedb.collection("MovieLists")
                .document(user.getUid())
                .update(field, list);
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

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    mapid = (List<Map<String, ArrayList>>) document.get("id");
                    mapimg = (List<Map<String, ArrayList>>) document.get("img");
                    maptitle = (List<Map<String, ArrayList>>) document.get("title");
                    maptype = (List<Map<String, ArrayList>>) document.get("type");
                    mapyear = (List<Map<String, ArrayList>>) document.get("year");
                    mapfirstText = (List<Map<String, ArrayList>>) document.get("firstText");
                    mapsecondText = (List<Map<String, ArrayList>>) document.get("secondText");
                    mapduration = (List<Map<String, ArrayList>>) document.get("duration");

                    isMovieSaved(mapid.size());

                } else {
                    SetMovieNotSaved();
                }
            }
        });
    }

    private void isMovieSaved(int size) {
        SetMovieNotSaved();
        for (int i = 0; i < size; i++) {
            if (String.valueOf(mapid.get(i)).equals(movieID))
                SetMovieSaved();
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


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
        blackFilter.setVisibility(View.INVISIBLE);
        Bundle extras = getIntent().getExtras();
        movieID = extras.getString("MovieID");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieID = null;
    }
}