package com.network.scanner

import android.app.Application
import com.network.devicescanner.presentation.DeviceListFragmentFactory
import com.network.scanner.common.fragment.creator.AppFragmentCreator
import com.network.scanner.core.scanner.domain.NetScan
import com.network.scanner.pingdevice.presentation.PingDeviceFragmentFactory
import com.network.scanner.portscan.presentation.PortScanFragmentFactory
import com.network.scanner.speed.presentation.NetworkSpeedFragmentFactory

class NetScanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NetScan.init(this)
        AppFragmentCreator.initialize(
            listOf(
                DeviceListFragmentFactory,
                PingDeviceFragmentFactory,
                PortScanFragmentFactory,
                NetworkSpeedFragmentFactory,
            )
        )
    }
}