package com.network.scanner.core.scanner.data.tools.devicescanner

import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.data.tools.ping.system.SystemPingWorker
import com.network.scanner.core.scanner.domain.entities.DeviceInfo
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.domain.tools.DomesticDeviceScanner
import java.util.concurrent.ExecutorService
import java.util.concurrent.atomic.AtomicInteger

class DomesticDeviceScannerImpl(
    private val executor: ExecutorService,
    private val facade: NetScanFacade
) : DomesticDeviceScanner {

    private val counter: AtomicInteger = AtomicInteger(0)

    override fun findDevices(): NetScanObservable<DeviceInfo> {
        val listener = NetScanObservable<DeviceInfo>(executor::shutdownNow)
        val localAddress = facade.getMyIpAddress()
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
        return listener
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