package com.network.scanner.common.navigation

import androidx.fragment.app.Fragment

interface NavigationManager {
    fun replaceAndCleanBackStack(newFragment: Fragment, containerId: Int? = null)
    fun replaceFragment(newFragment: Fragment, addBackStack: Boolean, containerId: Int? = null): Int
    fun cleanFragmentBackStack()
    fun pop()
    fun popAll()
}