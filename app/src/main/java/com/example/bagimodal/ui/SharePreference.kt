package com.example.bagimodal.ui

import android.content.Context
import android.content.SharedPreferences

class SharePreference(val context: Context) {

    private val PREFSNAME = "dataUser"
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    fun getValueString(KEY_NAME: String): String? = sharedPref.getString(KEY_NAME, null)

}