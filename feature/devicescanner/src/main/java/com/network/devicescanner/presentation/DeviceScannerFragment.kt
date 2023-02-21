package com.network.devicescanner.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.network.devicescanner.R
import com.network.devicescanner.databinding.FragmentDeviceScannerBinding
import com.network.devicescanner.presentation.DeviceScannerState.AddDevice
import com.network.devicescanner.presentation.DeviceScannerState.RequestPermission
import com.network.devicescanner.presentation.DeviceScannerState.SearchingDeviceList
import com.network.scanner.common.netScanToolbar
import com.network.scanner.core.domain.NetScan
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

class DeviceScannerFragment : ScopeFragment() {

    override val scope: Scope by fragmentScope()

    private val adapter = DeviceScannerAdapter()
    private var binding: FragmentDeviceScannerBinding? = null
    private val viewModel by viewModel<DeviceScannerViewModel>()

    private val hasNotPermission
        get() = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceScannerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            recycler.adapter = adapter
            findDevicesButton.setOnClickListener {
                viewModel.scanDevices(hasNotPermission)
            }
        }

        netScanToolbar().apply {
            showBackButton { requireActivity().onBackPressed() }
            title(context.getString(R.string.device_scanner_button_title))
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchingDeviceList -> {
                    binding?.findDevicesButton?.apply {
                        isEnabled = false
                        text = context.getString(R.string.device_scanner_button_searching)
                    }
                }
                RequestPermission -> ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    12
                )
                is AddDevice -> adapter.addItem(state.device)
                DeviceScannerState.DeviceSearchFinished -> enableButton()
                DeviceScannerState.WifiDisconnected -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.please_connect_wifi),
                        Toast.LENGTH_LONG
                    ).show()
                    enableButton()
                }
            }
        }
    }

    private fun enableButton() {
        binding?.findDevicesButton?.apply {
            isEnabled = true
            text = context.getString(R.string.device_scanner_button_search)
        }
    }
}