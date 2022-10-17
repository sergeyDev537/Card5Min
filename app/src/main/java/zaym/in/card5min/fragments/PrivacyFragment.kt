package zaym.`in`.card5min.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_privacy.*
import zaym.`in`.card5min.R
import zaym.`in`.card5min.viewModels.AuthViewModel

class PrivacyFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val root: View = inflater.inflate(R.layout.fragment_privacy, container, false)

        authViewModel.editTextPrivacy()!!.observe(viewLifecycleOwner) {
            text_privacy.text = HtmlCompat.fromHtml(
                it!!,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
        return root
    }
}