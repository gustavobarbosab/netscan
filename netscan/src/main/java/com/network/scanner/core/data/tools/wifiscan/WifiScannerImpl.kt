package com.network.scanner.core.data.tools.wifiscan

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.domain.entities.observable.NetScanObservable
import com.network.scanner.core.domain.entities.WifiInfoResult
import com.network.scanner.core.domain.entities.observable.ObservableSubject
import com.network.scanner.core.domain.entities.observable.SubscribeResult
import com.network.scanner.core.domain.exceptions.WifiNotFoundException
import com.network.scanner.core.domain.tools.WifiScanner

@RequiresApi(Build.VERSION_CODES.M)
class WifiScannerImpl(private val application: Application): WifiScanner {

    private val observable: ObservableSubject<List<WifiInfoResult>> = NetScanObservable(this::stop)
    private val wifiManager
        get() = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val intentFilter = IntentFilter()
    private val wifiScanReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val results = wifiManager.scanResults
            if (results.isEmpty()) {
                scanFailure(WifiNotFoundException("Nothing was found, please try again..."))
                return
            }
            scanSuccess(results)
        }
    }

    override fun startScan(): SubscribeResult<List<WifiInfoResult>> {
        wifiManager.startScan()
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION)
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        application.registerReceiver(wifiScanReceiver, intentFilter)
        return observable.subscriber()
    }

    private fun scanSuccess(results: MutableList<ScanResult>) {
        observable.emit(results.map { WifiInfoResult(it.SSID, it.BSSID, it.capabilities) })
        observable.complete()
    }

    private fun scanFailure(exception: Exception) {
        observable.throwException(exception)
    }

    override fun stop() {
        application.unregisterReceiver(wifiScanReceiver)
    }
}
