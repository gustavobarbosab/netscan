package com.network.wifiscanner.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.network.wifiscanner.databinding.WifiScannerItemBinding
import com.network.wifiscanner.domain.WifiItemModel

class WifiScannerAdapter : RecyclerView.Adapter<WifiScannerAdapter.ViewHolder>() {

    private val items = mutableSetOf<WifiItemModel>()

    fun setItems(list: List<WifiItemModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WifiScannerItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fillLayout(items.elementAt(position))
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: WifiScannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun fillLayout(model: WifiItemModel) = with(binding) {
            wifiTitle.text = model.title
            wifiSubtitle.text = model.subTitle
        }
    }
}
