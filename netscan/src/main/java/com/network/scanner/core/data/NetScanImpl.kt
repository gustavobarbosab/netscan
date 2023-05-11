package com.network.scanner.core.data

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.data.facade.NetScanFacade
import com.network.scanner.core.data.factory.NetScanFactory
import com.network.scanner.core.data.factory.NetScanFactoryImpl
import com.network.scanner.core.data.tools.ping.system.SystemPingWorker
import com.network.scanner.core.domain.NetScan
import com.network.scanner.core.domain.entities.DeviceInfoResult
import com.network.scanner.core.domain.entities.PingResult
import com.network.scanner.core.domain.entities.PortScanResult
import com.network.scanner.core.domain.entities.observable.SubscribeResult
import com.network.scanner.core.domain.tools.DeviceConnection
import com.network.scanner.core.domain.tools.DomesticDeviceScanner
import com.network.scanner.core.domain.tools.NetworkSpeed
import com.network.scanner.core.domain.tools.PingOption
import com.network.scanner.core.domain.tools.PortScan
import com.network.scanner.core.domain.tools.WifiScanner
import com.network.scanner.core.domain.tools.Worker
import java.lang.ref.WeakReference

class NetScanImpl(private var application: Application) : NetScan {

    // region Attributes
    private val factory: NetScanFactory = NetScanFactoryImpl()

    private val facade: NetScanFacade by lazy {
        factory.provideFacade(WeakReference(application.applicationContext))
    }

    private val portScan: PortScan by lazy { factory.providePortScan() }

    private val systemPing: PingOption by lazy { factory.provideSystemPing() }

    private val deviceConnection: DeviceConnection by lazy { factory.provideDeviceConnection(facade) }

    private val networkSpeed: NetworkSpeed by lazy { factory.provideNetworkSpeed(facade) }

    private val deviceScanner: DomesticDeviceScanner
        get() = factory.provideDeviceScanner(facade)

    private val wifiScanner: WifiScanner
        @RequiresApi(Build.VERSION_CODES.M)
        get() = factory.provideWifiScanner(application)
    // endregion

    // region Library methods
    override fun hasWifiConnection(): Boolean = deviceConnection.hasWifiConnection()

    override fun hasCellularConnection(): Boolean = deviceConnection.hasCellularConnection()

    override fun hasEthernetConnection(): Boolean = deviceConnection.hasEthernetConnection()

    override fun hasInternetConnection(): Boolean = deviceConnection.hasInternetConnection()

    override fun pingAsync(hostAddress: String): SubscribeResult<PingResult> =
            systemPing.execute(hostAddress)

    override fun ping(hostAddress: String): PingResult {
        val worker: Worker<PingResult> = SystemPingWorker(hostAddress)
        return worker.execute()
    }

    override fun domesticDeviceListScanner(): SubscribeResult<DeviceInfoResult> =
        deviceScanner.findDevices()

    override fun portScanAsync(
        hostAddress: String,
        port: Int,
        timeout: Int
    ): SubscribeResult<PortScanResult> = portScan.scan(hostAddress, port, timeout)

    override fun portScan(hostAddress: String, port: Int, timeout: Int): PortScanResult =
        factory.providePortScanWorker(hostAddress, port, timeout).execute()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun checkNetworkSpeed() = networkSpeed.checkSpeed()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun wifiScanner() = wifiScanner.startScan()

    override fun getMyAddress(): String = facade.getMyIpAddress()
    // endregion
}