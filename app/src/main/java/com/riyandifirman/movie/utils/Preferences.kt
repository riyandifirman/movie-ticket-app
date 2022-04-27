package com.riyandifirman.movie.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences (val context: Context) {
    companion object {
        const val MEETING_PREF = "USER_PREFF"
    }

    val sharedPreferences = context.getSharedPreferences(MEETING_PREF, 0)

    fun setValue(key: String, value: String) {
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String): String? {
        return sharedPreferences.getString(key, "")
    }
}