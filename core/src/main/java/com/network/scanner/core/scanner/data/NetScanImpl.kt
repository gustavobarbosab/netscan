package com.network.scanner.core.scanner.data

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.data.factory.NetScanFactory
import com.network.scanner.core.scanner.data.tools.ping.system.SystemPingWorker
import com.network.scanner.core.scanner.domain.NetScan
import com.network.scanner.core.scanner.domain.entities.DeviceInfo
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.domain.entities.PingResult
import com.network.scanner.core.scanner.domain.entities.PortScanResult
import java.lang.ref.WeakReference

class NetScanImpl(private var application: Application) : NetScan {

    // region Attributes
    private val facade: NetScanFacade by lazy {
        NetScanFactory.provideFacade(WeakReference(application.applicationContext))
    }

    private val portScan by lazy { NetScanFactory.providePortScan() }

    @delegate:RequiresApi(Build.VERSION_CODES.M)
    private val javaIcmp by lazy { NetScanFactory.provideJavaIcmp(facade) }

    private val systemPing by lazy { NetScanFactory.provideSystemPing() }

    private val deviceConnection by lazy { NetScanFactory.provideDeviceConnection(facade) }

    private val networkSpeed by lazy { NetScanFactory.provideNetworkSpeed(facade) }

    private val deviceScanner
        get() = NetScanFactory.provideDeviceScanner(facade)

    private val wifiScanner
        @RequiresApi(Build.VERSION_CODES.M)
        get() = NetScanFactory.provideWifiScanner(application)
    // endregion

    // region Library methods
    override fun hasWifiConnection(): Boolean = deviceConnection.hasWifiConnection()

    override fun hasCellularConnection(): Boolean = deviceConnection.hasCellularConnection()

    override fun hasEthernetConnection(): Boolean = deviceConnection.hasEthernetConnection()

    override fun hasInternetConnection(): Boolean = deviceConnection.hasInternetConnection()

    override fun pingAsync(hostAddress: String): NetScanObservable<PingResult> =
        systemPing.execute(hostAddress)

    override fun ping(hostAddress: String): PingResult = SystemPingWorker(hostAddress).execute()

    override fun domesticDeviceListScanner(): NetScanObservable<DeviceInfo> =
        deviceScanner.findDevices()

    override fun portScanAsync(
        hostAddress: String,
        port: Int,
        timeout: Int
    ): NetScanObservable<PortScanResult> = portScan.scan(hostAddress, port, timeout)

    override fun portScan(hostAddress: String, port: Int, timeout: Int): PortScanResult =
        NetScanFactory.providePortScanWorker(hostAddress, port, timeout).execute()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun checkNetworkSpeed() = networkSpeed.checkSpeed()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun wifiScanner() = wifiScanner.startScan()

    override fun getMyAddress(): String = facade.getMyIpAddress()
    // endregion
}