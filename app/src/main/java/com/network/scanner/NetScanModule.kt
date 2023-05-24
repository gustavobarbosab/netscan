package com.network.scanner

import com.network.scanner.core.domain.NetScan
import com.network.scanner.pingdevice.di.PingDeviceModule
import com.network.scanner.portscan.di.PortScanModule
import com.network.wifiscanner.di.WifiScannerModule
import org.koin.dsl.module

object NetScanModule {
    val global = module {
        single { NetScan.Library.requireInstance() }
    }

    val modules = listOf(
        global,
        PingDeviceModule.instance,
        PortScanModule.instance,
        WifiScannerModule.instance
    )
}
