package com.network.scanner

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.appcompat.app.AppCompatActivity
import com.network.scanner.core.scanner.NetScan
import com.network.scanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val netScan = NetScan.requireInstance()
    private val repository = MainRepository(netScan)
    private val viewModel = MainViewModel(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pingButton.setOnClickListener { viewModel.pingDevice() }

        viewModel.viewAction.observe(this) {
            when (it) {
                is MainState.ActionState.UpdateScreen -> binding.title.text = it.value
            }
        }
    }
}