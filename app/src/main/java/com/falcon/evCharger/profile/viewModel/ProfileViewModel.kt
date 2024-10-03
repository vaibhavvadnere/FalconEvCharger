package com.falcon.evCharger.profile.viewModel

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
import com.falcon.evCharger.onBoarding.LetsYouInFragment
import com.falcon.evCharger.menu.MenuFragment
import com.falcon.evCharger.profile.ProfileFragment
import com.falcon.evCharger.response.GetTransactionHistoryResponse
import com.falcon.evCharger.response.UserDetailsResponse
import com.falcon.evcharger.R
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.GetTransactionHistoryRequest
import com.iSay1.roamstick.data.model.request.GetUserDataRequest
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onEditClick(view: View) {
        EventBus.getDefault().post(ProfileFragment.ViewOnClick.EDIT)
    }

    fun onDOBClick(view: View) {
        EventBus.getDefault().post(ProfileFragment.ViewOnClick.DOB_CLICKED)
    }
    fun onUpdateClick(view: View) {
        EventBus.getDefault().post(ProfileFragment.ViewOnClick.UPDATE)
    }

    fun getUserDetails(getUserDataRequest: GetUserDataRequest?) {
        Log.e("userRequestLog", ":" + Gson().toJson(getUserDataRequest))

        if (mainRepo != null) {
            mainRepo!!.getUserDetails(getUserDataRequest).enqueue(object : Callback<UserDetailsResponse> {
                override fun onResponse(
                    call: Call<UserDetailsResponse>, response: Response<UserDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e(
                            "getUserDetailsLogs", ":Success:" + Gson().toJson(response.body())
                        )

                        if (response.body()?.Result == true) {
                            EventBus.getDefault().post(response.body())
                        } else {
                            Toast.makeText(mActivity, mActivity?.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()

                            EventBus.getDefault().post(HistoryFragment.UpdateEvent.API_FAILED)
                        }

                    } else {
                        Log.e("UserDetailsLogs", ":FailedOnResponse:")
                        Toast.makeText(mActivity, mActivity?.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                        EventBus.getDefault().post(HistoryFragment.UpdateEvent.API_FAILED)

                    }
                }

                override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                    t.printStackTrace()

                    Log.e("UserDetailsLogs", ":Failed:" + t.message)
                    Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()

                    EventBus.getDefault().post(HistoryFragment.UpdateEvent.API_FAILED)

                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getUserDetails(getUserDataRequest)
        }
    }

}