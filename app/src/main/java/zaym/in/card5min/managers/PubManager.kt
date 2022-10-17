package zaym.`in`.card5min.managers

import android.content.Context
import android.content.Intent
import android.net.Uri
import zaym.`in`.card5min.models.ModelBase

class PubManager {

    companion object{

        fun openURLBrowser(context: Context, url: String){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        }

        fun addParamsURL(
            stringBaseUrl: String,
            listDataSender: List<ModelBase>
        ): String {
            var stringFinish: String = stringBaseUrl

            if (listDataSender.size == 1) {
                if (listDataSender[0].key == "firebaseAppID") {
                    stringFinish += "&aff_sub6=${listDataSender[0].value}"
                }
                stringFinish += if (listDataSender[0].key == "AdvertisingID") {
                    "&aff_sub7=${listDataSender[0].value}"
                } else {
                    "&aff_sub7=null"
                }
            } else {
                if (listDataSender[0].key == "firebaseAppID") {
                    stringFinish += "&aff_sub6=${listDataSender[0].value}"
                } else if (listDataSender[1].key == "firebaseAppID") {
                    stringFinish += "&aff_sub6=${listDataSender[1].value}"
                }
                stringFinish += if (listDataSender[0].key == "AdvertisingID") {
                    "&aff_sub7=${listDataSender[0].value}"
                } else if (listDataSender[1].key == "AdvertisingID") {
                    "&aff_sub7=${listDataSender[1].value}"
                } else {
                    "&aff_sub7=null"
                }
            }
            return stringFinish
        }

    }

}