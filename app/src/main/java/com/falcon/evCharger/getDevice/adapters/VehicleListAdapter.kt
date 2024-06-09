package com.falcon.evCharger.getDevice.adapters

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
    val context: Context, private var vechicleDataList: MutableList<UserList?>
) : RecyclerView.Adapter<VehicleListAdapter.ViewHolder>() {

    var selectedTpIndex: Int = -1

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
        fun onItemClick(UserList: UserList?, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lay_select_item_with_checkbox, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.cbSelect.isChecked = vechicleDataList[position]!!.selected

        holder.tvTitle.text = vechicleDataList[position]!!.Vehicle_No

        holder.cbSelect.setOnClickListener {

            Log.e("cbSelectDeviceState", "  :  " + holder.cbSelect.isChecked)

            vechicleDataList[position]!!.selected = holder.cbSelect.isChecked

            careAlertListener.onItemClick(
                vechicleDataList[position], holder.cbSelect.isChecked
            )
        }

    }

    override fun getItemCount(): Int {
        return vechicleDataList.size
    }

    fun setData(_items: List<UserList?>) {
        vechicleDataList.clear();
        if (_items != null) vechicleDataList.addAll(_items)
        notifyDataSetChanged()
    }

    fun setClickListener(mCareAlertListener: CareAlertListener) {
        this.careAlertListener = mCareAlertListener
    }
}