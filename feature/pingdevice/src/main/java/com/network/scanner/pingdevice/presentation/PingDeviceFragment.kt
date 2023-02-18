package com.network.scanner.pingdevice.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.network.scanner.common.netScanToolbar
import com.network.scanner.core.scanner.domain.NetScan
import com.network.scanner.pingdevice.R
import com.network.scanner.pingdevice.databinding.FragmentPingDeviceBinding

class PingDeviceFragment : Fragment() {

    private var binding: FragmentPingDeviceBinding? = null
    private var viewModel = PingDeviceViewModel(NetScan.requireInstance())

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
        binding = FragmentPingDeviceBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            pingDevicesButton.setOnClickListener { viewModel.pingDevice(editText.text?.toString()) }
            editText.doOnTextChanged { text, start, before, count ->
                textInputLayout.error = null
            }
        }

        netScanToolbar().apply {
            showBackButton { requireActivity().onBackPressed() }
            title("PING")
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                PingDeviceScreenState.DeviceFound -> changeLayout(
                    com.network.scanner.common.R.drawable.ic_success,
                    R.string.device_found
                )
                PingDeviceScreenState.DeviceNotFound -> changeLayout(
                    com.network.scanner.common.R.drawable.ic_not_found,
                    R.string.device_not_found
                )
                PingDeviceScreenState.Searching -> {
                    binding?.pingDevicesButton?.isEnabled = false
                    binding?.pingDevicesButton?.setText(R.string.searching_device)
                    changeLayout(
                        com.network.scanner.common.R.drawable.ic_loading,
                        R.string.searching_device
                    )
                }
                PingDeviceScreenState.InvalidAddress -> binding?.textInputLayout?.error =
                    "Ipv4 inválido"
                PingDeviceScreenState.ScreenStarted -> {
                    binding?.textInputLayout?.error = null
                    binding?.pingDevicesButton?.isEnabled = true
                    binding?.pingDevicesButton?.setText(R.string.start_ping)
                }
            }

        }
    }

    private fun changeLayout(@DrawableRes image: Int, @StringRes text: Int) {
        binding?.imageFeedback?.setImageResource(image)
        binding?.feedbackText?.setText(text)
    }

    companion object {
        fun newInstance() = PingDeviceFragment()
    }
}