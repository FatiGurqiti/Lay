package com.example.fibuv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fibuv2.api.GetDetailsAPITest;
import com.example.fibuv2.api.MoreLikeThisAPITest;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details2);


        Bundle extras = getIntent().getExtras();
        String movieID = extras.getString("movieID");
        String movieTitle = extras.getString("movieTitle");
        String movieYear = extras.getString("movieYear");
        String movieCast = extras.getString("movieCast");
        String moviePhoto = extras.getString("moviePhoto");

        try {
            MoreLikeThisAPITest.get("[\"/title/tt0167261/\",\"/title/tt0120737/\",\"/title/tt0468569/\",\"/title/tt0110912/\",\"/title/tt0109830/\",\"/title/tt1375666/\",\"/title/tt0108052/\",\"/title/tt0080684/\",\"/title/tt0111161/\",\"/title/tt0068646/\",\"/title/tt0071562/\",\"/title/tt0137523/\",\"/title/tt0076759/\",\"/title/tt0816692/\",\"/title/tt0114369/\"]");
            GetDetailsAPITest.get("{\"id\":\"/title/tt0167261/\",\"title\":{\"@type\":\"imdb.api.title.title\",\"id\":\"/title/tt0167261/\",\"image\":{\"height\":1500,\"id\":\"/title/tt0167261/images/rm306845440\",\"url\":\"https://m.media-amazon.com/images/M/MV5BZGMxZTdjZmYtMmE2Ni00ZTdkLWI5NTgtNjlmMjBiNzU2MmI5XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg\",\"width\":964},\"runningTimeInMinutes\":179,\"title\":\"The Lord of the Rings: The Two Towers\",\"titleType\":\"movie\",\"year\":2002},\"certificates\":{\"US\":[{\"attributes\":[\"Preview Short\"],\"certificate\":\"PG\",\"certificateNumber\":38927,\"ratingsBody\":\"MPAA\",\"country\":\"US\"}]},\"ratings\":{\"canRate\":true,\"rating\":8.7,\"ratingCount\":1567551,\"topRank\":15},\"genres\":[\"Action\",\"Adventure\",\"Drama\",\"Fantasy\"],\"releaseDate\":\"2002-12-18\",\"plotOutline\":{\"id\":\"/title/tt0167261/plot/po0952965\",\"text\":\"While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.\"},\"plotSummary\":{\"author\":\"Jwelch5742\",\"id\":\"/title/tt0167261/plot/ps2971820\",\"text\":\"The continuing quest of Frodo and the Fellowship to destroy the One Ring. Frodo and Sam discover they are being followed by the mysterious Gollum. Aragorn, the Elf archer Legolas, and Gimli the Dwarf encounter the besieged Rohan kingdom, whose once great King Theoden has fallen under Saruman's deadly spell.\"}}");
            Log.d("Suggestion",MoreLikeThisAPITest.suggestion.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }



        ImageView detailsThumbnail = (ImageView) findViewById(R.id.imageThumbnailinDetails);
        ImageView moreLikeThisPicture1 = (ImageView) findViewById(R.id.morelikethisimage1);
        ImageView moreLikeThisPicture2 = (ImageView) findViewById(R.id.morelikethisimage2);
        ImageView moreLikeThisPicture3 = (ImageView) findViewById(R.id.morelikethisimage3);
        ImageView save = (ImageView) findViewById(R.id.save);
        ImageView saved = (ImageView) findViewById(R.id.saved);
        TextView readMore = (TextView) findViewById(R.id.readMore);
        TextView secondText = (TextView) findViewById(R.id.secondText);
        TextView title = (TextView) findViewById(R.id.title);
        TextView year = (TextView) findViewById(R.id.year);

        title.setText(movieTitle);
        year.setText(movieYear);
        secondText.setVisibility(View.INVISIBLE);
        saved.setVisibility(View.INVISIBLE);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saved.setVisibility(View.VISIBLE);
                save.setVisibility(View.INVISIBLE);

                Toast toast = Toast.makeText(getApplicationContext(),
                        movieTitle + " is added to your list",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setVisibility(View.VISIBLE);
                saved.setVisibility(View.INVISIBLE);

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


        Picasso.get().load(moviePhoto).transform(new RoundedTransformation(50, 0)).into(moreLikeThisPicture1);
        Picasso.get().load(moviePhoto).transform(new RoundedTransformation(50, 0)).into(moreLikeThisPicture2);
        Picasso.get().load(moviePhoto).transform(new RoundedTransformation(50, 0)).into(moreLikeThisPicture3);

        detailsThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetails.this, ThumbnailFullScreen.class);
                intent.putExtra("moviePhoto",moviePhoto);
                startActivity(intent);
            }
        });

    }
}