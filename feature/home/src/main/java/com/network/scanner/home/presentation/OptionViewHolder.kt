package com.network.scanner.home.presentation

import androidx.recyclerview.widget.RecyclerView
import com.network.scanner.home.databinding.ItemHomeOptionsBinding
import com.network.scanner.home.presentation.model.HomeOption

class OptionViewHolder(private val binding: ItemHomeOptionsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: HomeOption, listener: (HomeOption) -> Unit) = binding.apply {
        optionIcon.setImageResource(model.imageResource)
        optionTitle.setText(model.title)
        root.setOnClickListener { listener(model) }
    }
}