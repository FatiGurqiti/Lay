package com.lay.fibuv2.movieDetails;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lay.fibuv2.R;
import com.lay.fibuv2.RoundedTransformation;
import com.lay.fibuv2.ThumbnailFullScreen;
import com.lay.fibuv2.database.DatabaseHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

    private ProgressBar progressBar;
    private ImageView blackFilter;

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
    private MovieDetailsViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details2);
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        ImageView detailsThumbnail = findViewById(R.id.imageThumbnailinDetails);
        ImageView moreLikeThisPicture = findViewById(R.id.morelikethisimage1);
        ImageView moreLikeThisFilter = findViewById(R.id.morelikethisfilter);
        progressBar = findViewById(R.id.progressbarindetail);
        blackFilter = findViewById(R.id.detailBlackFilter);
        save = findViewById(R.id.save);
        saved = findViewById(R.id.saved);
        TextView suggestionTitleText = findViewById(R.id.morelikethisTitle);
        TextView rateText = findViewById(R.id.rate);
        ImageView rateStar = findViewById(R.id.star);
        ImageView rateBg = findViewById(R.id.RateBackground);
        TextView products = findViewById(R.id.products);
        TextView readMore = findViewById(R.id.readMore);
        TextView firstText = findViewById(R.id.firstText);
        TextView secondText = findViewById(R.id.secondText);
        TextView title = findViewById(R.id.title);
        TextView year = findViewById(R.id.year);
        TextView time = findViewById(R.id.time);
        TextView type = findViewById(R.id.type);

        try {
            Bundle extras = getIntent().getExtras();
            viewModel.intialanize(extras, firstText, title, year, time, type, rateText, secondText, products, moreLikeThisPicture, moreLikeThisFilter, suggestionTitleText, detailsThumbnail,rateBg);
            Thread.sleep(50);
            if (db.getIsLiteMode()) {
                rateText.setVisibility(View.GONE);
                rateStar.setVisibility(View.GONE);
                rateBg.setVisibility(View.GONE);
            }
            intianalizeOldData();
            save.setOnClickListener(v -> {

                saveMovie();
                SetMovieSaved();
                Toast toast = Toast.makeText(getApplicationContext(),
                        viewModel.movieTitle + " is added to your list",
                        Toast.LENGTH_SHORT);
                toast.show();
            });
            saved.setOnClickListener(v -> {
                SetMovieNotSaved();  //Set's icon unsaved
                unSaveMovie();      //Unsaves movie
                Toast toast = Toast.makeText(getApplicationContext(),
                        viewModel.movieTitle + " is removed from your list",
                        Toast.LENGTH_SHORT);
                toast.show();


            });

            readMore.setOnClickListener(v -> {
                secondText.setVisibility(View.VISIBLE);
                readMore.setVisibility(View.INVISIBLE);
            });

            Picasso.get().load(extras.getString("MoviePhoto")).transform(new RoundedTransformation(25, 0)).fit().centerCrop(700).into(detailsThumbnail);

            if (!db.getIsLiteMode()) {
                // Lite mode is off
                Picasso.get().load(viewModel.SuggestionImg).transform(new RoundedTransformation(50, 0)).fit().centerCrop(300).into(moreLikeThisPicture);
                Picasso.get().load(R.drawable.black_filer_resource).transform(new RoundedTransformation(55, 0)).fit().centerCrop(700).into(moreLikeThisFilter);
                suggestionTitleText.setText(viewModel.SuggestionTitle);

                moreLikeThisPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);
                        blackFilter.setVisibility(View.VISIBLE);
                        openMovieDetail(viewModel.SuggestionID, viewModel.SuggestionImg,viewModel.SuggestionTitle);
                    }
                });
            } else {
                products.setVisibility(View.GONE);
                moreLikeThisPicture.setVisibility(View.GONE);
                moreLikeThisFilter.setVisibility(View.GONE);
                suggestionTitleText.setVisibility(View.GONE);

                rateText.setVisibility(View.GONE);
                rateStar.setVisibility(View.GONE);
            }

            if (viewModel.isSuggestionPage) {
                products.setVisibility(View.GONE);
                moreLikeThisPicture.setVisibility(View.GONE);
                suggestionTitleText.setVisibility(View.GONE);
                moreLikeThisFilter.setVisibility(View.GONE);
            }

            detailsThumbnail.setOnClickListener(v -> {
                Intent intent = new Intent(MovieDetails.this, ThumbnailFullScreen.class);
                intent.putExtra("moviePhoto", viewModel.moviePhoto);
                startActivity(intent);
            });

        } catch (InterruptedException e) {
            Log.d("firstOrlast", "3");
            e.printStackTrace();
            Log.d("firstOrlast", "4");
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
        blackFilter.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        viewModel.movieID = extras.getString("MovieID");
        viewModel.moviePhoto = extras.getString("MoviePhoto");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.movieID = null;
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

                } else
                    SetMovieNotSaved();
            }
        });
    }

    private void saveMovie() {

        // add new array list to the old one
        if (mapid != null) // User has previously saved data
        {
            for (int i = 0; i < mapid.size(); i++) {

                if (!String.valueOf(mapid.get(i)).equals(viewModel.movieID)) {

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
            id.add(viewModel.movieID);
            img.add(viewModel.moviePhoto);
            title.add(viewModel.movieTitle);
            year.add(viewModel.movieYear);
            duration.add(viewModel.Runingtime);
            type.add(viewModel.Type);
            firstText.add(viewModel.FistText);
            secondText.add(viewModel.SecondText);

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

            id.add(viewModel.movieID);
            img.add(viewModel.moviePhoto);
            title.add(viewModel.movieTitle);
            year.add(viewModel.movieYear);
            duration.add(viewModel.Runingtime);
            type.add(viewModel.Type);
            firstText.add(viewModel.FistText);
            secondText.add(viewModel.SecondText);

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


    private void isMovieSaved(int size) {
        SetMovieNotSaved();
        for (int i = 0; i < size; i++) {
            if (String.valueOf(mapid.get(i)).equals(viewModel.movieID)) {
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

    private void openMovieDetail(String MovieID, String MoviePhoto, String MovieTitle) {
        Intent intent = new Intent(this, MovieDetails.class);
        viewModel.prepareDetails(MovieID, MoviePhoto, MovieTitle,this);
        intent.putExtra("MovieID", MovieID);
        intent.putExtra("MoviePhoto", MoviePhoto);
        intent.putExtra("MovieTitle", MovieTitle);
        intent.putExtra("IsSuggestedMovie", true); //For not showing more suggestion in next page
        startActivity(intent);
    }


}
