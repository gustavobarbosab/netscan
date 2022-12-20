package com.network.scanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.showLogo()

        navigation.replaceFragment(HomeFragment.newInstance(), false)

        viewModel.viewAction.observe(this) {
            when (it) {
                is MainState.ActionState.UpdateScreen -> {
                }
            }
        }
    }
}