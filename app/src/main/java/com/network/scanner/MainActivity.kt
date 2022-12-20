package com.network.scanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.network.scanner.common.navigation.navigation
import com.network.scanner.core.scanner.NetScan
import com.network.scanner.databinding.ActivityMainBinding
import com.network.scanner.home.presentation.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val netScan = NetScan.requireInstance()
    private val repository = MainRepository(netScan)
    private val viewModel = MainViewModel(repository)

    private val navigation by navigation()

    private val homeFragment by lazy { HomeFragment.newInstance() }
    private val newsFragment by lazy { Fragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        viewModel.viewAction.observe(this) {
            when (it) {
                is MainState.ActionState.UpdateScreen -> {
                }
            }
        }
    }
}