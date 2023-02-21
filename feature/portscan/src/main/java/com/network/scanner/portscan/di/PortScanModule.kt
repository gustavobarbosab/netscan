package com.network.scanner.portscan.di

import com.network.scanner.portscan.presentation.PortScanFragment
import com.network.scanner.portscan.presentation.PortScanViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object PortScanModule {
    val instance = module {
        scope<PortScanFragment> {
            viewModelOf(::PortScanViewModel)
        }
    }
}