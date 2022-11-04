package com.fdev.lay.ui.MainScreen.favourite_list.List_Fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fdev.lay.common.models.SavedMovieListModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class FavouriteListViewModel : ViewModel() {
    val savedMovieDetailsLiveData: MutableLiveData<SavedMovieListModel> by lazy { MutableLiveData<SavedMovieListModel>() }
    val savedMovieIds: MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getSavedMovies()
            getSeenMovies()
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
                    savedMovieDetailsLiveData.value = SavedMovieListModel(
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

    fun like(id: String, name: String, img: String, like: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("MovieRate").document(id)
            docRef.get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        val rate = document.getLong("rate")!!.toInt()
                        if (like) docRef.update("rate", rate + 1)
                        else if (rate > 1) docRef.update("rate", rate - 1)
                    } else {
                        val movieRate: MutableMap<String, String> = HashMap()
                        movieRate["id"] = id
                        movieRate["name"] = name
                        movieRate["img"] = img
                        if (like) movieRate["rate"] = "1"
                        db.collection("MovieRate").document(id).set(movieRate)
                        docRef.update("rate", 1)
                    }
                }
            }
        }
    }

    fun addSeenMovies(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var seenMovieId = ArrayList<String>()
            val db = FirebaseFirestore.getInstance()
            val user = FirebaseAuth.getInstance().currentUser
            val docRef = db.collection("SeenMovies").document(user!!.uid)

            //Get previous data
            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) seenMovieId = document["id"] as ArrayList<String>
                    else {
                        seenMovieId.add(id)
                        val seenMovie: MutableMap<String, ArrayList<*>> = HashMap()
                        seenMovie["id"] = seenMovieId
                        db.collection("SeenMovies")
                            .document(user.uid)
                            .set(seenMovie)
                    }
                }
                if (seenMovieId.size > 0) {
                    for (i in seenMovieId.indices) {
                        if (seenMovieId[i] != id) seenMovieId.add(id)
                        //Removes duplicated data
                        if (i + 1 < seenMovieId.size) {
                            if (seenMovieId[i] == seenMovieId[i + 1]) seenMovieId.removeAt(i + 1)
                        }
                    }
                    val data: MutableMap<String, ArrayList<*>> = HashMap()
                    data["id"] = seenMovieId
                    db.collection("SeenMovies").document(user.uid)[data] = SetOptions.merge()
                }
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
                if (document.exists()) savedMovieIds.value = document["id"] as ArrayList<String>
            }
        }
    }
}