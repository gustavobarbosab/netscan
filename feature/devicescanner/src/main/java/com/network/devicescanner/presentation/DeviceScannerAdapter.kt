package com.network.devicescanner.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.network.devicescanner.databinding.DeviceScannerItemBinding
import com.network.scanner.core.scanner.domain.entities.DeviceAddress

class DeviceScannerAdapter : RecyclerView.Adapter<DeviceScannerAdapter.ViewHolder>() {

    private val items = mutableSetOf<DeviceAddress>()

    fun addItem(device: DeviceAddress) {
        items.add(device)
        val lastItemPositionAfterAdd = items.size - 1
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DeviceScannerItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fillLayout(items.elementAt(position))
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: DeviceScannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun fillLayout(deviceAddress: DeviceAddress) = with(binding) {
            deviceName.text = deviceAddress.hostname
            deviceIpAddress.text = deviceAddress.ipAddress
        }
    }
}