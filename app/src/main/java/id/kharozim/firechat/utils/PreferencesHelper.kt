package id.kharozim.firechat.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesHelper (context: Context){
    private val PREF_NAME = "id.kharozim.firechat"
    private val sharedPref: SharedPreferences by lazy { context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) }

    private val tokenKey = "TOKEN_KEY"
    private val uidKey = "UID_KEY"

    var uid
        get() = sharedPref.getString(uidKey, "") ?: ""
        set(value) {
            sharedPref.edit { putString(uidKey, value) }
        }

    var token
        get() = sharedPref.getString(tokenKey, "") ?: ""
        set(value) {
            sharedPref.edit { putString(tokenKey, value) }
        }

    fun onClear() {
        sharedPref.edit().clear().apply()
    }

//    fun put(key: String, value: String) {
//        editor.putString(key, value)
//            .apply()
//    }
//
//    fun getString(key: String): String? {
//        return sharedPref.getString(key, null)
//    }
//
//    fun put(key: String, value: Boolean) {
//        editor.putBoolean(key, value)
//            .apply()
//    }
//
//    fun getBoolean(key: String): Boolean {
//        return sharedPref.getBoolean(key, false)
//    }
//
//    fun clear(){
//        editor.clear().apply()
//    }
}