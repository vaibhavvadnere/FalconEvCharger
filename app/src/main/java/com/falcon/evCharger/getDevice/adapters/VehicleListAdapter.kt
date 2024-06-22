package com.falcon.evCharger.getDevice.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.falcon.evCharger.response.UserList
import com.falcon.evcharger.R

class VehicleListAdapter(
    val context: Context, private var vehicleDataList: MutableList<UserList?>
) : RecyclerView.Adapter<VehicleListAdapter.ViewHolder>() {

    lateinit var careAlertListener: CareAlertListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView
        val cbSelect: CheckBox

        init {
            tvTitle = view.findViewById(R.id.tv_title)
            cbSelect = view.findViewById(R.id.check_box_select)

        }
    }

    public interface CareAlertListener {
        fun onItemClick(userList: UserList?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lay_select_item_with_checkbox, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.e("vehicleDataHolder", "  :  $vehicleDataList"+ vehicleDataList[position]!!.Vehicle_Type)

        holder.cbSelect.isChecked = vehicleDataList[position]!!.selected
        holder.tvTitle.text = vehicleDataList[position]!!.Vehicle_No + " - ${vehicleDataList[position]!!.Vehicle_Type ?: ""}"

        holder.cbSelect.setOnClickListener {

            Log.e("cbSelectVehicleLogs", "  :  " + holder.cbSelect.isChecked)

            vehicleDataList[position]!!.selected = holder.cbSelect.isChecked

            careAlertListener.onItemClick(
                vehicleDataList[position]
            )
        }

    }

    override fun getItemCount(): Int {
        return vehicleDataList.size
    }

    fun setData(_items: List<UserList?>) {
        vehicleDataList.clear();
        if (_items != null) vehicleDataList.addAll(_items)

        notifyDataSetChanged()
    }

    fun setClickListener(mCareAlertListener: CareAlertListener) {
        this.careAlertListener = mCareAlertListener
    }
}