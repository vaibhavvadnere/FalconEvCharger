package com.falcon.evCharger.login.viewModel

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.login.LoginFragment
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import org.greenrobot.eventbus.EventBus

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
    fun onContinueClick(view: View) {
        EventBus.getDefault().post(LoginFragment.ViewOnClick.CONTINUE)
    }
}