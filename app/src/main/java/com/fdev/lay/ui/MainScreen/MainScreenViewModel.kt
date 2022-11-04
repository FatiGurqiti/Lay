package com.fdev.lay.ui.MainScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdev.lay.common.Constants
import com.fdev.lay.common.Instants
import com.fdev.lay.common.Instants.canShowUserName
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel : ViewModel() {

    private var username: MutableLiveData<String?>? = null
    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            setCurrentName()
            isFavouriteListEmpty()
        }
    }

    private fun isFavouriteListEmpty() {
        val firebase = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val docRef = firebase.collection("MovieLists").document(user!!.uid)

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document: DocumentSnapshot = task.result
                Instants.isEmptyFavouriteList.value = (document.exists())
            }
        }
    }

    fun getUsername(): MutableLiveData<String?>? {
        if (username == null) {
            username = MutableLiveData()
        }
        return username
    }

    private suspend fun setCurrentName() {
        if (getUsername() != null) {
            if (canShowUserName) {
                db.collection("users")
                    .whereEqualTo("email", mAuth.currentUser!!.email)
                    .get()
                    .addOnCompleteListener { task: Task<QuerySnapshot> ->
                        if (task.isSuccessful) {
                            for (document in task.result) {
                                username!!.value = document.getString("username")
                            }
                        }
                    }
            }
            withContext(Dispatchers.Main) { username!!.value = "" }
        }
    }
}