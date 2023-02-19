package com.network.scanner.core.data.factory

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.data.Worker
import com.network.scanner.core.data.facade.NetScanFacade
import com.network.scanner.core.data.facade.NetScanFacadeImpl
import com.network.scanner.core.data.tools.connection.DeviceConnectionImpl
import com.network.scanner.core.data.tools.devicescanner.DomesticDeviceScannerImpl
import com.network.scanner.core.data.tools.ping.java.JavaIcmp
import com.network.scanner.core.data.tools.ping.system.SystemPing
import com.network.scanner.core.data.tools.portscan.PortScan
import com.network.scanner.core.data.tools.portscan.PortScanWorker
import com.network.scanner.core.data.tools.speed.NetworkSpeedImpl
import com.network.scanner.core.data.tools.wifiscan.WifiScanner
import com.network.scanner.core.domain.entities.PortScanResult
import com.network.scanner.core.domain.tools.DeviceConnection
import com.network.scanner.core.domain.tools.DomesticDeviceScanner
import com.network.scanner.core.domain.tools.NetworkSpeed
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

object NetScanFactory {

    fun provideFacade(context: WeakReference<Context>): NetScanFacade = NetScanFacadeImpl(context)

    fun providePortScan(): PortScan = PortScan(Executors.newSingleThreadExecutor())

    fun providePortScanWorker(ip: String, port: Int, timeout: Int): Worker<PortScanResult> =
        PortScanWorker(ip, port, timeout)

    fun provideJavaIcmp(facade: NetScanFacade): JavaIcmp =
        JavaIcmp(facade, Executors.newSingleThreadExecutor())

    fun provideSystemPing(): SystemPing = SystemPing(Executors.newSingleThreadExecutor())

    fun provideDeviceConnection(facade: NetScanFacade): DeviceConnection =
        DeviceConnectionImpl(facade)

    fun provideNetworkSpeed(facade: NetScanFacade): NetworkSpeed = NetworkSpeedImpl(facade)

    fun provideDeviceScanner(facade: NetScanFacade): DomesticDeviceScanner =
        DomesticDeviceScannerImpl(
            Executors.newCachedThreadPool(),
            facade,
            provideDeviceConnection(facade)
        )

    @RequiresApi(Build.VERSION_CODES.M)
    fun provideWifiScanner(application: Application) = WifiScanner(application)
}