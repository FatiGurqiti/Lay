package com.fdev.lay.data.local.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class LayDefaults(activity: AppCompatActivity) {

    private val LOGIN_KEY = "lay_loging_status"

    private val prefs_name = "lay_defaults"
    var editor: SharedPreferences? = null
    var editorEdit: SharedPreferences.Editor?

    init {
        editor = activity.getSharedPreferences(prefs_name, Context.MODE_PRIVATE)
        editorEdit = editor?.edit()
    }

    //User Login Status
    fun setLoginStatus(loginStatus: Int) {
        editorEdit?.putInt(LOGIN_KEY, loginStatus);
        editorEdit?.commit()
    }

    fun getLoginStatus(): Int? {
        return editor?.getInt(LOGIN_KEY, 0)
    }
}