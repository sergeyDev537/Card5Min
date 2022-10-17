package zaym.`in`.card5min.managers

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern

class SettingsExtreme(context: Context) {

    private var sharedPreferencesManager: SharedPreferencesManager

    init {
        sharedPreferencesManager = SharedPreferencesManager(context)
    }

    @SuppressLint("CommitPrefEdits")
    fun getMinSumPaymentValueSHPLoan(context: Context): Int {
        val mSharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        return mSharedPreferences.getInt(
            "${context.packageName}/minSumPaymentValueSHPLoan",
            999999999
        )

    }

    fun setMinMaxValueSumPaymentContainLoan(context: Context, sumPayment: String) {
        val minSum = getMinSumPaymentValueSHPLoan(context)
        val maxSum = sharedPreferencesManager.loadInt(
            "${context.packageName}/maxSumPaymentValueSHPLoan"
        )

        val pat: Pattern = Pattern.compile("[-]?[0-9]+(.[0-9]+)?")
        val matcher: Matcher = pat.matcher(sumPayment)
        while (matcher.find()) {
            if (matcher.group().toInt() < minSum) {
                sharedPreferencesManager.saveInt(
                    "${context.packageName}/minSumPaymentValueSHPLoan",
                    matcher.group().toInt()
                )
            }
            if (matcher.group().toInt() > maxSum) {
                sharedPreferencesManager.saveInt(
                    "${context.packageName}/maxSumPaymentValueSHPLoan",
                    matcher.group().toInt()
                )
            }
        }
    }

    private fun getMinPercentValueInDay(context: Context): Float {
        val mSharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        return mSharedPreferences.getFloat(
            "${context.packageName}/minPercentValueSHPLoan",
            100f
        )

    }

    fun setMinMaxValuePercentInDay(context: Context, sumPayment: String) {
        val minSum = getMinPercentValueInDay(context)

        val pat: Pattern = Pattern.compile("[-]?[0-9]+(.[0-9]+)?")
        val matcher: Matcher = pat.matcher(sumPayment)
        while (matcher.find()) {
            try {
                if (matcher.group().toFloat() < minSum) {
                    sharedPreferencesManager.saveFloat(
                        "${context.packageName}/minPercentValueSHPLoan",
                        matcher.group().toFloat()
                    )
                }
            } catch (e: Exception) {
                sharedPreferencesManager.saveFloat(
                    "${context.packageName}/minPercentValueSHPLoan",
                    0F
                )
            }
        }
    }
}