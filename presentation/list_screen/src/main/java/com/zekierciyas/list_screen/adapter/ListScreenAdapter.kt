package com.zekierciyas.list_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zekierciyas.cache.model.satellite_list.SatelliteListItem
import com.zekierciyas.list_screen.databinding.ItemListScreenBinding
import com.zekierciyas.list_screen.search.CachedSearchingEngine
import javax.inject.Inject

class ListScreenAdapter @Inject constructor(
    private val searchingEngine: CachedSearchingEngine
) :
    RecyclerView.Adapter<ListScreenAdapter.ViewHolder>(), Filterable {

    /** Contains the original list from the initial setup phase */
    private var originalList: List<SatelliteListItem> = listOf()

    /** Contains the currently filtered data list in the original list */
    private var filteredList = originalList

    private var onClickListener: OnClickListener? = null

    fun setClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    inner class ViewHolder(private val binding: ItemListScreenBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(satelliteModel: SatelliteListItem) {
            //Binding the relevant model data
            binding.data = satelliteModel
            //Listening click events to pass them click listener
            binding.root.setOnClickListener { onClickListener!!.onClick(satelliteModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListScreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount() = filteredList.size

    /** Init the original list of data as [SatelliteListItem]
     *
     * @param originalList : The list of data that contains all list without filtered
     */
    fun initList(originalList: List<SatelliteListItem>) = apply {
        this.originalList = originalList
        searchingEngine!!.init(givenDataList = originalList)
        updateList(originalList)
    }

    /** Updates the original list data as [SatelliteListItem]
     *
     * @param newList: New list to update original list with filtered current data
     */
    private fun updateList(newList: List<SatelliteListItem>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffUtil(filteredList, newList))
        filteredList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    private inner class MyDiffUtil(
        private val oldList: List<SatelliteListItem>,
        private val newList: List<SatelliteListItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    /** Query search [Filter] mechanism to make searches from original list
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrBlank()) {
                    originalList
                } else {
                    searchingEngine!!.search(constraint.toString())
                }
                return FilterResults().apply {
                    values = filteredList
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? List<SatelliteListItem> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

    /**Handles user item-click events*/
    interface OnClickListener {
        fun onClick(item: SatelliteListItem)
    }
}
