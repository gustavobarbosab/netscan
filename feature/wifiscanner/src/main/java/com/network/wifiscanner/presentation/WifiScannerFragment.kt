package com.network.wifiscanner.presentation

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.network.wifiscanner.presentation.WifiScannerState.LoadWifiList
import com.network.wifiscanner.presentation.WifiScannerState.RequestPermission
import com.network.wifiscanner.presentation.WifiScannerState.SearchingDeviceList
import com.network.scanner.common.netScanToolbar
import com.network.wifiscanner.R
import com.network.wifiscanner.databinding.FragmentWifiScannerBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class WifiScannerFragment : ScopeFragment() {

    override val scope: Scope by fragmentScope()

    private val adapter = WifiScannerAdapter()
    private var binding: FragmentWifiScannerBinding? = null
    private val viewModel by viewModel<WifiScannerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWifiScannerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            recycler.adapter = adapter
            findDevicesButton.setOnClickListener {
                viewModel.wifiScan()
            }
        }

        netScanToolbar().apply {
            showBackButton { requireActivity().onBackPressed() }
            title(context.getString(R.string.wifi_scanner_button_title))
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchingDeviceList -> {
                    binding?.findDevicesButton?.apply {
                        isEnabled = false
                        text = context.getString(R.string.wifi_scanner_button_searching)
                    }
                }
                RequestPermission -> ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    12
                )
                is LoadWifiList -> adapter.setItems(state.list)
                WifiScannerState.DeviceSearchFinished -> enableButton()
                WifiScannerState.WifiDisconnected -> {
                    showToast(getString(R.string.please_connect_wifi))
                    enableButton()
                }
                WifiScannerState.UnsupportedDevice -> showToast(getString(R.string.unsupported_device))
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun enableButton() {
        binding?.findDevicesButton?.apply {
            isEnabled = true
            text = context.getString(R.string.wifi_scanner_button_search)
        }
    }
}
