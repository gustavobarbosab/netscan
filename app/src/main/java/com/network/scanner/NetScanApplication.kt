package com.network.scanner

import android.app.Application
import com.network.scanner.core.scanner.NetScan

class NetScanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NetScan.init(this)
    }
}