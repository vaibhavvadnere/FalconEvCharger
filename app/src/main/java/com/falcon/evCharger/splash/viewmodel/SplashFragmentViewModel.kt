package com.falcon.evCharger.splash.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.data.api.ApiHelper
import com.falcon.evCharger.data.api.ApiServiceImpl
import com.falcon.evCharger.data.repositry.MainRepo
import com.falcon.evCharger.data.repositry.SharePrefRepo

class SplashFragmentViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: EVMainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: EVMainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onContinueClick(view: View) {
        Log.e("onContinueClick", ":clicked:")
        //        EventBus.getDefault().post(SelectLanguageFragment.ViewOnClick.CONTINUE_CLICK)
    }

}