package com.network.scanner.core.scanner

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.network.scanner.core.scanner.facade.NetScanFacade
import com.network.scanner.core.scanner.factory.NetScanFactory
import com.network.scanner.core.scanner.model.NetScanObservable
import com.network.scanner.core.scanner.tools.devicescanner.DeviceScanner
import com.network.scanner.core.scanner.tools.devicescanner.DeviceScannerImpl
import com.network.scanner.core.scanner.tools.ping.JavaIcmp
import com.network.scanner.core.scanner.tools.ping.PingOption
import com.network.scanner.core.scanner.tools.ping.SystemPing
import com.network.scanner.core.scanner.tools.portscan.PortScan
import com.network.scanner.core.scanner.tools.portscan.PortScanResult
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

class NetScanImpl internal constructor(
    private var context: WeakReference<Context>,
    private var lifecycleOwner: LifecycleOwner?
) : NetScan, LifecycleEventObserver {

    init {
        lifecycleOwner?.lifecycle?.addObserver(this)
    }

    private val facade: NetScanFacade by lazy {
        NetScanFactory.provideFacade(context)
    }

    private val portScan by lazy { PortScan(Executors.newSingleThreadExecutor()) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun pingByIcmp(): PingOption = JavaIcmp(facade, Executors.newSingleThreadExecutor())

    @RequiresApi(Build.VERSION_CODES.M)
    override fun findNetworkDevices(): DeviceScanner =
        DeviceScannerImpl(Executors.newSingleThreadExecutor())

    @RequiresApi(Build.VERSION_CODES.M)
    override fun isWifiConnected(): Boolean = facade.isWifiConnected()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun isBluetoothConnected(): Boolean = facade.isBluetoothConnected()

    override fun isPhoneNetworkConnected(): Boolean = facade.isPhoneNetworkConnected()

    override fun pingBySystem(): PingOption = SystemPing(Executors.newSingleThreadExecutor())

    override fun portScan(
        ipAddress: String,
        port: Int,
        timeout: Int
    ): NetScanObservable<PortScanResult> = portScan.scan(ipAddress, port, timeout)

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            facade.unbind()
            lifecycleOwner?.lifecycle?.removeObserver(this)
            this.lifecycleOwner = null
            this.context.clear()
        }
    }
}