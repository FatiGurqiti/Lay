package com.fdev.lay.ui.base

import androidx.lifecycle.ViewModel
import com.fdev.lay.common.Instants
import com.fdev.lay.common.models.SavedMovieListModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainMainViewModel : ViewModel() {

    fun loadMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            getSeenMovies()
            getSavedMovies()
        }
    }

    private fun getSavedMovies() {
        val firebaseDb = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val docRef = firebaseDb.collection("MovieLists").document(user!!.uid)

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document: DocumentSnapshot = task.getResult()
                if (document.exists()) {
                    Instants.savedMovieDetailsLiveData.value = SavedMovieListModel(
                        id = (document.get("id") as ArrayList<String>),
                        imgURL = (document.get("img") as ArrayList<String>),
                        title = (document.get("title") as ArrayList<String>),
                        type = (document.get("type") as ArrayList<String>),
                        year = (document.get("year") as ArrayList<String>),
                        firstText = (document.get("firstText") as ArrayList<String>),
                        secondText = (document.get("secondText") as ArrayList<String>),
                        duration = (document.get("duration") as ArrayList<String>)
                    )
                }
                Instants.isSavedMovieDataLoaded = true
            }
        }
    }

    private fun getSeenMovies() {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val docRef = db.collection("SeenMovies").document(user!!.uid)
        docRef.get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists())
                    Instants.savedMovieIds.value = document["id"] as ArrayList<String>
            }
        }
    }
}