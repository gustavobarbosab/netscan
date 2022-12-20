package com.network.scanner.home.presentation.model

sealed class OptionsIdentifiers {
    object MiraiScan : OptionsIdentifiers()
    object PortScan : OptionsIdentifiers()
    object Ping : OptionsIdentifiers()
    object SpeedTest : OptionsIdentifiers()
}
