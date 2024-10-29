package com.falcon.evCharger.history.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.falcon.evCharger.history.viewModel.ChargingData
import com.falcon.evcharger.R

class ChargingAdapter(
    private val items: List<ChargingData>,
) : RecyclerView.Adapter<ChargingAdapter.EnergyMeterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnergyMeterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_charging, parent, false)
        return EnergyMeterViewHolder(view)
    }

    override fun onBindViewHolder(holder: EnergyMeterViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.stopChargingButton.setOnClickListener {
        }
    }

    override fun getItemCount(): Int = items.size

    inner class EnergyMeterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val chargingTimeValue: TextView = view.findViewById(R.id.chargingTimeValue)
        private val batteryValue: TextView = view.findViewById(R.id.batteryValue)
        private val currentValue: TextView = view.findViewById(R.id.currentValue)
        private val totalFeesValue: TextView = view.findViewById(R.id.totalFeesValue)
        val stopChargingButton: Button = view.findViewById(R.id.stopChargingButton)

        fun bind(item: ChargingData) {
            chargingTimeValue.text = item.chargingTime
            batteryValue.text = item.battery
            currentValue.text = item.current
            totalFeesValue.text = item.totalFees
        }
    }
}
