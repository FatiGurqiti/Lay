@file:JvmName("InstantsUtils")

package com.fdev.lay.common

import androidx.lifecycle.MutableLiveData

object Instants {
    var canShowUserName = false

    var isInternetAvailable = false

    val isEmptyFavouriteList: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
}