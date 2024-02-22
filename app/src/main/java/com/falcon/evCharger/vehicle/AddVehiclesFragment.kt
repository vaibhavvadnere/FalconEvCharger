package com.falcon.evCharger.vehicle

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.vehicle.viewModel.AddVehiclesViewModel
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.AddVehiclesFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AddVehiclesFragment : HomeBaseFragment() {

    private lateinit var addVehiclesFragmentBinding: AddVehiclesFragmentBinding

    private val addVehiclesViewModel: AddVehiclesViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        CANCEL, ADD_VEHICLE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        addVehiclesFragmentBinding = AddVehiclesFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { addVehiclesViewModel.init(it) }

        addVehiclesFragmentBinding.viewModel = addVehiclesViewModel



        return addVehiclesFragmentBinding.root
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
            ViewOnClick.CANCEL -> {
                Log.e("onCancelClick", ":clicked  CANCEL:")
            }

            ViewOnClick.ADD_VEHICLE -> {
                Log.e("onAddVehicleClick", ":clicked  ADD_VEHICLE:")
                //                mActivity?.navController?.navigate(R.id.action_sign_up)
            }

            else -> {

            }
        }
    }


}

