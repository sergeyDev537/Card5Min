package zaym.`in`.card5min.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import zaym.`in`.card5min.R
import zaym.`in`.card5min.background.GetADSKey
import zaym.`in`.card5min.background.GetCountAdsServer
import zaym.`in`.card5min.managers.SharedPreferencesManager
import zaym.`in`.card5min.utils.strPrivacy

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private var stateAuth: MutableLiveData<Boolean?>? = null
    private var dataActionADS: MutableLiveData<String>? = null
    private var dataCountADS: MutableLiveData<Int>? = null
    private var mSharedPreferencesManager = SharedPreferencesManager(getApplication())

    fun loadStateAuth(context: Context): MutableLiveData<Boolean?>? {
        if (stateAuth == null) {
            stateAuth = MutableLiveData()
            stateAuth!!.postValue(
                mSharedPreferencesManager.loadBoolean("${context.packageName}/authKeySHPLoan")
            )
        }
        return stateAuth
    }

    fun getActionADS(): LiveData<String> {
        if (dataActionADS == null){
            dataActionADS = MutableLiveData()
            val asyncInt = GetADSKey(
                dataActionADS
            )
            asyncInt.execute(mutableMapOf<String, String>())
        }
        return dataActionADS as MutableLiveData<String>
    }

    fun getCountADS(): LiveData<Int> {
        if (dataCountADS == null){
            dataCountADS = MutableLiveData()
            val asyncInt = GetCountAdsServer(
                dataCountADS
            )
            asyncInt.execute(mutableMapOf<String, String>())
        }
        return dataCountADS as MutableLiveData<Int>
    }



    fun setSaveSubscribe(context: Context) {
        Firebase.messaging.subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = context.getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = context.getString(R.string.msg_subscribe_failed)
                }
            }
    }
}