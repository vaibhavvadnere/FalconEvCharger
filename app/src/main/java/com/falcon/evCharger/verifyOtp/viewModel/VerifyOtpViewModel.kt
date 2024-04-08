package com.iSay1.roamstick.verifyOtp.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.base.BaseActivity
import com.falcon.evCharger.dashboard.verifyOtp.VerifyOtpFragment
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.request.VerifyOtpRequest
import com.falcon.evCharger.response.CreateUserResponse
import com.falcon.evcharger.R
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyOtpViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: BaseActivity? = null

    var strDigitOne: String = ""
    var strDigitTwo: String = ""
    var strDigitThree: String = ""
    var strDigitFour: String = ""

    //    private val sharePrefRepo = SharePrefRepo.getInstance()
    fun init(mainActivity: BaseActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onVerifyClick(view: View) {
        EventBus.getDefault().post(VerifyOtpFragment.ViewOnClick.VERIFY)
    }

    fun afterDigitOneChanged(s: CharSequence) {
        strDigitOne = s.toString()
        EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.ENTERED_FIRST_DIGIT)

    }

    fun afterDigitTwoChanged(s: CharSequence) {
        strDigitTwo = s.toString()
        EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.ENTERED_SECOND_DIGIT)
    }

    fun afterDigitThreeChanged(s: CharSequence) {
        strDigitThree = s.toString()
        EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.ENTERED_THIRD_DIGIT)
    }

    fun afterDigitFourChanged(s: CharSequence) {
        strDigitFour = s.toString()
        EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.ENTERED_FOURTH_DIGIT)
    }

    fun verifyUsers(mobileNo: String) {
        if (mainRepo != null) {

            val strOTP = strDigitOne + strDigitTwo + strDigitThree + strDigitFour
            val verifyOtpRequest = VerifyOtpRequest()

            if (strOTP.length == 4) {
                verifyOtpRequest.mobile_no = mobileNo
                verifyOtpRequest.otp = strOTP
            } else {
                return
            }

            mainRepo!!.verifyOtp(verifyOtpRequest).enqueue(object : Callback<CreateUserResponse> {
                override fun onResponse(
                    call: Call<CreateUserResponse>, response: Response<CreateUserResponse>
                ) {

                    if (response.isSuccessful) {
                        Log.e(
                            "verifyOtpLogs", ":Success:" + Gson().toJson(response.body())
                        )


                        if (response.body()?.Result!!) {
                            EventBus.getDefault().post(response.body())
                        } else {

                            Toast.makeText(
                                mActivity, response.body()?.Message, Toast.LENGTH_SHORT
                            ).show()

                            EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.VERIFY_OTP_FAILED)
                        }

                    } else {
                        Log.e("verifyOtpLogs", ":FailedOnResponse:")

                        EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.VERIFY_OTP_FAILED)

                    }
                }

                override fun onFailure(call: Call<CreateUserResponse>, t: Throwable) {
                    t.printStackTrace()

                    Log.e("verifyOtpLogs", ":Failed:" + t.message)
                    Toast.makeText(
                        mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT
                    ).show()

                    EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.VERIFY_OTP_FAILED)
                }
            })


        } else {
          //  verifyUsers(email)
        }
    }


}