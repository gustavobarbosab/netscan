package com.network.scanner.core.scanner.tools.ping

import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.facade.NetScanFacade
import com.network.scanner.core.scanner.model.NetScanObservable
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.UnknownHostException
import java.util.concurrent.Executor
import kotlin.runCatching

@RequiresApi(Build.VERSION_CODES.M)
class JavaIcmp(
    private val netScanFacade: NetScanFacade,
    private val executor: Executor
) : PingOption {

    override fun execute(host: String): NetScanObservable {
        val listener = NetScanObservable()
        executor.execute {
            runCatching {
                val ipAddress = netScanFacade.getMyIpAddress()

                val iFace: NetworkInterface = netScanFacade.getNetworkInterface(ipAddress)
                val pingAddress: InetAddress = netScanFacade.getInetAddress(host)
                val isReachable = pingAddress.isReachable(
                    iFace,
                    DEFAULT_TIME_TO_LIVE,
                    DEFAULT_TIMEOUT
                )
                listener.emit(PingResult(isReachable, host))
            }.onFailure(listener::throwException)
        }
        return listener
    }

    companion object {
        const val DEFAULT_TIME_TO_LIVE = 4
        const val DEFAULT_TIMEOUT = 3
    }
}