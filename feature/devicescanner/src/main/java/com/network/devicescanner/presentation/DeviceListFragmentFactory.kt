package com.network.devicescanner.presentation

import androidx.fragment.app.Fragment
import com.network.devicescanner.presentation.DeviceScannerFragment
import com.network.scanner.common.fragment.action.DeviceListAction
import com.network.scanner.common.fragment.action.FragmentAction
import com.network.scanner.common.fragment.factory.FragmentFactory

object DeviceListFragmentFactory : FragmentFactory {

    override val action: FragmentAction = DeviceListAction

    override fun newInstance(): Fragment = DeviceScannerFragment()
}