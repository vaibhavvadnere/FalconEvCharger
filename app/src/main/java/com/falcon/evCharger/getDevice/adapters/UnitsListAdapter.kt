package com.falcon.evCharger.getDevice.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.falcon.evCharger.response.UnitsData
import com.falcon.evCharger.response.UserList
import com.falcon.evcharger.R

class UnitsListAdapter(
    val context: Context, private var unitsList: MutableList<UnitsData?>
) : RecyclerView.Adapter<UnitsListAdapter.ViewHolder>() {

    lateinit var itemClickListener: ItemClickListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView
        val cbSelect: CheckBox

        init {
            tvTitle = view.findViewById(R.id.tv_title)
            cbSelect = view.findViewById(R.id.check_box_select)

        }
    }

    public interface ItemClickListener {
        fun onItemClick(unitsData: UnitsData?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lay_select_item_with_checkbox, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.e("vehicleDataHolder", "  :  $unitsList")

        holder.cbSelect.isChecked = unitsList[position]?.selected!!

        holder.tvTitle.text = unitsList[position]!!.unitName

        holder.cbSelect.setOnClickListener {

            Log.e("cbSelectVehicleLogs", "  :  " + holder.cbSelect.isChecked)

            itemClickListener.onItemClick(
                unitsList[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return unitsList.size
    }

    fun setData(_items: ArrayList<UnitsData>) {
        unitsList.clear();
        if (_items != null) unitsList.addAll(_items)

        notifyDataSetChanged()
    }

    fun setClickListener(mItemClickListener: ItemClickListener) {
        this.itemClickListener = mItemClickListener
    }
}