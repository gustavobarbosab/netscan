package com.network.scanner.home.presentation

import androidx.recyclerview.widget.RecyclerView
import com.network.scanner.home.databinding.ItemHomeOptionsBinding
import com.network.scanner.home.presentation.model.HomeOptionModel

class OptionViewHolder(private val binding: ItemHomeOptionsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: HomeOptionModel, listener: (HomeOptionModel) -> Unit) = binding.apply {
        optionIcon.setImageResource(model.imageResource)
        optionTitle.text = model.title
        root.setOnClickListener { listener(model) }
    }
}