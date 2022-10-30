package com.fdev.lay.ui.MainScreen.favourite_list.List_Fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fdev.lay.common.models.local.SavedMovieModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteListViewModel : ViewModel() {

    val savedMovieDetailsLiveData: MutableLiveData<SavedMovieModel> by lazy { MutableLiveData<SavedMovieModel>() }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getSavedMovies()
        }
    }

    private suspend fun getSavedMovies() {
        val firebaseDb = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val docRef = firebaseDb.collection("MovieLists").document(user!!.uid)

            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document: DocumentSnapshot = task.getResult()
                    if (document.exists()) {
                        savedMovieDetailsLiveData.value = SavedMovieModel(
                            id = document.get("id") as ArrayList<String>,
                            imgURL = document.get("img") as ArrayList<String>,
                            title = document.get("title") as ArrayList<String>,
                            type = document.get("type") as ArrayList<String>,
                            year = document.get("year") as ArrayList<String>,
                            firstText = document.get("firstText") as ArrayList<String>,
                            secondText = document.get("secondText") as ArrayList<String>,
                            duration = document.get("duration") as ArrayList<String>
                        )
                    }
                }
            }
        }
    }