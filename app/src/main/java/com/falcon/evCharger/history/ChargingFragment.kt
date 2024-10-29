package com.falcon.evCharger.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.falcon.evCharger.history.adapters.ChargingAdapter
import com.falcon.evCharger.history.viewModel.ChargingData
import com.falcon.evcharger.R


class ChargingFragment : DialogFragment() {
    private lateinit var adapter: ChargingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_charging, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val dataList = listOf(
            ChargingData("2", "3434", "33", "3434"),
            ChargingData("1", "1", "2", "2"),
            ChargingData("12", "21", "221", "12"),
        )
        ChargingData("22", "33", "2", "2")
        adapter = ChargingAdapter(dataList)
        recyclerView.adapter = adapter

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

   /* companion object {
        fun newInstance(): ChargingFragment {
            return ChargingFragment()
        }
    }*/
}