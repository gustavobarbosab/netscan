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

    fun title(text: String) {
        binding.title.text = text
        showTitle()
    }

    fun title(@StringRes text: Int) {
        binding.title.setText(text)
        showTitle()
    }

    fun setLeftIcon(@DrawableRes resource: Int) {
        binding.leftButton.isVisible = true
        binding.leftButton.setImageResource(resource)
    }

    fun setRightIcon(@DrawableRes resource: Int) {
        binding.rightButton.isVisible = true
        binding.rightButton.setImageResource(resource)
    }

    fun showBackButton(listener: () -> Unit) {
        setLeftIcon(R.drawable.ic_back)
        leftIconListener(listener)
    }

    fun leftIconListener(listener: () -> Unit) {
        binding.leftButton.isVisible = true
        binding.leftButton.setOnClickListener { listener() }
    }

    fun rightIconListener(listener: () -> Unit) {
        binding.rightButton.isVisible = true
        binding.rightButton.setOnClickListener { listener() }
    }

    fun showTitle() {
        binding.logo.isVisible = false
        binding.title.isVisible = true
    }

    fun showLogo() {
        binding.logo.isVisible = true
        binding.title.isVisible = false
    }

    fun hideButtons() {
        binding.leftButton.isVisible = false
        binding.rightButton.isVisible = false
    }
}