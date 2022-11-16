package com.fdev.lay.ui.login

import android.app.Activity
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdev.lay.common.enums.UserLoginStatus
import com.fdev.lay.common.enums.setUserStatus
import com.fdev.lay.data.local.database.DatabaseHandler
import com.fdev.lay.ui.MainScreen.HomePage
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class LoginViewModel : ViewModel() {

    fun userLogin(
        activity: Activity,
        progressBar: ProgressBar,
        email: String,
        password: String
    ) {
        try {
            if (!isPasswordValid(password) && !isUserNameValid(email)) {
                Toast.makeText(
                    activity,
                    "Would you mind if you fill the inputs?",
                    Toast.LENGTH_LONG
                )
                    .show()
                progressBar.visibility = View.INVISIBLE
            } else {
                val mAuth = FirebaseAuth.getInstance()

                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        activity
                    ) { task: Task<AuthResult?> ->
                        viewModelScope.launch(Dispatchers.IO) {
                            if (task.isSuccessful) {
                                //Set's the log in true. So, the user won't have to sign in again
                                withContext(Dispatchers.Main) {
                                    with(activity) {
                                        setUserStatus(activity, UserLoginStatus.LOGGED_IN)
                                        DatabaseHandler(activity).setLoginTrue()
                                        val intent = Intent(activity, HomePage::class.java)
                                        intent.putExtra("email", email)
                                        startActivity(intent)
                                        finish()
                                        progressBar.visibility = View.INVISIBLE
                                    }
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    progressBar.visibility = View.INVISIBLE
                                    Toast.makeText(
                                        activity, "Authentication failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
            }
        } catch (e: Exception) {
            Toast.makeText(activity, "Unexpected Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isUserNameValid(username: String?): Boolean {
        if (username.isNullOrEmpty() || username == "")
            return false

        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            !username.trim { it <= ' ' }.isEmpty()
        }
    }

    private fun isPasswordValid(password: String?): Boolean {
        if (password.isNullOrEmpty() || password == "")
            return false

        return (password.trim { it <= ' ' }.length) > 5
    }
}