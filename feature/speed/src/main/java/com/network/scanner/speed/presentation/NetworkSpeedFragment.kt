package com.network.scanner.speed.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.network.scanner.common.netScanToolbar
import com.network.scanner.core.domain.NetScan
import com.network.scanner.speed.R
import com.network.scanner.speed.databinding.FragmentNetworkSpeedBinding

class NetworkSpeedFragment : Fragment() {

    private var binding: FragmentNetworkSpeedBinding? = null
    private var viewModel = NetworkSpeedViewModel(NetScan.requireInstance())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNetworkSpeedBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

        }

        netScanToolbar().apply {
            showBackButton { requireActivity().onBackPressed() }
            title(context.getString(R.string.network_speed_title))
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                NetworkSpeedScreenState.DeviceNotSupported -> binding?.apply {
                    downloadSubtitle.text = getString(R.string.device_not_supported)
                    uploadSubtitle.text = getString(R.string.device_not_supported)
                }
                NetworkSpeedScreenState.Failure -> updateSubtitles("Erro =X", "Erro =X")
                NetworkSpeedScreenState.ScreenStarted -> updateSubtitles("--", "--")
                NetworkSpeedScreenState.Searching -> updateSubtitles("Analisando", "Analisando")
                is NetworkSpeedScreenState.UpdateSpeed -> updateSubtitles(
                    getString(R.string.speed_mask, state.download),
                    getString(R.string.speed_mask, state.upload)
                )
            }
        }
        viewModel.checkSpeed()
    }

    private fun updateSubtitles(download: String, upload: String) = binding?.apply {
        downloadSubtitle.text = download
        uploadSubtitle.text = upload
    }

    companion object {
        fun newInstance() = NetworkSpeedFragment()
    }
}