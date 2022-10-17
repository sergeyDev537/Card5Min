package zaym.`in`.card5min.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_item_loan.*
import zaym.`in`.card5min.R
import zaym.`in`.card5min.models.ModelJSON
import zaym.`in`.card5min.managers.FirebaseApp
import zaym.`in`.card5min.managers.PubManager


class ItemLoanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_item_loan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewDescription()
    }

    private fun setViewDescription() {
        if (arguments != null) {
            val dataModelDescription =
                requireArguments().getSerializable("dataModelClass") as ModelJSON
            textViewTitleItem.text = dataModelDescription.title
            textViewDescriptionItem.text =
                HtmlCompat.fromHtml(
                    dataModelDescription.description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )

            Picasso.get()
                .load(dataModelDescription.logo)
                .resize(800, 300)
                .into(imageViewDescItem)

            appCompatButtonGetMoneyItem.setOnClickListener {
                val customEvent = FirebaseApp(requireContext())
                customEvent.sendEvent(
                    "clickOffer",
                    "eventClickOffer"
                )
                PubManager.openURLBrowser(
                    requireContext(),
                    dataModelDescription.url
                )
            }
        }
    }
}