package com.fdev.lay.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainMainViewModel extends ViewModel {

    private MutableLiveData<String> username;

    public MutableLiveData<String> getUsername() {
        if (username == null) {
            username = new MutableLiveData<String>();
        }
        return username;
    }

    public void setCurrentName(){
        if (username != null)
        username.setValue("username");
    }
}
