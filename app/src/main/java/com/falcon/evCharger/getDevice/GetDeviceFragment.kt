package com.falcon.evCharger.getDevice

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.getDevice.viewModel.GetDeviceFragmentViewModel
import com.falcon.evCharger.response.GetDeviceResponse
import com.falcon.evCharger.response.LoginDataResponse
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.GetDeviceFragmentBinding
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class GetDeviceFragment : HomeBaseFragment() {

    private lateinit var getDeviceBinding: GetDeviceFragmentBinding

    private val getDeviceFragmentViewModel: GetDeviceFragmentViewModel by activityViewModels()

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
        getDeviceBinding = GetDeviceFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { getDeviceFragmentViewModel.init(it) }

        getDeviceBinding.viewModel = getDeviceFragmentViewModel
        getDeviceBinding.tvAvailableBalance.text = sharePrefRepo.balance.toString()

        return getDeviceBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val response: GetDeviceResponse? = arguments?.getParcelable(Constants.DEVICE_RESPONSE)

        if (response != null) {
            updateViewComponents(response)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateViewComponents(response: GetDeviceResponse) {
        sharePrefRepo.balance = 199
        getDeviceBinding.tvAvailableBalance.text = "₹ ${sharePrefRepo.balance}"

        val textColor = context?.let { Constants.getColor(sharePrefRepo.balance, it) }
        if (textColor != null) {
            getDeviceBinding.tvAvailableBalance.setTextColor(textColor)
        }

        getDeviceBinding.tvChargerName.text = response.User_Details.Device_Name
        getDeviceBinding.tvMaxPower.text = response.User_Details.Max_Power.toString() +" V"
        getDeviceBinding.tvPerUnitCharges.text =  getString(R.string.charges_per_unit, response.User_Details.ChargesPerUnit.toString())
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

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(loginResponse: LoginDataResponse) {
        Log.e("LoginResponseLogs", ":" + Gson().toJson(loginResponse))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {

    }
}

