package com.network.scanner.core.scanner.data

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.data.factory.NetScanFactory
import com.network.scanner.core.scanner.data.tools.wifiscan.WifiScanner
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.domain.entities.PortScanResult
import com.network.scanner.core.scanner.domain.NetScan
import com.network.scanner.core.scanner.domain.entities.DeviceScanResult
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

    private val deviceScanner by lazy { NetScanFactory.provideDeviceScanner(facade) }

    private val wifiScanner
        @RequiresApi(Build.VERSION_CODES.M)
        get() = NetScanFactory.provideWifiScanner(application)
    // endregion

    // region Library methods
    @RequiresApi(Build.VERSION_CODES.M)
    override fun pingByInetAddressAsync(hostAddress: String) = javaIcmp.execute(hostAddress)

    override fun pingBySystemAsync(hostAddress: String) = systemPing.execute(hostAddress)

    @RequiresApi(value = Build.VERSION_CODES.M)
    override fun hasWifiConnection(): Boolean = deviceConnection.hasWifiConnection()

    @RequiresApi(value = Build.VERSION_CODES.M)
    override fun hasCellularConnection(): Boolean = deviceConnection.hasCellularConnection()

    @RequiresApi(value = Build.VERSION_CODES.M)
    override fun hasEthernetConnection(): Boolean = deviceConnection.hasEthernetConnection()

    @RequiresApi(value = Build.VERSION_CODES.M)
    override fun hasSomeConnection(): Boolean = deviceConnection.hasSomeConnection()

    override fun hasInternetConnection(): Boolean = deviceConnection.hasInternetConnection()

    override fun domesticDeviceListScanner(): NetScanObservable<DeviceScanResult> =
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
    // endregion
}