package zaym.`in`.card5min.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_settings.*
import zaym.`in`.card5min.R
import zaym.`in`.card5min.managers.SettingsExtreme
import zaym.`in`.card5min.managers.SharedPreferencesManager
import zaym.`in`.card5min.viewModels.AuthViewModel

class SettingsFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var settingsExtreme: SettingsExtreme
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        settingsExtreme = SettingsExtreme(requireContext())
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
        setMinMaxSeekBarSettings()
        initChangeEditText()
    }

    private fun initChangeEditText() {
        sumPaymentSettings.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    textViewSumValueSettings.text = p0!!.progress.toString()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }
            })

        percentPaymentSettings.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    textViewPercentValueSettings.text = p0!!.progress.toString()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }
            })
    }


    private fun setMinMaxSeekBarSettings() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            when {
                settingsExtreme.getMinSumPaymentValueSHPLoan(requireContext()) == 999999999 -> {
                    sumPaymentSettings.min = 0
                }
                sharedPreferencesManager.loadInt(
                    "${requireContext().packageName}/maxSumPaymentValueSHPLoan"
                ) == 0 -> {
                    sumPaymentSettings.min = 1000000
                }
                else -> {
                    sumPaymentSettings.min =
                        settingsExtreme.getMinSumPaymentValueSHPLoan(requireContext())
                    sumPaymentSettings.max =
                        sharedPreferencesManager.loadInt(
                            "${requireContext().packageName}/maxSumPaymentValueSHPLoan"
                        )
                }
            }

            percentPaymentSettings.min = 1
            percentPaymentSettings.max = 365
            percentPaymentSettings.progress = 62
            textViewPercentValueSettings.text = 62.toString()
        } else {
            val minSeekBarSumPayment: Int

            when {
                settingsExtreme.getMinSumPaymentValueSHPLoan(requireContext()) == 999999999 -> {
                    minSeekBarSumPayment = 0
                }
                sharedPreferencesManager.loadInt(
                    "${requireContext().packageName}/maxSumPaymentValueSHPLoan"
                ) == 0 -> {
                    minSeekBarSumPayment = 1000000
                }
                else -> {
                    minSeekBarSumPayment =
                        settingsExtreme.getMinSumPaymentValueSHPLoan(requireContext())
                    sumPaymentSettings.max =
                        sharedPreferencesManager.loadInt(
                            "${requireContext().packageName}/maxSumPaymentValueSHPLoan"
                        )
                }
            }

            val minSeekBarPercentPayment = 1
            percentPaymentSettings.max = 365
            percentPaymentSettings.progress = 62
            textViewPercentValueSettings.text = 62.toString()

            sumPaymentSettings.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        if (p1 < minSeekBarSumPayment) {
                            sumPaymentSettings.progress = minSeekBarSumPayment
                        }
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {}

                    override fun onStopTrackingTouch(p0: SeekBar?) {}
                })

            percentPaymentSettings.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        if (p1 < minSeekBarPercentPayment) {
                            percentPaymentSettings.progress = minSeekBarPercentPayment
                        }
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {}

                    override fun onStopTrackingTouch(p0: SeekBar?) {}
                })


        }
    }
}