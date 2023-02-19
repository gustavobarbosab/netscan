package com.network.scanner.core.scanner.data.tools.devicescanner

import android.os.Handler
import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.data.tools.ping.system.SystemPingWorker
import com.network.scanner.core.scanner.domain.entities.DeviceInfo
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.domain.tools.DeviceConnection
import com.network.scanner.core.scanner.domain.tools.DomesticDeviceScanner
import java.lang.UnsupportedOperationException
import java.util.concurrent.ExecutorService
import java.util.concurrent.atomic.AtomicInteger

class DomesticDeviceScannerImpl(
    private val executor: ExecutorService,
    private val facade: NetScanFacade,
    private val deviceConnection: DeviceConnection
) : DomesticDeviceScanner {

    private val counter: AtomicInteger = AtomicInteger(0)

    override fun findDevices(): NetScanObservable<DeviceInfo> {
        val listener = NetScanObservable<DeviceInfo>(executor::shutdownNow)
        val localAddress = facade.getMyIpAddress()
        if (deviceConnection.hasWifiConnection().not()) {
            return listener.also {
                it.throwException(UnsupportedOperationException("We need a Wifi connection"))
            }
        }
        startScan(localAddress, listener)
        return listener
    }

    private fun startScan(localAddress: String, listener: NetScanObservable<DeviceInfo>) {
        val subNetAddress = localAddress.substringBeforeLast(".").plus(".")
        INTERVAL.forEachIndexed { hostIndex, _ ->
            counter.addAndGet(1)
            executor.submit {
                runCatching {
                    val result = executeWork(subNetAddress, hostIndex)
                    listener.emit(result)
                }.onFailure(listener::throwException)
                    .also {
                        counter.decrementAndGet()
                        val counterValue = counter.get()
                        if (counterValue == 0) {
                            listener.complete()
                        }
                    }
            }
        }
    }

    private fun executeWork(subNetAddress: String, hostIndex: Int): DeviceInfo {
        val hostAddress = subNetAddress + hostIndex
        val pingWorker = SystemPingWorker(hostAddress)
        val pingResult = pingWorker.execute()
        return DeviceInfo(pingResult.hostname, pingResult.hostAddress)
    }

    companion object {
        private val INTERVAL = 0..255
    }
}