package zaym.`in`.card5min.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_splash.*
import zaym.`in`.card5min.R
import zaym.`in`.card5min.managers.SharedPreferencesManager
import zaym.`in`.card5min.viewModels.AuthViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        sharedPreferencesManager = SharedPreferencesManager(this)

        authViewModel.getActionADS().observe(this@SplashActivity) {
            if (it == "0") {
                sharedPreferencesManager.saveBoolean("actionADSSHPLoan", false)
            } else {
                sharedPreferencesManager.saveBoolean("actionADSSHPLoan", true)
            }
        }

        authViewModel.getCountADS().observe(this@SplashActivity) {
            sharedPreferencesManager.saveInt(
                "${packageName}/countADSSHPLoan",
                it
            )
        }

        authViewModel.setSaveSubscribe(this)

        createTimerSplash()
    }

    private fun createTimerSplash() {
        lottieAnimationSplash.playAnimation()
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {

                authViewModel.loadStateAuth(this@SplashActivity)!!.observe(
                    this@SplashActivity
                ) {
                    if (it == true) {
                        startActivity(
                            Intent(
                                this@SplashActivity,
                                MainActivity::class.java
                            )
                        )
                        finish()
                    } else {
                        //TODO LOAD PRIVACY POLICY
                    }
                }
            }
        }.start()
    }
}