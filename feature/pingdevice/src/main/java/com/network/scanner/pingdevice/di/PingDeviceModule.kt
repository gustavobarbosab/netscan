package com.network.scanner.pingdevice.di

import com.network.scanner.pingdevice.presentation.PingDeviceFragment
import com.network.scanner.pingdevice.presentation.PingDeviceViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object PingDeviceModule {
    val instance = module {
        scope<PingDeviceFragment> {
            viewModelOf(::PingDeviceViewModel)
        }
    }
}