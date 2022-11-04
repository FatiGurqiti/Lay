package com.fdev.lay.ui.movieDetails;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.ViewModel;

import com.fdev.lay.data.remote.api.DetailsAPI;
import com.fdev.lay.data.remote.api.GetMoreLikeThisAPI;
import com.fdev.lay.data.remote.api.RateAPI;
import com.fdev.lay.data.remote.api.SearchAPI;
import com.fdev.lay.data.local.database.DatabaseHandler;

import java.util.Objects;


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
    private static boolean isLiteMode;

    public void prepareDetails(String movieID, String moviePhoto, String movieTitle, Activity activity) {
        DatabaseHandler db = new DatabaseHandler(activity);
        this.moviePhoto = moviePhoto;
        this.movieTitle = movieTitle;
        DetailsAPI.getDetails(movieID);

        if (!db.getIsLiteMode()) {
            RateAPI.rate(movieID);
            GetMoreLikeThisAPI.getmorelikethiss(movieID);
            isLiteMode = false;
        }
        else isLiteMode = true;
    }

    public void intialanize(Bundle extras, TextView firstText, TextView title, TextView year, TextView time, TextView type, TextView rateText, TextView secondText, TextView products, ImageView moreLikeThisPicture, ImageView moreLikeThisFilter, TextView suggestionTitleText, ImageView detailsThumbnail, ImageView rateBg) {
        isSuggestionPage = extras.getBoolean("IsSuggestedMovie");
        DetailsAPI detailsAPI = new DetailsAPI();

        movieTitle = extras.getString("MovieTitle");
        movieYear = detailsAPI.getYear();
        Runingtime = detailsAPI.getRunningTimeInMinutes();
        moviePhoto = extras.getString("MoviePhoto");
        if (detailsAPI.getGenresList() != null) {
            Type = detailsAPI.getGenresList().get(0).toString();
            for (int j = 1; j < detailsAPI.getGenresList().size(); j++)
                Type += ", " + detailsAPI.getGenresList().get(j).toString();
        } else Type = "";

        if (detailsAPI.getPlotOutlineList().size() > 0) {
            FistText = isNull(detailsAPI.getPlotOutlineList().get(0));
            SecondText = isNull(detailsAPI.getPlotOutlineList().get(0));
            detailsAPI.clearPlotOutlineList();
        } else {
            FistText = "";
            SecondText = "";
        }


        title.setText(extras.getString("MovieTitle"));
        year.setText(movieYear);
        minutesToHours(Integer.parseInt(Runingtime));
        time.setText(hours + "h " + minutes + "m");
        type.setText(Type);
        firstText.setText(FistText);
        secondText.setText(SecondText);

        if (FistText.equals(SecondText))
            secondText.setVisibility(View.GONE);

        if (!isLiteMode){

        rateText.setText(RateAPI.contentRate);
        rateText.bringToFront();

            SearchAPI.autoCompleteAPI(GetMoreLikeThisAPI.morelikethis);
            int index = 0;
            for (int i = 1; i < SearchAPI.movieTitle.size(); i++) {

                if ((SearchAPI.movieQ.get(i) != null) &&
                        !Objects.equals(SearchAPI.movieQ.get(i), "video game")) {
                    index = i;
                    break;
                }
            }
            SuggestionImg = SearchAPI.movieImageUrl.get(index);
            SuggestionTitle = SearchAPI.movieTitle.get(index);
            SuggestionID = GetMoreLikeThisAPI.morelikethis;

        }
        else {
            rateText.setVisibility(View.INVISIBLE);
            rateBg.setVisibility(View.INVISIBLE);
            moreLikeThisPicture.setVisibility(View.INVISIBLE);
            moreLikeThisFilter.setVisibility(View.INVISIBLE);
        }

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
