package com.network.scanner.portscan.presentation

import androidx.fragment.app.Fragment
import com.network.scanner.common.fragment.action.FragmentAction
import com.network.scanner.common.fragment.action.PortScanAction
import com.network.scanner.common.fragment.factory.FragmentFactory

object PortScanFragmentFactory : FragmentFactory {

    override val action: FragmentAction = PortScanAction

    override fun newInstance(): Fragment = PortScanFragment.newInstance()
}