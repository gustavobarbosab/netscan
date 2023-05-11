package com.network.scanner.core.data.factory

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.domain.tools.Worker
import com.network.scanner.core.data.facade.NetScanFacade
import com.network.scanner.core.domain.entities.PortScanResult
import com.network.scanner.core.domain.tools.DeviceConnection
import com.network.scanner.core.domain.tools.DomesticDeviceScanner
import com.network.scanner.core.domain.tools.NetworkSpeed
import com.network.scanner.core.domain.tools.PingOption
import com.network.scanner.core.domain.tools.PortScan
import com.network.scanner.core.domain.tools.WifiScanner
import java.lang.ref.WeakReference

interface NetScanFactory {
    fun provideFacade(context: WeakReference<Context>): NetScanFacade

    fun providePortScan(): PortScan

    fun providePortScanWorker(ip: String, port: Int, timeout: Int): Worker<PortScanResult>

    fun provideJavaIcmp(facade: NetScanFacade): PingOption

    fun provideSystemPing(): PingOption

    fun provideDeviceConnection(facade: NetScanFacade): DeviceConnection

    fun provideNetworkSpeed(facade: NetScanFacade): NetworkSpeed

    fun provideDeviceScanner(facade: NetScanFacade): DomesticDeviceScanner

    @RequiresApi(Build.VERSION_CODES.M)
    fun provideWifiScanner(application: Application): WifiScanner
}