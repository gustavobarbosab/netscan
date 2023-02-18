package com.network.devicescanner.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.network.devicescanner.databinding.DeviceScannerItemBinding
import com.network.devicescanner.domain.DeviceItem
import com.network.scanner.core.scanner.domain.entities.DeviceInfo

class DeviceScannerAdapter : RecyclerView.Adapter<DeviceScannerAdapter.ViewHolder>() {

    private val items = mutableSetOf<DeviceItem>()

    fun addItem(device: DeviceItem) {
        items.add(device)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DeviceScannerItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fillLayout(items.elementAt(position))
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: DeviceScannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun fillLayout(deviceAddress: DeviceItem) = with(binding) {
            deviceName.text = deviceAddress.hostname
            deviceIpAddress.text = deviceAddress.address
            imageAlert.setImageResource(deviceAddress.iconToShow())
            devicePort23.setText(deviceAddress.port23Text())
            devicePort2323.setText(deviceAddress.port2323Text())
            devicePort48101.setText(deviceAddress.port48101OText())
        }
    }
}