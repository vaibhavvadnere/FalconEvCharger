package com.falcon.evCharger.login.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.login.LoginFragment
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.response.LoginDataResponse
import com.falcon.evcharger.R
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.LoginRequest
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetDeviceFragmentViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    //    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    fun onLoginClick(view: View) {
        EventBus.getDefault().post(LoginFragment.ViewOnClick.LOG_IN)
    }

    fun onVehicleClicked(view: View) {
      //  EventBus.getDefault().post(AddPropertyFragment.ViewOnClick.GET_CARETAKER)
    }

}