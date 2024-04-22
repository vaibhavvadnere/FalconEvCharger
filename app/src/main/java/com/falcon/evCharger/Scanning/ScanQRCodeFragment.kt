package com.falcon.evCharger.Scanning

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.login.viewModel.ScanQrCodeViewModel
import com.falcon.evCharger.response.GetDeviceResponse
import com.falcon.evcharger.R
import com.google.gson.Gson
import com.google.zxing.ResultPoint
import com.iSay1.roamstick.data.model.request.GetDeviceRequest
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ScanQRCodeFragment : HomeBaseFragment() {
    private lateinit var barcodeView: CompoundBarcodeView

    private val scanQrCodeViewModel: ScanQrCodeViewModel by activityViewModels()

    enum class UpdateEvent {
        SCAN_SUCCESS, SCAN_FAILED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scan_qr_code, container, false)
        mActivity?.let { scanQrCodeViewModel.init(it) }
        barcodeView = view.findViewById(R.id.barcode_scanner)
        return view
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    val qrCodeText = it.text.substringAfter("-", "")
                    Log.d("qr_code_value_log", ":) => $qrCodeText")

                    var getDeviceRequest: GetDeviceRequest = GetDeviceRequest()

                    showDialog()

                    getDeviceRequest.Device_ID = qrCodeText
                    scanQrCodeViewModel.getDevice(getDeviceRequest)


                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {}
        })
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }


    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(getDeviceResponse: GetDeviceResponse) {
        hideDialog()
        Log.e("getDeviceResponseLogs", ":" + Gson().toJson(getDeviceResponse))
        val bundle = Bundle()
        bundle.putParcelable(Constants.DEVICE_RESPONSE, getDeviceResponse)
        mActivity?.navController?.navigate(R.id.action_get_device,bundle)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.SCAN_FAILED -> {
                Log.e("UpdateEventLog", ":clicked  LOGIN_FAILED:")

                hideDialog()

                mActivity?.navController?.navigate(R.id.action_menu_in)
            }

            else -> {

            }
        }
}
}