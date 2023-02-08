package com.network.scanner

import android.app.Application
import com.network.scanner.common.fragment.creator.AppFragmentCreator
import com.network.scanner.core.scanner.domain.NetScan
import com.network.devicescanner.presentation.DeviceListFragmentFactory

class NetScanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NetScan.init(this)
        AppFragmentCreator.initialize(
            listOf(
                DeviceListFragmentFactory
            )
        )
    }
}