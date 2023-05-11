package com.network.scanner.core.domain

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.data.NetScanImpl
import com.network.scanner.core.domain.entities.DeviceInfoResult
import com.network.scanner.core.domain.entities.NetworkSpeedResult
import com.network.scanner.core.domain.entities.PingResult
import com.network.scanner.core.domain.entities.PortScanResult
import com.network.scanner.core.domain.entities.WifiInfoResult
import com.network.scanner.core.domain.entities.observable.SubscribeResult

interface NetScan {

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
     * This method is used to check if the device has internet connection.
     * @return Will return true is returned if has connection and false if has not.
     * */
    fun hasInternetConnection(): Boolean

    /**
     * This method is used to understand if a specific device is reachable in the current network,
     * using the "ping" unix command.
     * @param hostAddress It is the address that you want to find.
     * @return SubscribeResult<PingResult> You can use the methods, onResult() and onError()
     * to observe the ping response.
     * */
    fun pingAsync(hostAddress: String): SubscribeResult<PingResult>

    /**
     * This method is used to understand if a specific device is reachable in the current network,
     * using the "ping" unix command.
     * @param hostAddress It is the address that you want to find.
     * @return PingResult
     * */
    fun ping(hostAddress: String): PingResult

    /**
     * This is a asynchronous method, used to understand if a specific device port is opened.
     * @param hostAddress It is the address from the device to check.
     * @param port It is the port to check.
     * @param timeout It is the timeout to check if the port is opened.
     * @return NetScanObservable<PortScanResult> You can use the methods, onResult() and onError()
     * to observe the ping response.
     * */
    fun portScanAsync(
        hostAddress: String,
        port: Int,
        timeout: Int
    ): SubscribeResult<PortScanResult>

    /**
     * This is a synchronous method, used to understand if a specific device port is opened.
     * @param hostAddress It is the address from the device to check.
     * @param port It is the port to check.
     * @param timeout It is the timeout to check if the port is opened.
     * @return PortScanResult
     * */
    fun portScan(hostAddress: String, port: Int, timeout: Int): PortScanResult

    /**
     * This method is used to check the network speed, getting the download and upload speed.
     * @return NetworkSpeedResult It has the download and upload parameters.
     * */
    @RequiresApi(value = Build.VERSION_CODES.M)
    fun checkNetworkSpeed(): NetworkSpeedResult

    /**
     * This method is used to get the device list near to your device
     * @return NetScanObservable<DeviceScanResult> You can use the methods, onResult() and onError()
     * to observe the scanner response.
     * */
    fun domesticDeviceListScanner(): SubscribeResult<DeviceInfoResult>

    /**
     * This method is used to get the wifi list near to your device
     * @return NetScanObservable<DeviceScanResult> You can use the methods, onResult() and onError()
     * to observe the scanner response.
     * */
    @RequiresApi(value = Build.VERSION_CODES.M)
    fun wifiScanner(): SubscribeResult<List<WifiInfoResult>>

    fun getMyAddress(): String

    companion object Library {
        private var instance: NetScan? = null

        fun init(application: Application): NetScan {
            instance = NetScanImpl(application)
            return instance!!
        }

        fun requireInstance(): NetScan = try {
            instance!!
        } catch (_: TypeCastException) {
            throw TypeCastException("Please, initialize the library using the method NetScan.init() in your Aplication")
        }
    }
}
