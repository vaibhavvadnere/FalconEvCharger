package com.falcon.evCharger.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.Constants
import com.falcon.evCharger.login.viewModel.LoginFragmentViewModel
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.response.LoginDataResponse
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.LogInFragmentBinding
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.LoginRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LoginFragment : HomeBaseFragment() {

    private lateinit var logInFragmentBinding: LogInFragmentBinding

    private val loginFragmentViewModel: LoginFragmentViewModel by activityViewModels()

    val sharePrefRepo: SharePrefRepo = SharePrefRepo.getInstance()


    //Class to Handle all the button click
    enum class ViewOnClick {
        LOG_IN, SIGN_UP, SCAN_QR_BARCODE,
    }

    enum class UpdateEvent {
        LOGIN_SUCCESS, LOGIN_FAILED, SCAN_QR_BARCODE,
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
                Log.e("onInClick", ":clicked  LOGIN:")
                if (logInFragmentBinding.checkBox.isChecked) {
                    showDialog()

                    var loginRequest: LoginRequest = LoginRequest()

                    loginRequest.Phone_Number = logInFragmentBinding.emailOrPhoneNumber.text.toString()
                    loginFragmentViewModel.login(loginRequest)
                } else
                    Toast.makeText(mActivity, R.string.accept_the_terms_first, Toast.LENGTH_SHORT).show()
            //    mActivity?.navController?.navigate(R.id.action_log_in)
            }

            else -> {

            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(loginResponse: LoginDataResponse) {

        hideDialog()

        Log.e("LoginResponseLogs", ":" + Gson().toJson(loginResponse))

        sharePrefRepo.putBoolean(Constants.IS_LOGGED_IN, true)

        sharePrefRepo.putInt(Constants.ID, loginResponse.User.ID)
        sharePrefRepo.putInt(Constants.SOCIETY_ID, loginResponse.User.Society_ID)
        sharePrefRepo.putString(Constants.USER_ID, loginResponse.User.User_ID)
        sharePrefRepo.putString(Constants.USER_NAME, loginResponse.User.User_Name)
        sharePrefRepo.putString(Constants.Phone_Number, loginResponse.User.Phone_Number)
        sharePrefRepo.putString(Constants.USER_ADDRESS, loginResponse.User.Address)
        sharePrefRepo.putString(Constants.VEHICLE_NO, loginResponse.User.Vehicle_No)
        sharePrefRepo.putString(Constants.VEHICLE_TYPE, loginResponse.User.Vehicle_Type)
        sharePrefRepo.putInt(Constants.IS_ACTIVE, loginResponse.User.Active)

        mActivity?.navController?.navigate(R.id.action_log_in)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.LOGIN_FAILED -> {
                Log.e("UpdateEventLog", ":clicked  LOGIN_FAILED:")

                hideDialog()
            }

            else -> {

            }
        }
    }



}

