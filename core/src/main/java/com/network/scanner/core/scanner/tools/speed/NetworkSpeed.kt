package com.network.scanner.core.scanner.tools.speed

import android.os.Build
import androidx.annotation.RequiresApi

interface NetworkSpeed {
    @RequiresApi(Build.VERSION_CODES.M)
    fun checkSpeed(): NetworkSpeedResult
}