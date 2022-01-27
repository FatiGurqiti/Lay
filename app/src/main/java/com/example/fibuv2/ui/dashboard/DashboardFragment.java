package com.example.fibuv2.ui.dashboard;

import android.content.Intent;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fibuv2.MovieDetails;
import com.example.fibuv2.R;
import com.example.fibuv2.Search;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar pg;
    private ArrayList<String> id = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);


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


        ProgressBar pg = root.findViewById(R.id.progressBarInMainDashBorad);
        pg.setVisibility(View.INVISIBLE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference movieRate = db.collection("MovieRate");


        String TAG = "rateMovie";
        DocumentReference docRef = db.collection("MovieRate").document(user.getUid());
        CollectionReference collectionReference = db.collection("MovieRates");
        //collectionReference.orderBy("rate").limit(4);
        collectionReference.whereGreaterThan("rate", 1).orderBy("rate");

        Log.d(TAG, String.valueOf(collectionReference.orderBy("rate").limit(4)));




        CollectionReference collectionRef = db.collection("MovieRate");
        collectionRef.orderBy("rate", Query.Direction.DESCENDING).limit(4)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int i =0;i<5;i++){
                        Log.d(TAG, String.valueOf(queryDocumentSnapshots.getDocuments().get(0).get("id")));
                        Log.d(TAG, String.valueOf(queryDocumentSnapshots.getDocuments().get(0).get("img")));
                        Log.d(TAG, String.valueOf(queryDocumentSnapshots.getDocuments().get(0).get("name")));
                        Log.d(TAG, String.valueOf(queryDocumentSnapshots.getDocuments().get(0).get("rate")));

                        id.add(String.valueOf(queryDocumentSnapshots.getDocuments().get(i).get("id")));
                            Log.d(TAG, "IdListinLoop" + String.valueOf(id));
                        }

                    }
                });


        coolsearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pg.setVisibility(View.VISIBLE);

                String searchbarText = searchBar.getText().toString();


                if (!searchbarText.isEmpty()) {

                    Intent intent = new Intent(getActivity(), Search.class);

                    Log.d("SearchContent", searchbarText);
                    intent.putExtra("searchContent", searchbarText);
                    startActivity(intent);
                }

                pg.setVisibility(View.INVISIBLE);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovieDetail("The Lord of the Rings");
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovieDetail("Avengers");
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovieDetail("Fight Club");
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovieDetail("Dune");
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

    private void openMovieDetail(String movieName) {
        Intent intent = new Intent(getActivity(), MovieDetails.class);
        intent.putExtra("movieName", movieName);
        startActivity(intent);
    }


}