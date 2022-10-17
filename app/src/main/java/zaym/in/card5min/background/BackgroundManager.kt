package zaym.`in`.card5min.background

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.preference.PreferenceManager
import okhttp3.MultipartBody
import zaym.`in`.card5min.BuildConfig
import zaym.`in`.card5min.managers.FirebaseApp

class BackgroundManager {

    companion object{

        @SuppressLint("HardwareIds")
        fun createParamsRequest(context: Context, builderPost: MultipartBody.Builder): MultipartBody {
            builderPost.addFormDataPart(
                "aid",
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            )
            builderPost.addFormDataPart("sdk", Build.VERSION.SDK_INT.toString() + "")
            builderPost.addFormDataPart("pac", context.packageName)
            builderPost.addFormDataPart("model", Build.MODEL)
            builderPost.addFormDataPart("device", Build.BRAND)
            builderPost.addFormDataPart("version", BuildConfig.VERSION_NAME)
            builderPost.addFormDataPart(
                "code",
                (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).networkCountryIso
            )
            builderPost.addFormDataPart(
                "firebaseID",
                FirebaseApp(context).loadFirebaseID(context)
            )
            builderPost.addFormDataPart(
                "advertisingID",
                PreferenceManager.getDefaultSharedPreferences(context)
                    .getString("${context.packageName}/adsKeyApplication", "null")!!
            )

            builderPost.addFormDataPart(
                "name",
                PreferenceManager.getDefaultSharedPreferences(context)
                    .getString("${context.packageName}/nameFinKey", "")!!
            )

            builderPost.addFormDataPart(
                "phone",
                PreferenceManager.getDefaultSharedPreferences(context)
                    .getString("${context.packageName}/phoneFinKey", "")!!
            )

            builderPost.addFormDataPart(
                "referrer",
                PreferenceManager.getDefaultSharedPreferences(context)
                    .getString("${context.packageName}/referrerValue", "")!!
            )

            builderPost.build()
            return builderPost.build()
        }

    }

}