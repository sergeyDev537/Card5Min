package zaym.`in`.card5min.managers

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import zaym.`in`.card5min.models.ModelBase

class FirebaseApp(context: Context) {

    private var firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun sendEvent(stringParams: String, nameEventFirebase: String) {
        val params = Bundle()
        params.putBoolean(stringParams, true)
        firebaseAnalytics.logEvent(nameEventFirebase, params)
    }

    fun getFirebaseAppID(
        context: Context,
        dataSender: MutableLiveData<List<ModelBase>>
    ): String {
        var firebaseID = ""
        FirebaseAnalytics.getInstance(context).appInstanceId.addOnCompleteListener { task ->
            firebaseID = task.result
            saveFirebaseID(context, firebaseID)
            dataSender.postValue(
                listOf(
                    ModelBase(
                        "firebaseAppID",
                        firebaseID
                    )
                )
            )
        }
        return firebaseID
    }

    private fun saveFirebaseID(context: Context, firebaseID: String) {
        val mSharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val mEditor: SharedPreferences.Editor = mSharedPreferences.edit()
        mEditor.putString("${context.packageName}/firebaseIDKey", firebaseID).apply()
    }

    fun loadFirebaseID(context: Context): String {
        val mSharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        return mSharedPreferences.getString("${context.packageName}/firebaseIDKey", "")!!
    }

}