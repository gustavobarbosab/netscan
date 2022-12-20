package com.network.scanner.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.network.scanner.common.R
import com.network.scanner.common.databinding.ToolbarBinding

class Toolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding: ToolbarBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ToolbarBinding.inflate(inflater, this)
    }

    fun showLogo() {
        binding.logo.isVisible = true
        binding.title.isVisible = false
    }

    fun title(text: String) {
        binding.title.text = text
    }

    fun title(@StringRes text: Int) {
        binding.title.setText(text)
    }

    fun setLeftIcon(@DrawableRes resource: Int) {
        binding.leftButton.setImageResource(resource)
    }

    fun setRightIcon(@DrawableRes resource: Int) {
        binding.rightButton.setImageResource(resource)
    }

    fun showBackButton(listener: () -> Unit) {
        setRightIcon(R.drawable.ic_back)
        leftIconListener(listener)
    }

    fun leftIconListener(listener: () -> Unit) {
        binding.leftButton.setOnClickListener { listener() }
    }

    fun rightIconListener(listener: () -> Unit) {
        binding.rightButton.setOnClickListener { listener() }
    }
}