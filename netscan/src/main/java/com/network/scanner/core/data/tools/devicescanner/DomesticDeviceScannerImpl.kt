package com.network.scanner.core.data.tools.devicescanner

import com.network.scanner.core.data.facade.NetScanFacade
import com.network.scanner.core.data.tools.ping.system.SystemPingWorker
import com.network.scanner.core.domain.entities.DeviceInfoResult
import com.network.scanner.core.domain.entities.observable.NetScanObservable
import com.network.scanner.core.domain.entities.observable.ObservableSubject
import com.network.scanner.core.domain.entities.observable.SubscribeResult
import com.network.scanner.core.domain.exceptions.InternetConnectionNotFoundException
import com.network.scanner.core.domain.tools.DeviceConnection
import com.network.scanner.core.domain.tools.DomesticDeviceScanner
import java.util.concurrent.ExecutorService
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class DomesticDeviceScannerImpl(
    private val executor: ExecutorService,
    private val scheduleExecutor: ScheduledExecutorService,
    private val facade: NetScanFacade,
    private val deviceConnection: DeviceConnection
) : DomesticDeviceScanner {

    private val counter: AtomicInteger = AtomicInteger(0)

    override fun findDevices(): SubscribeResult<DeviceInfoResult> {
        val observable: ObservableSubject<DeviceInfoResult> =
            NetScanObservable(executor::shutdownNow)
        val localAddress = facade.getMyIpAddress()
        startScan(localAddress, observable)
        return observable.subscriber()
    }

    private fun startScan(localAddress: String, observable: ObservableSubject<DeviceInfoResult>) {
        val subNetAddress = localAddress.substringBeforeLast(".").plus(".")
        if (deviceConnection.hasInternetConnection().not()) {
            scheduleExecutor.schedule({
                observable.throwException(
                    InternetConnectionNotFoundException("Please, connect your device on the internet!")
                )
            }, TWO, TimeUnit.SECONDS)
            return
        }

        INTERVAL.forEachIndexed { hostIndex, _ ->
            counter.addAndGet(1)
            executor.submit {
                runCatching {
                    val result = executeJob(subNetAddress, hostIndex)
                    observable.emit(result)
                }.onFailure(observable::throwException)
                    .also {
                        counter.decrementAndGet()
                        val counterValue = counter.get()
                        if (counterValue == 0) {
                            observable.complete()
                        }
                    }
            }
        }
    }

    private fun executeJob(subNetAddress: String, hostIndex: Int): DeviceInfoResult {
        val hostAddress = subNetAddress + hostIndex
        val pingWorker = SystemPingWorker(hostAddress)
        val pingResult = pingWorker.execute()
        return DeviceInfoResult(pingResult.hostname, pingResult.hostAddress)
    }

    companion object {
        private val INTERVAL = 0..255
        private const val TWO = 2L
    }
}