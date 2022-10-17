package zaym.`in`.card5min.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails

class ReferrerManager {

    companion object{

        fun getReferrerString(context: Context, stringReferrer: MutableLiveData<String>) {
            val referrerClient = InstallReferrerClient.newBuilder(context).build()
            referrerClient.startConnection(object : InstallReferrerStateListener {

                override fun onInstallReferrerSetupFinished(responseCode: Int) {
                    when (responseCode) {
                        InstallReferrerClient.InstallReferrerResponse.OK -> {
                            val response: ReferrerDetails = referrerClient.installReferrer
                            val referrerUrl: String = response.installReferrer
                            stringReferrer.postValue(referrerUrl)
                            setSaveReferrer(context, referrerUrl)
                        }
                        InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {}
                        InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {}
                    }
                }

                override fun onInstallReferrerServiceDisconnected() {}
            })
        }

        fun setSaveReferrer(context: Context, value: String) {
            val mSharedPreferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context)
            val mEditor: SharedPreferences.Editor = mSharedPreferences.edit()
            mEditor.putString("${context.packageName}/referrerValue", value).apply()
        }

    }
}