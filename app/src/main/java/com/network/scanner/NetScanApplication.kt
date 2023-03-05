package com.network.scanner

import android.app.Application
import com.network.devicescanner.presentation.DeviceListFragmentFactory
import com.network.scanner.common.fragment.creator.AppFragmentCreator
import com.network.scanner.core.domain.NetScan
import com.network.scanner.pingdevice.presentation.PingDeviceFragmentFactory
import com.network.scanner.portscan.presentation.PortScanFragmentFactory
import com.network.scanner.speed.presentation.NetworkSpeedFragmentFactory
import com.network.wifiscanner.presentation.WifiScannerFragmentFactory
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class NetScanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NetScan.init(this)
        initFragmentCreator()
        startKoin {
            androidLogger()
            modules(NetScanModule.modules)
        }
    }

    private fun initFragmentCreator() = AppFragmentCreator.initialize(
        listOf(
            DeviceListFragmentFactory,
            PingDeviceFragmentFactory,
            PortScanFragmentFactory,
            NetworkSpeedFragmentFactory,
            WifiScannerFragmentFactory,
        )
    )
}
