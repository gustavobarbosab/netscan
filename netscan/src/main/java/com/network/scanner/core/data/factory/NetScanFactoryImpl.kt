package com.network.scanner.core.data.factory

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.domain.tools.Worker
import com.network.scanner.core.data.facade.NetScanFacade
import com.network.scanner.core.data.facade.NetScanFacadeImpl
import com.network.scanner.core.data.tools.connection.DeviceConnectionImpl
import com.network.scanner.core.data.tools.devicescanner.DomesticDeviceScannerImpl
import com.network.scanner.core.data.tools.ping.java.JavaIcmp
import com.network.scanner.core.data.tools.ping.system.SystemPing
import com.network.scanner.core.data.tools.portscan.PortScanImpl
import com.network.scanner.core.data.tools.portscan.PortScanWorker
import com.network.scanner.core.data.tools.speed.NetworkSpeedImpl
import com.network.scanner.core.data.tools.wifiscan.WifiScannerImpl
import com.network.scanner.core.domain.entities.PortScanResult
import com.network.scanner.core.domain.tools.DeviceConnection
import com.network.scanner.core.domain.tools.DomesticDeviceScanner
import com.network.scanner.core.domain.tools.NetworkSpeed
import com.network.scanner.core.domain.tools.PingOption
import com.network.scanner.core.domain.tools.PortScan
import com.network.scanner.core.domain.tools.WifiScanner
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

class NetScanFactoryImpl: NetScanFactory {

    override fun provideFacade(context: WeakReference<Context>): NetScanFacade = NetScanFacadeImpl(context)

    override fun providePortScan(): PortScan = PortScanImpl(Executors.newSingleThreadExecutor())

    override fun providePortScanWorker(ip: String, port: Int, timeout: Int): Worker<PortScanResult> =
        PortScanWorker(ip, port, timeout)

    override fun provideJavaIcmp(facade: NetScanFacade): PingOption =
        JavaIcmp(facade, Executors.newSingleThreadExecutor())

    override fun provideSystemPing(): PingOption = SystemPing(Executors.newSingleThreadExecutor())

    override fun provideDeviceConnection(facade: NetScanFacade): DeviceConnection =
        DeviceConnectionImpl(facade)

    override fun provideNetworkSpeed(facade: NetScanFacade): NetworkSpeed = NetworkSpeedImpl(facade)

    override fun provideDeviceScanner(facade: NetScanFacade): DomesticDeviceScanner =
        DomesticDeviceScannerImpl(
            Executors.newCachedThreadPool(),
            Executors.newSingleThreadScheduledExecutor(),
            facade,
            provideDeviceConnection(facade)
        )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun provideWifiScanner(application: Application): WifiScanner = WifiScannerImpl(application)
}