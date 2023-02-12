package com.network.scanner.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.network.scanner.common.fragment.action.DeviceListAction
import com.network.scanner.common.fragment.creator.AppFragmentCreator
import com.network.scanner.common.navigation.parentNavigation
import com.network.scanner.common.netScanToolbar
import com.network.scanner.home.databinding.FragmentHomeBinding
import com.network.scanner.home.presentation.model.HomeOption

class HomeFragment : Fragment() {

    private val activityNavigation by parentNavigation()
    private val fragmentCreator = AppFragmentCreator
    private var binding: FragmentHomeBinding? = null
    private val adapter: OptionsAdapter = OptionsAdapter {
        when(it) {
            is HomeOption.MiraiScan -> it.listener()
            HomeOption.Ping -> TODO()
            HomeOption.PortScan -> TODO()
            HomeOption.SpeedTest -> TODO()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            recyclerView.adapter = adapter
            adapter.setItems(
                listOf(
                    HomeOption.Ping,
                    HomeOption.PortScan,
                    HomeOption.SpeedTest,
                    HomeOption.MiraiScan(this@HomeFragment::openDeviceList),
                )
            )
        }
        netScanToolbar().apply {
            showLogo()
            hideButtons()
        }
    }

    private fun openDeviceList() {
        val fragment = fragmentCreator.newInstance(DeviceListAction)
        activityNavigation.replaceFragment(fragment, true)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}