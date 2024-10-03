package com.falcon.evCharger.myAccount

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.myAccount.viewModel.AccountSetupViewModel
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.AccountSetupFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AccountSetupFragment : HomeBaseFragment() {

    private lateinit var accountSetupFragmentBinding: AccountSetupFragmentBinding

    private val accountSetupViewModel: AccountSetupViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        PROFILE, MY_VEHICLE, ADD_MONEY, CHANGE_LANGUAGE, DELETE_USER, LOGOUT_USER
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        accountSetupFragmentBinding = AccountSetupFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { accountSetupViewModel.init(it) }

        accountSetupFragmentBinding.viewModel = accountSetupViewModel

        return accountSetupFragmentBinding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(viewOnClick: ViewOnClick) {
        when (viewOnClick) {
            ViewOnClick.PROFILE -> {
                Log.e("onSignInClick", ":clicked  PROFILE:")
                mActivity?.navController?.navigate(R.id.action_profile)
            }

            ViewOnClick.MY_VEHICLE -> {
                mActivity?.navController?.navigate(R.id.action_my_vehicle)
            }

            ViewOnClick.CHANGE_LANGUAGE -> {

            }

            ViewOnClick.LOGOUT_USER -> {
                Log.e("onSignInClick", ":clicked  SIGN_IN:")
                SharePrefRepo.getInstance().clearSharePref()
                mActivity?.navController?.navigate(R.id.action_lets_in)
            }

            ViewOnClick.DELETE_USER -> {


            }

            else -> {

            }
        }
    }


}

