package com.network.scanner.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.network.scanner.home.databinding.ItemHomeOptionsBinding
import com.network.scanner.home.presentation.model.HomeOption

class OptionsAdapter(
    private val listener: (HomeOption) -> Unit
) : RecyclerView.Adapter<OptionViewHolder>() {

    private val options: MutableList<HomeOption> = mutableListOf()

    fun setItems(newOptions: List<HomeOption>) {
        options.clear()
        options.addAll(newOptions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding = ItemHomeOptionsBinding.inflate(layout, parent, false)
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(options[position], listener)
    }

    override fun getItemCount(): Int = options.size
}