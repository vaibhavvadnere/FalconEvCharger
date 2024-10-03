package com.falcon.evCharger.myAccount.viewModel

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.myAccount.AccountSetupFragment
import org.greenrobot.eventbus.EventBus

class AccountSetupViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onProfileClick(view: View) {
        EventBus.getDefault().post(AccountSetupFragment.ViewOnClick.PROFILE)
    }

    fun onMyVehiclesClick(view: View) {
        EventBus.getDefault().post(AccountSetupFragment.ViewOnClick.MY_VEHICLE)
    }

    fun onAddMoneyClick(view: View) {
        EventBus.getDefault().post(AccountSetupFragment.ViewOnClick.ADD_MONEY)
    }

    fun onChangeLanguageClick(view: View) {
        EventBus.getDefault().post(AccountSetupFragment.ViewOnClick.CHANGE_LANGUAGE)
    }

    fun onLogOutClick(view: View) {
        EventBus.getDefault().post(AccountSetupFragment.ViewOnClick.LOGOUT_USER)
    }

    fun onDeleteAccountClick(view: View) {
        EventBus.getDefault().post(AccountSetupFragment.ViewOnClick.DELETE_USER)
    }
}