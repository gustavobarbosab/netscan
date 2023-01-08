package com.network.scanner.core.scanner.data.tools.wifiscan

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.domain.exceptions.WifiNotFoundException

@RequiresApi(Build.VERSION_CODES.M)
class WifiScanner(private val application: Application) {

    private val listener = NetScanObservable<List<ScanResult>>(this::stop)
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

    fun startScan(): NetScanObservable<List<ScanResult>> {
        wifiManager.startScan()
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION)
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        application.registerReceiver(wifiScanReceiver, intentFilter)
        return listener
    }

    private fun scanSuccess(results: MutableList<ScanResult>) {
        listener.emit(results)
    }

    private fun scanFailure(exception: Exception) {
        listener.throwException(exception)
    }

    fun stop() {
        application.unregisterReceiver(wifiScanReceiver)
    }
}