package com.falcon.evCharger.dashboard.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.Constants
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.dashboard.DashboardFragment
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.onBoarding.LetsYouInFragment
import org.greenrobot.eventbus.EventBus

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

        Log.e("share_pref_log", "user_id => $id society_id => $societyId USER_ID => $userId user_Name => $userName " +
                "phone_Number => $phoneNumber USER_ADDRESS => $userAddress VEHICLE_NO => $vehicleNo VEHICLE_TYPE => $vehicleType " +
                "IS_ACTIVE => $isActive")

    }

    //Function to handle Yes Click
    fun onScanQRClick(view: View) {
        EventBus.getDefault().post(DashboardFragment.ViewOnClick.SCAN_QR)
    }

}