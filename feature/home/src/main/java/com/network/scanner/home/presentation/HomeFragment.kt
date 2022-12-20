package com.network.scanner.home.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.network.scanner.common.navigation.parentNavigation
import com.network.scanner.home.databinding.FragmentHomeBinding
import com.network.scanner.home.presentation.model.HomeOption

class HomeFragment : Fragment() {

    val activityNavigation by parentNavigation()
    private var binding: FragmentHomeBinding? = null
    private val adapter: OptionsAdapter = OptionsAdapter {
        Toast.makeText(requireContext(), getString(it.title), Toast.LENGTH_SHORT).show()
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
                    HomeOption.MiraiScan,
                    HomeOption.PortScan,
                    HomeOption.Ping,
                    HomeOption.SpeedTest,
                )
            )
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}