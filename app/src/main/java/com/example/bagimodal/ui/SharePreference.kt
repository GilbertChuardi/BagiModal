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

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    fun getValueString(KEY_NAME: String): String? = sharedPref.getString(KEY_NAME, null)

    fun getValueInt(KEY_NAME: String): Int =sharedPref.getInt(KEY_NAME, 0)

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}