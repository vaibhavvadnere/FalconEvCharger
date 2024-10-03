package com.falcon.evCharger.history.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.history.HistoryFragment
import com.falcon.evCharger.response.GetTransactionHistoryResponse
import com.falcon.evcharger.R
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.GetTransactionHistoryRequest
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onSignInClick(view: View) {
        EventBus.getDefault().post(HistoryFragment.ViewOnClick.SIGN_IN)
    }

    fun onLogoutClick(view: View) {
        EventBus.getDefault().post(HistoryFragment.ViewOnClick.LOGOUT_USER)
    }

    fun getTransactionHistory(getTransactionHistoryRequest: GetTransactionHistoryRequest?) {
        Log.e("loginRequestLog", ":" + Gson().toJson(getTransactionHistoryRequest))

        if (mainRepo != null) {
            mainRepo!!.getTransactionHistory(getTransactionHistoryRequest).enqueue(object : Callback<GetTransactionHistoryResponse> {
                override fun onResponse(
                    call: Call<GetTransactionHistoryResponse>, response: Response<GetTransactionHistoryResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e(
                            "getUserDetailsLogs", ":Success:" + Gson().toJson(response.body())
                        )

                        if (response.body()?.Result == true) {
                            EventBus.getDefault().post(response.body())
                        } else {
                            Toast.makeText(mActivity, response.body()?.Message, Toast.LENGTH_SHORT).show()

                            EventBus.getDefault().post(HistoryFragment.UpdateEvent.API_FAILED)
                        }

                    } else {
                        Log.e("loginLogs", ":FailedOnResponse:")
                        Toast.makeText(mActivity, response.body()?.Message, Toast.LENGTH_SHORT).show()
                        EventBus.getDefault().post(HistoryFragment.UpdateEvent.API_FAILED)

                    }
                }

                override fun onFailure(call: Call<GetTransactionHistoryResponse>, t: Throwable) {
                    t.printStackTrace()

                    Log.e("loginLogs", ":Failed:" + t.message)
                    Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()

                    EventBus.getDefault().post(HistoryFragment.UpdateEvent.API_FAILED)

                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getTransactionHistory(getTransactionHistoryRequest)
        }
    }

}