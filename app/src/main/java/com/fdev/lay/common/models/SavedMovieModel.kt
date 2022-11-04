package com.fdev.lay.common.models

data class MovieModel(
    val id: String,
    val imgURL: String,
    val title: String,
    val type: String,
    val year: String,
    val firstText: String,
    val secondText: String,
    val duration: String
)
