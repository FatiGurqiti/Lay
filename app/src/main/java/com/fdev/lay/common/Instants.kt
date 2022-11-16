@file:JvmName("InstantsUtils")

package com.fdev.lay.common

import androidx.lifecycle.MutableLiveData
import com.fdev.lay.common.enums.UserLoginStatus
import com.fdev.lay.common.models.SavedMovieListModel

object Instants {
    var canShowUserName = false

    var isInternetAvailable = false

    val isEmptyFavouriteList: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    val savedMovieDetailsLiveData: MutableLiveData<SavedMovieListModel> by lazy { MutableLiveData<SavedMovieListModel>() }

    @JvmField
    var isSavedMovieDataLoaded: Boolean = false

    val savedMovieIds: MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
}