package com.network.devicescanner.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.network.devicescanner.databinding.FragmentDeviceScannerBinding
import com.network.devicescanner.presentation.DeviceScannerState.AddDevice
import com.network.devicescanner.presentation.DeviceScannerState.RequestPermission
import com.network.devicescanner.presentation.DeviceScannerState.SearchingDeviceList
import com.network.scanner.core.scanner.domain.NetScan

class DeviceScannerFragment : Fragment() {

    private val adapter = DeviceScannerAdapter()
    private var binding: FragmentDeviceScannerBinding? = null
    private var viewModel = DeviceScannerViewModel(NetScan.requireInstance())

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

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchingDeviceList -> {
                    binding?.findDevicesButton?.apply {
                        isEnabled = false
                        text = "Pesquisando..."
                    }
                }
                RequestPermission -> ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    12
                )
                is AddDevice -> adapter.addItem(state.device)
                DeviceScannerState.DeviceSearchFinished -> {
                    binding?.findDevicesButton?.apply {
                        isEnabled = true
                        text = "Pesquisar dispositivos"
                    }
                }
            }
        }
    }
}