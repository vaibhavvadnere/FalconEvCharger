package com.falcon.evCharger.dashboard.verifyOtp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.response.CreateUserResponse
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.VerifyOtpFragmentBinding
import com.google.gson.Gson
import com.iSay1.roamstick.verifyOtp.viewModel.VerifyOtpViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Random
import kotlin.concurrent.timer

class VerifyOtpFragment : HomeBaseFragment() {

    private lateinit var verifyOtpFragmentBinding: VerifyOtpFragmentBinding

    private val verifyOtpViewModel: VerifyOtpViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        VERIFY, SIGN_UP, SCAN_QR_BARCODE,
    }


    val sharePrefRepo: SharePrefRepo = SharePrefRepo.getInstance()

    enum class UpdateEvent {
        VERIFY_OTP_FAILED, ENTERED_FIRST_DIGIT, ENTERED_SECOND_DIGIT, ENTERED_THIRD_DIGIT, ENTERED_FOURTH_DIGIT
    }

    var mobileNo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        verifyOtpFragmentBinding = VerifyOtpFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { verifyOtpViewModel.init(it) }

        verifyOtpFragmentBinding.viewModel = verifyOtpViewModel

        return verifyOtpFragmentBinding.root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {

            if (bundle.containsKey(Constants.Phone_Number)) {
                mobileNo = bundle.getString(Constants.Phone_Number)
                Log.e("loginDataLogs", " : prop :" + mobileNo)
            }
        }
        getTimeRemainingForResendOtp();
    }

    @SuppressLint("StringFormatInvalid")
    private fun getTimeRemainingForResendOtp() {
        val intervalSeconds = 1L
        var remainingSeconds = 10L

        val handler = Handler(Looper.getMainLooper())
        Log.e("OTP will expire in:", ":=> $remainingSeconds seconds")

        val timerTask = timer(initialDelay = 0, period = intervalSeconds * 1000) {
            remainingSeconds--
            if (remainingSeconds >= 0) {
                verifyOtpFragmentBinding.tvResend.visibility = View.GONE
                verifyOtpFragmentBinding.tvResendEmail.visibility = View.VISIBLE

                handler.post {
                    verifyOtpFragmentBinding.tvResendEmail.text = context?.resources?.getString(R.string.remaining_time, remainingSeconds)
                    Log.e("Remaining time:", ":=> $remainingSeconds seconds")
                }
            } else {
                handler.post {
                    Log.e("OTP expired!", ":")
                    verifyOtpFragmentBinding.tvResend.visibility = View.VISIBLE
                    verifyOtpFragmentBinding.tvResendEmail.visibility = View.GONE

                    val resendOtpString = context?.resources?.getString(R.string.resend_otp)
                    verifyOtpFragmentBinding.tvResend.text = Html.fromHtml(resendOtpString)
                    cancel()
                }
            }
        }
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

            ViewOnClick.VERIFY -> {


                Log.e("onInClick", ":clicked  CONTINUE:")

                if (verifyOtpFragmentBinding.otpDigit1.text.isNotEmpty() && verifyOtpFragmentBinding.otpDigit2.text.isNotEmpty()
                    && verifyOtpFragmentBinding.otpDigit3.text.isNotEmpty() && verifyOtpFragmentBinding.otpDigit4.text.isNotEmpty()) {
                    showDialog()

                    mobileNo?.let { verifyOtpViewModel.verifyUsers(it) }
                }
                else{
                    Toast.makeText(mActivity, R.string.enter_otp, Toast.LENGTH_SHORT).show()
                }
            }

            else -> {

            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.ENTERED_FIRST_DIGIT -> {
                verifyOtpFragmentBinding.otpDigit2.requestFocus()
            }

            UpdateEvent.ENTERED_SECOND_DIGIT -> {
                verifyOtpFragmentBinding.otpDigit3.requestFocus()

            }

            UpdateEvent.ENTERED_THIRD_DIGIT -> {
                verifyOtpFragmentBinding.otpDigit4.requestFocus()

            }

            UpdateEvent.ENTERED_FOURTH_DIGIT -> {
                mActivity?.hideKeyboard()
            }

            UpdateEvent.VERIFY_OTP_FAILED -> {
                hideDialog()
            }

            else -> {

            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(createUserResponse: CreateUserResponse) {

        hideDialog()

        Log.e("otp_verified_response", ":" + Gson().toJson(createUserResponse))

        sharePrefRepo.putBoolean(Constants.IS_LOGGED_IN, true)

        sharePrefRepo.putInt(Constants.ID, createUserResponse.User_Details.ID)
        sharePrefRepo.putInt(Constants.SOCIETY_ID, createUserResponse.User_Details.Society_ID)
        sharePrefRepo.putString(Constants.USER_ID, createUserResponse.User_Details.User_ID)
        sharePrefRepo.putString(Constants.USER_NAME, createUserResponse.User_Details.User_Name)
        sharePrefRepo.putString(Constants.Phone_Number, createUserResponse.User_Details.Phone_Number)
        sharePrefRepo.putString(Constants.USER_ADDRESS, createUserResponse.User_Details.Address)
        sharePrefRepo.putString(Constants.VEHICLE_NO, createUserResponse.User_Details.Vehicle_No)
        sharePrefRepo.putString(Constants.VEHICLE_TYPE, createUserResponse.User_Details.Vehicle_Type)
        sharePrefRepo.putInt(Constants.IS_ACTIVE, createUserResponse.User_Details.Active)

        mActivity?.navController?.navigate(R.id.action_verified_dashboard)
    }
}

