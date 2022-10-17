package zaym.`in`.card5min.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferencesManager(context: Context) {

    private var context: Context
    private var sharedPreferences: SharedPreferences
    private var editorSharedPreferences: SharedPreferences.Editor

    init {
        this.context = context
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.apply()
    }

    fun saveInt(key: String, value: Int){
        editorSharedPreferences.putInt(key, value).apply()
    }

    fun loadInt(key: String): Int{
        return sharedPreferences.getInt(key, 0)
    }

    fun saveString(key: String, value: String){
        editorSharedPreferences.putString(key, value).apply()
    }

    fun loadString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun saveBoolean(key: String, value: Boolean){
        editorSharedPreferences.putBoolean(key, value).apply()
    }

    fun loadBoolean(key: String): Boolean{
        return sharedPreferences.getBoolean(key, false)
    }


    fun getBoolean(key: String): Boolean{
        return sharedPreferences.getBoolean(key, false)
    }



}