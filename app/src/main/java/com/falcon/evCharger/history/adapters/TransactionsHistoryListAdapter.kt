package com.falcon.evCharger.history.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.falcon.evCharger.response.HistoryResponse
import com.falcon.evCharger.response.UserList
import com.falcon.evCharger.util.DTU
import com.falcon.evcharger.R

class TransactionsHistoryListAdapter(
    val context: Context, private var historyList: MutableList<HistoryResponse?>
) : RecyclerView.Adapter<TransactionsHistoryListAdapter.ViewHolder>() {

    lateinit var careAlertListener: CareAlertListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvVehicleNumber: TextView
        val tvUsedUnits: TextView
        val tvDeviceId: TextView
        val tvDate: TextView
        val tvAmount: TextView
        private val ivArrow: ImageView

        init {
            tvVehicleNumber = view.findViewById(R.id.tv_vehicle_number)
            tvUsedUnits = view.findViewById(R.id.tv_used_units)
            tvDeviceId = view.findViewById(R.id.tv_device_id)
            tvDate = view.findViewById(R.id.tv_date)
            ivArrow = view.findViewById(R.id.iv_arrow)
            tvAmount = view.findViewById(R.id.tv_amount)
        }
    }

    public interface CareAlertListener {
        fun onItemClick(userList: UserList?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_debit_transaction_history_row, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.e("historyDataHolder", "  :  $historyList" + historyList[position]!!.Vehicle_No)

        holder.tvVehicleNumber.text = historyList[position]?.Vehicle_No
        holder.tvUsedUnits.text = historyList[position]?.Unit_Consumed.toString()
        holder.tvDeviceId.text = historyList[position]?.Device_ID

        try {
            val finalDate = DTU.get_MMM_DD_FromDate(historyList[position]?.Date_Time) + " " + DTU.get_HH_MM_FromTime(historyList[position]?.Start_Time) + " to " +
                    DTU.get_MMM_DD_FromDate(historyList[position]?.Date_Time) + " " + DTU.get_HH_MM_FromTime(historyList[position]?.End_Time)

            holder.tvDate.text = finalDate

            val usedAmt : Float? = historyList[position]?.Unit_Consumed?.toFloat()?.times(historyList[position]?.Unit_Rate?.toFloat()!!)

//            holder.tvAmount.text = context.getString(R.string.rs_amount, historyList[position]?.Amount_Used.toString())
            holder.tvAmount.text = context.getString(R.string.rs_amount, usedAmt.toString())

            holder.tvAmount.setTextColor(context.getColor(R.color.red_200))

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        /*holder.itemView.setOnClickListener {
//            careAlertListener.onItemClick(historyList[position])
        }*/
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun setData(_items: List<HistoryResponse?>) {
        historyList.clear();
        if (_items != null) historyList.addAll(_items)

        notifyDataSetChanged()
    }

    fun setClickListener(mCareAlertListener: CareAlertListener) {
        this.careAlertListener = mCareAlertListener
    }
}