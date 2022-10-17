package zaym.`in`.card5min.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import zaym.`in`.card5min.R
import zaym.`in`.card5min.utils.strPrivacy

class PrivacyViewModel(application: Application) : AndroidViewModel(application) {

    private var textPrivacyPolicy: MutableLiveData<String?>? = MutableLiveData()

    fun editTextPrivacy(): MutableLiveData<String?>? {
        var stringCustom = strPrivacy
        stringCustom =
            stringCustom.replace("{PACKAGE}", getApplication<Application>().packageName)
        stringCustom = stringCustom.replace(
            "{NAME}",
            getApplication<Application>().getString(R.string.app_name)
        )
        textPrivacyPolicy!!.postValue(
            stringCustom
        )
        return textPrivacyPolicy
    }

}