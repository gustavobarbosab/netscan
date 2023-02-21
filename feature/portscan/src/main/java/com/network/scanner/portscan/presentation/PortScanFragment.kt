package com.network.scanner.portscan.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.network.scanner.common.netScanToolbar
import com.network.scanner.core.domain.NetScan
import com.network.scanner.portscan.R
import com.network.scanner.portscan.databinding.FragmentPortScanBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class PortScanFragment : ScopeFragment() {

    override val scope: Scope by fragmentScope()
    private var binding: FragmentPortScanBinding? = null
    private val viewModel by viewModel<PortScanViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortScanBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            portScanDevicesButton.setOnClickListener {
                viewModel.portScan(
                    editText.text?.toString(),
                    editTextPort.text?.toString()
                )
            }
            editText.doOnTextChanged { _, _, _, _ ->
                textInputLayout.error = null
            }
            editTextPort.doOnTextChanged { _, _, _, _ ->
                textInputLayoutPort.error = null
            }
        }

        netScanToolbar().apply {
            showBackButton { requireActivity().onBackPressed() }
            title(context.getString(R.string.port_scan_toolbar))
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                PortScanScreenState.PortOpened -> changeLayout(
                    R.drawable.ic_open,
                    R.string.port_scan_opened
                )
                PortScanScreenState.DeviceNotFound -> changeLayout(
                    com.network.scanner.common.R.drawable.ic_not_found,
                    R.string.port_scan_not_found
                )
                PortScanScreenState.Searching -> {
                    binding?.portScanDevicesButton?.isEnabled = false
                    binding?.portScanDevicesButton?.setText(R.string.port_scan_loading)
                    changeLayout(
                        com.network.scanner.common.R.drawable.ic_loading,
                        R.string.port_scan_loading
                    )
                }
                PortScanScreenState.InvalidAddress -> {
                    binding?.textInputLayout?.error = getString(R.string.port_scan_invalid_address)
                }
                PortScanScreenState.ScreenStarted -> {
                    binding?.textInputLayout?.error = null
                    binding?.portScanDevicesButton?.isEnabled = true
                    binding?.portScanDevicesButton?.setText(R.string.start_scan)
                }
                PortScanScreenState.InvalidPort -> binding?.textInputLayoutPort?.error =
                    getString(R.string.invalid_port)
            }
        }
        viewModel.localAddress.observe(viewLifecycleOwner) {
            binding?.apply {
                localAddressText.isVisible = true
                localAddressText.text = getString(R.string.port_scan_address, it)
            }
        }
        viewModel.findMyAddress()
    }

    private fun changeLayout(@DrawableRes image: Int, @StringRes text: Int) {
        binding?.imageFeedback?.setImageResource(image)
        binding?.feedbackText?.setText(text)
    }

    companion object {
        fun newInstance() = PortScanFragment()
    }
}