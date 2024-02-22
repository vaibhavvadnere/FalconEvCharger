package com.falcon.evCharger.vehicle.viewModel

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.onBoarding.LetsYouInFragment
import com.falcon.evCharger.vehicle.AddVehiclesFragment
import org.greenrobot.eventbus.EventBus

class AddVehiclesViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Cancel Click
    fun onCancelClick(view: View) {
        EventBus.getDefault().post(AddVehiclesFragment.ViewOnClick.CANCEL)
    }

    //Function to handle Add Vehicle
    fun onAddVehicleClick(view: View) {
        EventBus.getDefault().post(AddVehiclesFragment.ViewOnClick.ADD_VEHICLE)
    }

}