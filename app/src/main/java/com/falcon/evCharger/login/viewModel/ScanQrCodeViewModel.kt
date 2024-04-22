package com.falcon.evCharger.login.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.Scanning.ScanQRCodeFragment
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.response.GetDeviceResponse
import com.falcon.evcharger.R
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.GetDeviceRequest
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanQrCodeViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    //    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click

    fun getDevice(getDeviceRequest: GetDeviceRequest?) {
        Log.e("deviceIdLog", ":" + Gson().toJson(getDeviceRequest))

        if (mainRepo != null) {
            mainRepo!!.getDevice(getDeviceRequest).enqueue(object : Callback<GetDeviceResponse> {
                override fun onResponse(
                    call: Call<GetDeviceResponse>, response: Response<GetDeviceResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e(
                            "scan_qr_log", ":Success:" + Gson().toJson(response.body())
                        )

                        if (response.body()?.Result == true) {
                            EventBus.getDefault().post(response.body())
                        } else {
                            Toast.makeText(mActivity, response.body()?.Message, Toast.LENGTH_SHORT).show()
                            EventBus.getDefault().post(ScanQRCodeFragment.UpdateEvent.SCAN_FAILED)
                        }

                    } else {
                        Log.e("scan_qr_log", ":FailedOnResponse:")
                        Toast.makeText(mActivity, response.body()?.Message, Toast.LENGTH_SHORT).show()
                        EventBus.getDefault().post(ScanQRCodeFragment.UpdateEvent.SCAN_FAILED)

                    }
                }

                override fun onFailure(call: Call<GetDeviceResponse>, t: Throwable) {
                    t.printStackTrace()


                    Log.e("scan_qr_log", ":Failed:" + t.message)
                    Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()

                    EventBus.getDefault().post(ScanQRCodeFragment.UpdateEvent.SCAN_FAILED)

                }
            })
        }
    }
}