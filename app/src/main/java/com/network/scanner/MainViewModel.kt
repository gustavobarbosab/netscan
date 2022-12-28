package com.network.scanner

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val mainState = MainState()

    val viewAction
        get() = mainState.action

    fun pingDevice() {
        val speed = repository.speed().downstreamKbps
        val speedMb = ((speed / 8) / 1000) / 8
        viewAction.value =
            MainState.ActionState.UpdateScreen(speedMb.toString(), 100)
        Log.i("RESULTADOOO", repository.speed().toString())
    }
}