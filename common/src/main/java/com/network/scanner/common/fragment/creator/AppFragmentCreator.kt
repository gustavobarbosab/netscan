package com.network.scanner.common.fragment.creator

import androidx.fragment.app.Fragment
import com.network.scanner.common.fragment.action.FragmentAction
import com.network.scanner.common.fragment.factory.FragmentFactory

object AppFragmentCreator {

    private var list = mutableListOf<FragmentFactory>()

    fun initialize(newList: List<FragmentFactory>) {
        list.addAll(newList)
    }

    fun newInstance(action: FragmentAction): Fragment {
        val factory = list.firstOrNull { it.action == action }
            ?: throw NullPointerException("Please create a factory for this action: " + action.identifier)
        return factory.newInstance()
    }
}