package com.network.scanner.home.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.network.scanner.home.R

sealed class HomeOption(
    @DrawableRes val imageResource: Int,
    @StringRes val title: Int
) {
    object PortScan : HomeOption(R.drawable.ic_scan, R.string.home_option_port_scan)
    object Ping : HomeOption(R.drawable.ic_ping, R.string.home_option_ping)
    object WifiScanner : HomeOption(
        com.network.scanner.common.R.drawable.ic_wifi,
        R.string.home_option_wifi_scan
    )

    object SpeedTest : HomeOption(R.drawable.ic_speed, R.string.home_option_velocity)
    object MiraiScan : HomeOption(R.drawable.ic_devices, R.string.home_option_mirai_scanner)
}
