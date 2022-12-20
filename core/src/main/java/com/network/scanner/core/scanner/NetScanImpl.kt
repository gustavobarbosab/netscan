package com.network.scanner.core.scanner

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.facade.NetScanFacade
import com.network.scanner.core.scanner.factory.NetScanFactory
import com.network.scanner.core.scanner.model.NetScanObservable
import com.network.scanner.core.scanner.tools.connection.DeviceConnectionImpl
import com.network.scanner.core.scanner.tools.ping.JavaIcmp
import com.network.scanner.core.scanner.tools.ping.SystemPing
import com.network.scanner.core.scanner.tools.portscan.PortScan
import com.network.scanner.core.scanner.tools.portscan.PortScanResult
import com.network.scanner.core.scanner.tools.speed.NetworkSpeedImpl
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

class NetScanImpl(private var application: Application) : NetScan {

    // region Attributes
    private val facade: NetScanFacade by lazy {
        NetScanFactory.provideFacade(WeakReference(application.applicationContext))
    }

    private val portScan by lazy { PortScan(Executors.newSingleThreadExecutor()) }

    @delegate:RequiresApi(Build.VERSION_CODES.M)
    private val javaIcmp by lazy { JavaIcmp(facade, Executors.newSingleThreadExecutor()) }

    private val systemPing by lazy { SystemPing(Executors.newSingleThreadExecutor()) }

    private val deviceConnection by lazy { DeviceConnectionImpl(facade.connectivityManager) }

    private val networkSpeed by lazy { NetworkSpeedImpl(facade.connectivityManager) }
    // endregion

    // region Library methods
    @RequiresApi(Build.VERSION_CODES.M)
    override fun pingByIcmp(hostAddress: String) = javaIcmp.execute(hostAddress)

    override fun pingBySystem(hostAddress: String) = systemPing.execute(hostAddress)

    @RequiresApi(value = Build.VERSION_CODES.M)
    override fun hasWifiConnection(): Boolean = deviceConnection.hasWifiConnection()

    @RequiresApi(value = Build.VERSION_CODES.M)
    override fun hasCellularConnection(): Boolean = deviceConnection.hasCellularConnection()

    @RequiresApi(value = Build.VERSION_CODES.M)
    override fun hasEthernetConnection(): Boolean = deviceConnection.hasEthernetConnection()

    @RequiresApi(value = Build.VERSION_CODES.M)
    override fun hasSomeConnection(): Boolean = deviceConnection.hasSomeConnection()

    override fun hasInternetConnection(): Boolean = deviceConnection.hasInternetConnection()

    override fun portScan(
        hostAddress: String,
        port: Int,
        timeout: Int
    ): NetScanObservable<PortScanResult> = portScan.scan(hostAddress, port, timeout)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun checkNetworkSpeed() = networkSpeed.checkSpeed()
    // endregion
}