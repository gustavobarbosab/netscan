package com.network.scanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.navigation.NavigationBarView
import com.network.scanner.common.navigation.navigation
import com.network.scanner.common.widget.Toolbar
import com.network.scanner.common.widget.ToolbarOwnerListener
import com.network.scanner.core.scanner.domain.NetScan
import com.network.scanner.databinding.ActivityMainBinding
import com.network.scanner.home.presentation.HomeFragment
import com.network.scanner.news.NewsFragment

class MainActivity : AppCompatActivity(), ToolbarOwnerListener {

    private lateinit var binding: ActivityMainBinding

    private val netScan = NetScan.requireInstance()
    private val viewModel = MainViewModel(netScan)

    private val navigation by navigation()

    private val homeFragment by lazy { HomeFragment.newInstance() }
    private val newsFragment by lazy { NewsFragment.newInstance() }


    override val toolbar: Toolbar
        get() = binding.toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.fragments.firstOrNull()
            binding.navigation.isVisible = fragment is HomeFragment || fragment is NewsFragment
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.showLogo()

        binding.navigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.action_home -> homeFragment
                R.id.action_news -> newsFragment
                else -> return@OnItemSelectedListener false
            }
            navigation.replaceFragment(fragment, false)
            return@OnItemSelectedListener true
        })
        binding.navigation.selectedItemId = R.id.action_home
    }
}