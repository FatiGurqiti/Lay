package com.fdev.lay.presentation.ui.favourite_list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavouriteListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FavouriteListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

}