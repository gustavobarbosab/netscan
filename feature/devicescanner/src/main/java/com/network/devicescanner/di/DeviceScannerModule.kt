package com.network.devicescanner.di

import com.network.devicescanner.presentation.DeviceScannerFragment
import com.network.devicescanner.presentation.DeviceScannerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object DeviceScannerModule {
    val instance = module {
        scope<DeviceScannerFragment> {
            viewModelOf(::DeviceScannerViewModel)
        }
    }
}