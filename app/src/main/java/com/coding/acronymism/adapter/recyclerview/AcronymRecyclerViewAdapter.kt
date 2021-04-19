package com.coding.acronymism.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coding.acronymism.R
import com.coding.acronymism.databinding.AcronymMeaningLayoutItemBinding
import com.coding.acronymism.interfaces.OnItemSelectedListener
import com.coding.acronymism.model.Meaning
import com.coding.acronymism.ui.fragment.searchfragment.AcronymMeaningItemViewModel

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}

class AcronymRecyclerViewAdapter(private val onItemSelectedListener: OnItemSelectedListener) :
    RecyclerView.Adapter<AcronymRecyclerViewAdapter.MyViewHolder>() {

    private val meanings: MutableList<Meaning> = ArrayList()

    class MyViewHolder(private val acronymMeaningLayoutItemBinding: AcronymMeaningLayoutItemBinding) :
        RecyclerView.ViewHolder(acronymMeaningLayoutItemBinding.root) {
        fun bindItemView(acronymMeaningItemViewModel: AcronymMeaningItemViewModel) {
            acronymMeaningLayoutItemBinding.viewModel = acronymMeaningItemViewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val acronymMeaningLayoutItemBinding: AcronymMeaningLayoutItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.acronym_meaning_layout_item,
                parent,
                false
            )
        return MyViewHolder(acronymMeaningLayoutItemBinding).listen { pos, type ->
            onItemSelectedListener.clickAction(meanings[pos])
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItemView(AcronymMeaningItemViewModel(meanings[position]))
    }

    fun setData(data: List<Meaning>) {
        meanings.clear()
        meanings.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return meanings.size
    }
}
