package com.lay.fibuv2.movieDetails;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lay.fibuv2.api.DetailsAPI;
import com.lay.fibuv2.api.GetMoreLikeThisAPI;
import com.lay.fibuv2.api.RateAPI;
import com.lay.fibuv2.api.SearchAPI;
import com.lay.fibuv2.database.DatabaseHandler;


public class MovieDetailsViewModel extends ViewModel {

    String movieID;
    String moviePhoto;
    String movieTitle;
    String movieYear;
    String Runingtime;
    String Type;
    String FistText;
    String SecondText;
    String SuggestionImg;
    String SuggestionTitle;
    String SuggestionID;
    boolean isSuggestionPage;

    private static int hours;
    private static int minutes;

    private FirebaseFirestore Firedb = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference docRef = Firedb.collection("MovieLists").document(user.getUid());

   public void intialanize(Bundle extras, Activity activity, TextView firstText, TextView title, TextView year, TextView time, TextView type, TextView rateText, TextView secondText, TextView products, ImageView moreLikeThisPicture, ImageView moreLikeThisFilter, TextView suggestionTitleText){
        movieID = extras.getString("MovieID");
        moviePhoto = extras.getString("MoviePhoto");
        isSuggestionPage = extras.getBoolean("IsSuggestedMovie");
        DatabaseHandler db = new DatabaseHandler(activity);

        DetailsAPI.getDetails(movieID);
        movieTitle = isNull(DetailsAPI.name);
        movieYear = isNull(DetailsAPI.year);
        Runingtime = isNull(DetailsAPI.runningTimeInMinutes);
        Type = isNull(DetailsAPI.genresList.get(0).toString());
        for (int j = 1; j < DetailsAPI.genresList.size(); j++) {
            Type += ", " + DetailsAPI.genresList.get(j).toString();
        }

        if (DetailsAPI.plotOutlineList.size() > 0) {
            FistText = isNull(DetailsAPI.plotOutlineList.get(0));
            SecondText = isNull(DetailsAPI.plotOutlineList.get(1));
            DetailsAPI.plotOutlineList.clear(); //Clear source data after using it
        } else {
            FistText = "";
            SecondText = "";
        }
        if (!db.getIsLiteMode() || !isSuggestionPage) {
            RateAPI.rate(movieID);
            //Load Suggestion Data
            GetMoreLikeThisAPI.getmorelikethiss(movieID);
            SearchAPI.autoCompleteAPI(GetMoreLikeThisAPI.morelikethis);
            SuggestionImg = SearchAPI.movieImageUrl.get(0);
            SuggestionTitle = SearchAPI.movieTitle.get(0);
            SuggestionID = GetMoreLikeThisAPI.morelikethis;
        }


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
           products.setVisibility(View.GONE);
           moreLikeThisPicture.setVisibility(View.GONE);
           moreLikeThisFilter.setVisibility(View.GONE);
           suggestionTitleText.setVisibility(View.GONE);
       }
    }

    private String isNull(String text) {
        if (TextUtils.isEmpty(text)) return "";
        else return text;
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
}
