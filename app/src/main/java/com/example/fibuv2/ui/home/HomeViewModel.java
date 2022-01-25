package com.example.fibuv2.ui.home;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fibuv2.R;
import com.example.fibuv2.RoundedTransformation;
import com.example.fibuv2.Search;
import com.example.fibuv2.api.SearchAPI;
import com.squareup.picasso.Picasso;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

}