package com.network.scanner.common.navigation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.network.scanner.common.R
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun Fragment.parentNavigation(defaultContainer: Int = R.id.container) =
    ParentNavigationManagerDelegate(defaultContainer)

fun Fragment.navigation(defaultContainer: Int = R.id.container) =
    ChildNavigationManagerDelegate(defaultContainer)

fun Activity.navigation(defaultContainer: Int = R.id.container) =
    NavigationManagerDelegate(defaultContainer)

class ChildNavigationManagerDelegate(private val defaultContainer: Int) :
    ReadOnlyProperty<Fragment, NavigationManager> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): NavigationManager =
        NavigationManagerImpl(thisRef.childFragmentManager, defaultContainer)
}

class ParentNavigationManagerDelegate(private val defaultContainer: Int) :
    ReadOnlyProperty<Fragment, NavigationManager> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): NavigationManager =
        NavigationManagerImpl(thisRef.requireActivity().supportFragmentManager, defaultContainer)
}

class NavigationManagerDelegate(private val defaultContainer: Int) :
    ReadOnlyProperty<AppCompatActivity, NavigationManager> {

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): NavigationManager =
        NavigationManagerImpl(thisRef.supportFragmentManager, defaultContainer)
}