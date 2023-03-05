package com.network.wifiscanner.presentation

import androidx.fragment.app.Fragment
import com.network.scanner.common.fragment.action.FragmentAction
import com.network.scanner.common.fragment.action.WifiScannerAction
import com.network.scanner.common.fragment.factory.FragmentFactory

object WifiScannerFragmentFactory : FragmentFactory {

    override val action: FragmentAction = WifiScannerAction

    override fun newInstance(): Fragment = WifiScannerFragment()
}