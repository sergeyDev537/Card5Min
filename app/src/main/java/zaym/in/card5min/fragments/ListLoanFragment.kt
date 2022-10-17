package zaym.`in`.card5min.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list_loan.*
import zaym.`in`.card5min.R
import zaym.`in`.card5min.adapters.CardAdapter
import zaym.`in`.card5min.models.ModelBase
import zaym.`in`.card5min.models.ModelJSON
import zaym.`in`.card5min.managers.FirebaseApp
import zaym.`in`.card5min.managers.PubManager
import zaym.`in`.card5min.viewModels.FragmentsViewModel

class ListLoanFragment : Fragment() {

    private lateinit var fragmentsViewModel: FragmentsViewModel
    private lateinit var adapterCard: CardAdapter
    private lateinit var listDataSenderHome: List<ModelBase>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentsViewModel = ViewModelProvider(this)[FragmentsViewModel::class.java]
        createAdapterHome()

        fragmentsViewModel.getDataSender()!!.observe(viewLifecycleOwner) { itDataList ->
            fragmentsViewModel.getListBank()!!.observe(
                viewLifecycleOwner
            ) { itDataModel ->
                checkEmptyDataList(itDataModel, itDataList)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_list_loan, container, false)
    }

    private fun checkEmptyDataList(
        itDataModel: List<ModelJSON>,
        itDataList: List<ModelBase>
    ) {
        if (itDataModel.isNotEmpty()) {
            adapterCard.setItems(itDataModel)
            listDataSenderHome = itDataList
            loadLoan.visibility = View.GONE
            textViewEmptyJSONLoan.visibility = View.GONE
            recyclerViewLoan.visibility = View.VISIBLE
        } else {
            textViewEmptyJSONLoan.visibility = View.VISIBLE
            recyclerViewLoan.visibility = View.GONE
            loadLoan.visibility = View.GONE

            val customEvent = FirebaseApp(requireContext())
            customEvent.sendEvent(
                "showOffers",
                "eventShowOffers"
            )
        }
    }

    private fun createAdapterHome() {
        adapterCard = CardAdapter(
            ArrayList(),
            requireContext()
        )

        adapterCard.clickItem = {
            val bundle = Bundle()
            it.url = PubManager.addParamsURL(it.url, listDataSenderHome)
            bundle.putSerializable("dataModelClass", it)
            //TODO START NEXT FRAGMENT
        }

        adapterCard.clickURL = {
            val customEventHome = FirebaseApp(requireContext())
            customEventHome.sendEvent(
                "clickOffer",
                "eventClickOffer"
            )
            PubManager.openURLBrowser(
                requireContext(),
                PubManager.addParamsURL(it.url, listDataSenderHome)
            )
        }

        val recyclerViewBankViewHome: RecyclerView = recyclerViewLoan
        recyclerViewBankViewHome.layoutManager = LinearLayoutManager(context)
        recyclerViewBankViewHome.adapter = adapterCard
    }

}