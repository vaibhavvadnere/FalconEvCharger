package com.falcon.evCharger.getDevice.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.getDevice.GetDeviceFragment
import com.falcon.evCharger.login.LoginFragment
import com.falcon.evCharger.response.GetVehicleListResponse
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.GetVehicleListRequest
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
        EventBus.getDefault().post(GetDeviceFragment.ViewOnClick.GET_VEHICLES)
    }
    fun onSelectUnitClicked(view: View) {
        EventBus.getDefault().post(GetDeviceFragment.ViewOnClick.SELECT_UNITS)
    }

    fun getVehicleList(getVehicleListRequest: GetVehicleListRequest) {
        Log.e("get_vehicle_list_log", "Mobile no - :$getVehicleListRequest")

        if (mainRepo != null) {
            mainRepo!!.getVehicleList(getVehicleListRequest).enqueue(object : Callback<GetVehicleListResponse> {
                override fun onResponse(
                    call: Call<GetVehicleListResponse>, response: Response<GetVehicleListResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e(
                            "get_vehice_log", ":Success:" + Gson().toJson(response.body())
                        )

                        if (response.body()?.Result == true) {
                            EventBus.getDefault().post(response.body())
                        } else {
                            EventBus.getDefault().post(GetDeviceFragment.UpdateEvent.FAILED)
                        }

                    } else {
                        Log.e("get_vehice_log", ":FailedOnResponse:")
                        EventBus.getDefault().post(GetDeviceFragment.UpdateEvent.FAILED)

                    }
                }

                override fun onFailure(call: Call<GetVehicleListResponse>, t: Throwable) {
                    t.printStackTrace()


                    Log.e("get_vehice_log", ":Failed:" + t.message)
                    EventBus.getDefault().post(GetDeviceFragment.UpdateEvent.FAILED)

                }
            })
        }
    }

}