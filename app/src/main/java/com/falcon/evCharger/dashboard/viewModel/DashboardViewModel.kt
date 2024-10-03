package com.falcon.evCharger.dashboard.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.Constants
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.dashboard.DashboardFragment
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.response.WalletResponse
import com.falcon.evcharger.R
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.GetTransactionHistoryRequest
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity

        val id = sharePrefRepo.getInt(Constants.ID)
        val societyId = sharePrefRepo.getInt(Constants.SOCIETY_ID)
        val userId = sharePrefRepo.getString(Constants.USER_ID)
        val userName = sharePrefRepo.getString(Constants.USER_NAME)
        val phoneNumber = sharePrefRepo.getString(Constants.Phone_Number)
        val userAddress = sharePrefRepo.getString(Constants.USER_ADDRESS)
        val vehicleNo = sharePrefRepo.getString(Constants.VEHICLE_NO)
        val vehicleType = sharePrefRepo.getString(Constants.VEHICLE_TYPE)

        val isActive = sharePrefRepo.getInt(Constants.IS_ACTIVE)

        Log.e(
            "share_pref_log", "user_id => $id society_id => $societyId USER_ID => $userId user_Name => $userName " + "phone_Number => $phoneNumber USER_ADDRESS => $userAddress VEHICLE_NO => $vehicleNo VEHICLE_TYPE => $vehicleType " + "IS_ACTIVE => $isActive"
        )

    }

    //Function to handle Yes Click
    fun onScanQRClick(view: View) {
        EventBus.getDefault().post(DashboardFragment.ViewOnClick.SCAN_QR)
    }

    fun getWalletDetails(getTransactionHistoryRequest: GetTransactionHistoryRequest?) {
        Log.e("walletReqLog", ":" + Gson().toJson(getTransactionHistoryRequest))

        if (mainRepo != null) {
            mainRepo!!.getWalletDetails(getTransactionHistoryRequest).enqueue(object : Callback<WalletResponse> {
                override fun onResponse(
                    call: Call<WalletResponse>, response: Response<WalletResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e(
                            "getWalletLogs", ":Success:" + Gson().toJson(response.body())
                        )

                        if (response.body()?.Result == true) {
                            EventBus.getDefault().post(response.body())
                        } else {
                            Toast.makeText(mActivity, mActivity?.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()

                            EventBus.getDefault().post(DashboardFragment.UpdateEvent.API_FAILED)
                        }

                    } else {
                        Log.e("getWalletLogs", ":FailedOnResponse:")
                        Toast.makeText(mActivity, mActivity?.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                        EventBus.getDefault().post(DashboardFragment.UpdateEvent.API_FAILED)

                    }
                }

                override fun onFailure(call: Call<WalletResponse>, t: Throwable) {
                    t.printStackTrace()

                    Log.e("getWalletLogs", ":Failed:" + t.message)
                    Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()

                    EventBus.getDefault().post(DashboardFragment.UpdateEvent.API_FAILED)

                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getWalletDetails(getTransactionHistoryRequest)
        }
    }

}