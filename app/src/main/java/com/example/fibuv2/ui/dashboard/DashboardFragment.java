package com.example.fibuv2.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.example.fibuv2.Admin;
import com.example.fibuv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean ifmoviexists = false;
    private TextView moviedetail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);

        ImageView notfoundIMAGE = root.findViewById(R.id.notFoundImage);
        TextView notfoundText = root.findViewById(R.id.notFoundText);
        Button coolBtn = root.findViewById(R.id.coolbutton);
        ImageButton coolsearchBtn = root.findViewById(R.id.coolsearchbtn);
        EditText searchBar = root.findViewById(R.id.search_bar);
        ProgressBar progressBar = root.findViewById(R.id.searchProgressBar);
        CardView blue = root.findViewById(R.id.blueCard);
        CardView yellow = root.findViewById(R.id.yellowCard);
        CardView green = root.findViewById(R.id.greenCard);
        CardView red = root.findViewById(R.id.redCard);


        moviedetail = root.findViewById(R.id.movieDetails);



        notfoundIMAGE.setVisibility(View.INVISIBLE);
        notfoundText.setVisibility(View.INVISIBLE);
        coolBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);


        coolsearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!searchBar.getText().toString().isEmpty()){

                    progressBar.setVisibility(View.VISIBLE);


                    ifdataexists(searchBar.getText().toString());

                    blue.setVisibility(View.INVISIBLE);
                    yellow.setVisibility(View.INVISIBLE);
                    green.setVisibility(View.INVISIBLE);
                    red.setVisibility(View.INVISIBLE);

                    if(ifmoviexists){

                        //Movie does not exist
                notfoundIMAGE.setVisibility(View.VISIBLE);
                notfoundText.setVisibility(View.VISIBLE);
                coolBtn.setVisibility(View.VISIBLE);
                Log.d("doesMovieExit","Movie Doesn't Exit");
                    }
                    else
                    {
                        Log.d("doesMovieExit","Movie Exits");
                        //Movie exits
                        moviedetail.setVisibility(View.VISIBLE);
                        searchBar.setText(null);
                    }

                progressBar.setVisibility(View.INVISIBLE);
                }


            }
        });

        coolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Admin.class);
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

    private void ifdataexists(String search){



        DocumentReference docRef = db.collection("movies").document(search);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("ifDataExists", "DocumentSnapshot data: " + document.getString("name"));
                        ifmoviexists=true;
                        moviedetail.setText(document.getData().toString());

                    } else {
                        Log.d("ifDataExists", "No such document");

                    }
                } else {
                    Log.d("ifDataExists", "get failed with ", task.getException());
                }
            }
        });

    }
}