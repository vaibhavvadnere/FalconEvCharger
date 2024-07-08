package com.falcon.evCharger.getDevice.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.getDevice.GetDeviceFragment
import com.falcon.evCharger.getDevice.SuccessFragment
import com.falcon.evCharger.getDevice.getDeviceResponses
import com.falcon.evCharger.response.GetStartChargingResponse
import com.falcon.evCharger.response.StopChargingResponse
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.GetStartChargingRequest
import com.iSay1.roamstick.data.model.request.StopChargingRequest
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuccessViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    fun okOkClick(view: View) {
        EventBus.getDefault().post(SuccessFragment.ViewOnClick.OK_CLICK)
    }

    fun onStopChargingClick(view: View) {
        EventBus.getDefault().post(SuccessFragment.ViewOnClick.STOP_CHARGING)
    }

    fun stopCharging(stopChargingRequest: StopChargingRequest) {

        if (mainRepo != null) {
            mainRepo!!.stopCharging(stopChargingRequest).enqueue(object : Callback<StopChargingResponse> {
                override fun onResponse(
                    call: Call<StopChargingResponse>, response: Response<StopChargingResponse>
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

                override fun onFailure(call: Call<StopChargingResponse>, t: Throwable) {
                    t.printStackTrace()


                    Log.e("get_vehice_log", ":Failed:" + t.message)
                    EventBus.getDefault().post(GetDeviceFragment.UpdateEvent.FAILED)

                }
            })
        }

    }


}