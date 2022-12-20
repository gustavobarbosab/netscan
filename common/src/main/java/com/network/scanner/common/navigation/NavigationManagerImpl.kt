package com.network.scanner.common.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class NavigationManagerImpl(
    private val supportFragmentManager: FragmentManager,
    private val defaultContainer: Int
) : NavigationManager {

    override fun replaceAndCleanBackStack(
        newFragment: Fragment,
        containerId: Int?
    ) {
        cleanFragmentBackStack()
        replaceFragment(newFragment, false, containerId ?: defaultContainer)
    }

    override fun replaceFragment(
        newFragment: Fragment,
        addBackStack: Boolean,
        containerId: Int?
    ) = supportFragmentManager.beginTransaction()
        .replace(containerId ?: defaultContainer, newFragment)
        .also {
            if (addBackStack) {
                it.addToBackStack(null)
            }
        }.commitAllowingStateLoss()


    override fun cleanFragmentBackStack() {
        supportFragmentManager.popBackStackImmediate(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun pop() {
        supportFragmentManager.popBackStack()
    }

    override fun popAll() {
        supportFragmentManager.apply {
            val transaction = beginTransaction()
            fragments.forEach {
                transaction.remove(it)
            }
            transaction.commitAllowingStateLoss()
        }
    }
}