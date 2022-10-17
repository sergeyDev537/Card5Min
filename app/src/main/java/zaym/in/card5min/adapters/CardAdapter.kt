package zaym.`in`.card5min.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import zaym.`in`.card5min.databinding.ItemLoanBinding
import zaym.`in`.card5min.managers.SettingsExtreme
import zaym.`in`.card5min.models.ModelJSON
import zaym.`in`.card5min.models.ModelURL

class CardAdapter(
    private var listDataJSON: List<ModelJSON>,
    private val context: Context
) : RecyclerView.Adapter<CardAdapter.CardAdapterViewHolder>() {

    var clickItem: ((ModelJSON) -> Unit)? = null
    var clickURL: ((ModelURL) -> Unit)? = null
    private var settingsExtreme: SettingsExtreme = SettingsExtreme(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardAdapter.CardAdapterViewHolder {
        val binding = ItemLoanBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CardAdapterViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: CardAdapter.CardAdapterViewHolder, position: Int) {
        Picasso.get()
            .load(listDataJSON[position].logo)
            .resize(250, 100)
            .into(holder.binding.imageViewBaseItem)

        settingsExtreme.setMinMaxValueSumPaymentContainLoan(context, listDataJSON[position].sum)
        settingsExtreme.setMinMaxValuePercentInDay(context, listDataJSON[position].percent)

        holder.binding.textViewNameItem.text = listDataJSON[position].title
        holder.binding.textViewSumValue.text = listDataJSON[position].sum
        holder.binding.textViewPercentItem.text = listDataJSON[position].percent
        holder.binding.ratingBarItem.rating = listDataJSON[position].rating.toFloat()
        holder.binding.ratingBarItem.setOnTouchListener { v, event -> true }
    }

    override fun getItemCount() = listDataJSON.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<ModelJSON>) {
        listDataJSON = list
        notifyDataSetChanged()
    }

    inner class CardAdapterViewHolder(val binding: ItemLoanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickItem?.invoke(listDataJSON[adapterPosition])
            }

            binding.buttonIssueItem.setOnClickListener {
                clickURL?.invoke(
                    ModelURL(
                        listDataJSON[adapterPosition].open,
                        listDataJSON[adapterPosition].url
                    )
                )
            }
        }
    }

}