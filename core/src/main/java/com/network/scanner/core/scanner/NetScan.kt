package com.network.scanner.core.scanner

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.model.NetScanObservable
import com.network.scanner.core.scanner.tools.ping.PingResult
import com.network.scanner.core.scanner.tools.portscan.PortScanResult

interface NetScan {

    /**
     * This method is used to understand if a specific device is reachable in the current network,
     * using the Java ICMP layer.
     * @param host It is the hostname that you want to find.
     * @return NetScanObservable<PingResult> You can use the methods, onResult() and onError()
     * to observe the ping response.
     * */
    @RequiresApi(Build.VERSION_CODES.M)
    fun pingByIcmp(host: String): NetScanObservable<PingResult>


    /**
     * This method is used to check if the device has wifi connection.
     * @return Will return true is returned if has connection and false if has not.
     * */
    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasWifiConnection(): Boolean

    /**
     * This method is used to check if the device has cellular connection.
     * @return Will return true is returned if has connection and false if has not.
     * */
    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasCellularConnection(): Boolean

    /**
     * This method is used to check if the device has ethernet connection.
     * @return Will return true is returned if has connection and false if has not.
     * */
    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasEthernetConnection(): Boolean

    /**
     * This method is used to check if the device has some connection.
     * The connection can be from wifi, ethernet or cellular.
     * @return Will return true is returned if has connection and false if has not.
     * */
    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasSomeConnection(): Boolean

    /**
     * This method is used to check if the device has internet connection.
     * @return Will return true is returned if has connection and false if has not.
     * */
    fun hasInternetConnection(): Boolean

    /**
     * This method is used to understand if a specific device is reachable in the current network,
     * using the "ping" unix command.
     * @param host It is the hostname that you want to find.
     * @return NetScanObservable<PingResult> You can use the methods, onResult() and onError()
     * to observe the ping response.
     * */
    fun pingBySystem(host: String): NetScanObservable<PingResult>

    /**
     * This method is used to understand if a specific device port is opened.
     * @param ipAddress It is the ipAddress from the device to check.
     * @param port It is the port to check.
     * @param timeout It is the timeout to check if the port is opened.
     * @return NetScanObservable<PortScanResult> You can use the methods, onResult() and onError()
     * to observe the ping response.
     * */
    fun portScan(ipAddress: String, port: Int, timeout: Int): NetScanObservable<PortScanResult>

    companion object {
        var instance: NetScan? = null

        fun init(application: Application): NetScan {
            instance = NetScanImpl(application)
            return instance!!
        }

        fun requireInstance(): NetScan = try {
            instance!!
        } catch (exception: TypeCastException) {
            throw TypeCastException("Please, initialize the library using the method NetScan.init() in your Aplication")
        }
    }
}