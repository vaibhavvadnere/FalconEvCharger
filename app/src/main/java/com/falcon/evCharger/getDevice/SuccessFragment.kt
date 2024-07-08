package com.falcon.evCharger.getDevice

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.getDevice.viewModel.SuccessViewModel
import com.falcon.evCharger.response.StopChargingResponse
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.SuccessFragmentBinding
import com.iSay1.roamstick.data.model.request.StopChargingRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SuccessFragment : HomeBaseFragment() {

    private lateinit var successFragmentBinding: SuccessFragmentBinding

    private val successViewModel: SuccessViewModel by activityViewModels()

    val sharePrefRepo: SharePrefRepo = SharePrefRepo.getInstance()

    var vehicleType: String? = ""
    var vehicleNumber: String? = ""
    var deviceId: String? = ""

    private lateinit var confirmationDialog: Dialog

    enum class ViewOnClick {
        OK_CLICK, STOP_CHARGING
    }

    enum class UpdateEvent {
        SUCCESS, FAILED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        successFragmentBinding = SuccessFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { successViewModel.init(it) }

        successFragmentBinding.viewModel = successViewModel

        return successFragmentBinding.root
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vehicleNumber = arguments?.getString(Constants.VEHICLE_NO)
        deviceId = arguments?.getString(Constants.DEVICE_ID)
        vehicleType = arguments?.getString(Constants.VEHICLE_TYPE)

        val message = context?.getString(R.string.falcon_success_sub_header, vehicleType, vehicleNumber, deviceId)
        successFragmentBinding.tvSubHeader.text = message
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(viewOnClick: ViewOnClick) {
        when (viewOnClick) {
            ViewOnClick.OK_CLICK -> {

                mActivity?.navController?.navigate(R.id.action_ok_clicked)

            }

            ViewOnClick.STOP_CHARGING -> {

                val stopChargingRequest = StopChargingRequest()

                stopChargingRequest.Device_ID = deviceId
                stopChargingRequest.Vehicle_No = vehicleNumber
                stopChargingRequest.User_ID = sharePrefRepo.getString(Constants.USER_ID)

                Log.e("stopChargingRequestLog", " : " + stopChargingRequest);

                stopChargeClicked(stopChargingRequest)

            }

            else -> {

            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.FAILED -> {
                hideDialog()
            }

            else -> {

            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(stopChargingResponse: StopChargingResponse) {
        hideDialog()

        mActivity?.navController?.navigate(R.id.action_ok_clicked)
    }

    fun stopChargeClicked(stopChargingRequest: StopChargingRequest) {

        confirmationDialog = Dialog(mActivity!!, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        confirmationDialog.setContentView(R.layout.lay_stop_charging_confirm)
        val cancel = confirmationDialog.findViewById<TextView>(R.id.cancel_button)
        val delete = confirmationDialog.findViewById<TextView>(R.id.delete_location)

        delete.setOnClickListener {
            confirmationDialog.dismiss()

            showDialog()

            successViewModel.stopCharging(stopChargingRequest)

        }

        cancel.setOnClickListener { v: View? -> confirmationDialog.dismiss() }
        confirmationDialog.show()
    }

}