package com.network.scanner.core.scanner.data.tools.devicescanner

import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.data.tools.ping.system.SystemPingWorker
import com.network.scanner.core.scanner.domain.entities.DeviceAddress
import com.network.scanner.core.scanner.domain.entities.DeviceScanResult
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.domain.tools.DomesticDeviceScanner
import java.util.concurrent.ExecutorService

class DomesticDeviceScannerImpl(
    private val executor: ExecutorService,
    private val facade: NetScanFacade
) : DomesticDeviceScanner {

    override fun findDevices(): NetScanObservable<DeviceScanResult> {
        val listener = NetScanObservable<DeviceScanResult>(executor::shutdownNow)
        val localAddress = facade.getMyIpAddress()
        val subNetAddress = localAddress.substringBeforeLast(".")
        executor.execute {
            val devices = mutableListOf<DeviceAddress>()
            INTERVAL.forEachIndexed { hostIndex, _ ->
                runCatching {
                    val hostAddress = subNetAddress + hostIndex
                    val pingWorker = SystemPingWorker(hostAddress)
                    pingWorker.execute()
                    devices.add(DeviceAddress(hostAddress))
                }
            }
            listener.emit(DeviceScanResult(localAddress, devices))
        }
        return listener
    }

    companion object {
        private val INTERVAL = 0..255
    }
}