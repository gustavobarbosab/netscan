package com.network.scanner.core.scanner.data

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.data.factory.NetScanFactory
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.data.tools.connection.DeviceConnectionImpl
import com.network.scanner.core.scanner.data.tools.ping.JavaIcmp
import com.network.scanner.core.scanner.data.tools.ping.SystemPing
import com.network.scanner.core.scanner.data.tools.portscan.PortScan
import com.network.scanner.core.scanner.domain.entities.PortScanResult
import com.network.scanner.core.scanner.data.tools.speed.NetworkSpeedImpl
import com.network.scanner.core.scanner.domain.NetScan
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

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
    // endregion

    // region Library methods
    @RequiresApi(Build.VERSION_CODES.M)
    override fun pingByInetAddress(hostAddress: String) = javaIcmp.execute(hostAddress)

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