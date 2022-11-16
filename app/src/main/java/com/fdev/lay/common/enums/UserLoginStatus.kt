package com.fdev.lay.common.enums

import android.app.Activity
import com.fdev.lay.data.local.sharedpreferences.LayDefaults

val FIRST_TIME = 0
val LOGGED_IN = 1
val LOGGED_OUT = 2

enum class UserLoginStatus {
    FIRST_TIME, LOGGED_IN, LOGGED_OUT
}

fun setUserStatus(activity: Activity, loginStatus: UserLoginStatus) {
    when (loginStatus) {
        UserLoginStatus.FIRST_TIME -> LayDefaults(activity).setLoginStatus(FIRST_TIME)
        UserLoginStatus.LOGGED_IN -> LayDefaults(activity).setLoginStatus(LOGGED_IN)
        UserLoginStatus.LOGGED_OUT -> LayDefaults(activity).setLoginStatus(LOGGED_OUT)
        else -> {}
    }
}

fun getLastStatus(activity: Activity): UserLoginStatus {
    return LayDefaults(activity).getLoginStatus()?.let {
        when (it) {
            FIRST_TIME -> UserLoginStatus.FIRST_TIME
            LOGGED_IN -> UserLoginStatus.LOGGED_IN
            LOGGED_OUT -> UserLoginStatus.LOGGED_OUT
            else -> UserLoginStatus.FIRST_TIME
        }
    } ?: UserLoginStatus.FIRST_TIME
}
