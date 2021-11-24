package com.example.fibuv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;


public class Admin<YonetmekEkle> extends AppCompatActivity  {

    private EditText MovieName,MovieType,MovieYear,MovieURL;
    private MultiAutoCompleteTextView MovieAbout;
    private Button AddButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        MovieName = (EditText) findViewById(R.id.movie_name);
        MovieType = (EditText) findViewById(R.id.movie_type);
        MovieYear= (EditText) findViewById(R.id.movie_year);
        MovieAbout= (MultiAutoCompleteTextView) findViewById(R.id.movie_about);
        MovieURL= (EditText) findViewById(R.id.movie_url);
        AddButton = (Button) findViewById(R.id.AddButton);



        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String movieName = MovieName.getText().toString();
                String movieType = MovieType.getText().toString();
                String movieYear = MovieYear.getText().toString();
                String movieAbout = MovieAbout.getText().toString();
                String movieURL = MovieAbout.getText().toString();

                addMovie(movieName,movieType,movieYear,movieAbout,movieURL);
            }
        });

    }



    private void addMovie(String name, String type, String year,String about,String movieURL) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("movies");


        Map<String, Object> movie = new HashMap<>();
        //user.put("userID", userF.getUid().toString());
        movie.put("name", name);
        movie.put("type", type);
        movie.put("year", year);
        movie.put("about", about);
        movie.put("url", movieURL);

        db.collection("movies").document(name)
                .set(movie)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Succeeded
                        Toast.makeText(Admin.this, "Movie " + name + " added succesfully",
                                Toast.LENGTH_SHORT).show();
                        emptyInputs();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Failed
                Toast.makeText(Admin.this, "Failed to add " + movie,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void emptyInputs(){
        MovieAbout.setText("");
        MovieName.setText("");
        MovieYear.setText("");
        MovieType.setText("");
        MovieURL.setText("");
    }



}
