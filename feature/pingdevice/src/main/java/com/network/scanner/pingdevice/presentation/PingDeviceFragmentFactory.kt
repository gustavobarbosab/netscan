package com.network.scanner.pingdevice.presentation

import androidx.fragment.app.Fragment
import com.network.scanner.common.fragment.action.FragmentAction
import com.network.scanner.common.fragment.action.PingDeviceAction
import com.network.scanner.common.fragment.factory.FragmentFactory

object PingDeviceFragmentFactory : FragmentFactory {

    override val action: FragmentAction = PingDeviceAction

    override fun newInstance(): Fragment = PingDeviceFragment()
}