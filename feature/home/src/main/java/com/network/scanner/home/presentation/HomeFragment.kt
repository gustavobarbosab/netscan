package com.network.scanner.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.network.scanner.common.navigation.parentNavigation
import com.network.scanner.home.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    val activityNavigation by parentNavigation()
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}