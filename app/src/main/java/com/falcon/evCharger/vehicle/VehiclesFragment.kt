package com.falcon.evCharger.vehicle

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.dashboard.viewModel.DashboardViewModel
import com.falcon.evCharger.vehicle.viewModel.VehiclesViewModel
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.DashboardFragmentBinding
import com.falcon.evcharger.databinding.VehiclesFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class VehiclesFragment : HomeBaseFragment() {

    private lateinit var vehiclesFragmentBinding: VehiclesFragmentBinding

    private val vehiclesViewModel: VehiclesViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        ADD_VEHICLE, SIGN_UP, SCAN_QR_BARCODE,
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        vehiclesFragmentBinding = VehiclesFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { vehiclesViewModel.init(it) }

        vehiclesFragmentBinding.viewModel = vehiclesViewModel



        return vehiclesFragmentBinding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)


    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(viewOnClick: ViewOnClick) {
        when (viewOnClick) {
            ViewOnClick.ADD_VEHICLE -> {
                Log.e("onAddVehicleClick", ":clicked  ADD_VEHICLE:")
                mActivity?.navController?.navigate(R.id.action_add_vehicle)
            }

            else -> {

            }
        }
    }


}

