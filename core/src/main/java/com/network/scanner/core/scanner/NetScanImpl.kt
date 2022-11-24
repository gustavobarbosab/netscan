package com.network.scanner.core.scanner

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.network.scanner.core.scanner.facade.NetScanFacade
import com.network.scanner.core.scanner.factory.NetScanFactory
import com.network.scanner.core.scanner.tools.networkdevices.NetworkDevices
import com.network.scanner.core.scanner.tools.networkdevices.NetworkDevicesImpl
import com.network.scanner.core.scanner.tools.ping.JavaIcmp
import com.network.scanner.core.scanner.tools.ping.PingOption
import com.network.scanner.core.scanner.tools.ping.SystemPing
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun pingByIcmp(): PingOption = JavaIcmp(facade, Executors.newSingleThreadExecutor())

    @RequiresApi(Build.VERSION_CODES.M)
    override fun findNetworkDevices(): NetworkDevices = NetworkDevicesImpl(facade)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun isWifiConnected(): Boolean = facade.isWifiConnected()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun isBluetoothConnected(): Boolean = facade.isBluetoothConnected()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun isPhoneNetworkConnected(): Boolean = facade.isPhoneNetworkConnected()

    override fun pingBySystem(): PingOption = SystemPing(Executors.newSingleThreadExecutor())

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            facade.unbind()
            lifecycleOwner?.lifecycle?.removeObserver(this)
            this.lifecycleOwner = null
            this.context.clear()
        }
    }
}