package zaym.`in`.card5min.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import zaym.`in`.card5min.models.ModelBase
import zaym.`in`.card5min.models.ModelJSON
import zaym.`in`.card5min.managers.FirebaseApp
import zaym.`in`.card5min.background.GetListCount

class FragmentsViewModel(application: Application): AndroidViewModel(application) {

    private var dataFiles: MutableLiveData<List<ModelJSON>>? = null
    private var dataSender: MutableLiveData<List<ModelBase>>? = null

    fun getListBank():
            LiveData<List<ModelJSON>>? {
        if (dataFiles == null) {
            dataFiles = MutableLiveData()
            val asyncInt = GetListCount(
                getApplication(),
                dataFiles
            )
            asyncInt.execute(mutableMapOf<String, String>())
        }
        return dataFiles
    }

    fun getDataSender(): LiveData<List<ModelBase>>? {
        if (dataSender == null) {
            dataSender = MutableLiveData()
            val customEvent = FirebaseApp(getApplication())
            customEvent.getFirebaseAppID(getApplication(), dataSender!!)
            //TODO CALL ADVERTISING MANAGER
        }
        return dataSender
    }

}