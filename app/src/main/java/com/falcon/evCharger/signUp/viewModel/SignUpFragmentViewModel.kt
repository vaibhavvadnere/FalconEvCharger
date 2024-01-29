package com.falcon.evCharger.signUp.viewModel

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.login.LoginFragment
import org.greenrobot.eventbus.EventBus

class SignUpFragmentViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Continue Click
    fun onContinueClick(view: View) {
        EventBus.getDefault().post(LoginFragment.ViewOnClick.CONTINUE)
    }
}