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

class LoginFragmentViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    //    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onLoginClick(view: View) {
        EventBus.getDefault().post(LoginFragment.ViewOnClick.LOG_IN)
    }

    fun login(loginRequest: LoginRequest?) {
        Log.e("loginRequestLog", ":" + Gson().toJson(loginRequest))

        if (mainRepo != null) {
            mainRepo!!.loginUser(loginRequest).enqueue(object : Callback<LoginDataResponse> {
                override fun onResponse(
                    call: Call<LoginDataResponse>, response: Response<LoginDataResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e(
                            "loginLogs", ":Success:" + Gson().toJson(response.body())
                        )


                        if (response.body()?.Result == true) {
                            EventBus.getDefault().post(response.body())
                        } else {
                            Toast.makeText(mActivity, response.body()?.Message, Toast.LENGTH_SHORT).show()

                            EventBus.getDefault().post(LoginFragment.UpdateEvent.LOGIN_FAILED)
                        }

                    } else {
                        Log.e("loginLogs", ":FailedOnResponse:")
                        Toast.makeText(mActivity, response.body()?.Message, Toast.LENGTH_SHORT).show()
                        EventBus.getDefault().post(LoginFragment.UpdateEvent.LOGIN_FAILED)

                    }
                }

                override fun onFailure(call: Call<LoginDataResponse>, t: Throwable) {
                    t.printStackTrace()


                    Log.e("loginLogs", ":Failed:" + t.message)
                    Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()

                   EventBus.getDefault().post(LoginFragment.UpdateEvent.LOGIN_FAILED)

                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            login(loginRequest)
        }
    }
}