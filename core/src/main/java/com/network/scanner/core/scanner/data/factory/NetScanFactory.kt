package com.network.scanner.core.scanner.data.factory

import android.content.Context
import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.data.facade.NetScanFacadeImpl
import com.network.scanner.core.scanner.data.tools.connection.DeviceConnectionImpl
import com.network.scanner.core.scanner.data.tools.ping.JavaIcmp
import com.network.scanner.core.scanner.data.tools.ping.SystemPing
import com.network.scanner.core.scanner.data.tools.portscan.PortScan
import com.network.scanner.core.scanner.data.tools.speed.NetworkSpeedImpl
import com.network.scanner.core.scanner.domain.tools.DeviceConnection
import com.network.scanner.core.scanner.domain.tools.NetworkSpeed
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

object NetScanFactory {

    fun provideFacade(context: WeakReference<Context>): NetScanFacade = NetScanFacadeImpl(context)

    fun providePortScan(): PortScan = PortScan(Executors.newSingleThreadExecutor())

    fun provideJavaIcmp(facade: NetScanFacade): JavaIcmp =
        JavaIcmp(facade, Executors.newSingleThreadExecutor())

    fun provideSystemPing(): SystemPing = SystemPing(Executors.newSingleThreadExecutor())

    fun provideDeviceConnection(facade: NetScanFacade): DeviceConnection =
        DeviceConnectionImpl(facade)

    fun provideNetworkSpeed(facade: NetScanFacade): NetworkSpeed = NetworkSpeedImpl(facade)
}