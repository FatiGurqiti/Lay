package com.fdev.lay.common.models

data class SavedMovieListModel(
    val id: ArrayList<String>,
    val imgURL: ArrayList<String>,
    val title: ArrayList<String>,
    val type: ArrayList<String>,
    val year: ArrayList<String>,
    val firstText: ArrayList<String>,
    val secondText: ArrayList<String>,
    val duration: ArrayList<String>
)
