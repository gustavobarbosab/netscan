package com.network.scanner

import com.network.devicescanner.di.DeviceScannerModule
import com.network.scanner.core.domain.NetScan
import com.network.scanner.pingdevice.di.PingDeviceModule
import com.network.scanner.portscan.di.PortScanModule
import org.koin.dsl.module

object NetScanModule {
    val global = module {
        single { NetScan.requireInstance() }
    }

    val modules = listOf(
        global,
        DeviceScannerModule.instance,
        PingDeviceModule.instance,
        PortScanModule.instance
    )
}