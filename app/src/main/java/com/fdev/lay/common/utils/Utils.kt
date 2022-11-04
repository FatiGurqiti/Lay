@file:JvmName("LayUtils")
package com.fdev.lay.common.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import com.fdev.lay.common.models.MovieModel
import com.fdev.lay.common.models.SavedMovieListModel

object Utils {
    @JvmStatic
    fun isNetworkAvailable(activity: Activity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun SavedMovieListModel.toMovieModel(position: Int): MovieModel {
        return MovieModel(
            id[position],
            imgURL[position],
            title[position],
            type[position],
            year[position],
            firstText[position],
            secondText[position],
            duration[position]
        )
    }
}