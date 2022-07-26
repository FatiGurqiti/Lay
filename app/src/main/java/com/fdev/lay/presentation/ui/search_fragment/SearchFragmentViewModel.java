package com.fdev.lay.presentation.ui.search_fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchFragmentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchFragmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}