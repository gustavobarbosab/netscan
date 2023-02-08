package com.network.scanner.common.fragment.factory

import androidx.fragment.app.Fragment
import com.network.scanner.common.fragment.action.FragmentAction

interface FragmentFactory {
    val action: FragmentAction
    fun newInstance(): Fragment
}