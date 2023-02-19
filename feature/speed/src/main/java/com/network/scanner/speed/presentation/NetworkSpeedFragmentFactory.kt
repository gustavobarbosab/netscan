package com.network.scanner.speed.presentation

import androidx.fragment.app.Fragment
import com.network.scanner.common.fragment.action.FragmentAction
import com.network.scanner.common.fragment.action.NetworkSpeedAction
import com.network.scanner.common.fragment.factory.FragmentFactory

object NetworkSpeedFragmentFactory : FragmentFactory {

    override val action: FragmentAction = NetworkSpeedAction

    override fun newInstance(): Fragment = NetworkSpeedFragment.newInstance()
}