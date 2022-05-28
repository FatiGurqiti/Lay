package com.lay.fibuv2.movieDetails;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.ViewModel;
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

    public void prepareDetails(String movieID, String moviePhoto) {
        DetailsAPI.getDetails(movieID);
        RateAPI.rate(movieID);
        GetMoreLikeThisAPI.getmorelikethiss(movieID);
        SearchAPI.autoCompleteAPI(GetMoreLikeThisAPI.morelikethis);
        moviePhoto = this.moviePhoto;
    }

    public void intialanize(Bundle extras, TextView firstText, TextView title, TextView year, TextView time, TextView type, TextView rateText, TextView secondText, TextView products, ImageView moreLikeThisPicture, ImageView moreLikeThisFilter, TextView suggestionTitleText, ImageView detailsThumbnail) {
        isSuggestionPage = extras.getBoolean("IsSuggestedMovie");
        DetailsAPI detailsAPI = new DetailsAPI();

        movieTitle = detailsAPI.getName();
        movieYear = detailsAPI.getYear();
        Runingtime = detailsAPI.getRunningTimeInMinutes();
        Type = detailsAPI.getGenresList().get(0).toString();
        for (int j = 1; j < detailsAPI.getGenresList().size(); j++) {
            Type += ", " + detailsAPI.getGenresList().get(j).toString();
        }

        if (detailsAPI.getPlotOutlineList().size() > 0) {
            FistText = isNull(detailsAPI.getPlotOutlineList().get(0));
            SecondText = isNull(detailsAPI.getPlotOutlineList().get(0));
            detailsAPI.clearPlotOutlineList();
        } else {
            FistText = "";
            SecondText = "";
        }
        SuggestionImg = SearchAPI.movieImageUrl.get(0);
        SuggestionTitle = SearchAPI.movieTitle.get(0);
        SuggestionID = GetMoreLikeThisAPI.morelikethis;

        title.setText(movieTitle);
        year.setText(movieYear);
        rateText.setText(RateAPI.contentRate);
        if (Runingtime != "") minutesToHours(Integer.parseInt(Runingtime));
        else minutesToHours(0);
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
