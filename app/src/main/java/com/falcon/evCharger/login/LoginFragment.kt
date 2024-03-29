package com.falcon.evCharger.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.login.viewModel.LoginFragmentViewModel
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.LogInFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LoginFragment : HomeBaseFragment() {

    private lateinit var logInFragmentBinding: LogInFragmentBinding

    private val loginFragmentViewModel: LoginFragmentViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        LOG_IN, SIGN_UP, SCAN_QR_BARCODE,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        logInFragmentBinding = LogInFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { loginFragmentViewModel.init(it) }

        logInFragmentBinding.viewModel = loginFragmentViewModel

        return logInFragmentBinding.root
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

            ViewOnClick.LOG_IN -> {
                Log.e("onInClick", ":clicked  CONTINUE:")
                mActivity?.navController?.navigate(R.id.action_log_in)
            }

            else -> {

            }
        }
    }


}

